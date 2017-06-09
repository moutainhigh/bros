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

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductInformationService;
/**
 * 
 * @ClassName: ProductInformationServiceImpl 
 * @Description: 产品信息基础服务实现类
 * @author huangdazhou
 * @date 2016年6月30日 下午5:11:08 
 * @version 1.0
 */
@Repository(value="productInformationService")
public class ProductInformationServiceImpl implements
		IProductInformationService {
	private static final  Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: addProductInformationMethod 
	 * @Description: 添加产品信息
	 * @param bodyMap 报文体信息map
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int addProductInformationMethod(Map<String, Object> bodyMap) throws ServiceException {
		int resultInt=0;
		Map<String, Object> productMap=new HashMap<String, Object>();//产品map
		productMap.put("productCode", (String)bodyMap.get("productCode"));//产品ID
		productMap.put("prdName", (String)bodyMap.get("prdName"));//产品名称
		productMap.put("cateCode", (String)bodyMap.get("cateCode"));//产品目录编号
		productMap.put("prdTypeCode", (String)bodyMap.get("prdTypeCode"));//产品分类编号
		productMap.put("modifiedBy", (String)bodyMap.get("modifiedBy"));//维护操作员
		productMap.put("modifiedDate", (String)bodyMap.get("modifiedDate"));//维护日期
		try {
			//添加产品信息
			resultInt=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.PrdProduct.insertPrdProduct", productMap);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品信息失败", e);
		}
		
		return resultInt;
	}
	
	/**
	 * 
	 * @Title: addAttributeParameterMethod 
	 * @Description: 添加产品属性参数
	 * @param bodyMap 报文体信息map
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	public int addAttributeParameterMethod(List<Map<String,Object>> parmINList) throws ServiceException{
		int resultInt=0;
		try {
			//添加产品属性参数
			resultInt=myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.prdattriparamspec.insertPrdAttriParamSpec", parmINList);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品信息失败", e);
		}
		return resultInt;
	}
	/**
	 * 
	 * @Title: updateProductInformationMethod 
	 * @Description: 修改产品信息
	 * @param bodyMap 报文体信息map
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int updateProductInformationMethod(Map<String, Object> bodyMap) throws ServiceException {
		int resultInt=0;
		Map<String, Object> productMap=new HashMap<String, Object>();//产品map
		productMap.put("productCode", (String)bodyMap.get("productCode"));//产品ID
		productMap.put("prdName", (String)bodyMap.get("prdName"));//产品名称
		productMap.put("cateCode", (String)bodyMap.get("cateCode"));//产品目录编号
		productMap.put("prdTypeCode", (String)bodyMap.get("prdTypeCode"));//产品分类编号
		productMap.put("modifiedBy", (String)bodyMap.get("modifiedBy"));//维护操作员
		productMap.put("modifiedDate", (String)bodyMap.get("modifiedDate"));//维护日期
		try {
			//修改产品分类信息
			resultInt=myBatisDaoSysDao.update("mybatis.mapper.single.table.PrdProduct.updatePrdProduct", productMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "updateProductInformationMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改产品信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: deleteProductInformationMethod 
	 * @Description: 删除产品信息
	 * @param productCode 产品ID
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int deleteProductInformationMethod(String productCode) throws ServiceException {
		int resultInt=0;
		Map<String, Object> productMap=new HashMap<String, Object>();//产品map
		productMap.put("productCode", (String)productCode);//产品ID
		try {
			//删除产品信息
			resultInt=myBatisDaoSysDao.delete("mybatis.mapper.single.table.PrdProduct.deletePrdProductByProductCode", productMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductInformationMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品信息失败", e);
		}
		return resultInt;
	}
	
	/**
	 * 
	 * @Title: deleteAttributeParameterMethod 
	 * @Description: 删除产品属性参数
	 * @param productCode 产品ID
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	public int deleteAttributeParameterMethod(String productCode) throws ServiceException{
		int resultInt=0;
		Map<String, Object> tmpAttriParamMap=new HashMap<String, Object>();//产品map
		tmpAttriParamMap.put("productCode", (String)productCode);//产品ID
		try {
			//删除产品属性参数信息
			resultInt=myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdattriparamspec.deletePrdAttriParamSpecByProductCode", tmpAttriParamMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductInformationMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: queryAttributeParameterByProductTypeCodeMethod 
	 * @Description: 根据产品分类编码、产品属性查询规格属性参数信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map 包含以下参数
	 * @param prdTypeCode 产品分类编号
	 * @param property 产品属性
	 * @return List<Map<String, Object>> 属性参数值列表信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryPropertyValueByProductTypeCodeMethod(Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		try {
				String prdTypeCode = (String) (bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode"));  // 产品分类编号
				String property = (String) (bodyMap.get("property")==null?"":bodyMap.get("property"));  // 产品属性
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("prdTypeCode", prdTypeCode);//产品分类编号
				param.put("property", property);//产品属性
				returnList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.prdproduct-prdattriparamspec.queryPropertyValueInfo",param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryPropertyValueByProductTypeCodeMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryPropertyValueByProductTypeCodeMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据产品分类编码、产品属性查询规格属性参数信息", e);
		}
		return returnList;
	}

	/**
	 * 查询产品信息
	 */
	public List<Map<String, Object>> queryProductInformationMethod(
			String productCode, String prdName, String cateCode,
			String prdTypeCode, String modifiedBy, String modifiedDate)
			throws ServiceException {
			List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
			Map<String, Object> parmIN=new HashMap<String, Object>();
			parmIN.put("productCode", productCode);
			parmIN.put("prdName", prdName);
			parmIN.put("cateCode", cateCode);
			parmIN.put("prdTypeCode", prdTypeCode);
			parmIN.put("modifiedBy", modifiedBy);
			parmIN.put("modifiedDate", modifiedDate);
			try {
				returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.PrdProduct.queryPrdProductByParameter", parmIN);
			} catch (NumberFormatException exy) {
				logger.error("Exception from " + this.getClass().getName() + "queryProductInformationMethod", exy);
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", exy);
			}  catch(BrosBaseException e){
				logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", e);
			}
			return returnList;
	}

	/**
	 * 分页查询产品信息
	 */
	public Map<String, Object> queryProductInformationPageMethod(
			String productCode, String prdName, String cateCode,
			String prdTypeCode, String modifiedBy, String modifiedDate,
			int pageNo, int pageSize) throws ServiceException {
		int totalNum=0;
		Map<String, Object> resultMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("productCode", productCode);
		parmIN.put("prdName", prdName);
		parmIN.put("cateCode", cateCode);
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("modifiedBy", modifiedBy);
		parmIN.put("modifiedDate", modifiedDate);
		try {
			returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.PrdProduct.queryPrdProductByParameter", 
					parmIN, pageNo, pageSize);
			totalNum=queryProductInformationNumMethod(productCode, prdName, cateCode, prdTypeCode, modifiedBy, modifiedDate);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum", totalNum);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductInformationMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", e);
		}
		return resultMap;
	}

	/**
	 * 单笔查询产品信息
	 */
	public Map<String, Object> queryProductInformationOneMethod(
			String productCode) throws ServiceException {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("productCode", productCode);
		try {
			resultMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.PrdProduct.queryPrdProductByParameter", parmIN);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryAttributeParameterMethod 
	 * @Description: 查询规格属性参数信息
	 * @param bodyMap 报文体信息map
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryAttributeParameterMethod(
			Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		try {
			if((bodyMap.get("pageNo") != null && !bodyMap.get("pageNo").equals("")) &&
					(bodyMap.get("pageSize") != null && !bodyMap.get("pageSize").equals(""))	){
				returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdattriparamspec.queryPrdAttriParamSpecByParameter", bodyMap, 
						Integer.parseInt(String.valueOf(bodyMap.get("pageNo"))), 
						Integer.parseInt(String.valueOf(bodyMap.get("pageSize"))));
			}else{
				returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdattriparamspec.queryPrdAttriParamSpecByParameter", bodyMap);
			}
			
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryAttributeParameterMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品属性参数失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryAttributeParameterMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品属性参数失败", e);
		}
		return returnList;
	}


	/**
	 * 查询规格属性参数信息
	 */
	public List<Map<String, Object>> queryAttributeParameterMethod(
			String productCode, String property, String propertyName,
			String setValue, String description) throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		 Map<String, Object> parmIN=new HashMap<String, Object>();
		 parmIN.put("productCode", productCode);
		 parmIN.put("property", property);
		 parmIN.put("propertyName", propertyName);
		 parmIN.put("setValue", setValue);
		 parmIN.put("description", description);
		try {
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdattriparamspec.queryPrdAttriParamSpecByParameter", parmIN);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryAttributeParameterMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品属性参数失败", e);
		}
		return returnList;
	}

	/**
	 * 分页查询规格属性参数信息
	 */
	public Map<String, Object> queryAttributePageMethod(
			String productCode, String property, String propertyName,
			String setValue, String description, int pageNo, int pageSize)
			throws ServiceException {
			int totalNum=0;
		 List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		 Map<String, Object> resultMap=new HashMap<String, Object>();
		 Map<String, Object> parmIN=new HashMap<String, Object>();
		 parmIN.put("productCode", productCode);
		 parmIN.put("property", property);
		 parmIN.put("propertyName", propertyName);
		 parmIN.put("setValue", setValue);
		 parmIN.put("description", description);
		try {
			returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdattriparamspec.queryPrdAttriParamSpecByParameter",
					parmIN,pageNo,pageSize);
			totalNum=queryAttributeNumMethod(productCode, property, propertyName, setValue, description);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum",totalNum);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryAttributeParameterMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品属性参数失败", e);
		}
		return resultMap;
	}

	/**
	 * 单笔查询规格属性参数信息
	 */
	public Map<String, Object> queryAttributeOneMethod(
			String productCode, String property,String setValue) throws ServiceException {
		 List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		 Map<String, Object> resultMap=new HashMap<String, Object>();
		 Map<String, Object> parmIN=new HashMap<String, Object>();
		 parmIN.put("productCode", productCode);
		 parmIN.put("property", property);
		 parmIN.put("setValue", setValue);
		try {
			resultMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdattriparamspec.queryPrdAttriParamSpecByParameter",parmIN);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryAttributeParameterMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品属性参数失败", e);
		}
		return resultMap;
	}

	/**
	 * 查询产品信息笔数
	 */
	public int queryProductInformationNumMethod(String productCode,String prdName,
			String cateCode,String prdTypeCode,String modifiedBy,String modifiedDate) throws ServiceException {
		int totalNum=0;
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("productCode", productCode);
		parmIN.put("prdName", prdName);
		parmIN.put("cateCode", cateCode);
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("modifiedBy", modifiedBy);
		parmIN.put("modifiedDate", modifiedDate);
		try {
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.PrdProduct.queryPrdProductNum", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductInformationMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", e);
		}
		return totalNum;
	}

	/**
	 * 查询规格属性参数信息笔数
	 */
	public int queryAttributeNumMethod(String productCode,String property,
			String propertyName,String setValue,String description) throws ServiceException {
		int totalNum=0;
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("productCode", productCode);
		parmIN.put("propertyName", propertyName);
		parmIN.put("property", property);
		parmIN.put("setValue", setValue);
		parmIN.put("description", description);
		try {
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdattriparamspec.queryPrdAttriParamSpecNum", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductInformationMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品信息失败", e);
		}
		return totalNum;
	}
	/**
	 * 
	 * @Title: queryAttributeInfoMethod 
	 * @Description: 根据产品分类编码、产品编码查询产品属性信息
	 * @param prdTypeCode 产品分类编码
	 * @param productCode 产品编码
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryAttributeInfoMethod(String prdTypeCode,String productCode) throws ServiceException {
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		try {
			Map<String, Object> parmIN=new HashMap<String, Object>();
			parmIN.put("productCode", productCode);
			parmIN.put("prdTypeCode", prdTypeCode);
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.prdptparspec-prdattriparamspec.queryPropertyInfo", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductInformationMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品属性信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品属性信息失败", e);
		}
		return returnList;
	}
}
