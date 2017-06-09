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
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductInformationService;
import bros.provider.parent.bankmanage.product.service.IProductTypeAssembleService;
import bros.provider.parent.bankmanage.product.service.IProductTypeService;
/**
 * 
 * @ClassName: ProductTypeAssembleServiceImpl 
 * @Description: 产品分类组装实现类
 * @author huangdazhou
 * @date 2016年6月27日 下午2:32:57 
 * @version 1.0
 */
@Repository(value="productTypeAssembleService")
public class ProductTypeAssembleServiceImpl implements IProductTypeAssembleService {
	private static final  Logger logger = LoggerFactory.getLogger(ProductTypeAssembleServiceImpl.class);
	/**
	 * 产品分类实体服务
	 */
	@Autowired
	private IProductTypeService productTypeService;
	/**
	 * 产品信息实体服务
	 */
	@Autowired
	private IProductInformationService productInformationService;

	/**
	 * 
	 * @Title: addProductTypeAssembleMethod 
	 * @Description: 添加产品分类信息
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public void addProductTypeAssembleMethod(Map<String, Object> parmIN)
			throws ServiceException {
		Map<String, Object> productTypeMap=new HashMap<String, Object>();//产品分类map
		List<Map<String, Object>> specificationList=new ArrayList<Map<String,Object>>();//规格属性
		List<Map<String, Object>> instanceList=new ArrayList<Map<String,Object>>();//实例属性
		productTypeMap.put("prdTypeCode", (String)parmIN.get("prdTypeCode"));//产品分类编号
		productTypeMap.put("parentCode", (String)parmIN.get("parentCode"));//上级产品分类编号
		productTypeMap.put("prdTypeName", (String)parmIN.get("prdTypeName"));//产品分类名称
		productTypeMap.put("prdTypeDesc", (String)parmIN.get("prdTypeDesc"));//分类描述
		productTypeMap.put("basePrdCategoryId", (String)parmIN.get("basePrdCategoryId"));//基础产品编号
		productTypeMap.put("categoryName", (String)parmIN.get("categoryName"));//基础产品名称
		specificationList=(List<Map<String, Object>>)parmIN.get("specificationAttributesList");
		instanceList=(List<Map<String, Object>>)parmIN.get("instanceAttributesList");
		try {
			productTypeService.addProductTypeMethod(productTypeMap);
			if(specificationList!=null&&specificationList.size()>0){//规格属性值不为空则添加属性表
				for(int i=0;i<specificationList.size();i++){//校验笔数
					String prdTypeCode=specificationList.get(i).get("prdTypeCode")==null?"":(String)specificationList.get(i).get("prdTypeCode");
					String property=specificationList.get(i).get("property")==null?"":(String)specificationList.get(i).get("property");
					String propertyName=specificationList.get(i).get("propertyName")==null?"":(String)specificationList.get(i).get("propertyName");
					if(ValidateUtil.isEmpty(prdTypeCode)){//判空
						throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：prdTypeCode不能为空");
					}
					if(ValidateUtil.isEmpty(property)){//判空
						throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：property不能为空");
					}
					if(ValidateUtil.isEmpty(propertyName)){//判空
						throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：propertyName不能为空");
					}
				}
				productTypeService.addSpecificationMethod(specificationList);
			}
			if(instanceList!=null&&instanceList.size()>0){//实例属性值不为空则添加属性表
				for(int i=0;i<instanceList.size();i++){//校验笔数
					String prdTypeCode=instanceList.get(i).get("prdTypeCode")==null?"":(String)instanceList.get(i).get("prdTypeCode");
					String property=instanceList.get(i).get("property")==null?"":(String)instanceList.get(i).get("property");
					String propertyName=instanceList.get(i).get("propertyName")==null?"":(String)instanceList.get(i).get("propertyName");
					if(ValidateUtil.isEmpty(prdTypeCode)){//判空
						throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：prdTypeCode不能为空");
					}
					if(ValidateUtil.isEmpty(property)){//判空
						throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：property不能为空");
					}
					if(ValidateUtil.isEmpty(propertyName)){//判空
						throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：propertyName不能为空");
					}
				}
				productTypeService.addInstanceMethod(instanceList);
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类信息添加失败", ex);
		}
	}
	
	/**
	 * 
	 * @Title: updateProductTypeAssembleMethod 
	 * @Description: 修改产品分类信息
	 * @param parmIN 参数列表
	 * @return int 返回对象信息
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public void updateProductTypeAssembleMethod(Map<String, Object> parmIN) throws ServiceException {
		Map<String, Object> productTypeMap=new HashMap<String, Object>();//产品分类map
		List<Map<String, Object>> specificationList=new ArrayList<Map<String,Object>>();//规格属性
		List<Map<String, Object>> instanceList=new ArrayList<Map<String,Object>>();//实例属性
		List<Map<String, Object>> tmpSpecificationList=new ArrayList<Map<String,Object>>();//规格属性
		List<Map<String, Object>> tmpInstanceList=new ArrayList<Map<String,Object>>();//实例属性
		productTypeMap.put("prdTypeCode", (String)parmIN.get("prdTypeCode"));//产品分类编号
		productTypeMap.put("parentCode", (String)parmIN.get("parentCode"));//上级产品分类编号
		productTypeMap.put("prdTypeName", (String)parmIN.get("prdTypeName"));//产品分类名称
		productTypeMap.put("prdTypeDesc", (String)parmIN.get("prdTypeDesc"));//分类描述
		productTypeMap.put("basePrdCategoryId", (String)parmIN.get("basePrdCategoryId"));//基础产品编号
		productTypeMap.put("categoryName", (String)parmIN.get("categoryName"));//基础产品名称
		specificationList=(List<Map<String, Object>>)parmIN.get("specificationAttributesList");
		instanceList=(List<Map<String, Object>>)parmIN.get("instanceAttributesList");
		String prdTypeCode=(String)parmIN.get("prdTypeCode");
		try {
			//修改产品分类信息
			productTypeService.updateProductTypeMethod(productTypeMap);
			//查询规格属性信息
			tmpSpecificationList=productTypeService.querySpecificationMethod(prdTypeCode);
			if(tmpSpecificationList==null){
				if(specificationList!=null && specificationList.size()>0){
					for(int i=0;i<specificationList.size();i++){//校验笔数
						String prdTypeCodetmp=specificationList.get(i).get("prdTypeCode")==null?"":(String)specificationList.get(i).get("prdTypeCode");
						String property=specificationList.get(i).get("property")==null?"":(String)specificationList.get(i).get("property");
						String propertyName=specificationList.get(i).get("propertyName")==null?"":(String)specificationList.get(i).get("propertyName");
						if(ValidateUtil.isEmpty(prdTypeCodetmp)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：prdTypeCode不能为空");
						}
						if(ValidateUtil.isEmpty(property)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：property不能为空");
						}
						if(ValidateUtil.isEmpty(propertyName)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：propertyName不能为空");
						}
					}
					//添加规格属性表
					productTypeService.addSpecificationMethod(specificationList);
				}
			}else{
				List<Map<String, Object>> tmpSpeciResultList=new ArrayList<Map<String,Object>>();
				for(int i=0;i<tmpSpecificationList.size();i++){
					Map<String, Object> tmpSpeciMap=new HashMap<String, Object>();//产品分类map-查询库表
					String property=(String)tmpSpecificationList.get(i).get("property");
					//查询产品配置里是否有规格属性
					tmpSpeciResultList=productInformationService.queryAttributeParameterMethod("", property, "", "", "");
					if(tmpSpeciResultList!=null && tmpSpeciResultList.size()>0){
						if(specificationList!=null && specificationList.size()>0){
							String tmpFlag="1";
							for(int j=0;j<specificationList.size();j++){
								String tmpProperty=(String)specificationList.get(j).get("property");
								if(tmpProperty.equals(property)){
									tmpFlag="0";
								}
							}
							if(tmpFlag.equals("1")){
								logger.error("Exception from " + this.getClass().getName() + "updateProductTypeMethod");
								throw new ServiceException(BankmanageErrorCodeConstants.PPBM0158, "规格属性已配置属性值");
							}
						}else{
							logger.error("Exception from " + this.getClass().getName() + "updateProductTypeMethod");
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0158, "规格属性已配置属性值");
						}
					}
			}
				//删除规格属性表信息
				productTypeService.deleteSpecificationMethod(prdTypeCode);
				if(specificationList!=null&&specificationList.size()>0){
					for(int i=0;i<specificationList.size();i++){//校验笔数
						String prdTypeCodetmp=specificationList.get(i).get("prdTypeCode")==null?"":(String)specificationList.get(i).get("prdTypeCode");
						String property=specificationList.get(i).get("property")==null?"":(String)specificationList.get(i).get("property");
						String propertyName=specificationList.get(i).get("propertyName")==null?"":(String)specificationList.get(i).get("propertyName");
						if(ValidateUtil.isEmpty(prdTypeCodetmp)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：prdTypeCode不能为空");
						}
						if(ValidateUtil.isEmpty(property)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：property不能为空");
						}
						if(ValidateUtil.isEmpty(propertyName)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类规格属性添加失败：propertyName不能为空");
						}
					}
					//添加规格属性表
					productTypeService.addSpecificationMethod(specificationList);
				}
			}
			//查询实例属性信息
			tmpInstanceList=productTypeService.queryInstanceMethod(prdTypeCode);
			if(tmpInstanceList==null){
				if(instanceList!=null && instanceList.size()>0){
					for(int i=0;i<instanceList.size();i++){//校验笔数
						String prdTypeCodeTmp=instanceList.get(i).get("prdTypeCode")==null?"":(String)instanceList.get(i).get("prdTypeCode");
						String property=instanceList.get(i).get("property")==null?"":(String)instanceList.get(i).get("property");
						String propertyName=instanceList.get(i).get("propertyName")==null?"":(String)instanceList.get(i).get("propertyName");
						if(ValidateUtil.isEmpty(prdTypeCodeTmp)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：prdTypeCode不能为空");
						}
						if(ValidateUtil.isEmpty(property)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：property不能为空");
						}
						if(ValidateUtil.isEmpty(propertyName)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：propertyName不能为空");
						}
					}
					//添加实例属性表
					productTypeService.addInstanceMethod(instanceList);
				}
			}else{
				List<Map<String, Object>> tmpInstanceResultList=new ArrayList<Map<String,Object>>();
				for(int i=0;i<tmpInstanceList.size();i++){
					Map<String, Object> tmpInstanceMap=new HashMap<String, Object>();//产品分类map-查询库表
					String property=(String)tmpInstanceList.get(i).get("property");
					//查询产品配置里是否实例属性
					tmpInstanceResultList=productInformationService.queryAttributeParameterMethod("", property, "", "", "");
					if(tmpInstanceResultList!=null && tmpInstanceResultList.size()>0){
						if(instanceList!=null && instanceList.size()>0){
							String tmpFlag="1";
							for(int j=0;j<instanceList.size();j++){
								String tmpProperty=(String)instanceList.get(j).get("property");
								if(tmpProperty.equals(property)){
									tmpFlag="0";
								}
							}
							if(tmpFlag.equals("1")){
								logger.error("Exception from " + this.getClass().getName() + "updateProductTypeMethod");
								throw new ServiceException(BankmanageErrorCodeConstants.PPBM0159, "实例属性已配置属性值");
							}
						}else{
							logger.error("Exception from " + this.getClass().getName() + "updateProductTypeMethod");
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0159, "实例属性已配置属性值");
						}
					}
				}
				//删除规格属性表信息
				int retult=0;
				retult=productTypeService.deleteInstanceMethod(prdTypeCode);
				if(instanceList!=null && instanceList.size()>0){
					for(int i=0;i<instanceList.size();i++){//校验笔数
						String prdTypeCodeTmp=instanceList.get(i).get("prdTypeCode")==null?"":(String)instanceList.get(i).get("prdTypeCode");
						String property=instanceList.get(i).get("property")==null?"":(String)instanceList.get(i).get("property");
						String propertyName=instanceList.get(i).get("propertyName")==null?"":(String)instanceList.get(i).get("propertyName");
						if(ValidateUtil.isEmpty(prdTypeCodeTmp)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：prdTypeCode不能为空");
						}
						if(ValidateUtil.isEmpty(property)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：property不能为空");
						}
						if(ValidateUtil.isEmpty(propertyName)){//判空
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类实例属性添加失败：propertyName不能为空");
						}
					}
					//添加实例属性表
					productTypeService.addInstanceMethod(instanceList);
				}
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "updateProductTypeMethod", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "updateProductTypeMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类信息修改失败", ex);
		}
	}

	/**
	 * 
	 * @Title: deleteProductTypeAssembleMethod 
	 * @Description: 删除产品分类信息
	 * @param prdTypeCode 产品分类编号
	 * @return Map<String,Object> 返回对象信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public void deleteProductTypeAssembleMethod(String prdTypeCode)
			throws ServiceException {
		Map<String, Object> productTypeMap=new HashMap<String, Object>();//产品分类map
		Map<String, Object> tmpCommMap=new HashMap<String, Object>();//公共临时map
		List<Map<String, Object>> tmpSpecificationList=new ArrayList<Map<String,Object>>();//规格属性
		List<Map<String, Object>> tmpInstanceList=new ArrayList<Map<String,Object>>();//实例属性
		productTypeMap.put("parentCode", prdTypeCode);//上级产品分类编号
		tmpCommMap.put("prdTypeCode", prdTypeCode);
		try {
			//删除产品分类信息
			productTypeService.deleteProductTypeMethod(prdTypeCode);
			//查询规格属性信息
			tmpSpecificationList=productTypeService.querySpecificationMethod(prdTypeCode);
			if(tmpSpecificationList!=null){
				//删除规格属性信息
				productTypeService.deleteSpecificationMethod(prdTypeCode);
			}
			//查询实例属性信息
			tmpInstanceList=productTypeService.queryInstanceMethod(prdTypeCode);
			if(tmpInstanceList!=null){
				//删除实例属性信息
				productTypeService.deleteInstanceMethod(prdTypeCode);
			}
		} catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductTypeMethod", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductTypeMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品分类失败", ex);
		}
	}

	
}
