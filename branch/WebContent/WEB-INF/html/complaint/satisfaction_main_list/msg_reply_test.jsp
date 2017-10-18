<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>

<script>
</script>
</HEAD>	
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">客服满意度管理</a>&gt;&gt;<span class="top_crumbs_txt">短信回复测试</span></div>
	<form name="form2" id="form2" method="post" onSubmit=""
		enctype="multipart/form-data" action="msgReplyTest-insertRecord">

		<table>
			<tr>
				<td>电话号码： <input type="text" size="10" name="tel" value=" "/></td>
			</tr>
			<tr>
				<td>电话号码必须是在表ct_sign_satisfaction存在的订单联系人电话</td>
			</tr>
			<tr>
				<td>短信回复内容： <input type="text" size="10" name="msg" value="" /> </td>
			</tr>
			<tr>
				<td>尊敬的XXX，感谢您订购途牛旅游产品，诚邀您对门市/上门签约人员的服务进行点评，1非常满意，2满意，3一般，4不满意，回复数字加点评意见即可 </td>
			</tr>
			<tr>
				<td><input type="submit" size="10" name="btn"  id="btn"  value="测试"  /> </td>
			</tr>
		</table>
 	</form>
</BODY>
