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
import bros.p.bankmanage.facade.service.IPShelfInfoServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.mall.service.IMallInfoService;
import bros.provider.parent.bankmanage.shelf.service.IShelfGoodsInfoService;
import bros.provider.parent.bankmanage.shelf.service.IShelfInfoService;

/**
 * 
 * @ClassName: PShelfInfoServiceFacadeImpl 
 * @Description: 货架信息对外接口实现类
 * @author gaoyongjing 
 * @date 2016年7月1日 下午15:35:28 
 * @version 1.0
 */
@Component("pshelfInfoServiceFacade")
public class PShelfInfoServiceFacadeImpl implements IPShelfInfoServiceFacade {
	/**
	 * 货架信息服务
	 */
	@Autowired
	private IShelfInfoService shelfInfoService;
	/**
	 * 货架商品信息服务
	 */
	@Autowired
	private IShelfGoodsInfoService shelfGoodsInfoService;
	/**
	 * 商城信息服务
	 */
	@Autowired
	private IMallInfoService mallInfoService;
	/**
	 * 
	 * @Title: addShelfInfoMethod
	 * @Description: 新增货架信息
	 * @param headMap  头信息
	 * @param paramMap  货架信息，包括以下
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000110")
	@Override
	public ResponseEntity addShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));      //货架编码
		String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));               //商城id
		String parentShelf = (String) (paramMap.get("parentShelf")==null?"":paramMap.get("parentShelf"));//上级货架编码
		String shelfName = (String) (paramMap.get("shelfName")==null?"":paramMap.get("shelfName"));      //货架名称
		String shelfDesc = (String) (paramMap.get("shelfDesc")==null?"":paramMap.get("shelfDesc"));      //货架描述
		
		//temp1 查询商城信息是否存在
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("mallId", mallId);//商城id
		List<Map<String,Object>> mallInfoList = new ArrayList<Map<String,Object>>();
		
		mallInfoList = mallInfoService.queryMallInfoMethod(param);
		
		if(mallInfoList == null || mallInfoList.size() <= 0){
			//商城信息不存在，不允许添加货架信息
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0120);
		}
		
		//temp2 查询货架信息是否存在
		Map<String,Object> shelfInfoMap = new HashMap<String,Object>();
		
		shelfInfoMap = shelfInfoService.queryShelfInfoByObjectIdMethod(shelfCode,"");
		
		if(shelfInfoMap != null && shelfInfoMap.size() > 0){
			//已经存在不允许添加
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0104);
		} 
				
		//temp3上级货架编码不为空时，查询上级货架是否存在
		if(!"".equals(parentShelf)){
			Map<String,Object> parentShelfInfoMap = new HashMap<String,Object>();
			
			parentShelfInfoMap = shelfInfoService.queryShelfInfoByObjectIdMethod(parentShelf,mallId);
			
			if(parentShelfInfoMap == null || parentShelfInfoMap.size() <= 0){
				//上级货架信息不存在，不允许新增货架信息
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0124);
			} 
			//temp4 上级货架存在时，判断货架标志，如果为下架状态则不允许在此货架下新增货架信息
			String shelfFlag = (String)parentShelfInfoMap.get("shelfFlag");
			if("01".equals(shelfFlag)){
				//上级货架为下架状态，不允许新增货架信息
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0129);
			}
		}
		
		//temp5 货架信息不存在，则新增货架信息
		shelfInfoService.addShelfInfoMethod(shelfCode, shelfName, shelfDesc, parentShelf, mallId);
		
		return entity;
	}

	/**
	 * 
	 * @Title: updateShelfInfoMethod
	 * @Description: 修改货架信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000119")
	@Override
	public ResponseEntity updateShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));
		String shelfName = (String) (paramMap.get("shelfName")==null?"":paramMap.get("shelfName")); 
		String shelfDesc = (String) (paramMap.get("shelfDesc")==null?"":paramMap.get("shelfDesc")); 
		String parentShelf = (String) (paramMap.get("parentShelf")==null?"":paramMap.get("parentShelf"));
		String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId")); 
		String shelfFlag = (String) (paramMap.get("shelfFlag")==null?"":paramMap.get("shelfFlag"));
		
		//temp1 查询货架信息是否存在
		Map<String,Object> shelfInfoMap = new HashMap<String,Object>();
		
		shelfInfoMap = shelfInfoService.queryShelfInfoByObjectIdMethod(shelfCode,mallId);
		
		if(shelfInfoMap == null || shelfInfoMap.size() <= 0){
			//不存在不允许修改
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0105);
		}
		
		if("01".equals(shelfFlag)){//如果要下架该货架则需校验2、3步
			
			//temp2 判断该货架是否存在未下架的子货架信息
			Map<String,Object> shelfParamMap = new HashMap<String, Object>();
			shelfParamMap.put("parentShelf", shelfCode);
			shelfParamMap.put("mallId", mallId);
			shelfParamMap.put("shelfFlag", "00");
			List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
			
			shelfInfoList = shelfInfoService.queryShelfInfoMethod(shelfParamMap);
			
			if(shelfInfoList != null && shelfInfoList.size() > 0){
				//存在已上架的下级货架信息，不允许下架
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0130);
			}
			
			//temp3 判断货架下是否有已上架的商品信息
			Map<String,Object> paramGoodsMap = new HashMap<String,Object>();
			List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
			
			paramGoodsMap.put("shelfCode", shelfCode);
			paramGoodsMap.put("goodsFlag", "00");//商品标志00-上架
			
			shelfGoodsList = shelfGoodsInfoService.queryShelfGoodsInfoMethod(paramGoodsMap);
			
			if(shelfGoodsList != null && shelfGoodsList.size() > 0){
				//存在商品信息，不允许下架
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0131);
			}
		}
		
		//temp4 校验完成，可以修改货架信息
		shelfInfoService.updateShelfInfoMethod(shelfCode, shelfName, shelfDesc, parentShelf, mallId, shelfFlag);
		
		return entity;
	}

	/**
	 * 
	 * @Title: deleteShelfInfoMethod
	 * @Description: 删除货架信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param mallId 商城ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000111")
	@Override
	public ResponseEntity deleteShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));  
		String mallId = (String) (paramMap.get("mallId")==null?"":paramMap.get("mallId"));
		
		//temp1 查询货架信息是否存在
		Map<String,Object> shelfInfoMap = new HashMap<String,Object>();
		shelfInfoMap = shelfInfoService.queryShelfInfoByObjectIdMethod(shelfCode,mallId);
		if(shelfInfoMap == null || shelfInfoMap.size() <= 0){
			//不存在不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0106);
		}
		
		//temp2 判断该货架是否存在子货架信息
		Map<String,Object> shelfParamMap = new HashMap<String, Object>();
		shelfParamMap.put("parentShelf", shelfCode);
		shelfParamMap.put("mallId", mallId);
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		
		shelfInfoList = shelfInfoService.queryShelfInfoMethod(shelfParamMap);
		
		if(shelfInfoList != null && shelfInfoList.size() > 0){
			//存在下级货架信息，不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0109);
		}
		
		//temp3 判断货架下是否有商品信息
		
		Map<String,Object> paramGoodsMap = new HashMap<String,Object>();
		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
		
		paramGoodsMap.put("shelfCode", shelfCode);
		
		shelfGoodsList = shelfGoodsInfoService.queryShelfGoodsInfoMethod(paramGoodsMap);
		
		if(shelfGoodsList != null && shelfGoodsList.size() > 0){
			//存在商品信息，不允许删除
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0110);
		}
		
		//temp4 校验完成后删除货架信息
		shelfInfoService.deleteShelfInfoMethod(shelfCode,mallId);
		
		return entity;
	}

	/**
	 * 
	 * @Title: queryShelfInfoMethod
	 * @Description: 查询货架信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		Map<String,Object> shelfInfoMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		
		shelfInfoList = shelfInfoService.queryShelfInfoMethod(paramMap);
		
		shelfInfoMap.put("returnList", shelfInfoList);
		
		ResponseEntity entity = new ResponseEntity(shelfInfoMap);
		
		return entity;
	}
	/**
	 * 
	 * @Title: queryShelfInfoPageMethod
	 * @Description: 分页查询货架信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Validation(value="p0000132")
	@Override
	public ResponseEntity queryShelfInfoPageMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		Map<String,Object> shelfInfoMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		
		shelfInfoList = shelfInfoService.queryShelfInfoPageMethod(paramMap);
		
		int totalNum = shelfInfoService.queryShelfTotalNumMethod(paramMap);
		
		shelfInfoMap.put("returnList", shelfInfoList);
		
		shelfInfoMap.put("totalNum", totalNum);
		
		ResponseEntity entity = new ResponseEntity(shelfInfoMap);
		
		return entity;
	}
	/**
	 * 
	 * @Title: addShelfGoodsInfoMethod
	 * @Description: 新增货架商品信息
	 * @param headMap 头信息
	 * @param shelfGoodsInfoList 货架商品信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param goodsName 商品名称
	 * @param prdTypeCode 产品分类编号
	 * @param description 商品描述
	 * @param goodsType 商品类型
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000112")
	@Override
	public ResponseEntity addShelfGoodsInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		List<Map<String, Object>> shelfGoodsInfoList = new ArrayList<Map<String,Object>>();
		
		shelfGoodsInfoList = (List<Map<String, Object>>) bodyMap.get("shelfGoodsInfoList");
		
		shelfGoodsInfoService.addShelfGoodsInfoMethod(shelfGoodsInfoList);
		
		return entity;
		
	}

	/**
	 * 
	 * @Title: updateShelfGoodsInfoMethod
	 * @Description: 修改商品信息
	 * @param headMap 头信息
	 * @param paramMap 货架商品信息
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param goodsName 商品名称
	 * @param description 商品描述
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="p0000113")
	@Override
	public ResponseEntity updateShelfGoodsInfoMethod(Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));//货架编码
		String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode"));//商品编号
		String goodStatus = (String) (paramMap.get("goodStatus")==null?"":paramMap.get("goodStatus"));//商品状态
		String goodsName = (String) (paramMap.get("goodsName")==null?"":paramMap.get("goodsName"));//商品名称
		String description = (String) (paramMap.get("description")==null?"":paramMap.get("description"));//商品描述
		String goodsFlag = (String) (paramMap.get("goodsFlag")==null?"":paramMap.get("goodsFlag"));//商品标志00-上架 01-下架
		
		//查询货架商品信息是否存在
		Map<String,Object> shelfGoodsInfoMap = new HashMap<String,Object>();
		
		shelfGoodsInfoMap = shelfGoodsInfoService.queryShelfGoodsInfoByObjectMethod(paramMap);
		
		if(shelfGoodsInfoMap != null && shelfGoodsInfoMap.size() > 0){
			//修改货架商品信息
			shelfGoodsInfoService.updateShelfGoodsInfoMethod(shelfCode, goodsCode, goodStatus, goodsName, description, goodsFlag);
		}else{
			//不存在货架商品信息，不允许修改
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0107);
		}
		return entity;
	}

	/**
	 * 
	 * @Title: deleteShelfGoodsInfoMethod
	 * @Description: 删除货架商品信息
	 * @param headMap 头信息
	 * @param shelfGoodsInfoList 货架商品信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000114")
	@Override
	public ResponseEntity deleteShelfGoodsInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		List<Map<String, Object>> shelfGoodsInfoList = new ArrayList<Map<String,Object>>();
		
		shelfGoodsInfoList = (List<Map<String, Object>>) bodyMap.get("shelfGoodsInfoList");
		
		shelfGoodsInfoService.deleteShelfGoodsInfoMethod(shelfGoodsInfoList);
		
		return entity;
	}

	/**
	 * 
	 * @Title: queryShelfGoodsInfoMethod
	 * @Description: 查询货架商品信息
	 * @param headMap  头信息
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param prdTypeCode 产品分类编号
	 * @param goodsType 商品类型
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @return ResponseEntity Map<String,Object> returnList商品信息列表
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryShelfGoodsInfoMethod(
			Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> shelfGoodsMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
		
		shelfGoodsList = shelfGoodsInfoService.queryShelfGoodsInfoMethod(paramMap);
		
		shelfGoodsMap.put("returnList", shelfGoodsList);
		
		ResponseEntity entity = new ResponseEntity(shelfGoodsMap);
		
		return entity;
	}
	
	/**
	 * 
	 * @Title: queryShelfGoodsInfoPageMethod
	 * @Description: 分页查询货架商品信息
	 * @param headMap  头信息
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param prdTypeCode 产品分类编号
	 * @param goodsType 商品类型
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList商品信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Validation(value="p0000133")
	@Override
	public ResponseEntity queryShelfGoodsInfoPageMethod(
			Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		Map<String,Object> shelfGoodsMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
		
		shelfGoodsList = shelfGoodsInfoService.queryShelfGoodsInfoPageMethod(paramMap);
		
		int totalNum = shelfGoodsInfoService.queryShelfGoodsTotalNumMethod(paramMap);
		
		shelfGoodsMap.put("returnList", shelfGoodsList);
		
		shelfGoodsMap.put("totalNum", totalNum);
		
		ResponseEntity entity = new ResponseEntity(shelfGoodsMap);
		
		return entity;
	}
	
	/**
	 * 
	 * @Title: queryShowShelfInfoMethod
	 * @Description: 根据法人id、渠道号、机构号等信息查询已上架的货架信息
	 * @param headMap  头信息
	 * @param paramMap 信息,包含以下参数
	 * @param chlCode 渠道编码
	 * @param legalPersonId 法人id
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表
	 * @throws ServiceException
	 */
	@Validation(value="p0000115")
	@Override
	public ResponseEntity queryShowShelfInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException{
		Map<String,Object> shelfGoodsInfo = new HashMap<String,Object>();
		
		shelfGoodsInfo = shelfInfoService.queryShowShelfInfoMethod(paramMap);
		
		ResponseEntity entity = new ResponseEntity(shelfGoodsInfo);
		
		return entity;
	}
	/**
	 * 
	 * @Title: queryShowShelfGoodsInfoMethod
	 * @Description:   根据货架编码查询其下级货架编码
	 *                 根据货架编码查询需要展示的货架商品信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @return ResponseEntity Map<String,Object> 
	 * @throws ServiceException
	 */
	@Validation(value="p0000116")
	public ResponseEntity queryShelfAndShelfGoodsInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException{
		//返回Map
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//获取上送的货架编码
		String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));
		
		//子货架信息列表
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> param=new HashMap<String, Object>();
		
		param.put("parentShelf",shelfCode);//货架编码
		param.put("shelfFlag","00");//货架标志 00-上架
		
		shelfInfoList = shelfInfoService.queryShelfInfoMethod(param);
		
		//把子货架信息放到返回Map中
		returnMap.put("shelfInfoList", shelfInfoList);
		
		if(shelfInfoList != null && shelfInfoList.size() > 0 ){
			
			//循环查询货架需要展示的商品信息
			for(int i = 0; i < shelfInfoList.size(); i++){
				//商品信息的List
				List<Map<String,Object>> shelfGoodsInfoList = new ArrayList<Map<String,Object>>();
				Map<String,Object> paramShelfGoodsMap = new HashMap<String,Object>();
				String outShelfCode = (String)shelfInfoList.get(i).get("shelfCode");
				paramShelfGoodsMap.put("shelfCode", outShelfCode);//货架编码
				paramShelfGoodsMap.put("goodsFlag", "00");//商品标志00-上架
				paramShelfGoodsMap.put("goodStatus", "00");//商品状态00-展示
				shelfGoodsInfoList = shelfGoodsInfoService.queryShelfGoodsInfoMethod(paramShelfGoodsMap);
				//查询出的商品信息放到返回Map中
				returnMap.put(outShelfCode, shelfGoodsInfoList);
			}
		} 
		//商品信息的List
		List<Map<String,Object>> shelfGoodsInfoList = new ArrayList<Map<String,Object>>();
		Map<String,Object> paramShelfGoodsMap = new HashMap<String,Object>();
		paramShelfGoodsMap.put("shelfCode", shelfCode);//货架编码
		paramShelfGoodsMap.put("goodsFlag", "00");//商品标志00-上架
		paramShelfGoodsMap.put("goodStatus", "00");//商品状态00-展示
		shelfGoodsInfoList = shelfGoodsInfoService.queryShelfGoodsInfoMethod(paramShelfGoodsMap);
		//查询出的商品信息放到返回Map中
		returnMap.put(shelfCode, shelfGoodsInfoList);
		
		ResponseEntity entity = new ResponseEntity(returnMap);
		
		return entity;
	}
}
