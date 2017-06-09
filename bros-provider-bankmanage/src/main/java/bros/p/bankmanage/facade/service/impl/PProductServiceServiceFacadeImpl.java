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
import bros.p.bankmanage.facade.service.IPProductServiceServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductServiceService;
import bros.provider.parent.bankmanage.product.service.IProductTypeService;
import bros.provider.parent.bankmanage.shelf.service.IShelfGoodsInfoService;

@Component("pproductServiceServiceFacade")
public class PProductServiceServiceFacadeImpl implements
		IPProductServiceServiceFacade {
	/**
     * 产品实体服务
     */
	@Autowired
	private IProductServiceService productServiceService;
	/**
     * 产品分类实体服务
     */
	@Autowired
	private IProductTypeService productTypeService;
	/**
     * 货架商品信息服务
     */
	@Autowired
	private IShelfGoodsInfoService shelfGoodsInfoService;

	/**
	 * 
	 * @Title: addProductServiceMethod 
	 * @Description: 新增产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="p0000155")
	public ResponseEntity addProductServiceMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//查询产品服务信息
		List<Map<String, Object>> resultPrdServiceList=new ArrayList<Map<String,Object>>();//产品分类map-查询库表
		String prdSvrCode=bodyMap.get("prdSvrCode")==null?"":bodyMap.get("prdSvrCode").toString();
		resultPrdServiceList=productServiceService.queryProductServiceMethod(prdSvrCode, "", "");
		if(resultPrdServiceList!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0163, "该产品服务信息已存在");
		}
		//查询产品分类信息
		List<Map<String, Object>> tmpProductTypeList=new ArrayList<Map<String,Object>>();//产品分类map-查询库表
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		tmpProductTypeList=productTypeService.queryProductTypeMethod(prdTypeCode, "", "");
		if(tmpProductTypeList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0157, "产品分类信息不存在");
		}
		productServiceService.addProductServiceMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@Validation(value="p0000155")
	public ResponseEntity updateProductServiceMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		//查询产品服务信息
		List<Map<String, Object>> resultPrdServiceList=new ArrayList<Map<String,Object>>();//产品分类map-查询库表
		String prdSvrCode=bodyMap.get("prdSvrCode")==null?"":bodyMap.get("prdSvrCode").toString();
		resultPrdServiceList=productServiceService.queryProductServiceMethod(prdSvrCode, "", "");
		if(resultPrdServiceList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0164, "该产品服务信息不存在");
		}
		productServiceService.updateProductServiceMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@Validation(value="p0000156")
	public ResponseEntity deleteProductServiceMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdSvrCode=bodyMap.get("prdSvrCode")==null?"":bodyMap.get("prdSvrCode").toString();
		//查询产品服务信息
		List<Map<String, Object>> resultPrdServiceList=new ArrayList<Map<String,Object>>();//产品分类map-查询库表
		resultPrdServiceList=productServiceService.queryProductServiceMethod(prdSvrCode, "", "");
		if(resultPrdServiceList==null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0164, "该产品服务信息不存在");
		}
		//查询产品服务货架信息
		Map<String, Object> tmpGoodsMap=new HashMap<String, Object>();
		List<Map<String, Object>> resultGoodsList=new ArrayList<Map<String,Object>>();
		tmpGoodsMap.put("goodsCode",prdSvrCode);
		tmpGoodsMap.put("goodsFlag","00");//00-上架
		resultGoodsList=shelfGoodsInfoService.queryShelfGoodsInfoMethod(tmpGoodsMap);
		if(resultGoodsList!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0165, "该产品服务已经上架");
		}
		productServiceService.deleteProductServiceMethod(prdSvrCode);
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
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
	
	public ResponseEntity queryProductServiceMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String prdSvrCode=bodyMap.get("prdSvrCode")==null?"":bodyMap.get("prdSvrCode").toString();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String prdSvrName=bodyMap.get("prdSvrName")==null?"":bodyMap.get("prdSvrName").toString();
		returnList=productServiceService.queryProductServiceMethod(prdSvrCode, prdTypeCode, prdSvrName);
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
	 * 分页查询产品服务方法
	 */
	@Validation(value="p0000157")
	public ResponseEntity queryProductServicePageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String prdSvrCode=bodyMap.get("prdSvrCode")==null?"":bodyMap.get("prdSvrCode").toString();
		String prdTypeCode=bodyMap.get("prdTypeCode")==null?"":bodyMap.get("prdTypeCode").toString();
		String prdSvrName=bodyMap.get("prdSvrName")==null?"":bodyMap.get("prdSvrName").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		responseMap=productServiceService.queryProductServicePageMethod(prdSvrCode, prdTypeCode, prdSvrName,
				Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * 单笔查询产品服务方法
	 */
	@Validation(value="p0000156")
	public ResponseEntity queryProductServiceOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String prdSvrCode=bodyMap.get("prdSvrCode")==null?"":bodyMap.get("prdSvrCode").toString();
		responseMap=productServiceService.queryProductServiceOneMethod(prdSvrCode);
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

}
