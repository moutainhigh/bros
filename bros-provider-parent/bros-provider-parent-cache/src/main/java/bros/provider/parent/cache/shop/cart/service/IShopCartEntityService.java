package bros.provider.parent.cache.shop.cart.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IShopCartEntityService 
 * @Description: 购物车缓存接口
 * @author 何鹏
 * @date 2016年8月24日 上午9:38:53 
 * @version 1.0
 */
public interface IShopCartEntityService {
	/**
	 * 
	 * @Title: addRecordShopCart 
	 * @Description: 添加某条记录到购物车中
	 * @param customerInfo 客户唯一标识
	 * @param map  需要添加的记录
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> addRecordShopCart(String customerInfo,Map<String,Object> map) throws ServiceException;
	/**
	 * 
	 * @Title: mergeRecordShopCart 
	 * @Description: 合并购物车记录并返回合并后的记录（登录的时候使用），金融类的购物车不存在合并的情况，输入要素需要进行录入，每次录入的情况，都需要新增购物车，只是记录需要重新排序
	 * @param customerInfo  客户唯一标识
	 * @param list  需要合并的记录
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> mergeRecordShopCart(String customerInfo,List<Map<String,Object>> list) throws ServiceException;
	/**
	 * 
	 * @Title: updateRecordShopCart 
	 * @Description: 更新购物车的某条记录，并返回更新之后的购物车记录
	 * @param customerInfo
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> updateRecordShopCart(String customerInfo,Map<String,Object> map) throws ServiceException;
	/**
	 * 
	 * @Title: updateBatchRecordShopCart 
	 * @Description: 批量更新购物车
	 * @param customerInfo
	 * @param list
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> updateBatchRecordShopCart(String customerInfo, List<String> list) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBatchRecordShopCartList 
	 * @Description: 批量更新购物车
	 * @param customerInfo
	 * @param list
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> updateBatchRecordShopCartList(String customerInfo, List<Map<String,Object>> list) throws ServiceException;
	/**
	 * 
	 * @Title: queryShopCart 
	 * @Description: 查询购物车信息
	 * @param customerInfo 客户唯一标识
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryShopCart(String customerInfo) throws ServiceException;
	/**
	 * 
	 * @Title: deleteShopCartRecord 
	 * @Description: 删除购物车记录并返回购物车记录
	 * @param customerInfo 客户唯一标识
	 * @param list  购物车唯一标识uuid
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> deleteShopCartRecord(String customerInfo,List<String> list) throws ServiceException;
	/**
	 * 
	 * @Title: clearShopCart 
	 * @Description: 清除客户购物车信息
	 * @param customerInfo
	 * @throws ServiceException
	 */
	public void clearShopCart(String customerInfo) throws ServiceException;
	/**
	 * 
	 * @Title: updateRecordShopCartNew 
	 * @Description: 更新购物车的某条记录，并返回更新之后的购物车记录
	 * @param customerInfo
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> updateRecordShopCartNew(String customerInfo,Map<String,Object> map) throws ServiceException;
}
