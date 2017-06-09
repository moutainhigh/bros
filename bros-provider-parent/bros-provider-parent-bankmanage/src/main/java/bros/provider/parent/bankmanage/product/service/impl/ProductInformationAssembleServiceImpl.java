package bros.provider.parent.bankmanage.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductInformationAssembleService;
import bros.provider.parent.bankmanage.product.service.IProductInformationService;
import bros.provider.parent.bankmanage.shelf.service.IShelfGoodsInfoService;
/**
 * 
 * @ClassName: ProductInformationServiceImpl 
 * @Description: 产品信息基础服务实现类
 * @author huangdazhou
 * @date 2016年6月30日 下午5:11:08 
 * @version 1.0
 */
@Repository(value="productInformationAssembleService")
public class ProductInformationAssembleServiceImpl implements
		IProductInformationAssembleService {
	private static final  Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

	@Autowired
	private IProductInformationService productInformationService;
	@Autowired
	private IShelfGoodsInfoService shelfGoodsInfoService;
	/**
	 * 
	 * @Title: addProductInformationMethod 
	 * @Description: 添加产品信息
	 * @param bodyMap 报文体信息map
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public void addProductInformationAssembleMethod(
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> productMap=new HashMap<String, Object>();//产品map
		List<Map<String, Object>> attributeParameterList=new ArrayList<Map<String,Object>>();
		productMap.put("productCode", (String)bodyMap.get("productCode"));//产品ID
		productMap.put("prdName", (String)bodyMap.get("prdName"));//产品名称
		productMap.put("cateCode", (String)bodyMap.get("cateCode"));//产品目录编号
		productMap.put("prdTypeCode", (String)bodyMap.get("prdTypeCode"));//产品分类编号
		productMap.put("modifiedBy", (String)bodyMap.get("modifiedBy"));//维护操作员
		productMap.put("modifiedDate", (String)bodyMap.get("modifiedDate"));//维护日期
		attributeParameterList=(List<Map<String, Object>>)bodyMap.get("attributeParameterList");
		try {
			//添加产品信息
			productInformationService.addProductInformationMethod(productMap);
			if(attributeParameterList!=null&&attributeParameterList.size()>0){
				productInformationService.addAttributeParameterMethod(attributeParameterList);
			}
		} catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品信息失败", ex);
		}
		
	}

	/**
	 * 
	 * @Title: updateProductInformationMethod 
	 * @Description: 修改产品信息
	 * @param bodyMap 报文体信息map
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public void updateProductInformationAssembleMethod(
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> productMap=new HashMap<String, Object>();//产品map
		List<Map<String, Object>> attributeParameterList=new ArrayList<Map<String,Object>>();
		productMap.put("productCode", (String)bodyMap.get("productCode"));//产品ID
		productMap.put("prdName", (String)bodyMap.get("prdName"));//产品名称
		productMap.put("cateCode", (String)bodyMap.get("cateCode"));//产品目录编号
		productMap.put("prdTypeCode", (String)bodyMap.get("prdTypeCode"));//产品分类编号
		productMap.put("modifiedBy", (String)bodyMap.get("modifiedBy"));//维护操作员
		productMap.put("modifiedDate", (String)bodyMap.get("modifiedDate"));//维护日期
		attributeParameterList=(List<Map<String, Object>>)bodyMap.get("attributeParameterList");
		String productCode=(String)bodyMap.get("productCode");
		try {
			//修改产品信息
			productInformationService.updateProductInformationMethod(productMap);
			if(attributeParameterList!=null&&attributeParameterList.size()>0){
				//查询产品属性参数信息
				Map<String, Object> tmpAttriParamMap=new HashMap<String, Object>();
				List<Map<String, Object>> resultprdAttriParamList=new ArrayList<Map<String,Object>>();
				tmpAttriParamMap.put("productCode", (String)bodyMap.get("productCode"));
				resultprdAttriParamList=productInformationService.queryAttributeParameterMethod(productCode, "", "", "", "");
				if(resultprdAttriParamList!=null&&resultprdAttriParamList.size()>0){
					//删除产品属性参数信息
					productInformationService.deleteAttributeParameterMethod(productCode);
				}
				//添加产品属性参数信息
				productInformationService.addAttributeParameterMethod(attributeParameterList);
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "updateProductInformationMethod", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "updateProductInformationMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改产品信息失败", ex);
		}
	}

	/**
	 * 
	 * @Title: deleteProductInformationMethod 
	 * @Description: 删除产品信息
	 * @param productCode 产品ID
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public void deleteProductInformationAssembleMethod(String productCode)
			throws ServiceException {
		Map<String, Object> productMap=new HashMap<String, Object>();//产品map
		productMap.put("productCode", (String)productCode);//产品ID
		try {
			//删除产品信息
			productInformationService.deleteProductInformationMethod(productCode);
			//查询产品属性参数信息
			List<Map<String, Object>> resultprdAttriParamList=new ArrayList<Map<String,Object>>();
			resultprdAttriParamList=productInformationService.queryAttributeParameterMethod(productCode, "", "", "", "");
			if(resultprdAttriParamList!=null&&resultprdAttriParamList.size()>0){
				//删除产品属性参数信息
				productInformationService.deleteAttributeParameterMethod(productCode);
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductInformationMethod", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductInformationMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品信息失败", ex);
		}
	}

}
