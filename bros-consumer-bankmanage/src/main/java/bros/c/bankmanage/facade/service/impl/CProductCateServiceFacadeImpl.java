package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICProductCateServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPProductCateServiceFacade;
/**
 * 
 * @ClassName: CProductCateServiceFacadeImpl 
 * @Description: 产品目录接口实现类
 * @author huangdazhou
 * @date 2016年6月30日 下午2:26:27 
 * @version 1.0
 */
@Component("cproductCateServiceFacade")
public class CProductCateServiceFacadeImpl implements ICProductCateServiceFacade {
	@Autowired
	private IPProductCateServiceFacade pproductCateServiceFacade;
	/**
	 * 
	 * @Title: addProductCateMethod 
	 * @Description: 新增产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000159")
	public ResponseEntity addProductCateMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductCateServiceFacade.addProductCateMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: updateProductCateMethod 
	 * @Description: 修改产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000159")
	public ResponseEntity updateProductCateMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductCateServiceFacade.updateProductCateMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: deleteProductCateMethod 
	 * @Description: 删除产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000160")
	public ResponseEntity deleteProductCateMethod(Map<String, Object> headMap,
			Map<String,Object> bodyMap) throws ServiceException {
		return pproductCateServiceFacade.deleteProductCateMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryProductCateMethod 
	 * @Description: 查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductCateMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductCateServiceFacade.queryProductCateMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryProductCatePageMethod 
	 * @Description: 分页查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000153")
	public ResponseEntity queryProductCatePageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductCateServiceFacade.queryProductCatePageMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryProductCateOneMethod 
	 * @Description: 单笔查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="c0000160")
	public ResponseEntity queryProductCateOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductCateServiceFacade.queryProductCateOneMethod(headMap,bodyMap);
	}

}
