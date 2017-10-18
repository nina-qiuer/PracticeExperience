<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC 
	"-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">  

<mapper namespace="{?app_dot_dir}{?app_id}.dao.sqlmap.imap.I{?ufirst_table_name}Map"> 
	<sql id="list">
		<include refid="tuniu.frm.core.IMapBase.fetchListBasic"/>
		<if test="dataLimitStart != null">
			limit #{dataLimitStart}
			<if test="dataLimitEnd != null">
				,#{dataLimitEnd}
			</if>
		</if>
	</sql>
	<select id="fetchList" parameterType="Map"  resultMap="listResultEntity">
		<include refid="list"/>
	</select>
	<resultMap id="listResultEntity" type="com.tuniu.gt.{?app_id}.entity.{?ufirst_table_name}Entity">  
		<!-- loop fieldMap -->
		<result property="{?standard_field_name}" column="{?field_name}" />
		<!-- end loop -->
	</resultMap>
	
	
	<select id="fetchListMap" parameterType="Map"  resultMap="listResultMap">
		<include refid="list"/>
	</select>
	<resultMap id="listResultMap" type="Map">
		<!-- loop fieldMap -->
		<result property="{?standard_field_name}" column="{?field_name}"/>
		<!-- end loop -->
	</resultMap>
	
	
	<select id="get" parameterType="Map"  resultMap="listResultEntity">
		<include refid="tuniu.frm.core.IMapBase.fetchListBasic"/>
		where id=#{id} limit 1
	</select>
	
	
	<sql id="insert">
		<include refid="tuniu.frm.core.IMapBase.insertBasic"/>
		(<!-- loop fieldMap -->
		  {?field_name}{?comma}
		 <!-- end loop -->
		) values  (
		<!-- loop fieldMap -->
		  #{e.{?standard_field_name}}{?comma} 
		 <!-- end loop -->
		)
	</sql>
		
	<insert id="insert" useGeneratedKeys="true" keyProperty="id" parameterType="Map" flushCache="true" statementType="PREPARED" > 
		<include refid="insert"/>
	</insert>
	
	<sql id="update">
		<include refid="tuniu.frm.core.IMapBase.updateBasic"/>
		<set>  
			<!-- loop fieldMap -->
				<if test="e.{?standard_field_name} != null">{?field_name}=#{e.{?standard_field_name}}{?comma}</if>
			<!-- end loop -->
		</set>
	    where id=#{e.id}
	</sql>	
	
	
	<update id="update" parameterType="Map" flushCache="true" statementType="PREPARED">
		<include refid="update"/>
	</update>
	
	
	<sql id="delete">
		<include refid="tuniu.frm.core.IMapBase.deleteBasic"/>
	</sql>
	
	<delete id="delete">
		<include refid="delete"/>
		  where id=#{id}
	</delete>
	
</mapper>