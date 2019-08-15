<%--
  Created by IntelliJ IDEA.
  User: tang
  Date: 2019/8/4
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name=viewport content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no,minimal-ui">
  <meta name="referrer" content="no-referrer">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <title>${empty requestScope.title == false? requestScope.title : "虎牙直播"}</title>
  <!-- Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
  <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
  <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

  <link href="https://a.msstatic.com/huya/main3/assets/css/header_8389f.css" rel="stylesheet">

  <link href="https://a.msstatic.com/huya/main3/app/room_normal_23f63.css" rel="stylesheet">

    <style type="text/css">
    html, body {width:100%;height:100%;margin:auto;overflow: hidden;}
  </style>
</head>
<body>
<h1>${requestScope.msg}</h1>
<form action="${pageContext.request.contextPath}/index" method="post">
  <label for="basic-url">请输入虎牙直播房间地址</label>
  <div class="input-group">
    <span class="input-group-addon" id="basic-addon3">https://www.huya.com/</span>
    <input type="text" name="room" class="form-control" id="basic-url" aria-describedoby="basic-addon3">
  </div>
  <input value="走你！Go~" type="submit" class="btn btn-default">
</form>
${requestScope.roomHeader}
<div class="room-player-main" id="J_playerMain">
  <div class="room-player-gift-placeholder"></div>
  <div class="room-player-layer" id="J_roomPlayerLayer"></div>
  <div class="room-player" id="liveRoomObj"></div>
</div>
${requestScope.roomRight}
${requestScope.jsCode}
<%--<div id="mse"></div>--%>
<%--<script src="//cdn.jsdelivr.net/npm/xgplayer@1.1.4/browser/index.js" charset="utf-8"></script>--%>
<%--<script src="//cdn.jsdelivr.net/npm/xgplayer-flv.js/browser/index.js" charset="utf-8"></script><script>--%>
<%--  let player = new FlvJsPlayer({--%>
<%--    "id": "mse",--%>
<%--    "url": "${requestScope.urls[0][1]}",--%>
<%--    "playsinline": true,--%>
<%--    "whitelist": [--%>
<%--      ""--%>
<%--    ],--%>
<%--    "width": "1000",--%>
<%--    "height": "565",--%>
<%--    "autoplay": true--%>
<%--  });--%>
<%--  player.emit('resourceReady', [--%>
<%--    {"name":"${requestScope.urls[0][0]}","url":"${requestScope.urls[0][1]}"}--%>
<%--  ,{"name":"${requestScope.urls[1][0]}","url":"${requestScope.urls[1][1]}"}--%>
<%--  ,{"name":"${requestScope.urls[2][0]}","url":"${requestScope.urls[2][1]}"}--%>
<%--  ,{"name":"${requestScope.urls[3][0]}","url":"${requestScope.urls[3][1]}"}--%>
<%--  ,{"name":"${requestScope.urls[4][0]}","url":"${requestScope.urls[4][1]}"}--%>
<%--  ]);--%>
<%--</script>--%>

</body>
</html>
