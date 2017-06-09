package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICProductInformationServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPProductInformationServiceFacade;
/**
 * 
 * @ClassName: CProductInformationServiceFacadeImpl 
 * @Description: 产品信息服务发布实现类
 * @author huangdazhou
 * @date 2016年6月30日 下午5:01:27 
 * @version 1.0
 */
@Component("cproductInformationServiceFacade")
public class CProductInformationServiceFacadeImpl implements
		ICProductInformationServiceFacade {
	@Autowired
	private IPProductInformationServiceFacade pproductInformationServiceFacade; 
	/**
	 * 
	 * @Title: addProductInformationMethod 
	 * @Description: 添加产品信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000161")
	public ResponseEntity addProductInformationMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductInformationServiceFacade.addProductInformationMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: updateProductInformationMethod 
	 * @Description: 修改产品信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000161")
	public ResponseEntity updateProductInformationMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductInformationServiceFacade.updateProductInformationMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: deleteProductInformationMethod 
	 * @Description: 删除产品信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000162")
	public ResponseEntity deleteProductInformationMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductInformationServiceFacade.deleteProductInformationMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryProductTypeMethod 
	 * @Description: 查询产品信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000153")
	public ResponseEntity queryProductInformationMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductInformationServiceFacade.queryProductInformationMethod(headMap,bodyMap);
	}

	/**
	 * 
	 * @Title: queryAttributeParameterMethod 
	 * @Description: 查询规格属性参数信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000153")
	public ResponseEntity queryAttributeParameterMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pproductInformationServiceFacade.queryAttributeParameterMethod(headMap,bodyMap);
	}
	/**
	 * 
	 * @Title: queryAttributeParameterByProductTypeCodeMethod 
	 * @Description: 根据产品分类编码查询产品属性及规格属性参数信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map 包含以下参数
	 * @param prdTypeCode 产品分类编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity queryAttributeParameterByProductTypeCodeMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException{
		return pproductInformationServiceFacade.queryAttributeParameterByProductTypeCodeMethod(headMap, bodyMap);
	}

	/**
	 * 分页查询产品信息
	 */
	public ResponseEntity queryProductInformationPageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductInformationServiceFacade.queryProductInformationPageMethod(headMap, bodyMap);
	}

	/**
	 * 单笔查询产品信息
	 */
	public ResponseEntity queryProductInformationOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pproductInformationServiceFacade.queryProductInformationOneMethod(headMap, bodyMap);
	}

	/**
	 * 分页查询规格属性参数信息
	 */
	public ResponseEntity queryAttributePageMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductInformationServiceFacade.queryAttributePageMethod(headMap, bodyMap);
	}

	/**
	 * 单笔查询规格属性参数信息s
	 */
	public ResponseEntity queryAttributeOneMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pproductInformationServiceFacade.queryAttributeOneMethod(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryAttributeInfoMethod 
	 * @Description: 根据产品分类编码、产品编码查询产品属性信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map 包含以下参数
	 * @param prdTypeCode 产品分类编码
	 * @param productCode 产品编码
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity queryAttributeInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return pproductInformationServiceFacade.queryAttributeInfoMethod(headMap, bodyMap);
	}
}
