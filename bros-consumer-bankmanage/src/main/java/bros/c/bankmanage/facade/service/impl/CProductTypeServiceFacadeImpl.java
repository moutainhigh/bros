package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICProductTypeServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPProductTypeServiceFacade;
/**
 * 
 * @ClassName: CProductTypeServiceFacadeImpl 
 * @Description: 产品分类接口实现
 * @author huangdazhou
 * @date 2016年6月27日 下午2:13:45 
 * @version 1.0
 */
@Component("cproductTypeServiceFacade")
public class CProductTypeServiceFacadeImpl implements ICProductTypeServiceFacade {
	/**
     * 登录业务服务
     */
	@Autowired
	private IPProductTypeServiceFacade pproductTypeServiceFacade;

	/**
	 * 
	 * @Title: addProductTypeMethod 
	 * @Description: 添加产品分类信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000151")
	public ResponseEntity addProductTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductTypeServiceFacade.addProductTypeMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: updateProductTypeMethod 
	 * @Description: 修改产品分类信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000151")
	public ResponseEntity updateProductTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductTypeServiceFacade.updateProductTypeMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: deleteProductTypeMethod 
	 * @Description: 删除产品分类信息
	 * @param headMap 报文头信息map
	 * @param prdTypeCode 产品分类编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000152")
	public ResponseEntity deleteProductTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductTypeServiceFacade.deleteProductTypeMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryProductTypeMethod 
	 * @Description: 查询产品分类信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	
	public ResponseEntity queryProductTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductTypeServiceFacade.queryProductTypeMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: querySpecificationAttributesMethod 
	 * @Description: 查询规格属性信息
	 * @param headMap 报文头信息map
	 * @param prdTypeCode 产品分类编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	
	public ResponseEntity querySpecificationAttributesMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.querySpecificationAttributesMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryInstanceAttributesMethod 
	 * @Description: 查询实例属性信息
	 * @param headMap 报文头信息map
	 * @param prdTypeCode 产品分类编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000154")
	public ResponseEntity queryInstanceAttributesMethod(
			Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.queryInstanceAttributesMethod(headMap,bodyMap);
	}

	/**
	 * 分页查询产品分类信息
	 */
	
	public ResponseEntity queryProductTypePageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.queryProductTypePageMethod(headMap,bodyMap);
	}

	/**
	 * 单笔查询产品分类信息
	 */
	public ResponseEntity queryProductTypeOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.queryProductTypeOneMethod(headMap,bodyMap);
	}

	/**
	 * 分页查询规格属性信息
	 */
	@Validation(value="c0000154")
	public ResponseEntity querySpecificationAttributesPageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.querySpecificationAttributesPageMethod(headMap,bodyMap);
	}

	/**
	 * 单笔查询规格属性信息
	 */
	public ResponseEntity querySpecificationAttributesOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.querySpecificationAttributesOneMethod(headMap,bodyMap);
	}

	/**
	 * 分页查询实例属性信息
	 */
	@Validation(value="c0000153")
	public ResponseEntity queryInstanceAttributesPageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.queryInstanceAttributesPageMethod(headMap,bodyMap);
	}

	/**
	 * 单笔查询实例属性信息
	 */
	public ResponseEntity queryInstanceAttributesOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductTypeServiceFacade.queryInstanceAttributesOneMethod(headMap,bodyMap);
	}

}
