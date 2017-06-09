package bros.provider.parent.cache.shop.cart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;
import bros.common.core.redis.IRedisDao;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.TransferCacheObjectUtil;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.cache.constants.CacheErrorCodeConstants;
import bros.provider.parent.cache.constants.ShopCartDefinitionConstants;
import bros.provider.parent.cache.shop.cart.service.IShopCartEntityService;

/**
 * 
 * @ClassName: ShopCartEntityServiceImpl 
 * @Description: 购物车缓存接口实现
 * @author 何鹏
 * @date 2016年8月24日 上午9:38:53 
 * @version 1.0
 */
public class ShopCartEntityServiceImpl implements IShopCartEntityService {
	/**
	 * 购物车主键前缀
	 */
	private String prefix;
	/**
	 * 购物车缓存操作
	 */
	private IRedisDao shopCartRedisDao; 
	/**
	 * 失效时间   默认一个星期
	 */
	private int expire=7;
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public IRedisDao getShopCartRedisDao() {
		return shopCartRedisDao;
	}
	public void setShopCartRedisDao(IRedisDao shopCartRedisDao) {
		this.shopCartRedisDao = shopCartRedisDao;
	}
	public int getExpire() {
		return expire;
	}
	public void setExpire(int expire) {
		this.expire = expire;
	}
	
	/**
	 * 
	 * @Title: addRecordShopCart 
	 * @Description: 添加某条记录到购物车中
	 * @param customerInfo 客户唯一标识
	 * @param map  需要添加的记录
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> addRecordShopCart(String customerInfo,Map<String, Object> map) throws ServiceException {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String key = prefix+"_"+customerInfo;
		Object obj = shopCartRedisDao.get(key);
		int trueExpire =  1000*60*60*24*expire;
		List<Map<String,Object>> resultList = TransferCacheObjectUtil.getListMapFromObject(obj);
		if(resultList == null){
			resultList = new ArrayList<Map<String,Object>>();
		}
		if(map == null || map.size()<=0){
			result = resultList;
		}else{
			//判断uuid是否存在，如果不存在的化，系统给生成，存在的化系统不生成
			String recordId = (String) (map.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_RECORD_ID));
			if(ValidateUtil.isEmpty(recordId)){
				recordId = BaseUtil.createUUID();
				map.put(ShopCartDefinitionConstants.CART_RECORD_ID, recordId);
			}
			result.add(map);
			result.addAll(resultList);
			shopCartRedisDao.set(key, result, trueExpire);
		}
		
		return result;
	}

	/**
	 * 
	 * @Title: mergeRecordShopCart 
	 * @Description: 合并购物车记录并返回合并后的记录（登录的时候使用），金融类的购物车不存在合并的情况，输入要素需要进行录入，每次录入的情况，都需要新增购物车，只是记录需要重新排序
	 * @param customerInfo  客户唯一标识
	 * @param list  需要合并的记录
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> mergeRecordShopCart(String customerInfo,List<Map<String, Object>> list) throws ServiceException {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String key = prefix+"_"+customerInfo;
		Object obj = shopCartRedisDao.get(key);
		List<Map<String,Object>> resultList = TransferCacheObjectUtil.getListMapFromObject(obj);
		if(list != null && list.size()>0){
			int trueExpire =  1000*60*60*24*expire;
			if(resultList != null && resultList.size() > 0){
				list.addAll(resultList);
			}
			//存放购物车数据
			shopCartRedisDao.set(key, list, trueExpire);
			result = list;
		}else{
			result = resultList;
		}
		return result;
	}

	/**
	 * 
	 * @Title: updateRecordShopCart 
	 * @Description: 更新购物车的某条记录，并返回更新之后的购物车记录
	 * @param customerInfo
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> updateRecordShopCart(String customerInfo,Map<String, Object> map) throws ServiceException {
		String key = prefix+"_"+customerInfo;
		Object obj = shopCartRedisDao.get(key);
		List<Map<String,Object>> resultList = TransferCacheObjectUtil.getListMapFromObject(obj);
		if(map == null || map.size()<=0){
			return resultList;
		}
		
		if(resultList == null || resultList.size()<=0){
			throw new ServiceException(CacheErrorCodeConstants.PPCE0002,"购物车不存在");
		}
		
		String recordId = (String) (map.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_RECORD_ID));
		String amount = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT));
		String des = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES));
		String name = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME));
		String price = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE));
		String productId = (String) (map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID));
		Map<String,Object> tradeData = (Map<String, Object>) (map.get(ShopCartDefinitionConstants.CART_TRADE_DATA)==null?new HashMap<String, Object>():map.get(ShopCartDefinitionConstants.CART_TRADE_DATA));
		
		int trueExpire =  1000*60*60*24*expire;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();//更新数据之后的结果
		boolean bool = false;
		for(Map<String,Object> mapTemp : resultList){
			String recordIdTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_RECORD_ID));
			if(recordId.equals(recordIdTemp)){
				bool = true;
				String amountTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT));
				String desTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_DES)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_DES));
				String nameTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME));
				String priceTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE));
				String productIdTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PROFUCT_ID)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PROFUCT_ID));
				Map<String,Object> tradeDataTemp = (Map<String, Object>) (mapTemp.get(ShopCartDefinitionConstants.CART_TRADE_DATA)==null?new HashMap<String, Object>():mapTemp.get(ShopCartDefinitionConstants.CART_TRADE_DATA));
				if(!ValidateUtil.isEmpty(amount)){
					amountTemp = amount;
				}
				if(!ValidateUtil.isEmpty(des)){
					desTemp = des;
				}
				if(!ValidateUtil.isEmpty(name)){
					nameTemp = name;
				}
				if(!ValidateUtil.isEmpty(price)){
					priceTemp = price;
				}
				if(!ValidateUtil.isEmpty(productId)){
					productIdTemp = productId;
				}
				if(tradeData != null && tradeData.size()>0){
					tradeDataTemp = tradeData;
				}
				
				Map<String,Object> record = new HashMap<String, Object>();
				record.put(ShopCartDefinitionConstants.CART_RECORD_ID, recordIdTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT, amountTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_DES, desTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_NAME, nameTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_PRICE, priceTemp);
				record.put(ShopCartDefinitionConstants.CART_PROFUCT_ID, productIdTemp);
				record.put(ShopCartDefinitionConstants.CART_TRADE_DATA, tradeDataTemp);
	            result.add(record);
				continue;
			}
			result.add(mapTemp);
		}
		if(!bool){
			throw new ServiceException(CacheErrorCodeConstants.PPCE0003,"购物车不存在该条更新记录");
		}
		
		shopCartRedisDao.set(key, result, trueExpire);
		
		return result;
	}
	
	/**
	 * 
	 * @Title: queryShopCart 
	 * @Description: 查询购物车信息
	 * @param customerInfo 客户唯一标识
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> queryShopCart(String customerInfo)
			throws ServiceException {
		String key = prefix+"_"+customerInfo;
		Object obj = shopCartRedisDao.get(key);
		return TransferCacheObjectUtil.getListMapFromObject(obj);
	}

	/**
	 * 
	 * @Title: deleteShopCartRecord 
	 * @Description: 删除购物车记录并返回购物车记录
	 * @param customerInfo 客户唯一标识
	 * @param list  购物车唯一标识uuid
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> deleteShopCartRecord(String customerInfo, List<String> list)throws ServiceException {
		String key = prefix+"_"+customerInfo;
		Object obj = shopCartRedisDao.get(key);
		List<Map<String,Object>> resultList = TransferCacheObjectUtil.getListMapFromObject(obj);
		List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
		if(resultList != null && resultList.size()>0){
			if(list == null || list.size()<=0){
				tempList = resultList;
			}else{
				int trueExpire =  1000*60*60*24*expire;
				boolean boolExist = false;//是否有删除记录
				for(Map<String,Object> map : resultList){
					String recordId = (String) map.get(ShopCartDefinitionConstants.CART_RECORD_ID);//购物车记录uuid
					boolean bool = false;
					for(String recordIdTemp : list){
						if(recordId.equals(recordIdTemp)){
							bool = true;
							boolExist = true;
							break;
						}
					}
					if(!bool){
						tempList.add(map);
					}
				}
				if(boolExist){//有删除记录，需要进行重新设置
					shopCartRedisDao.set(key, tempList, trueExpire);
				}
			}
		}
		return tempList;
	}

	/**
	 * 
	 * @Title: clearShopCart 
	 * @Description: 清除客户购物车信息
	 * @param customerInfo
	 * @throws ServiceException
	 */
	@Override
	public void clearShopCart(String customerInfo) throws ServiceException {
		String key = prefix+"_"+customerInfo;
		shopCartRedisDao.del(key);
	}
	
	/**
	 * 
	 * @Title: updateBatchRecordShopCart 
	 * @Description: 批量更新购物车
	 * @param customerInfo
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> updateBatchRecordShopCart(	String customerInfo,  List<String> list) throws ServiceException {
		
		String key = prefix+"_"+customerInfo;
		Object obj = shopCartRedisDao.get(key);
		List<Map<String,Object>> resultList = TransferCacheObjectUtil.getListMapFromObject(obj);
		if(list == null || list.size()<=0){
			return resultList;
		}
		
		if(resultList == null || resultList.size()<=0){
			throw new ServiceException(CacheErrorCodeConstants.PPCE0002,"购物车不存在");
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			
			String recordId = (String) (map.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_RECORD_ID));
			String amount = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT));
			String des = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES));
			String name = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME));
			String price = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE));
			String productId = (String) (map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID));
			Map<String,Object> tradeData = (Map<String, Object>) (map.get(ShopCartDefinitionConstants.CART_TRADE_DATA)==null?new HashMap<String, Object>():map.get(ShopCartDefinitionConstants.CART_TRADE_DATA));
			
			int trueExpire =  1000*60*60*24*expire;
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();//更新数据之后的结果
			boolean bool = false;
			for(Map<String,Object> mapTemp : resultList){
				String recordIdTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_RECORD_ID));
				if(recordId.equals(recordIdTemp)){
					bool = true;
					String amountTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT));
					String desTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_DES)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_DES));
					String nameTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME));
					String priceTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE));
					String productIdTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PROFUCT_ID)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PROFUCT_ID));
					Map<String,Object> tradeDataTemp = (Map<String, Object>) (mapTemp.get(ShopCartDefinitionConstants.CART_TRADE_DATA)==null?new HashMap<String, Object>():mapTemp.get(ShopCartDefinitionConstants.CART_TRADE_DATA));
					if(!ValidateUtil.isEmpty(amount)){
						amountTemp = amount;
					}
					if(!ValidateUtil.isEmpty(des)){
						desTemp = des;
					}
					if(!ValidateUtil.isEmpty(name)){
						nameTemp = name;
					}
					if(!ValidateUtil.isEmpty(price)){
						priceTemp = price;
					}
					if(!ValidateUtil.isEmpty(productId)){
						productIdTemp = productId;
					}
					if(tradeData != null && tradeData.size()>0){
						tradeDataTemp.putAll(tradeData);
					}
					
					Map<String,Object> record = new HashMap<String, Object>();
					record.put(ShopCartDefinitionConstants.CART_RECORD_ID, recordIdTemp);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT, amountTemp);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_DES, desTemp);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_NAME, nameTemp);
					record.put(ShopCartDefinitionConstants.CART_PRODUCT_PRICE, priceTemp);
					record.put(ShopCartDefinitionConstants.CART_PROFUCT_ID, productIdTemp);
					record.put(ShopCartDefinitionConstants.CART_TRADE_DATA, tradeDataTemp);
		            result.add(record);
					continue;
				}
				result.add(mapTemp);
			}
			if(bool){
				shopCartRedisDao.set(key, result, trueExpire);
			}
			
		}
		
		List<Map<String,Object>> resultListNew = TransferCacheObjectUtil.getListMapFromObject(obj);
		return resultListNew;
	}
	
	
	/**
	 * 
	 * @Title: updateRecordShopCartNew 
	 * @Description: 更新购物车的某条记录，并返回更新之后的购物车记录
	 * @param customerInfo
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> updateRecordShopCartNew(String customerInfo,Map<String, Object> map) throws ServiceException {
		String key = prefix+"_"+customerInfo;
		Object obj = shopCartRedisDao.get(key);
		List<Map<String,Object>> resultList = TransferCacheObjectUtil.getListMapFromObject(obj);
		if(map == null || map.size()<=0){
			return resultList;
		}
		
		if(resultList == null || resultList.size()<=0){
			throw new ServiceException(CacheErrorCodeConstants.PPCE0002,"购物车不存在");
		}
		
		String recordId = (String) (map.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_RECORD_ID));
		String amount = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT));
		String des = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_DES));
		String name = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME));
		String price = (String) (map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE)==null?"":map.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE));
		String productId = (String) (map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID)==null?"":map.get(ShopCartDefinitionConstants.CART_PROFUCT_ID));
		Map<String,Object> tradeData = (Map<String, Object>) (map.get(ShopCartDefinitionConstants.CART_TRADE_DATA)==null?new HashMap<String, Object>():map.get(ShopCartDefinitionConstants.CART_TRADE_DATA));
		
		int trueExpire =  1000*60*60*24*expire;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();//更新数据之后的结果
		boolean bool = false;
		for(Map<String,Object> mapTemp : resultList){
			String recordIdTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_RECORD_ID)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_RECORD_ID));
			if(recordId.equals(recordIdTemp)){
				bool = true;
				String amountTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT));
				String desTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_DES)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_DES));
				String nameTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_NAME));
				String priceTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PRODUCT_PRICE));
				String productIdTemp = (String) (mapTemp.get(ShopCartDefinitionConstants.CART_PROFUCT_ID)==null?"":mapTemp.get(ShopCartDefinitionConstants.CART_PROFUCT_ID));
				Map<String,Object> tradeDataTemp = (Map<String, Object>) (mapTemp.get(ShopCartDefinitionConstants.CART_TRADE_DATA)==null?new HashMap<String, Object>():mapTemp.get(ShopCartDefinitionConstants.CART_TRADE_DATA));
				if(!ValidateUtil.isEmpty(amount)){
					amountTemp = amount;
				}
				if(!ValidateUtil.isEmpty(des)){
					desTemp = des;
				}
				if(!ValidateUtil.isEmpty(name)){
					nameTemp = name;
				}
				if(!ValidateUtil.isEmpty(price)){
					priceTemp = price;
				}
				if(!ValidateUtil.isEmpty(productId)){
					productIdTemp = productId;
				}
				if(tradeData != null && tradeData.size()>0){
					tradeDataTemp.putAll(tradeData);
				}
				
				Map<String,Object> record = new HashMap<String, Object>();
				record.put(ShopCartDefinitionConstants.CART_RECORD_ID, recordIdTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_AMOUNT, amountTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_DES, desTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_NAME, nameTemp);
				record.put(ShopCartDefinitionConstants.CART_PRODUCT_PRICE, priceTemp);
				record.put(ShopCartDefinitionConstants.CART_PROFUCT_ID, productIdTemp);
				record.put(ShopCartDefinitionConstants.CART_TRADE_DATA, tradeDataTemp);
	            result.add(record);
				continue;
			}
			result.add(mapTemp);
		}
		
		
		shopCartRedisDao.set(key, result, trueExpire);
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> updateBatchRecordShopCartList(String customerInfo, List<Map<String, Object>> list) throws ServiceException {
		for (Map<String, Object> map : list) {
			updateRecordShopCartNew(customerInfo, map);
		}
		String key = prefix+"_"+customerInfo;
		Object objNew = shopCartRedisDao.get(key);
		List<Map<String,Object>> resultListNew = TransferCacheObjectUtil.getListMapFromObject(objNew);
		return resultListNew;
	}
}
