package bros.c.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICShopCartServiceFacade 
 * @Description: 购物车缓存对外暴露服务接口
 * @author 何鹏
 * @date 2016年8月24日 上午9:25:23 
 * @version 1.0
 */
public interface ICShopCartServiceFacade {
	/**
	 * 
	 * @Title: addRecordShopCart 
	 * @Description: 添加某条记录到购物车中
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addRecordShopCart(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: mergeRecordShopCart 
	 * @Description: 合并购物车记录并返回合并后的记录（登录的时候使用），金融类的购物车不存在合并的情况，输入要素需要进行录入，每次录入的情况，都需要新增购物车，只是记录需要重新排序
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity mergeRecordShopCart(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateRecordShopCart 
	 * @Description: 更新购物车的某条记录，并返回更新之后的购物车记录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateRecordShopCart(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryShopCart 
	 * @Description: 查询购物车信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryShopCart(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteShopCartRecord 
	 * @Description: 删除购物车记录并返回购物车记录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity deleteShopCartRecord(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteShopCartRecord 
	 * @Description: 清除客户购物车信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity clearShopCart(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBatchRecordShopCart 
	 * @Description: 批量更新购物车
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchRecordShopCart(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
