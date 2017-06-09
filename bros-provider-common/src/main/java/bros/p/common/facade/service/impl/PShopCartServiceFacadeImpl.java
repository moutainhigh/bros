package bros.p.common.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.p.common.facade.service.IPShopCartServiceFacade;
import bros.provider.common.constants.CommonErrorCodeConstants;
import bros.provider.parent.cache.constants.ShopCartDefinitionConstants;
import bros.provider.parent.cache.shop.cart.service.IShopCartEntityService;
/**
 * 
 * @ClassName: PShopCartServiceFacadeImpl 
 * @Description: 购物车缓存对外暴露服务接口实现
 * @author 何鹏
 * @date 2016年8月24日 上午9:30:37 
 * @version 1.0
 */
@Component(value="pshopCartServiceFacade")
public class PShopCartServiceFacadeImpl implements IPShopCartServiceFacade {

	@Autowired
	private IShopCartEntityService shopCartEntityService;
	/**
	 * 
	 * @Title: addRecordShopCart 
	 * @Description: 添加某条记录到购物车中
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000205")
	@Override
	public ResponseEntity addRecordShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
		String customerInfo = legalId+"_"+uniqueCstIdentity;
		Map<String,Object> map = (Map<String, Object>) bodyMap.get("recordMap");
		List<Map<String,Object>> list = shopCartEntityService.addRecordShopCart(customerInfo, map);
		Map<String,Object> responseMap = new HashMap<String, Object>();
		responseMap.put("recordList", list);
		return new ResponseEntity(responseMap);
	}

	/**
	 * 
	 * @Title: mergeRecordShopCart 
	 * @Description: 合并购物车记录并返回合并后的记录（登录的时候使用），金融类的购物车不存在合并的情况，输入要素需要进行录入，每次录入的情况，都需要新增购物车，只是记录需要重新排序
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000206")
	@Override
	public ResponseEntity mergeRecordShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
		String customerInfo = legalId+"_"+uniqueCstIdentity;
		List<Map<String,Object>> listTemp = (List<Map<String, Object>>) (bodyMap.get("recordList")==null?new ArrayList<Map<String,Object>>():bodyMap.get("recordList"));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(listTemp != null && listTemp.size()>0){//校验数据的必输项是否合法
			for(Map<String,Object> map : listTemp){
				String recordId = (String) (map.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_RECORD_ID));
				String amount = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT));
				String des = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES));
				String name = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME));
				String price = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE));
				String productId = (String) (map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID));
				Map<String,Object> tradeData = (Map<String, Object>) (map.get(ShopCartDefinitionConstants.CART_TRADE_DATA)==null?new HashMap<String, Object>():map.get(ShopCartDefinitionConstants.CART_TRADE_DATA));
				
				if(ValidateUtil.isEmpty(amount)){
					throw new ServiceException(CommonErrorCodeConstants.PCON0002,"productAmount不能为空");
				}
				if(ValidateUtil.isEmpty(des)){
					throw new ServiceException(CommonErrorCodeConstants.PCON0003,"productDes不能为空");
				}
				if(ValidateUtil.isEmpty(name)){
					throw new ServiceException(CommonErrorCodeConstants.PCON0004,"productName不能为空");
				}
				if(ValidateUtil.isEmpty(price)){
					throw new ServiceException(CommonErrorCodeConstants.PCON0005,"productPrice不能为空");
				}
				if(ValidateUtil.isEmpty(productId)){
					throw new ServiceException(CommonErrorCodeConstants.PCON0006,"productId不能为空");
				}
				
				if(ValidateUtil.isEmpty(recordId)){
					Map<String,Object> record = new HashMap<String, Object>();
					record.put(ShopCartDefinitionConstants.CART_RECORD_ID, recordId);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT, amount);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_DES, des);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_NAME, name);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_PRICE, price);
					record.put(ShopCartDefinitionConstants.CART_PROFUCT_ID, productId);
					record.put(ShopCartDefinitionConstants.CART_TRADE_DATA, tradeData);
					list.add(record);
				}else{
					list.add(map);
				}
			}
			
		}
		
		List<Map<String,Object>>  resultList = shopCartEntityService.mergeRecordShopCart(customerInfo, list);
		Map<String,Object> responseMap = new HashMap<String, Object>();
		responseMap.put("recordList", resultList);
		return new ResponseEntity(responseMap);
	}

	/**
	 * 
	 * @Title: updateRecordShopCart 
	 * @Description: 更新购物车的某条记录，并返回更新之后的购物车记录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000207")
	@Override
	public ResponseEntity updateRecordShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
		String customerInfo = legalId+"_"+uniqueCstIdentity;
		Map<String,Object> map = (Map<String, Object>) bodyMap.get("recordMap");
		List<Map<String,Object>> list = shopCartEntityService.updateRecordShopCart(customerInfo, map);
		Map<String,Object> responseMap = new HashMap<String, Object>();
		responseMap.put("recordList", list);
		return new ResponseEntity(responseMap);
	}

	/**
	 * 
	 * @Title: queryShopCart 
	 * @Description: 查询购物车信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000206")
	@Override
	public ResponseEntity queryShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
		String customerInfo = legalId+"_"+uniqueCstIdentity;
		List<Map<String,Object>> list = shopCartEntityService.queryShopCart(customerInfo);
		Map<String,Object> responseMap = new HashMap<String, Object>();
		responseMap.put("recordList", list);
		return new ResponseEntity(responseMap);
	}

	/**
	 * 
	 * @Title: deleteShopCartRecord 
	 * @Description: 删除购物车记录并返回购物车记录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000209")
	@Override
	public ResponseEntity deleteShopCartRecord(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
		String customerInfo = legalId+"_"+uniqueCstIdentity;
		List<String> list = (List<String>) bodyMap.get("recordList");
		List<Map<String,Object>> listResult = shopCartEntityService.deleteShopCartRecord(customerInfo, list);
		Map<String,Object> responseMap = new HashMap<String, Object>();
		responseMap.put("recordList", listResult);
		return new ResponseEntity(responseMap);
	}

	/**
	 * 
	 * @Title: deleteShopCartRecord 
	 * @Description: 清除客户购物车信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000206")
	@Override
	public ResponseEntity clearShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
		String customerInfo = legalId+"_"+uniqueCstIdentity;
		shopCartEntityService.clearShopCart(customerInfo);
		return new ResponseEntity();
	}
    
	/**
	 * 
	 * @Title: updateBatchRecordShopCart 
	 * @Description: 清除客户购物车信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000222")
	@Override
	public ResponseEntity updateBatchRecordShopCart(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String uniqueCstIdentity = (String) (bodyMap.get("uniqueCstIdentity")==null?"":bodyMap.get("uniqueCstIdentity"));//客户唯一标识
		String customerInfo = legalId+"_"+uniqueCstIdentity;
		List<String> list = (List<String>) bodyMap.get("recordList");
		List<Map<String,Object>> listResult = shopCartEntityService.updateBatchRecordShopCart(customerInfo, list);
		Map<String,Object> responseMap = new HashMap<String, Object>();
		responseMap.put("recordList", listResult);
		return new ResponseEntity(responseMap);
	}
	
}
