package bros.p.common.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.common.core.util.Base64Util;
import bros.common.core.util.ValidateUtil;
import bros.p.common.facade.service.IPShoppingCartServiceFacade;
import bros.provider.common.constants.CommonErrorCodeConstants;
import bros.provider.common.constants.CommonFormatCodeConstants;
import bros.provider.parent.bankmanage.mall.service.IMallInfoService;
import bros.provider.parent.bankmanage.shelf.service.IShelfGoodsInfoService;
import bros.provider.parent.cache.shop.cart.service.IShopCartEntityService;

/** 
 * @ClassName: PShoppingCartServiceFacadeImpl 
 * @Description: 购物车对外接口
 * @author pengxiangnan
 * @date 2016年9月8日 下午14:40:14 
 * @version 1.0 
 */
@Component("pshoppingCartServiceFacade")
public class PShoppingCartServiceFacadeImpl implements IPShoppingCartServiceFacade {
	
	private static final  Logger logger = LoggerFactory.getLogger(PShoppingCartServiceFacadeImpl.class);
	/**
	 * BIP通讯
	 */
	@Autowired
	private IHttpClientRouter   httpClientRouter;
	/**
	 * 货架商品信息服务
	 */
	@Autowired
	private IShelfGoodsInfoService shelfGoodsInfoService;
	/**
	 * 商城系统
	 */
	@Autowired
	private IMallInfoService mallInfoService;
	
	@Autowired
	private IShopCartEntityService shopCartEntityService;
    
	/**
	 * 查询促销
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000500")
	@Override
	public ResponseEntity queryDiscoverPromotions(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		try{
		Map<String, Object> sendMap  = new HashMap<String, Object>();
		String productStoreId = (String) bodyMap.get("productStoreId");
		//从报文头获取法人id、渠道编码
		String legalPersonId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String chlCode = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
		String branchNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);//机构号
		String mallCode = (String) bodyMap.get("productStoreId");//商城编码
		String goodStatus = (String) bodyMap.get("goodStatus");//商品状态00-展示 01-不展示
		String goodsType = (String) bodyMap.get("goodsType");//商品类型00-产品分类；01-产品服务；02-产品
		String goodsFlag = (String) bodyMap.get("goodsFlag");//商品上下架标志 00-上架 01-下架
		String shelfCode = "";//货架编码
		sendMap.put("productStoreId", productStoreId);
		//根据法人id、渠道、机构查询商城信息是否存在
		Map<String,Object> queryMallMap = new HashMap<String, Object>();
		queryMallMap.put("legalPersonId", legalPersonId);//法人id
		queryMallMap.put("chlCode", chlCode);//渠道编码
		queryMallMap.put("branchNo", branchNo);//机构号
		Map<String,Object> mallMap = new HashMap<String, Object>();
		mallMap = mallInfoService.queryMallInfoByChlCodeLegalIdBranchNoMethod(queryMallMap);
		
		if(mallMap != null){
			
		}else{
			branchNo = "";//机构号
		}
		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
		shelfGoodsList = shelfGoodsInfoService.queryShowShelfGoodsInfoMethod(chlCode,legalPersonId,branchNo,mallCode,goodStatus,goodsType,goodsFlag,shelfCode);
		
		
		List<Map<String,Object>> promotionsList = new ArrayList<Map<String,Object>>();
		Map<String, Object> resMap =   httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.QUERY_PROMOTION_CODE);
		List<Map<String,Object>> productStoreList = new ArrayList<Map<String,Object>>();
		productStoreList = (List<Map<String, Object>>) resMap.get("productPromos");
		for(int i=0;i<productStoreList.size();i++){
			List<Map<String,Object>> productIdList = (List<Map<String, Object>>) productStoreList.get(i).get("productIdList");
			for(int j = 0;j<productIdList.size();j++){
				for(int m=0;m<shelfGoodsList.size();m++){
					if((productIdList.get(j).get("productId")).equals(shelfGoodsList.get(m).get("goodsCode"))){	
						promotionsList.add(productStoreList.get(i));
					}
				}
			}
		}
		Map<String, Object> returnMap  = new HashMap<String, Object>();
		returnMap.put("promotionsList", promotionsList);
		ResponseEntity responseEntity = new ResponseEntity(returnMap);
		 return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0008, "查询促销失败", ex);
		}
	}
    
	/**
	 * 查询热销
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000501")
	@Override
	public ResponseEntity queryDiscoverHot(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
        try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
    		String productStoreId = (String) bodyMap.get("productStoreId");
    		sendMap.put("productStoreId", productStoreId);
    		List<Map<String,Object>> discoverHotList = new ArrayList<Map<String,Object>>();
    		//从报文头获取法人id、渠道编码
    		String legalPersonId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
    		String chlCode = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
    		String branchNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);//机构号
    		String mallCode = (String) bodyMap.get("productStoreId");//商城编码
    		String goodStatus = (String) bodyMap.get("goodStatus");//商品状态00-展示 01-不展示
    		String goodsType = (String) bodyMap.get("goodsType");//商品类型00-产品分类；01-产品服务；02-产品
    		String goodsFlag = (String) bodyMap.get("goodsFlag");//商品上下架标志 00-上架 01-下架
    		String shelfCode = "";//货架编码
    		sendMap.put("productStoreId", productStoreId);
    		//根据法人id、渠道、机构查询商城信息是否存在
    		Map<String,Object> queryMallMap = new HashMap<String, Object>();
    		queryMallMap.put("legalPersonId", legalPersonId);//法人id
    		queryMallMap.put("chlCode", chlCode);//渠道编码
    		queryMallMap.put("branchNo", branchNo);//机构号
    		Map<String,Object> mallMap = new HashMap<String, Object>();
    		mallMap = mallInfoService.queryMallInfoByChlCodeLegalIdBranchNoMethod(queryMallMap);
    		
    		if(mallMap != null){
    			
    		}else{
    			branchNo = "";//机构号
    		}
    		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
    		shelfGoodsList = shelfGoodsInfoService.queryShowShelfGoodsInfoMethod(chlCode,legalPersonId,branchNo,mallCode,goodStatus,goodsType,goodsFlag,shelfCode);
    		
    		Map<String, Object> resMap =   httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.QUERY_SELLING_CODE);
    		List<Map<String,Object>> bestSellingDetails = new ArrayList<Map<String,Object>>();
    		bestSellingDetails = (List<Map<String, Object>>) resMap.get("bestSellingDetails");
    		for(int i=0;i<bestSellingDetails.size();i++){	
    				for(int m=0;m<shelfGoodsList.size();m++){
    					if((bestSellingDetails.get(i).get("productId")).equals(shelfGoodsList.get(m).get("goodsCode"))){	
    						discoverHotList.add(bestSellingDetails.get(i));
    					}
    				}
    		}
    		Map<String, Object> returnMap  = new HashMap<String, Object>();
    		returnMap.put("discoverHotList", discoverHotList);
    		ResponseEntity responseEntity = new ResponseEntity(returnMap);
    		 return responseEntity;
        }catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0007, "查询热销失败", ex);
		}
		
	}
    
	/**
	 * 重新计算购物车
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000503")
	@Override
	public ResponseEntity calculateShoppingCartPrice(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
        	String productStoreId = (String) bodyMap.get("productStoreId");
        	String webSiteId = (String) bodyMap.get("webSiteId");
        	String currency = (String) bodyMap.get("currency");
        	String partyId = (String) bodyMap.get("partyId");
        	String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
    		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
    		String customerInfo = legalId+"_"+uniqueCstIdentity;
        	
        	sendMap.put("productStoreId", productStoreId);
        	sendMap.put("webSiteId",webSiteId);
        	sendMap.put("currency", currency);
        	sendMap.put("partyId", partyId);
        	
			List<Map<String, Object>> productItemList = (List<Map<String, Object>>) bodyMap.get("productItemList");
			List<Map<String, Object>> productItemListNew = new ArrayList<Map<String,Object>>();
			
        	for (Map<String, Object> map : productItemList) {
        		Map<String, Object> productItemMap  = new  HashMap<String, Object>();
				String productId =(String) map.get("productId");
				String prodCatalogId =(String) map.get("prodCatalogId");
				String configOptionId =(String) map.get("configOptionId");
				String quantity =(String) map.get("quantity");
				String cartItemId =(String) map.get("cartItemId");
				String selectedAmount =(String) map.get("selectedAmount");
				productItemMap.put("productId", productId);
				productItemMap.put("prodCatalogId",prodCatalogId);
				productItemMap.put("configOptionId", configOptionId);
				productItemMap.put("quantity", quantity);
				productItemMap.put("cartItemId", cartItemId);
				productItemMap.put("selectedAmount", selectedAmount);
				productItemListNew.add(productItemMap);
			}
        	sendMap.put("productItemList", productItemListNew);
        	
			Map<String, Object> resMap =   httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.SHOPPING_CALCULATION_CODE);
			List<Map<String, Object>> productPriceList =(List<Map<String, Object>>) resMap.get("productPriceList");
			List<Map<String, Object>>   batchList= new ArrayList<Map<String, Object>>();
			if(productPriceList!=null&&productPriceList.size()>0){
				for (Iterator<Map<String, Object>> iterator = productPriceList.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					Map<String, Object> isData = new HashMap<String, Object>();
					Map<String, Object> tradeData = new HashMap<String, Object>();
					String id = (String) map.get("cartItemId");
					String productAmount = (String) map.get("quantity");
					String displayPrice = (String) map.get("displayPrice");
					String otherAdjustments = (String) map.get("otherAdjustments");
					String displayItemSubTotal = (String) map.get("displayItemSubTotal");
					tradeData.put("displayPrice", displayPrice);
					tradeData.put("otherAdjustments", otherAdjustments);
					tradeData.put("displayItemSubTotal", displayItemSubTotal);
					isData.put("id", id);
					isData.put("productAmount", productAmount);
					isData.put("tradeData",tradeData);
					batchList.add(isData);
				}
			}
			List<Map<String, Object>> productList= shopCartEntityService.updateBatchRecordShopCartList(customerInfo, batchList);
			resMap.put("productList", productList);
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0009, "重新计算购物车失败", ex);
		}
		
	}
    
	/**
	 * 执行订单
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000502")
	@Override
	public ResponseEntity externalProcessOrder(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			
		try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
         	String productStoreId = (String) bodyMap.get("productStoreId");
        	String webSiteId = (String) bodyMap.get("webSiteId");
        	String currency = (String) bodyMap.get("currency");
        	String partyId = (String) bodyMap.get("partyId");
        	String shippingContactMechId = (String) bodyMap.get("shippingContactMechId");
        	String shippingMethod = (String) bodyMap.get("shippingMethod");
        	String paymentMethodId = (String) bodyMap.get("paymentMethodId");
        	
        	
        	sendMap.put("productStoreId", productStoreId);
        	sendMap.put("webSiteId",webSiteId);
        	sendMap.put("currency", currency);
        	sendMap.put("partyId", partyId);
        	sendMap.put("shippingContactMechId", shippingContactMechId);
        	sendMap.put("shippingMethod", shippingMethod);
        	sendMap.put("paymentMethodId", paymentMethodId);
        	
        	
			List<Map<String, Object>> productItemList = (List<Map<String, Object>>) bodyMap.get("productItemList");
			List<Map<String, Object>> productItemListNew = new ArrayList<Map<String,Object>>();
        	for (Map<String, Object> map : productItemList) {
        		Map<String, Object> productItemMap  = new  HashMap<String, Object>();
				String productId =(String) map.get("productId");
				String prodCatalogId =(String) map.get("prodCatalogId");
				String configOptionId =(String) map.get("configOptionId");
				String quantity =(String) map.get("quantity");
				JSONArray surveyInfo = JSONArray.fromObject(map.get("surveyInfo"));
				String selectedAmount =(String) map.get("selectedAmount");
				productItemMap.put("productId", productId);
				productItemMap.put("prodCatalogId",prodCatalogId);
				productItemMap.put("configOptionId", configOptionId);
				productItemMap.put("quantity", quantity);
				productItemMap.put("selectedAmount", selectedAmount);
				productItemMap.put("surveyInfo", Base64Util.encode(surveyInfo.toString().getBytes()));
				productItemListNew.add(productItemMap);
			}
        	sendMap.put("productItemList", productItemListNew);
        	
			Map<String, Object> resMap =   httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.ORDER_EXECUTE_CODE);
			String returnCode = (String) resMap.get("returnCode");
			String orderId =(String) resMap.get("orderId");
			if("00000000".equals(returnCode)){
				if(!ValidateUtil.isEmpty(orderId)){
					sendMap.clear();
					sendMap.put("orderId", orderId);
					resMap =   httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.ORDER_MAKESURE_CODE);
				}
			}
		
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0010, "执行订单失败", ex);
		}
	}
	
	/**
	 * 
	 * @Title: queryStoreListInfo 
	 * @Description: 查询店铺与客户支付工具和客户地址信息列表流程
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Validation(value="p0000504")
	@Override
	public ResponseEntity queryStoreListInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {

		try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
        	String partyId = (String) bodyMap.get("partyId");
        	String productId = (String) bodyMap.get("productId");
        	String productStoreId = (String) bodyMap.get("productStoreId");
    		sendMap.put("partyId", partyId);
    		sendMap.put("productId", productId);
    		sendMap.put("productStoreId", productStoreId);

			Map<String, Object> resMap =   httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.QUERY_SHOPCARTCSTINFO_CODE);
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0011, "查询店铺与客户支付工具和客户地址信息列表失败", ex);
		}
	}
	
	/**
	 * 
	 * @Title: queryProductListProcess 
	 * @Description: 查询产品列表流程
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Validation(value="p0000505")
	@Override
	public ResponseEntity queryProductListProcess(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {

		try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
    		String productStoreId = (String) bodyMap.get("productStoreId");
    		sendMap.put("productStoreId", productStoreId);
 
			Map<String, Object> resMap =  httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.QUERY_PRODUCTTABLE_CODE);
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0012, "查询产品列表流程失败", ex);
		}
	}
    
	/**
	 * 获取订单列表
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000506")
	@Override
	public ResponseEntity getOrdersList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			
		try{
			
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
        	
        	String orderId = (String) bodyMap.get("orderId");//订单标识
        	String orderName = (String) bodyMap.get("orderName");//订单名称
        	
        	List<Map<String, Object>> orderTypeIds = (List<Map<String, Object>>) bodyMap.get("orderTypeIds"); //订单类型标识列表
			List<Map<String, Object>> orderTypeIdsNew = new ArrayList<Map<String,Object>>();
			if(null!=orderTypeIds&&orderTypeIds.size()>0){
				for (Map<String, Object> map : orderTypeIds) {
	        		Map<String, Object> orderTypeIdtemMap  = new  HashMap<String, Object>();
					String orderTypeId =(String) map.get("orderTypeId");
					orderTypeIdtemMap.put("orderTypeId", orderTypeId);
					orderTypeIdsNew.add(orderTypeIdtemMap);
				}
			}
			sendMap.put("orderTypeIds", orderTypeIdsNew);
        	
        	List<Map<String, Object>> orderStatusIds = (List<Map<String, Object>>) bodyMap.get("orderStatusIds"); //订单状态标识
			List<Map<String, Object>> orderStatusIdsNew = new ArrayList<Map<String,Object>>();
			if(null!=orderStatusIds&&orderStatusIds.size()>0){
	        	for (Map<String, Object> map : orderStatusIds) {
	        		Map<String, Object> orderStatusIdstemMap  = new  HashMap<String, Object>();
					String orderStatusId =(String) map.get("orderStatusId");
					orderStatusIdstemMap.put("orderStatusId", orderStatusId);
					orderStatusIdsNew.add(orderStatusIdstemMap);
				}
			}
        	sendMap.put("orderStatusIds", orderStatusIdsNew);
     
        	List<Map<String, Object>> productStoreIds = (List<Map<String, Object>>) bodyMap.get("productStoreIds"); //产品店铺标识列表
			List<Map<String, Object>> productStoreIdsNew = new ArrayList<Map<String,Object>>();
			if(null!=productStoreIds&&productStoreIds.size()>0){
	        	for (Map<String, Object> map : productStoreIds) {
	        		Map<String, Object> productStoreIdstemMap  = new  HashMap<String, Object>();
					String productStoreId =(String) map.get("productStoreId");
					productStoreIdstemMap.put("productStoreId", productStoreId);
					productStoreIdsNew.add(productStoreIdstemMap);
				}
			}
        	sendMap.put("productStoreIds", productStoreIdsNew);
        	
        	List<Map<String, Object>> orderWebSiteIds = (List<Map<String, Object>>) bodyMap.get("orderWebSiteIds"); //产品店铺标识列表
			List<Map<String, Object>> orderWebSiteIdsNew = new ArrayList<Map<String,Object>>();
			if(null!=orderWebSiteIds&&orderWebSiteIds.size()>0){
	        	for (Map<String, Object> map : orderWebSiteIds) {
	        		Map<String, Object> orderWebSiteIdstemMap  = new  HashMap<String, Object>();
					String orderWebSiteId =(String) map.get("orderWebSiteId");
					orderWebSiteIdstemMap.put("orderWebSiteId", orderWebSiteId);
					orderWebSiteIdsNew.add(orderWebSiteIdstemMap);
				}
			}
        	sendMap.put("orderWebSiteIds", orderWebSiteIdsNew);
        	
        	List<Map<String, Object>> salesChannelEnumIds = (List<Map<String, Object>>) bodyMap.get("salesChannelEnumIds"); //销售渠道标识
			List<Map<String, Object>> salesChannelEnumIdsNew = new ArrayList<Map<String,Object>>();
			if(null!=salesChannelEnumIds&&salesChannelEnumIds.size()>0){
	        	for (Map<String, Object> map : salesChannelEnumIds) {
	        		Map<String, Object> salesChannelEnumIdstemMap  = new  HashMap<String, Object>();
					String salesChannelEnumId =(String) map.get("salesChannelEnumId");
					salesChannelEnumIdstemMap.put("salesChannelEnumId", salesChannelEnumId);
					salesChannelEnumIdsNew.add(salesChannelEnumIdstemMap);
				}
			}
        	sendMap.put("salesChannelEnumIds", salesChannelEnumIdsNew);
        					
        	String createdBy = (String) bodyMap.get("createdBy");//创建者
        	String terminalId = (String) bodyMap.get("terminalId");//终端标识
        	String transactionId = (String) bodyMap.get("transactionId");//事务标识
        	String externalId = (String) bodyMap.get("externalId");//外部标识
        	String internalCode = (String) bodyMap.get("internalCode");//内部代码
        	String useEntryDate = (String) bodyMap.get("useEntryDate");//使用订单日期
        	String minDate = (String) bodyMap.get("minDate");//开始日期
        	String maxDate = (String) bodyMap.get("maxDate");//终止日期
        					
        					
        	String hasBackOrders = (String) bodyMap.get("hasBackOrders");//包括缺货通知单
        	String userLoginId = (String) bodyMap.get("userLoginId");//用户登录标识
        	
        	List<Map<String, Object>> roleTypeIds = (List<Map<String, Object>>) bodyMap.get("roleTypeIds"); //角色类型标识
			List<Map<String, Object>> roleTypeIdsNew = new ArrayList<Map<String,Object>>();
			if(null!=roleTypeIds&&roleTypeIds.size()>0){
	        	for (Map<String, Object> map : roleTypeIds) {
	        		Map<String, Object> roleTypeIdstemMap  = new  HashMap<String, Object>();
					String roleTypeId =(String) map.get("roleTypeId");
					roleTypeIdstemMap.put("roleTypeId", roleTypeId);
					roleTypeIdsNew.add(roleTypeIdstemMap);
				}
			}
        	sendMap.put("roleTypeIds", roleTypeIdsNew);
        	
        	String partyId = (String) bodyMap.get("partyId");//客户标识
        	String correspondingPoId = (String) bodyMap.get("correspondingPoId");//客户购买订单号
        	
        	String subscriptionId = (String) bodyMap.get("subscriptionId");//订阅标识
        	String productId = (String) bodyMap.get("productId");//产品标识
        	String budgetId = (String) bodyMap.get("budgetId");//预算标识
        	String quoteId = (String) bodyMap.get("quoteId");//报价单标识
        	String goodIdentificationTypeId = (String) bodyMap.get("goodIdentificationTypeId");//产品类型标识
        	String goodIdentificationIdValue = (String) bodyMap.get("goodIdentificationIdValue");//产品标识
        	String billingAccountId = (String) bodyMap.get("billingAccountId");//账单账户
        	
        	String finAccountId = (String) bodyMap.get("finAccountId");//金融账户标识
        	String cardNumber = (String) bodyMap.get("cardNumber");//卡号
        	String accountNumber = (String) bodyMap.get("accountNumber");//客户号
        	String paymentStatusId = (String) bodyMap.get("paymentStatusId");//支付状态标识
        	String inventoryItemId = (String) bodyMap.get("inventoryItemId");//库存明细标识
        	String softIdentifier = (String) bodyMap.get("softIdentifier");//软标识
        	String serialNumber = (String) bodyMap.get("serialNumber");//序列号
        	String shipmentId = (String) bodyMap.get("shipmentId");//货运标识
        	String filterInventoryProblems = (String) bodyMap.get("filterInventoryProblems");//过滤器：库存问题
        	String filterPOsWithRejectedItems = (String) bodyMap.get("filterPOsWithRejectedItems");//过滤器: 采购订单有被拒绝的明细
        	String filterPOsOpenPastTheirETA = (String) bodyMap.get("filterPOsOpenPastTheirETA");//过滤器: 采购订单超过预期交货时间
        	
        	String filterPartiallyReceivedPOs = (String) bodyMap.get("filterPartiallyReceivedPOs");//过滤器: 采购订单部分收到货
        	String isViewed = (String) bodyMap.get("isViewed");//已浏览
        	String shipmentMethod = (String) bodyMap.get("shipmentMethod");//货运方式
        	String gatewayAvsResult = (String) bodyMap.get("gatewayAvsResult");//地址验证(AVS)
        	String gatewayScoreResult = (String) bodyMap.get("gatewayScoreResult");//记分
        	String countryGeoId = (String) bodyMap.get("countryGeoId");//货运到的国家标识
        	
        	
        	String includeCountry = (String) bodyMap.get("includeCountry");//包括国家
        	String viewIndex = (String) bodyMap.get("viewIndex");//页码
        	String viewSize = (String) bodyMap.get("viewSize");//一页的订单数
        	String showAll = (String) bodyMap.get("showAll");//显示全部
        					
        	sendMap.put("orderId",orderId);
        	sendMap.put("orderName",orderName);
        	sendMap.put("createdBy",createdBy);
        	sendMap.put("terminalId",terminalId);
        	sendMap.put("transactionId",transactionId);
        	sendMap.put("externalId",externalId);
        	sendMap.put("internalCode",internalCode);
        	sendMap.put("useEntryDate",useEntryDate);
        	sendMap.put("minDate",minDate);
        	sendMap.put("maxDate",maxDate);
        	sendMap.put("hasBackOrders",hasBackOrders);
        	sendMap.put("userLoginId",userLoginId);
        	sendMap.put("partyId",partyId);
        	sendMap.put("correspondingPoId",correspondingPoId);
        	sendMap.put("subscriptionId",subscriptionId);
        	sendMap.put("productId",productId);
        	sendMap.put("budgetId",budgetId);
        	sendMap.put("quoteId",quoteId);
        	sendMap.put("goodIdentificationTypeId",goodIdentificationTypeId);
        	sendMap.put("goodIdentificationIdValue",goodIdentificationIdValue);
        	sendMap.put("billingAccountId",billingAccountId);
        	sendMap.put("finAccountId",finAccountId);
        	sendMap.put("cardNumber",cardNumber);
        	sendMap.put("accountNumber",accountNumber);
        	sendMap.put("paymentStatusId",paymentStatusId);
        	sendMap.put("inventoryItemId",inventoryItemId);
        	sendMap.put("softIdentifier",softIdentifier);
        	sendMap.put("serialNumber",serialNumber);
        	sendMap.put("shipmentId",shipmentId);
        	sendMap.put("filterInventoryProblems",filterInventoryProblems);
        	sendMap.put("filterPOsWithRejectedItems",filterPOsWithRejectedItems);
        	sendMap.put("filterPOsOpenPastTheirETA",filterPOsOpenPastTheirETA);
        	sendMap.put("filterPartiallyReceivedPOs",filterPartiallyReceivedPOs);
        	sendMap.put("isViewed",isViewed);
        	sendMap.put("shipmentMethod",shipmentMethod);
        	sendMap.put("gatewayAvsResult",gatewayAvsResult);
        	sendMap.put("gatewayScoreResult",gatewayScoreResult);
        	sendMap.put("countryGeoId",countryGeoId);
        	sendMap.put("includeCountry",includeCountry);
        	sendMap.put("viewIndex",viewIndex);
        	sendMap.put("viewSize",viewSize);
        	sendMap.put("showAll",showAll);				
        					
        	
			Map<String, Object> resMap =  httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.ORDER_QUERYDTABLE_CODE);
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0013, "查询订单列表失败", ex);
		}
		
	}
    
     /**
      * 订单详情
      */
	@Validation(value="p0000507")
	@Override
	public ResponseEntity getOrderDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			
		try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
    		String partyId = (String) bodyMap.get("partyId"); //当事人编号
        	String orderId = (String) bodyMap.get("orderId"); //订单标识
        	String visitId = (String) bodyMap.get("visitId"); //流水标识
    		sendMap.put("partyId", partyId);
    		sendMap.put("orderId", orderId);
    		sendMap.put("visitId", visitId);
 
			Map<String, Object> resMap =  httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.ORDER_QUERYDETAIL_CODE);
	
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0014, "查询订单详情失败", ex);
		}
	}
	
	/**
     * 确认订单
     */
	@Validation(value="p0000508")
	@Override
	public ResponseEntity externalApprovedOrder(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
        	String orderId = (String) bodyMap.get("orderId"); //订单标识
    		sendMap.put("orderId", orderId);
 
			Map<String, Object> resMap =  httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.ORDER_MAKESURE_CODE);
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0015, "订单确认失败", ex);
		}
	}
	
    /**
     * 查询报价列表
     */
	//@Validation(value="p0000508")
	@Override
	public ResponseEntity queryQuotedPrice(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			
		try{
       	Map<String, Object> sendMap  = new HashMap<String, Object>();
   		String partyId = (String) bodyMap.get("partyId"); //当事人编号
       	String orderId = (String) bodyMap.get("orderId"); //订单标识
       	String visitId = (String) bodyMap.get("visitId"); //流水标识
   		sendMap.put("partyId", partyId);
   		sendMap.put("orderId", orderId);
   		sendMap.put("visitId", visitId);

			Map<String, Object> resMap =  httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.QUERYQUOTEDPRICE_CODE);
	
   		ResponseEntity responseEntity = new ResponseEntity(resMap);
   		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0018, "查询报价列表失败", ex);
		}
	}
    
	/**
     * 获取调查信息
     */
	@Validation(value="p0000509")
	@Override
	public ResponseEntity getSurveyQuestion(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
        	Map<String, Object> sendMap  = new HashMap<String, Object>();
        	String configOptionId = (String) bodyMap.get("configOptionId"); //订单标识
        	String productId = (String) bodyMap.get("productId"); //调查信息
    		sendMap.put("configOptionId", configOptionId);
    		sendMap.put("productId", productId);
 
			Map<String, Object> resMap =  httpClientRouter.send(headMap, sendMap, CommonFormatCodeConstants.QUERY_SEARCHINFO_CODE);
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0016, "获取调查信息", ex);
		}
	}
    
	
	
	/**
     * 需求提交
     */
//	@Validation(value="p0000509")
	@Override
	public ResponseEntity createShoppingListService(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			Map<String, Object> resMap =  httpClientRouter.send(headMap, bodyMap, CommonFormatCodeConstants.DEMAND_SUBMIT_CODE);
    		ResponseEntity responseEntity = new ResponseEntity(resMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0017, "需求提交失败", ex);
		}
	}
    
	/**
     * 校验商品是否上架
     */
	@Validation(value="p0000510")
	@Override
	public ResponseEntity checkShowShelfGoodsInfoMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String legalPersonId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
    		String chlCode = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
    		String branchNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);//机构号
    		String mallCode = (String) bodyMap.get("productStoreId");//商城编码
    		String productId = (String) bodyMap.get("productId"); //产品信息
    		String goodStatus = (String) bodyMap.get("goodStatus");//商品状态00-展示 01-不展示
    		String goodsType = (String) bodyMap.get("goodsType");//商品类型00-产品分类；01-产品服务；02-产品
    		String goodsFlag = (String) bodyMap.get("goodsFlag");//商品上下架标志 00-上架 01-下架
    		String shelfCode = "";//货架编码
    		//根据法人id、渠道、机构查询商城信息是否存在
    		Map<String,Object> queryMallMap = new HashMap<String, Object>();
    		queryMallMap.put("legalPersonId", legalPersonId);//法人id
    		queryMallMap.put("chlCode", chlCode);//渠道编码
    		queryMallMap.put("branchNo", branchNo);//机构号
    		Map<String,Object> mallMap = new HashMap<String, Object>();
    		mallMap = mallInfoService.queryMallInfoByChlCodeLegalIdBranchNoMethod(queryMallMap);
    		if(mallMap != null){
    		}else{
    			branchNo = "";//机构号
    		}
    		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
    		Boolean flag=false;
    		shelfGoodsList = shelfGoodsInfoService.queryShowShelfGoodsInfoMethod(chlCode,legalPersonId,branchNo,mallCode,goodStatus,goodsType,goodsFlag,shelfCode);
    		if(shelfGoodsList!=null&&shelfGoodsList.size()>0){
    			for (Map<String, Object> map : shelfGoodsList) {
    				String goodsCode = (String) map.get("goodsCode");
    				if(productId.equals(goodsCode)){
    					flag=true;
    					break;
    				}
				}
    		}
    		Map<String, Object> returnMap  = new HashMap<String, Object>();
    		returnMap.put("flag", flag);
    		ResponseEntity responseEntity = new ResponseEntity(returnMap);
    		return responseEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(CommonErrorCodeConstants.PCON0019, "校验产品上架失败", ex);
		}
	}


}
