/**
 * 
 */
package com.tuniu.gt.complaint.service;

import java.util.Map;

import com.tuniu.gt.tsp.entity.TspResponseEntity;

/**
 * @author Jiang Sir
 *
 */
public interface ITspService {
    String BOH_NM_PRODUCTCONTROLLER_QUERYCOMMONPRODUCTINFOS = "BOH.NM.ProductController.queryCommonProductInfos";
    String BOH_NM_PRODUCTCONTROLLER_QUERYMAINROUTEID = "BOH.NM.ProductController.queryMainRouteId";
    String CFM_CONFIRM_CONFIRMQUERYCONTROLLER_QUERYVENDORINFO = "CFM.confirm.ConfirmQueryController.queryVendorInfo";
    String PLA_BAP_WEATHERCONTROLLER_ALARM = "PLA.BAP.WeatherController.alarm";
    String TOR_NM_TEAMGUIDECONTROLLER_BATCHQUERYGROUPANDGUIDEDETAIL = "TOR.NM.TeamGuideController.batchQueryGroupAndGuideDetail";
    String ICS_SELLINCOME_REFUNDCONTROLLER_QUERYREPENTITYBYID = "ICS.sellincome.RefundController.queryRepEntityById";
    String ICS_PURCHASEPAY_AGENCYREPARATIONSCONTROLLER_INSERTAGENCYREPARATIONS = "ICS.purchasepay.AgencyReparationsController.insertAgencyReparations";
    String ICS_SELLINCOME_REFUNDCONTROLLER_ADDREP = "ICS.sellincome.RefundController.addRep";
    String ICS_NB_SUPPLIERAGENCYCONTROLLER_FINDAGENCYINFO = "ICS.nb.SupplierAgencyController.findAgencyInfo";
    
    TspResponseEntity executeGet(String serviceName,Map<String,Object> requestData);
    TspResponseEntity executePost(String serviceName,Map<String,Object> requestData);
}
