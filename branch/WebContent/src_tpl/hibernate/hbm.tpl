<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC
         "-//Hibernate/Hibernate Mapping DTD 3.0//EN" 
         "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd1">
<hibernate-mapping package="hbm">
	<class name="{ufirst_table_name}" table="{table_name}">
<!-- head end -->
		<id name="{field_name}" column="{field_name}" type="java.lang.{field_type}">
			<generator class="native"></generator>
		</id>
<!-- pk end -->
		<property name="{field_name}" type="java.lang.{field_type}" not-null="true">
			 <column name="{field_name}" />
	    </property>
<!-- body end -->
	</class>
</hibernate-mapping>