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
import bros.p.bankmanage.facade.service.IPProductCateServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductCateService;
/**
 * 
 * @ClassName: PProductCateServiceFacadeImpl 
 * @Description: 产品目录接口实现类
 * @author huangdazhou
 * @date 2016年6月30日 下午2:26:27 
 * @version 1.0
 */
@Component("pproductCateServiceFacade")
public class PProductCateServiceFacadeImpl implements IPProductCateServiceFacade {
	@Autowired
	private IProductCateService productCateService;
	/**
	 * 
	 * @Title: addProductCateMethod 
	 * @Description: 新增产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="p0000159")
	public ResponseEntity addProductCateMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//查询产品目录信息
		int totalNum=0;
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		totalNum=productCateService.queryProductCateNumMethod(cateCode,"","");
		if(totalNum!=0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0166, "该产品目录信息已存在");
		}
		productCateService.addProductCateMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@Validation(value="p0000159")
	public ResponseEntity updateProductCateMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//查询产品目录信息
		int totalNum=0;
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		totalNum=productCateService.queryProductCateNumMethod(cateCode,"","");
		if(totalNum==0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0167, "该产品目录信息不存在");
		}
		productCateService.updateProductCateMethod(bodyMap);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
	@Validation(value="p0000160")
	public ResponseEntity deleteProductCateMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		//查询产品目录信息
		int totalNum=0;
		totalNum=productCateService.queryProductCateNumMethod(cateCode,"","");
		if(totalNum==0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0167, "该产品目录信息不存在");
		}
		//查询产品目录是否存在子目录
		int totalNum1=0;
		String parentCate=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		totalNum1=productCateService.queryProductCateNumMethod("","",parentCate);
		if(totalNum1!=0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0165, "该产品目录存在子目录");
		}
		productCateService.deleteProductCateMethod(cateCode);
		ResponseEntity entity=new ResponseEntity();
		return entity;
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
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		String cateName=bodyMap.get("cateName")==null?"":bodyMap.get("cateName").toString();
		String parentCate=bodyMap.get("parentCate")==null?"":bodyMap.get("parentCate").toString();
		returnList=productCateService.queryProductCateMethod(cateCode, cateName, parentCate);
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
	 * @Title: queryProductCatePageMethod 
	 * @Description: 分页查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="p0000153")
	public ResponseEntity queryProductCatePageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		String cateName=bodyMap.get("cateName")==null?"":bodyMap.get("cateName").toString();
		String parentCate=bodyMap.get("parentCate")==null?"":bodyMap.get("parentCate").toString();
		String pageNo=String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo"));
		String pageSize=String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize"));
		resultMap=productCateService.queryProductCatePageMethod(cateCode, cateName, parentCate,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));;
		if((Integer)resultMap.get("totalNum")==0){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}else{
			responseMap.put("totalNum", resultMap.get("totalNum")+"");
			responseMap.put("returnList", resultMap.get("returnList"));
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}

	/**
	 * @Title: queryProductCateOneMethod 
	 * @Description: 单笔查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	@Validation(value="p0000160")
	public ResponseEntity queryProductCateOneMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		String cateCode=bodyMap.get("cateCode")==null?"":bodyMap.get("cateCode").toString();
		String cateName=bodyMap.get("cateName")==null?"":bodyMap.get("cateName").toString();
		String parentCate=bodyMap.get("parentCate")==null?"":bodyMap.get("parentCate").toString();
		responseMap=productCateService.queryProductCateOneMethod(cateCode);
		if(responseMap==null){
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0172, "查询无记录");
		}else{
			responseMap.put("totalNum", returnList.size()+"");
			responseMap.put("returnList", returnList);
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}
	

}
