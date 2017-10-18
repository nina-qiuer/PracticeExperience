<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>

<HEAD>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
    var options = { 
        //beforeSubmit:  check_form_submit,  // pre-submit callback 
        success:       success_function,  // post-submit callback 
    }; 
    $('#form').ajaxForm(options); 
});

function success_function() {
	//self.parent.easyDialog.close();
	parent.location.replace(parent.location.href);
}

</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" enctype="multipart/form-data" action="cc_email-add" onSubmit="">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="80">邮箱名称：</th>
		<td><input type="text"" name="entity.emailName" id="emailName"/></td>
	</tr>
	<tr>
		<th align="right" width="80">邮箱地址：</th>
		<td><input type="text"" name="entity.emailAddress" id="emailAddress" onblur=""/>@tuniu.com</td>
	</tr>
	<tr>
		<th align="right" width="80">条件配置：</th>
		<td>
			<table>
				<tr>
					<th>
						<input type="checkbox" name="entity.cfgList[0].compLevel" value="1" onclick="$('#level_1').toggle()"/> 一级
					</th>
					<td>
						<table id="level_1" style="display: none;">
							<tr>
								<td>
									<input type="checkbox" name="entity.cfgList[0].orderStateArr" value="出游前"/> 出游前　
									<input type="checkbox" name="entity.cfgList[0].orderStateArr" value="出游中"/> 出游中　　
									<input type="checkbox" name="entity.cfgList[0].orderStateArr" value="出游后"/> 出游后
								</td>
							</tr>
							<tr>
								<td>
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="网站"/> 网站　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="门市"/> 门市　　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="当地质检"/> 当地质检　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="来电投诉"/> 来电投诉　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="CS邮箱"/> CS邮箱　　
									<br>
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="回访"/> 回访　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="点评"/> 点评　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="旅游局"/> 旅游局　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="微博"/> 微博　　　　
									<input type="checkbox" name="entity.cfgList[0].comeFromArr" value="其他"/> 其他
								</td>
							</tr>
							<tr>
								<td>
									<input type="radio" name="entity.cfgList[0].isMedia" value="0" checked="checked"> 拒收媒体参与　　
									<input type="radio" name="entity.cfgList[0].isMedia" value="1"> 接收媒体参与
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>
						<input type="checkbox" name="entity.cfgList[1].compLevel" value="2" onclick="$('#level_2').toggle()"/> 二级
					</th>
					<td>
						<table id="level_2" style="display: none;">
							<tr>
								<td>
									<input type="checkbox" name="entity.cfgList[1].orderStateArr" value="出游前"/> 出游前　
									<input type="checkbox" name="entity.cfgList[1].orderStateArr" value="出游中"/> 出游中　　
									<input type="checkbox" name="entity.cfgList[1].orderStateArr" value="出游后"/> 出游后
								</td>
							</tr>
							<tr>
								<td>
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="网站"/> 网站　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="门市"/> 门市　　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="当地质检"/> 当地质检　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="来电投诉"/> 来电投诉　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="CS邮箱"/> CS邮箱　　
									<br>
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="回访"/> 回访　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="点评"/> 点评　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="旅游局"/> 旅游局　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="微博"/> 微博　　　　
									<input type="checkbox" name="entity.cfgList[1].comeFromArr" value="其他"/> 其他
								</td>
							</tr>
							<tr>
								<td>
									<input type="radio" name="entity.cfgList[1].isMedia" value="0" checked="checked"> 拒收媒体参与　　
									<input type="radio" name="entity.cfgList[1].isMedia" value="1"> 接收媒体参与
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>
						<input type="checkbox" name="entity.cfgList[2].compLevel" value="3" onclick="$('#level_3').toggle()"/> 三级
					</th>
					<td>
						<table id="level_3" style="display: none;">
							<tr>
								<td>
									<input type="checkbox" name="entity.cfgList[2].orderStateArr" value="出游前"/> 出游前　
									<input type="checkbox" name="entity.cfgList[2].orderStateArr" value="出游中"/> 出游中　　
									<input type="checkbox" name="entity.cfgList[2].orderStateArr" value="出游后"/> 出游后
								</td>
							</tr>
							<tr>
								<td>
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="网站"/> 网站　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="门市"/> 门市　　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="当地质检"/> 当地质检　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="来电投诉"/> 来电投诉　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="CS邮箱"/> CS邮箱　　
									<br>
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="回访"/> 回访　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="点评"/> 点评　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="旅游局"/> 旅游局　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="微博"/> 微博　　　　
									<input type="checkbox" name="entity.cfgList[2].comeFromArr" value="其他"/> 其他
								</td>
							</tr>
							<tr>
								<td>
									<input type="radio" name="entity.cfgList[2].isMedia" value="0" checked="checked"> 拒收媒体参与　　
									<input type="radio" name="entity.cfgList[2].isMedia" value="1"> 接收媒体参与
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<th></th>
		<td>
			<input type="submit" class="pd5" value="提交"> 
			<input type="button" class="pd5" value="重置" onclick="window.location.href='cc_email-toAdd'">
		</td>
	</tr>
</table>
</form>
</BODY>
