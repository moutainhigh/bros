package bros.p.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IPFrontShelfMenuRelServiceFacade  
 * @Description:前台展示菜单查询接口
 * @author  gaoyongjing
 * @date 2016年7月16日 上午9:45:58 
 * @version V1.0  
 */

public interface IPFrontShelfMenuRelServiceFacade {
	 /**
	 * 
	 * @Title: queryPbankLoginMenuMethod
	 * @Description: 查询个人网银柜员登录时展示的菜单信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
    public ResponseEntity queryPbankLoginMenuMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryCbankLoginMenuMethod
   	 * @Description: 查询企业网银柜员登录时展示的菜单信息
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
       public ResponseEntity queryCbankLoginMenuMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
       /**
   	 * 
   	 * @Title: queryFrontLoginMenuMethod
   	 * @Description: 查询柜面柜员登录时展示的菜单信息
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
       public ResponseEntity queryFrontLoginMenuMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
       /**
   	 * 
   	 * @Title: queryAllChlShelfGoodsByShelfCodeMethod
   	 * @Description: 根据货架编号查询货架菜单信息,适用于各个渠道
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
   	public ResponseEntity queryAllChlShelfGoodsByShelfCodeMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryShelfAndShelfGoodsMenuByShelfCodeMethod
   	 * @Description: 根据货架编号查询子货架信息及货架信息上的商品信息并与菜单关联
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
   	public ResponseEntity queryShelfAndShelfGoodsMenuByShelfCodeMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException;
}
