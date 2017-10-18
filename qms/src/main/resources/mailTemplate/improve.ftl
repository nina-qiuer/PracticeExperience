<formExport version="2.0">
  <summary id="" name=""/>
  <definitions/>
  <values>
    <column name="申请单号">
      <value></value>
    </column>
    <column name="投诉单号">
      <value><![CDATA[${cmpId?c}]]></value>
    </column>
    <column name="订单号">
    	<#if ordId??><value><![CDATA[${ordId?c}]]></value><#else><value></value></#if>
    </column>
    <column name="供应商">
    	<#if agencyName??><value><![CDATA[${agencyName}]]></value><#else><value></value></#if>
    </column>
     <column name="线路编号">
      <#if prdId??><value><![CDATA[${prdId?c}]]></value><#else><value></value></#if>
    </column>
    <column name="线路名称">
    	<#if routeName??><value><![CDATA[${routeName}]]></value><#else><value></value></#if>
    </column>
     <column name="产品专员">
      <#if producter??><value><![CDATA[${producter}]]></value><#else><value></value></#if>
    </column>
    <column name="产品经理">
      <#if productLeader??><value><![CDATA[${productLeader}]]></value><#else><value></value></#if>
    </column>
    <column name="高级产品经理">
      <#if productManager??><value><![CDATA[${productManager}]]></value><#else><value></value></#if>
    </column>
    <column name="售后客服">
      <#if customer??><value><![CDATA[${customer}]]></value><#else><value></value></#if>
    </column>
    <column name="客服经理">
      <#if customerLeader??><value><![CDATA[${customerLeader}]]></value><#else><value></value></#if>
    </column>
    <column name="高级客服经理">
      <#if customerManager??><value><![CDATA[${customerManager}]]></value><#else><value></value></#if>
    </column>
    <column name="投诉事宜">
      <value><![CDATA[${cmpAffair}]]></value>
    </column>
    <column name="改进点">
      <value><![CDATA[${improvePoint}]]></value>
    </column>
    <column name="申请人">
      <value><![CDATA[${impPersonId?c}]]></value>
    </column>
	   <column name="上传附件">
	   <#if attach??><value><![CDATA[${attach}]]></value><#else><value></value></#if>
    </column>
	<column name="改进报告单号">
      <value><![CDATA[${impId?c}]]></value>
    </column>
  </values>
  <subForms/>
</formExport>
