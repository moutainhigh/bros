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
import bros.p.bankmanage.facade.service.IPProductTypeServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductInformationService;
import bros.provider.parent.bankmanage.product.service.IProductTypeAssembleService;
import bros.provider.parent.bankmanage.product.service.IProductTypeService;
/**
 * 
 * @ClassName: PProductTypeServiceFacadeImpl 
 * @Description: 产品分类接口实现
 * @author huangdazhou
 * @date 2016年6月27日 下午2:13:45 
 * @version 1.0
 */
@Component("pproductTypeServiceFacade")
public class PProductTypeServiceFacadeImpl implements IPProductTypeServiceFacade {
	/**
     * 产品分类实体服务
     */
	@Autowired
	private IProductTypeService productTypeService;
	/**
     * 产品分类组装服务
     */
	@Autowired
	private IProductTypeAssembleService productTypeAssembleService;
	/**
     * 产品服务
     */
	@Autowired
	private IProductInformationService productInformationService;

	/**
	 * 
	 * @Title: addProductTypeMethod 
	 * @Description: 添加产品分类信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000151")
	public ResponseEntity addProductTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//查询产品分类信息
		List<Map<String, Object>> resultPrdTypeList=new ArrayList<Map<String,Object>>();//产品分类map-查询库表
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		resultPrdTypeList=productTypeService.queryProductTypeMethod(prdTypeCode, "", "");
		if(resultPrdTypeList!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0156, "该产品分类信息已存在");
		}
		//添加产品分类
		productTypeAssembleService.addProductTypeAssembleMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@Validation(value="p0000151")
	public ResponseEntity updateProductTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//查询产品分类信息
		List<Map<String, Object>> resultPrdTypeList=new ArrayList<Map<String,Object>>();//产品分类map-查询库表
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		resultPrdTypeList=productTypeService.queryProductTypeMethod(prdTypeCode, "", "");
		if(resultPrdTypeList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0157, "该产品分类信息不存在");
		}
		productTypeAssembleService.updateProductTypeAssembleMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
	}

	/**
	 * 
	 * @Title: deleteProductTypeMethod 
	 * @Description: 删除产品分类信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000152")
	public ResponseEntity deleteProductTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> tmpProductTypeList=new ArrayList<Map<String,Object>>();//产品分类子产品
		String parentCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String prdTypeCode=(String)bodyMap.get("prdTypeCode");
		//查询是否有子产品分类
		tmpProductTypeList=productTypeService.queryProductTypeMethod("", parentCode, "");
		if(tmpProductTypeList!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0160, "该产品分类存在子产品分类");
		}
		//查询改产品分类是否已经配置产品
		List<Map<String, Object>> tmpPrdServiceList=new ArrayList<Map<String,Object>>();//产品配置map
		Map<String, Object> tmpPrdServiceMap=new HashMap<String, Object>();//产品分类map
		tmpPrdServiceList=productInformationService.queryProductInformationMethod("", "", "", prdTypeCode, "", "");
		if(tmpPrdServiceList!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0161, "产品分类已配置产品信息");
		}
		productTypeAssembleService.deleteProductTypeAssembleMethod(prdTypeCode);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String prdTypeCode=bodyMap.get("parentCode")==null?"":bodyMap.get("parentCode").toString();
		String parentCode=bodyMap.get("parentCode")==null?"":bodyMap.get("parentCode").toString();
		String prdTypeName=bodyMap.get("prdTypeName")==null?"":bodyMap.get("prdTypeName").toString();
		returnList=productTypeService.queryProductTypeMethod(prdTypeCode, parentCode, prdTypeName);
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
	 * @Title: querySpecificationAttributesMethod 
	 * @Description: 查询规格属性信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	
	public ResponseEntity querySpecificationAttributesMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String prdTypeCode=(String)bodyMap.get("prdTypeCode");
		//String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		//String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		returnList=productTypeService.querySpecificationMethod(prdTypeCode);
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
	 * @Title: queryInstanceAttributesMethod 
	 * @Description: 查询实例属性信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	
	public ResponseEntity queryInstanceAttributesMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String prdTypeCode=(String)bodyMap.get("prdTypeCode");
		returnList=productTypeService.queryInstanceMethod(prdTypeCode);
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
	 * 分页查询产品分类信息
	 */
	@Validation(value="p0000153")
	public ResponseEntity queryProductTypePageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdTypeCode=bodyMap.get("parentCode")==null?"":bodyMap.get("parentCode").toString();
		String parentCode=bodyMap.get("parentCode")==null?"":bodyMap.get("parentCode").toString();
		String prdTypeName=bodyMap.get("prdTypeName")==null?"":bodyMap.get("prdTypeName").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		responseMap=productTypeService.queryProductTypePageMethod(prdTypeCode, parentCode, prdTypeName,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 单笔查询产品分类信息
	 */
	public ResponseEntity queryProductTypeOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdTypeCode=bodyMap.get("parentCode")==null?"":bodyMap.get("parentCode").toString();
		responseMap=productTypeService.queryProductTypeOneMethod(prdTypeCode);
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 分页查询规格属性信息
	 */
	@Validation(value="p0000154")
	public ResponseEntity querySpecificationAttributesPageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		responseMap=productTypeService.querySpecificationPageMethod(prdTypeCode, 
				Integer.parseInt(pageNo),Integer.parseInt(pageSize) );
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 单笔查询规格属性信息
	 */
	public ResponseEntity querySpecificationAttributesOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String property=bodyMap.get("property")==null?"":bodyMap.get("property").toString();
		responseMap=productTypeService.querySpecificationOneMethod(prdTypeCode, property);
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 分页查询实例属性信息
	 */
	@Validation(value="p0000154")
	public ResponseEntity queryInstanceAttributesPageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		responseMap=productTypeService.queryInstancePageMethod(prdTypeCode, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 单笔查询实例属性信息
	 */
	public ResponseEntity queryInstanceAttributesOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String property=bodyMap.get("property")==null?"":bodyMap.get("property").toString();
		responseMap=productTypeService.queryInstanceOneMethod(prdTypeCode, property);
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

}
