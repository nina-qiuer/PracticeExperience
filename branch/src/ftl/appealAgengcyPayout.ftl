<formExport version="2.0">
  <summary id="" name=""/>
  <definitions/>
  <values>
    <column name="申请单号">
      <value></value>
    </column>
    <column name="申请人">
      <value><![CDATA[${vo.appealerId?c}]]></value>
    </column>
    <column name="产品编号">
      <value><![CDATA[${vo.productId?c}]]></value>
    </column>
    <column name="产品名称">
      <value><![CDATA[${vo.productName}]]></value>
    </column>
    <column name="团期">
      <value><![CDATA[${vo.productDate}]]></value>
    </column>
    <column name="售后客服">
      <value><![CDATA[${vo.salerId?c}]]></value>
    </column>
    <column name="原因">
      <value><![CDATA[${vo.appealContent}]]></value>
    </column>
    <column name="品牌名">
      <value><![CDATA[${vo.companyName}]]></value>
    </column>
    <column name="供应商名称">
      <value><![CDATA[${vo.agencyName}]]></value>
    </column>
    <column name="供应商ID">
      <value><![CDATA[${vo.agencyId?c}]]></value>
    </column>
    <column name="是否对接NB">
      <value><![CDATA[${vo.agencyNbFlag}]]></value>
    </column>
    <column name="是否有质检报告">
      <value><![CDATA[${vo.haveQualityReport}]]></value>
    </column>
    <column name="质检报告">
      <value><![CDATA[${vo.qualityReportUrl}]]></value>
    </column>
    <column name="投诉单号">
      <value><![CDATA[${vo.complaintId?c}]]></value>
    </column>
    <column name="实担金额">
      <value><![CDATA[${vo.agencyPayout?c}]]></value>
    </column>
  </values>
  <subForms/>
</formExport>
