package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICProductServiceServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPProductServiceServiceFacade;
/**
 * 
 * @ClassName: CProductServiceServiceFacadeImpl 
 * @Description: 产品服务实现类
 * @author huangdazhou
 * @date 2016年6月30日 上午9:18:45 
 * @version 1.0
 */
@Component("cproductServiceServiceFacade")
public class CProductServiceServiceFacadeImpl implements
		ICProductServiceServiceFacade {
	@Autowired
	private IPProductServiceServiceFacade pproductServiceServiceFacade;

	/**
	 * 
	 * @Title: addProductServiceMethod 
	 * @Description: 新增产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000155")
	public ResponseEntity addProductServiceMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductServiceServiceFacade.addProductServiceMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: updateProductServiceMethod 
	 * @Description: 修改产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000155")
	public ResponseEntity updateProductServiceMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductServiceServiceFacade.updateProductServiceMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: deleteProductServiceMethod 
	 * @Description: 删除产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000156")
	public ResponseEntity deleteProductServiceMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductServiceServiceFacade.deleteProductServiceMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryProductServiceMethod 
	 * @Description: 查询产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000157")
	public ResponseEntity queryProductServiceMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductServiceServiceFacade.queryProductServiceMethod(headMap,bodyMap);
	}

	/**
	 * 单笔查询产品服务方法
	 */
	public ResponseEntity queryProductServicePageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductServiceServiceFacade.queryProductServicePageMethod(headMap,bodyMap);
	}

	/**
	 * 单笔查询产品服务方法
	 */
	public ResponseEntity queryProductServiceOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductServiceServiceFacade.queryProductServiceOneMethod(headMap,bodyMap);
	}

}
