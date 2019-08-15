
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/index")
public class HttpTest extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String room =  req.getParameter("room");
        if (room!= null) req.getSession().setAttribute("room", room);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(req.getSession().getAttribute("room"));
        HttpGet httpGet = new HttpGet("https://www.huya.com/" + req.getSession().getAttribute("room"));
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        httpGet.setHeader("cookie", "vplayer_sbanner_1259515661837_1259515661837=1; __yamid_tt1=0.8625208674820293; __yamid_new=C8656DFD31400001444AD5C01BF9138C; alphaValue=0.80; guid=0e74b0691178a75ce63e320d330d1a8c; udb_guiddata=b826cb301b334795b115673431a5548f; SoundValue=1.00; Hm_lvt_51700b6c722f5bb4cf39906a596ea41f=1561714576,1561978656,1562155371,1562240699; videoBitRate=2000; udb_accdata=08615860363372; h_unt=1564813558; PHPSESSID=fi8gs1261oh7hc9cm0gknji6b0; udb_passdata=3; __yasmid=0.8625208674820293; _yasids=__rootsid%3DC88C37E736E0000150F8D15016361020; isInLiveRoom=true");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String text = EntityUtils.toString(entity, "UTF-8");
            Pattern hyPlayerConfig_p = Pattern.compile("var hyPlayerConfig = (\\{[\\s\\S]*?});");
            Pattern state_p = Pattern.compile("\"state\":\"([A-Z]+)");
            Pattern title_p = Pattern.compile("<title>([\\s\\S]*)</title>");
            Pattern roomHeader_p = Pattern.compile("(<div class=\"room-hd\" id=\"J_roomHeader\">[\\s\\S]+</div>)[\\s]+<div class=\"room-player-wrap\">");
            Pattern roomRight_p = Pattern.compile("(<div class=\"room-core-r\">[\\s\\S]+</div>)<!-- /room-core-r -->");
            Pattern jsCode_p = Pattern.compile("(<script src=\"http[\\s\\S]+)</body>");
            Matcher title = title_p.matcher(text);
            if(title.find()){
                req.setAttribute("title", title.group(1));
            }

            Matcher roomHeader = roomHeader_p.matcher(text);
            if(roomHeader.find()){
                req.setAttribute("roomHeader", roomHeader.group(1));
            }

            Matcher roomRight = roomRight_p.matcher(text);
            if(roomRight.find()){
                req.setAttribute("roomRight", roomRight.group(1));
            }

            Matcher jsCode = jsCode_p.matcher(text);
            if(jsCode.find()){
                req.setAttribute("jsCode",jsCode.group(1));
            }

            Matcher state = state_p.matcher(text);
            if (state.find()) {
                if(state.group(1).equals("OFF")){
                    req.setAttribute("msg","房间当前未开播");
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }else if(state.group(1).equals("REPLAY")){
                    req.setAttribute("msg","房间当前处于重播状态，不能解析流数据");
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            }

            Matcher hyPlayerConfig = hyPlayerConfig_p.matcher(text);
            if (hyPlayerConfig.find()) {
                JSONObject jsonObject = JSON.parseObject(hyPlayerConfig.group(1));
                JSONObject dataJson = jsonObject.getJSONObject("stream").getJSONArray("data").getJSONObject(0);
                JSONArray gameStreamInfoList = dataJson.getJSONArray("gameStreamInfoList");
                String url = "";
                for (int i = 0; i < gameStreamInfoList.size(); i++) {
                    JSONObject item = gameStreamInfoList.getJSONObject(i);
                    if (item.getString("sCdnType").equals("AL")) {
                        url = item.getString("sFlvUrl") + "/" + item.getString("sStreamName") + "."
                                + item.getString("sFlvUrlSuffix") + "?" + item.getString("sFlvAntiCode");
                        break;
                    }
                }

                url = url.replace("amp;","");
                JSONArray vMultiStreamInfo = jsonObject.getJSONObject("stream").getJSONArray("vMultiStreamInfo");
                List<String[]> urls= new ArrayList<>();
                for (int i = 0; i < vMultiStreamInfo.size(); i++){
                    JSONObject item = vMultiStreamInfo.getJSONObject(i);
                    String absUrl = "";
                    if (item.getInteger("iBitRate")>0){
                        absUrl = url + "&ratio=" + item.getString("iBitRate");
                    }else {
                        absUrl = url;
                    }
                    String name = item.getString("sDisplayName");
                    urls.add(new String[]{name, absUrl});
                }
                req.setAttribute("urls",urls);
            }
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
