package bros.c.common.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.common.facade.service.ICShopCartServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPShopCartServiceFacade;
/**
 * 
 * @ClassName: CShopCartServiceFacadeImpl 
 * @Description: 购物车缓存对外暴露服务接口实现
 * @author 何鹏
 * @date 2016年8月24日 上午9:30:37 
 * @version 1.0
 */
@Component(value="cshopCartServiceFacade")
public class CShopCartServiceFacadeImpl implements ICShopCartServiceFacade {

	@Autowired
	private IPShopCartServiceFacade pshopCartServiceFacade;
	/**
	 * 
	 * @Title: addRecordShopCart 
	 * @Description: 添加某条记录到购物车中
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000205")
	@Override
	public ResponseEntity addRecordShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pshopCartServiceFacade.addRecordShopCart(headMap, bodyMap);
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
	@Validation(value="c0000206")
	@Override
	public ResponseEntity mergeRecordShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pshopCartServiceFacade.mergeRecordShopCart(headMap, bodyMap);
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
	@Validation(value="c0000207")
	@Override
	public ResponseEntity updateRecordShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pshopCartServiceFacade.updateRecordShopCart(headMap, bodyMap);
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
	@Validation(value="c0000206")
	@Override
	public ResponseEntity queryShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pshopCartServiceFacade.queryShopCart(headMap, bodyMap);
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
	@Validation(value="c0000209")
	@Override
	public ResponseEntity deleteShopCartRecord(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pshopCartServiceFacade.deleteShopCartRecord(headMap, bodyMap);
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
	@Validation(value="c0000206")
	@Override
	public ResponseEntity clearShopCart(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pshopCartServiceFacade.clearShopCart(headMap, bodyMap);
	}
    
	/**
	 * 
	 * @Title: updateBatchRecordShopCart 
	 * @Description: 更新购物车信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000222")
	@Override
	public ResponseEntity updateBatchRecordShopCart(Map<String, Object> headMap, Map<String, Object> bodyMap)  throws ServiceException {
		return pshopCartServiceFacade.updateBatchRecordShopCart(headMap, bodyMap);
	}
}
