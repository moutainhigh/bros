package bros.p.bankmanage.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPProductInformationServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductCateService;
import bros.provider.parent.bankmanage.product.service.IProductInformationAssembleService;
import bros.provider.parent.bankmanage.product.service.IProductInformationService;
import bros.provider.parent.bankmanage.product.service.IProductTypeService;
import bros.provider.parent.bankmanage.shelf.service.IShelfGoodsInfoService;
/**
 * 
 * @ClassName: PProductInformationServiceFacadeImpl 
 * @Description: 产品信息服务发布实现类
 * @author huangdazhou
 * @date 2016年6月30日 下午5:01:27 
 * @version 1.0
 */
@Component("pproductInformationServiceFacade")
public class PProductInformationServiceFacadeImpl implements
		IPProductInformationServiceFacade {
	@Autowired
	private IProductInformationService productInformationService; 
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private IProductInformationAssembleService productInformationAssembleService;
	@Autowired
	private IProductCateService productCateService;
	@Autowired
	private IShelfGoodsInfoService shelfGoodsInfoService;
	/**
	 * 
	 * @Title: addProductInformationMethod 
	 * @Description: 添加产品信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000161")
	public ResponseEntity addProductInformationMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		//查询产品信息
		Map<String, Object> tmpproductMap=new HashMap<String, Object>();
		List<Map<String, Object>> resultproductList=new ArrayList<Map<String,Object>>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		resultproductList=productInformationService.queryProductInformationMethod(productCode, "", "", "", "", "");
		if(resultproductList!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0169, "该产品信息已存在");
		}
		//查询产品分类信息
		List<Map<String, Object>> resultPrdTypeList=new ArrayList<Map<String,Object>>();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		resultPrdTypeList=productTypeService.queryProductTypeMethod(prdTypeCode, "", "");
		if(resultPrdTypeList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0157, "该产品分类信息不存在");
		}
		//查询产品目录信息
		Map<String, Object> tmpPrdCateMap=new HashMap<String, Object>();//产品分类map-查询库表
		int totalNum=0;
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		tmpPrdCateMap.put("cateCode", (String)bodyMap.get("cateCode"));
		totalNum=productCateService.queryProductCateNumMethod(cateCode,"","");
		if(totalNum==0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0167, "该产品目录信息不存在");
		}
		productInformationAssembleService.addProductInformationAssembleMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@Validation(value="p0000161")
	public ResponseEntity updateProductInformationMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		//查询产品信息
		Map<String, Object> tmpproductMap=new HashMap<String, Object>();
		List<Map<String, Object>> resultproductList=new ArrayList<Map<String,Object>>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		resultproductList=productInformationService.queryProductInformationMethod(productCode, "", "", "", "", "");
		if(resultproductList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0170, "该产品信息不存在");
		}
		productInformationAssembleService.updateProductInformationAssembleMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@SuppressWarnings("unchecked")
	@Validation(value="p0000162")
	public ResponseEntity deleteProductInformationMethod(
			Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> requestMap=new HashMap<String, Object>();
		String productCode=(String)bodyMap.get("productCode");
		//查询产品信息
		List<Map<String, Object>> resultproductList=new ArrayList<Map<String,Object>>();
		resultproductList=productInformationService.queryProductInformationMethod(productCode, "", "", "", "", "");
		if(resultproductList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0170, "该产品信息不存在");
		}
		//此处查询该产品服务是否已经上架
		Map<String, Object> tmpGoodsMap=new HashMap<String, Object>();
		List<Map<String, Object>> resultGoodsList=new ArrayList<Map<String,Object>>();
		tmpGoodsMap.put("goodsCode",productCode);
		tmpGoodsMap.put("goodsFlag","00");//商品标志00-上架
		resultGoodsList=shelfGoodsInfoService.queryShelfGoodsInfoMethod(tmpGoodsMap);
		 
		if(resultGoodsList!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0171, "该产品已经上架");
		}
		productInformationAssembleService.deleteProductInformationAssembleMethod(productCode);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@Validation(value="p0000153")
	public ResponseEntity queryProductInformationMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		String prdName=bodyMap.get("prdName")==null?"":bodyMap.get("prdName").toString();
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String modifiedBy=bodyMap.get("modifiedBy")==null?"":bodyMap.get("modifiedBy").toString();
		String modifiedDate=bodyMap.get("modifiedDate")==null?"":bodyMap.get("modifiedDate").toString();
		returnList=productInformationService.queryProductInformationMethod(productCode, prdName, cateCode, prdTypeCode, modifiedBy, modifiedDate);
		if(returnList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}else{
			responseMap.put("totalNum", returnList.size()+"");
			responseMap.put("returnList", returnList);
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
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
	
	public ResponseEntity queryAttributeParameterMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		String property=bodyMap.get("property")==null?"":bodyMap.get("property").toString();
		String setValue=bodyMap.get("setValue")==null?"":bodyMap.get("setValue").toString();
		String description=bodyMap.get("description")==null?"":bodyMap.get("description").toString();
		String propertyName=bodyMap.get("propertyName")==null?"":bodyMap.get("propertyName").toString();
		returnList=productInformationService.queryAttributeParameterMethod(productCode, property, propertyName, setValue, description);
		if(returnList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}else{
			responseMap.put("totalNum", returnList.size()+"");
			responseMap.put("returnList", returnList);
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
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
	@Validation(value="p0000118")
	public ResponseEntity queryAttributeParameterByProductTypeCodeMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException{
		//返回Map
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//获取上送的产品分类
		String prdTypeCode = (String) (bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode"));
		
		//规格属性列表
		List<Map<String,Object>> propertyList = new ArrayList<Map<String,Object>>();
		
		propertyList = productTypeService.querySpecificationMethod(prdTypeCode);
		
		//把规格属性信息放到返回Map中
		returnMap.put("propertyList", propertyList);
		
		if(propertyList != null && propertyList.size() > 0 ){
			
			//循环查询属性对应的参数值
			for(int i = 0; i < propertyList.size(); i++){
				//商品信息的List
				List<Map<String,Object>> propertyValueInfoList = new ArrayList<Map<String,Object>>();
				Map<String,Object> paramMap = new HashMap<String,Object>();
				
				String property = (String)propertyList.get(i).get("property");//产品属性
				
				paramMap.put("property", property);
				paramMap.put("prdTypeCode", prdTypeCode);
				
				propertyValueInfoList = productInformationService.queryPropertyValueByProductTypeCodeMethod(paramMap);
				
				//查询出的属性参数值放到返回Map中
				returnMap.put(property, propertyValueInfoList);
			}
		} 
		
		ResponseEntity entity = new ResponseEntity(returnMap);
		
		return entity;
	}

	/**
	 * 分页查询产品信息
	 */
	@Validation(value="p0000153")
	public ResponseEntity queryProductInformationPageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		String prdName=bodyMap.get("prdName")==null?"":bodyMap.get("prdName").toString();
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String modifiedBy=bodyMap.get("modifiedBy")==null?"":bodyMap.get("modifiedBy").toString();
		String modifiedDate=bodyMap.get("modifiedDate")==null?"":bodyMap.get("modifiedDate").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		responseMap=productInformationService.queryProductInformationPageMethod(productCode, prdName, cateCode, prdTypeCode, modifiedBy, modifiedDate,
				Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 单笔查询产品信息
	 */
	public ResponseEntity queryProductInformationOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		responseMap=productInformationService.queryProductInformationOneMethod(productCode);
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 分页查询规格属性参数信息
	 */
	@Validation(value="p0000153")
	public ResponseEntity queryAttributePageMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		String property=bodyMap.get("property")==null?"":bodyMap.get("property").toString();
		String setValue=bodyMap.get("setValue")==null?"":bodyMap.get("setValue").toString();
		String description=bodyMap.get("description")==null?"":bodyMap.get("description").toString();
		String propertyName=bodyMap.get("propertyName")==null?"":bodyMap.get("propertyName").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		responseMap=productInformationService.queryAttributePageMethod(productCode, property, propertyName, setValue, description,
				Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 单笔查询规格属性参数信息
	 */
	public ResponseEntity queryAttributeOneMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		String property=bodyMap.get("property")==null?"":bodyMap.get("property").toString();
		String setValue=bodyMap.get("setValue")==null?"":bodyMap.get("setValue").toString();
		responseMap=productInformationService.queryAttributeOneMethod(productCode, property,setValue);
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
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
	public ResponseEntity queryAttributeInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		//产品分类编码
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		//产品编码
		String productCode=bodyMap.get("productCode")==null?"":bodyMap.get("productCode").toString();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		returnList = productInformationService.queryAttributeInfoMethod(prdTypeCode, productCode);
		if(returnList != null && returnList.size() > 0){
			for (int i = 0; i < returnList.size(); i++) {
				String property = (String) returnList.get(i).get("property");
//				String propertyName = (String) returnList.get(i).get("propertyName");
				String setValue = (String) returnList.get(i).get("setValue");
//				String description = (String) returnList.get(i).get("description");
				responseMap.put(property, setValue);
			}
			String prdCode = (String) returnList.get(0).get("productCode");
			String prdName = (String) returnList.get(0).get("prdName");
			String cateCode = (String) returnList.get(0).get("cateCode");
			String prodTypeCode = (String) returnList.get(0).get("prdTypeCode");
			String parentCode = (String) returnList.get(0).get("parentCode");
			String parentName = (String) returnList.get(0).get("parentName");
			responseMap.put("goodsCode", prdCode);
			responseMap.put("goodsName", prdName);
			responseMap.put("cateCode", cateCode);
			responseMap.put("prdTypeCode", prodTypeCode);
			responseMap.put("parentCode", parentCode);
			responseMap.put("parentName", parentName);
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}
	
}
