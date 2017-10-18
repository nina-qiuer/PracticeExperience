	<package name="{?app_id}{?middleline_dir_name}" extends="apply-default" namespace="/{?app_id}/action/{?dir_path}"> 
		<action name="*-*" class="{?app_id}_action{?module_underline_dir}-{1}" method="{2}">
			<result name="form">/WEB-INF/html/{?app_id}/{?module_dir}{1}_form.jsp</result> 
			<result name="list">/WEB-INF/html/{?app_id}/{?module_dir}{1}_list.jsp</result> 
			<!-- 支持用户自定义指定jsp页面 -->
			<result name="*">/WEB-INF/html/{?app_id}/{?module_dir}{1}_${jspTpl}.jsp</result>
		</action>
	</package>