<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉事宜</title>
</head>
<body>
<table class="datatable" width="100%">
  <tr>
    <th width="100" align="right">一级分类：</th>

    <td><table id="complaint_type_1" class="datatable" width="100%">
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox73" id="checkbox73" />
              住宿</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox74" id="checkbox74" />
              餐饮</label></td>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox75" id="checkbox75" />
              导游</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox76" id="checkbox76" />
              交通</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox77" id="checkbox77" />

              购物</label></td>
          <td width="20%"><label>
              <input type="checkbox" name="checkbox78" id="checkbox78" />
              行程安排</label></td>
        </tr>
        <tr>
          <td><label>
              <input type="checkbox" name="checkbox84" id="checkbox84" />

              产品</label></td>
          <td><label>
              <input type="checkbox" name="checkbox83" id="checkbox83" />
              产品人员</label></td>
          <td><label>
              <input type="checkbox" name="checkbox82" id="checkbox82" />
              呼入</label></td>

          <td><label>
              <input type="checkbox" name="checkbox81" id="checkbox81" />
              售前客服</label></td>
          <td><label>
              <input type="checkbox" name="checkbox80" id="checkbox80" />
              售中客服</label></td>
          <td><label>
              <input type="checkbox" name="checkbox79" id="checkbox79" />

              售后客服</label></td>
        </tr>
        <tr>
          <td><label>
              <input type="checkbox" name="checkbox85" id="checkbox85" />
              门市服务人员</label></td>
          <td><label>
              <input type="checkbox" name="checkbox86" id="checkbox86" />

              上门签约</label></td>
          <td><label>
              <input type="checkbox" name="checkbox87" id="checkbox87" />
              在线预订</label></td>
          <td><label>
              <input type="checkbox" name="checkbox88" id="checkbox88" />
              电话预订</label></td>

          <td><label>
              <input type="checkbox" name="checkbox89" id="checkbox89" />
              网上浏览</label></td>
          <td><label>
              <input type="checkbox" name="checkbox90" id="checkbox90" />
              其他</label></td>
        </tr>
      </table></td>

  </tr>
  <tr>
    <th align="right">二级分类：</th>
    <td id="complaint_type_2"><table class="datatable" style="display:none;" id="tab-0" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">住宿</span></td>
        </tr>
        <tr>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox" id="checkbox" />
              地理位置</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox2" id="checkbox2" />
              周边环境</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox3" id="checkbox3" />

              卫生条件</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox4" id="checkbox4" />
              硬件设施</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox5" id="checkbox5" />
              酒店服务</label></td>

          <td width="20%"><label>
              <input type="checkbox" name="checkbox6" id="checkbox6" />
              标准与预定不符</label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-1" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">餐饮</span></td>

        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox7" id="checkbox7" />
              口味不好</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox8" id="checkbox8" />
              分量不足</label></td>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox9" id="checkbox9" />
              卫生条件</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox10" id="checkbox10" />
              餐标不符</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox11" id="checkbox11" />

              用餐环境</label></td>
          <td width="20%"><label>
              <input type="checkbox" name="checkbox12" id="checkbox12" />
              服务意识</label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-2" width="100%">
        <tr>

          <td colspan="6"><span class="fb ml5">导游</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox13" id="checkbox13" />
              导游服务态度</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox14" id="checkbox14" />

              领队服务态度</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox15" id="checkbox15" />
              讲解服务</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox16" id="checkbox16" />
              缩减行程</label></td>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox17" id="checkbox17" />
              更换行程</label></td>
          <td width="20%"><label>
              <input type="checkbox" name="checkbox18" id="checkbox18" />
              导游迟到</label></td>
        </tr>
        <tr>

          <td><label>
              <input type="checkbox" name="checkbox19" id="checkbox19" />
              组织能力</label></td>
          <td><label>
              <input type="checkbox" name="checkbox20" id="checkbox20" />
              强迫购物</label></td>
          <td><label>
              <input type="checkbox" name="checkbox21" id="checkbox21" />

              导游不认识路</label></td>
          <td><label>
              <input type="checkbox" name="checkbox22" id="checkbox22" />
              擅自增加自费项目</label></td>
          <td><label>
              <input type="checkbox" name="checkbox23" id="checkbox23" />
              擅自增加购物店</label></td>

          <td><label>
              <input type="checkbox" name="checkbox24" id="checkbox24" />
              处理紧急问题的能力</label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-3" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">交通</span></td>

        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox25" id="checkbox25" />
              司机服务态度</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox26" id="checkbox26" />
              司机不认识路</label></td>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox28" id="checkbox28" />
              卫生条件</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox29" id="checkbox29" />
              车辆硬件条件</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox30" id="checkbox30" />

              司机迟到</label></td>
          <td width="20%"><label>
              <input type="checkbox" name="checkbox31" id="checkbox31" />
              司机推销</label></td>
        </tr>
        <tr>
          <td><label>
              <input type="checkbox" name="checkbox32" id="checkbox32" />

              无人接机</label></td>
          <td><label>
              <input type="checkbox" name="checkbox27" id="checkbox27" />
              无人送机</label></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>

        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-4" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">购物</span></td>
        </tr>
        <tr>
          <td width="16%"><label>

              <input type="checkbox" name="checkbox33" id="checkbox33" />
              购物时间长</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox34" id="checkbox34" />
              购物质量差</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox35" id="checkbox35" />
              价格高</label></td>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox36" id="checkbox36" />
              不正当营业</label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>

      </table>
      <table class="datatable" style="display:none;" id="tab-5" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">行程安排</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox37" id="checkbox37" />

              实际游览与预定不符</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox38" id="checkbox38" />
              游览时间与网站不符</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox39" id="checkbox39" />
              行程安排不合理</label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-6" width="100%">

        <tr>
          <td colspan="6"><span class="fb ml5">产品</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox40" id="checkbox40" />
              价格变动</label></td>
          <td width="16%"><label>

              <input type="checkbox" name="checkbox41" id="checkbox41" />
              网上呈现行程变动</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox42" id="checkbox42" />
              不成团</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox43" id="checkbox43" />
              描述夸大</label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-7" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">产品人员</span></td>

        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox44" id="checkbox44" />
              添加或编辑产品错漏</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox45" id="checkbox45" />
              出团通知书编辑错误</label></td>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox46" id="checkbox46" />
              订单操作</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox47" id="checkbox47" />
              与客服部之间的信息传达</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox48" id="checkbox48" />

              业务知识</label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-8" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">呼入</span></td>

        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox49" id="checkbox49" />
              服务意识</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox50" id="checkbox50" />
              业务知识</label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>

      </table>
      <table class="datatable" style="display:none;" id="tab-9" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">售前客服</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox51" id="checkbox51" />

              服务意识</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox52" id="checkbox52" />
              业务知识</label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-10" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">售中客服</span></td>

        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox54" id="checkbox54" />
              服务意识</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox53" id="checkbox53" />
              业务知识</label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>

      </table>
      <table class="datatable" style="display:none;" id="tab-11" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">售后客服</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox55" id="checkbox55" />

              服务意识</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox56" id="checkbox56" />
              业务知识</label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-12" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">门市服务人员</span></td>

        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox57" id="checkbox57" />
              服务意识</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox58" id="checkbox58" />
              业务知识</label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>

      </table>
      <table class="datatable" style="display:none;" id="tab-13" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">上门签约</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox60" id="checkbox60" />

              准时性</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox59" id="checkbox59" />
              服务意识</label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-14" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">在线预订</span></td>

        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox61" id="checkbox61" />
              预定流程繁琐</label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;

            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>
      </table>

      <table class="datatable" style="display:none;" id="tab-15" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">电话预订</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox62" id="checkbox62" />
              预定流程繁琐</label></td>

          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;

            </label></td>
        </tr>
      </table>
      <table class="datatable" style="display:none;" id="tab-16" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">网上浏览</span></td>
        </tr>
        <tr>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox63" id="checkbox63" />
              网站内容</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox64" id="checkbox64" />
              网站布局</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox65" id="checkbox65" />

              网页打开慢、打不开</label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="16%">&nbsp;
            </label></td>
          <td width="20%">&nbsp;
            </label></td>
        </tr>

      </table>
      <table class="datatable" style="display:none;" id="tab-17" width="100%">
        <tr>
          <td colspan="6"><span class="fb ml5">其他</span></td>
        </tr>
        <tr>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox66" id="checkbox66" />

              航班延误</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox67" id="checkbox67" />
              不可抗力</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox68" id="checkbox68" />
              发票</label></td>

          <td width="16%"><label>
              <input type="checkbox" name="checkbox69" id="checkbox69" />
              退款</label></td>
          <td width="16%"><label>
              <input type="checkbox" name="checkbox70" id="checkbox70" />
              退货</label></td>
          <td width="20%"><label>
              <input type="checkbox" name="checkbox71" id="checkbox71" />

              其它</label></td>
        </tr>
        <tr>
          <td colspan="6"><label>
              <input type="checkbox" name="checkbox72" id="checkbox72" />
              大病（怀孕、突发疾病（需手术）、传染病）（需隔离））</label></td>
        </tr>
      </table></td>

  </tr>
  <tr>
    <th align="right">投诉详情： </th>
    <td><table>
        <tr>
          <td><textarea name="textarea" id="textarea" cols="45" rows="2"></textarea></td>
          <td><input class="pd10" type="submit" name="button" id="button" value="保存" /></td>
        </tr>

      </table></td>
  </tr>
  <tr>
    <th align="right">投诉事宜详情：</th>
    <td><p>1、住宿 - 周边环境：周边环境太差； </p>
      <p>2、交通 - 司机服务态度：服务态度恶劣，还骂人；</p></td>
  </tr>

  <tr>
    <th align="right">备注：</th>
    <td><textarea name="textarea2" id="textarea2" cols="45" rows="4">用以填写其他相关与客人的核实情况，可空</textarea></td>
  </tr>
  <tr>
    <th>&nbsp;</th>
    <td><input class="pd5" type="submit" name="button2" id="button2" value="确定" /></td>
  </tr>

</table>
<script type="text/javascript">
$(function(){
	$('#complaint_type_1 input').each(function(i){
		$(this).click(function(){									   
			if ($(this).attr('checked') == true){
				$('#tab-'+i).show().css({'border-bottom':'#ccc dotted 1px'});
				$('#complaint_type_2 table').hover(function(){
					$(this).find('td').css({'background':'#ffffcc'});													
				},function(){
					$(this).find('td').css({'background':'none'});	
				});
			} else {
				$('#tab-'+i).hide();
			}
		});
	});	   
});
</script>
</body>
<%@include file="/WEB-INF/html/foot.jsp" %>