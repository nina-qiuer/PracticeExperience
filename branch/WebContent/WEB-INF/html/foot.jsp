<%@ page  contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>	
	<div id="debug_info">
		<div style="align-center">struts启动时间:${session.debug.dispatch - session.debug.start} ms</div>	
		<div style="align-center">action执行时间:${session.debug.action - session.debug.dispatch} ms</div>
		<%
		request.setAttribute("now",  System.currentTimeMillis());
		%>
		<div style="align-center">c标签解析时间${now-session.debug.action} ms</div>  
	</div>
	</body>
</html>