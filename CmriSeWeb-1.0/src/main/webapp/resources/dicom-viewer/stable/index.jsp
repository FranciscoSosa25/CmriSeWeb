<!DOCTYPE html><html lang="en"><head>
  <meta charset="utf-8">
  <title>CMRise Dicom Viewer</title>
 <base href="/CmriSeWeb/resources/dicom-viewer/stable/">
   <script type="text/javascript">
  <%
  	String dcmkey = request.getParameter("dcmkey");  
  %>
  var DCM_FILE_PATH = '<%="/CmriSeWeb/PreviewDICOM.dcm?dcmkey="+dcmkey%>';
  </script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
<!--   <link rel="icon" type="image/x-icon" href="favicon.ico"> -->
  <link rel="shortcut icon" type="image/png"
			href="<%=request.getContextPath()%>/resources/poseidon-layout/images/login/cmri-120.png" />
  <style type="text/css">@font-face{font-family:'Material Icons';font-style:normal;font-weight:400;src:url(https://fonts.gstatic.com/s/materialicons/v88/flUhRq6tzZclQEJ-Vdg-IuiaDsNa.woff) format('woff');}.material-icons{font-family:'Material Icons';font-weight:normal;font-style:normal;font-size:24px;line-height:1;letter-spacing:normal;text-transform:none;display:inline-block;white-space:nowrap;word-wrap:normal;direction:ltr;font-feature-settings:'liga';}@font-face{font-family:'Material Icons';font-style:normal;font-weight:400;src:url(https://fonts.gstatic.com/s/materialicons/v88/flUhRq6tzZclQEJ-Vdg-IuiaDsNcIhQ8tQ.woff2) format('woff2');}.material-icons{font-family:'Material Icons';font-weight:normal;font-style:normal;font-size:24px;line-height:1;letter-spacing:normal;text-transform:none;display:inline-block;white-space:nowrap;word-wrap:normal;direction:ltr;-webkit-font-feature-settings:'liga';-webkit-font-smoothing:antialiased;}</style>
  <link rel="manifest" href="manifest.json">
  <meta name="theme-color" content="#1976d2">
<style>.mat-body{font:400 14px/20px Roboto,Helvetica Neue,sans-serif;letter-spacing:normal;}.mat-app-background{background-color:#fafafa;color:rgba(0,0,0,.87);}body,html{height:100%;}body{margin:0;}</style><link rel="stylesheet" href="styles.3f4e3ce11390000df935.css" media="print" onload="this.media='all'"><noscript><link rel="stylesheet" href="styles.3f4e3ce11390000df935.css"></noscript></head>
<body class="mat-app-background mat-body">
  <app-root></app-root>
  <noscript>Please enable JavaScript to continue using this application.</noscript>
<%-- <script src="runtime-es2015.e6edef62d7b2529b375c.js?<%=System.currentTimeMillis()%>" type="module">
</script><script src="runtime-es5.e6edef62d7b2529b375c.js?<%=System.currentTimeMillis()%>" nomodule defer></script>
<script src="polyfills-es5.cd48edb440ee34ef3811.js" nomodule defer></script>
<script src="polyfills-es2015.32009232e88f6483382d.js" type="module">
</script><script src="main-es2015.af7af4e7b4e737663f17.js?<%=System.currentTimeMillis()%>" type="module">
</script><script src="main-es5.af7af4e7b4e737663f17.js?<%=System.currentTimeMillis()%>" nomodule defer></script> --%>

<script src="runtime-es2015.e6edef62d7b2529b375c.js?<%=System.currentTimeMillis()%>" type="module">
</script><script src="runtime-es5.e6edef62d7b2529b375c.js?<%=System.currentTimeMillis()%>" nomodule defer></script>
<script src="polyfills-es5.cd48edb440ee34ef3811.js" nomodule defer></script>
<script src="polyfills-es2015.32009232e88f6483382d.js" type="module"></script>
<script src="main-es2015.799746e493003b1abfa1.js?<%=System.currentTimeMillis()%>" type="module"></script>
<script src="main-es5.799746e493003b1abfa1.js?<%=System.currentTimeMillis()%>" nomodule defer></script>




</body></html>






