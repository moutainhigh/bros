package bros.p.bankmanage.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPFrontShelfMenuRelServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.mall.service.IMallInfoService;
import bros.provider.parent.bankmanage.menudef.service.IMenudefService;
import bros.provider.parent.bankmanage.product.service.IProductInformationService;
import bros.provider.parent.bankmanage.shelf.service.IShelfGoodsInfoService;
import bros.provider.parent.bankmanage.shelf.service.IShelfInfoService;
import bros.provider.parent.bankmanage.teller.service.ITellerService;

/** 
 * @ClassName: PFrontShelfMenuRelServiceFacadeImpl  
 * @Description:前台展示菜单查询接口实现类
 * @author  gaoyongjing
 * @date 2016年7月16日 上午9:45:58 
 * @version V1.0  
 */
@Component("pfrontShelfMenuRelServiceFacade")
public class PFrontShelfMenuRelServiceFacadeImpl implements IPFrontShelfMenuRelServiceFacade {
	/**
	 * 柜员系统
	 */
	@Autowired
	private ITellerService tellerService;
	/**
	 * 商城系统
	 */
	@Autowired
	private IMallInfoService mallInfoService;
	/**
	 * 货架信息服务
	 */
	@Autowired
	private IShelfInfoService shelfInfoService;
	/**
	 * 菜单实现类
	 */
	@Autowired
	private IMenudefService menudefService;
	/**
	 * 货架商品信息服务
	 */
	@Autowired
	private IShelfGoodsInfoService shelfGoodsInfoService;
	/**
	 * 产品信息基础服务实现类
	 */
	@Autowired
	private IProductInformationService productInformationService;
	/**
	 * 
	 * @Title: queryPbankLoginMenuMethod
	 * @Description: 查询个人网银柜员登录时展示的菜单信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity queryPbankLoginMenuMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//从报文头获取法人id、渠道编码
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
		String branchNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);//机构号
		
		//temp1  查询菜单信息，个人网银没有角色权限，所以查询所有菜单
		Map<String,Object> queryMenuMap = new HashMap<String,Object>();
		queryMenuMap.put("legalPersonId", legalId);//法人id
		queryMenuMap.put("bmfChannel", channel);//渠道号
		queryMenuMap.put("pageSize", 1000);//每页显示条数
		queryMenuMap.put("pageNo", 1);//当前页码
		Map<String,Object> menuInfoMap = new HashMap<String,Object>();
		menuInfoMap = menudefService.queryMenudef(headMap, queryMenuMap);
		List<Map<String,Object>> menuNandYList = new ArrayList<Map<String,Object>>();
		if(menuInfoMap.get("returnList") != null){
			menuNandYList = (List<Map<String, Object>>) menuInfoMap.get("returnList");
		}
		
		if(menuNandYList == null || menuNandYList.size() <= 0){
			//菜单信息不存在
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0132);
		}
		
		//temp2 根据法人id、渠道、菜单性质为Y-与货架关联     （菜单表与关联表 连表查询）
		Map<String,Object> queryMenuRelMap = new HashMap<String,Object>();
		queryMenuRelMap.put("legalPersonId", legalId);//法人id
		queryMenuRelMap.put("btrchannel", channel);//渠道号
		queryMenuRelMap.put("btrproperties","Y");//菜单性质
		Map<String,Object> menuRelInfoMap = new HashMap<String,Object>();
		menuRelInfoMap = tellerService.queryMenudefPro(headMap, queryMenuRelMap);
		List<Map<String,Object>> menuYList = new ArrayList<Map<String,Object>>();
		if(menuRelInfoMap.get("returnList") != null){
			menuYList = (List<Map<String, Object>>) menuRelInfoMap.get("returnList");
		}
		
		//temp3根据法人id、渠道、机构查询商城信息是否存在
		Map<String,Object> queryMallMap = new HashMap<String, Object>();
		queryMallMap.put("legalPersonId", legalId);//法人id
		queryMallMap.put("chlCode", channel);//渠道编码
		queryMallMap.put("branchNo", branchNo);//机构号
		Map<String,Object> mallMap = new HashMap<String, Object>();
//	shaoxu 2017/06/07	mallMap = mallInfoService.queryMallInfoByChlCodeLegalIdBranchNoMethod(queryMallMap);
		
		
		//temp4 根据法人id、渠道、机构查询货架信息
		Map<String,Object> queryShelfMap = new HashMap<String, Object>();
		if(mallMap != null){
			queryShelfMap.put("branchNo", branchNo);//机构号
		}else{
			queryShelfMap.put("branchNo", "");//机构号
		}
		queryShelfMap.put("legalPersonId", legalId);//法人id
		queryShelfMap.put("chlCode", channel);//渠道编码
		Map<String,Object> shelfInfoMap = new HashMap<String, Object>();
//	shaoxu 2017/06/07	shelfInfoMap = shelfInfoService.queryShowShelfInfoMethod(queryShelfMap);
		List<Map<String,Object>> shelfList = new ArrayList<Map<String,Object>>();
		if(shelfInfoMap.get("returnList") != null){
			 shelfList = (List<Map<String, Object>>) shelfInfoMap.get("returnList");
	     }
		
//	shaoxu2017/06/07	return megerList(menuNandYList,menuYList,shelfList);
		menuNandYList.addAll(menuYList);
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnList", menuNandYList);
		ResponseEntity returnEntity = new ResponseEntity(returnMap);
		return returnEntity;
	}
	 /**
   	 * 
   	 * @Title: queryCbankLoginMenuMethod
   	 * @Description: 查询企业网银柜员登录时展示的菜单信息(未完成)
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@Override
	public ResponseEntity queryCbankLoginMenuMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//从报文头获取法人id、渠道编码、柜员号、机构号
//		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
//		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
//		String tranTellerNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//柜员号
//		//从报文体获取机构号
//		String branchNo = (String) (bodyMap.get("branchNo")==null?"":bodyMap.get("branchNo"));//机构号
		
//		//temp1  查询菜单信息，柜面有角色权限，所以根据柜员角色查询分配后的菜单信息
//		Map<String,Object> queryMenuMap = new HashMap<String,Object>();
//		queryMenuMap.put("bmfChannel", channel);//渠道号
//		queryMenuMap.put("btrCode", tranTellerNo);//柜员号
//		Map<String,Object> menuInfoMap = new HashMap<String,Object>();
//		menuInfoMap = tellerService.queryTellerRoleMenu(headMap, queryMenuMap);
//		List<Map<String,Object>> menuNandYList = new ArrayList<Map<String,Object>>();
//		menuNandYList = (List<Map<String, Object>>) menuInfoMap.get("returnList");
//		
//		//temp2 根据法人id、渠道、菜单性质为Y-与货架关联     （菜单表与关联表 连表查询）
//		Map<String,Object> queryMenuRelMap = new HashMap<String,Object>();
//		queryMenuRelMap.put("btrchannel", channel);//渠道号
//		queryMenuRelMap.put("btrproperties","Y");//菜单性质
//		Map<String,Object> menuRelInfoMap = new HashMap<String,Object>();
//		menuRelInfoMap = tellerService.queryMenudefPro(headMap, queryMenuRelMap);
//		List<Map<String,Object>> menuYList = new ArrayList<Map<String,Object>>();
//		menuYList = (List<Map<String, Object>>) menuRelInfoMap.get("returnList");
//		
//		//temp3 根据法人id、渠道、机构查询货架信息
//		Map<String,Object> queryShelfMap = new HashMap<String, Object>();
//		queryShelfMap.put("legalPersonId", legalId);//法人id
//		queryShelfMap.put("chlCode", channel);//渠道编码
//		queryShelfMap.put("branchNo", branchNo);//机构号
//		Map<String,Object> shelfInfoMap = new HashMap<String, Object>();
//		shelfInfoMap = shelfInfoService.queryShowShelfInfoMethod(queryShelfMap);
//		List<Map<String,Object>> shelfList = new ArrayList<Map<String,Object>>();
//		shelfList = (List<Map<String, Object>>) shelfInfoMap.get("returnList");
//		
//		 
//		return megerList(menuNandYList,menuYList,shelfList);
		return null;
	}
	 /**
   	 * 
   	 * @Title: queryFrontLoginMenuMethod
   	 * @Description: 查询柜面柜员登录时展示的菜单信息
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity queryFrontLoginMenuMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		//从报文头获取法人id、渠道编码、柜员号、机构号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
		String tranTellerNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//柜员号
		String branchNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);//机构号
		
		//temp1  查询菜单信息，柜面有角色权限，所以根据柜员角色查询分配后的菜单信息
		Map<String,Object> queryMenuMap = new HashMap<String,Object>();
		queryMenuMap.put("btrchannel", channel);//渠道号
		queryMenuMap.put("btrCode", tranTellerNo);//柜员号
		Map<String,Object> menuInfoMap = new HashMap<String,Object>();
		menuInfoMap = tellerService.queryTellerRoleMenu(headMap, queryMenuMap);
		List<Map<String,Object>> menuNandYList = new ArrayList<Map<String,Object>>();
		if(menuInfoMap.get("returnList") != null){
			menuNandYList = (List<Map<String, Object>>) menuInfoMap.get("returnList");
		}
		
		if(menuNandYList == null || menuNandYList.size() <= 0){
			//无菜单信息
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0132);
		}
		//temp2 根据法人id、渠道、菜单性质为Y-与货架关联     （菜单表与关联表 连表查询）
		Map<String,Object> queryMenuRelMap = new HashMap<String,Object>();
		queryMenuRelMap.put("legalPersonId", legalId);//法人id
		queryMenuRelMap.put("btrchannel", channel);//渠道号
		queryMenuRelMap.put("btrproperties","Y");//菜单性质
		Map<String,Object> menuRelInfoMap = new HashMap<String,Object>();
		menuRelInfoMap = tellerService.queryMenudefPro(headMap, queryMenuRelMap);
		List<Map<String,Object>> menuYList = new ArrayList<Map<String,Object>>();
		if( menuRelInfoMap.get("returnList") != null){
			menuYList = (List<Map<String, Object>>) menuRelInfoMap.get("returnList");
		}
		//temp3根据法人id、渠道、机构查询商城信息是否存在
		Map<String,Object> queryMallMap = new HashMap<String, Object>();
		queryMallMap.put("legalPersonId", legalId);//法人id
		queryMallMap.put("chlCode", channel);//渠道编码
		queryMallMap.put("branchNo", branchNo);//机构号
		Map<String,Object> mallMap = new HashMap<String, Object>();
		mallMap = mallInfoService.queryMallInfoByChlCodeLegalIdBranchNoMethod(queryMallMap);
		
		//temp4 根据法人id、渠道、机构查询货架信息
		Map<String,Object> queryShelfMap = new HashMap<String, Object>();
		if(mallMap != null){
			queryShelfMap.put("branchNo", branchNo);//机构号
		}else{
			queryShelfMap.put("branchNo", "");//机构号
		}
		queryShelfMap.put("legalPersonId", legalId);//法人id
		queryShelfMap.put("chlCode", channel);//渠道编码
		Map<String,Object> shelfInfoMap = new HashMap<String, Object>();
		shelfInfoMap = shelfInfoService.queryShowShelfInfoMethod(queryShelfMap);
		List<Map<String,Object>> shelfList = new ArrayList<Map<String,Object>>();
		if( shelfInfoMap.get("returnList") != null){
			shelfList = (List<Map<String, Object>>) shelfInfoMap.get("returnList");
		}
		
		return megerList(menuNandYList,menuYList,shelfList);
	}
	 /**
   	 * 
   	 * @Title: queryAllChlShelfGoodsByShelfCodeMethod
   	 * @Description: 根据货架编号查询货架商品及菜单信息,适用于各个渠道
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@Validation(value="p0000125")
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity queryAllChlShelfGoodsByShelfCodeMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		//从报文头获取渠道编码
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		//从报文头获取货架编码
		String shelfCode = (String)bodyMap.get("shelfCode");//货架编码
		
		//temp1 根据法人id、渠道查询菜单与货架关联信息(菜单表与关联表 两个表连表查询)
		Map<String,Object> queryMenuRelMap = new HashMap<String,Object>();
		queryMenuRelMap.put("legalPersonId", legalId);//法人id
		queryMenuRelMap.put("btrchannel", channel);//渠道号
		queryMenuRelMap.put("shelfCode", shelfCode);//货架编码
		queryMenuRelMap.put("btrproperties","Y");//菜单性质
		Map<String,Object> menuRelInfoMap = new HashMap<String,Object>();
		menuRelInfoMap = tellerService.queryMenudefPro(headMap, queryMenuRelMap);
		List<Map<String,Object>> menuYList = new ArrayList<Map<String,Object>>();
		if(menuRelInfoMap.get("returnList") != null){
			menuYList = (List<Map<String, Object>>)menuRelInfoMap.get("returnList");
		}
		if(menuYList == null || menuYList.size() <= 0){
			//无菜单关联信息
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0133);
		}
		//temp2 根据货架查询商品信息
		bodyMap.put("goodStatus", "00");//上架
		bodyMap.put("goodStatus", "00");//商品状态00-展示
		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
		shelfGoodsList = shelfGoodsInfoService.queryShelfGoodsInfoMethod(bodyMap);
		if(shelfGoodsList == null || shelfGoodsList.size() <= 0){
			//无货架信息
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0134);
		}
		//查询回的商品信息与查询回的菜单与商品关联信息取交集
		List<Map<String,Object>> newShelfGoodsList = new ArrayList<Map<String,Object>>();
		/**
		 * 1、商品已上架，已配置菜单，未配置关联表，前台不返回
		 * 2、商品已上架，未配置菜单，未配置关联表，前台不返回
		 * 3、商品已上架，未配置菜单，已配置关联表，前台不返回
		 * 4、商品已上架，已配置菜单，已配置关联表，前台返回
		 * 5、商品已下架，已配置菜单，已配置关联表，前台不返回
		 * 6、商品已下架，已配置菜单，未配置关联表，前台不返回
		 * 7、商品已下架，未配置菜单，已配置关联表，前台不返回
		 * 8、商品已下架，未配置菜单，未配置关联表，前台不返回
		 */
		if( menuYList.size() > 0 && shelfGoodsList.size() > 0 ){
			for(int i=0;i<menuYList.size();i++){
				Map<String, Object> shelfMenuMap = new HashMap<String, Object>(); 
				shelfMenuMap = menuYList.get(i);
				String bmprGoodsCode = (String)shelfMenuMap.get("bmprGoodscode");
				String bmprShelfcode = (String)shelfMenuMap.get("bmprShelfcode");
				for (int j = 0; j < shelfGoodsList.size(); j++) {
					String qshelfCode = (String)shelfGoodsList.get(j).get("shelfCode");
					String goodsCode = (String)shelfGoodsList.get(j).get("goodsCode");
					if((bmprShelfcode.equals(qshelfCode)) && (bmprGoodsCode.equals(goodsCode)) ){
						String goodsName = (String)shelfGoodsList.get(j).get("goodsName");//商品名称
						String prdTypeCode = (String)shelfGoodsList.get(j).get("prdTypeCode");//产品分类编号
						String description = (String)shelfGoodsList.get(j).get("description");//商品描述
						String goodsType = (String)shelfGoodsList.get(j).get("goodsType");//商品类型
						shelfMenuMap.put("goodsName", goodsName);//商品名称
						shelfMenuMap.put("prdTypeCode", prdTypeCode);//产品分类编号
						shelfMenuMap.put("description", description);//商品描述
						shelfMenuMap.put("goodsType", goodsType);//商品类型
						newShelfGoodsList.add(shelfMenuMap);
						break;
					}
				}
			}
		}
		 
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnList", newShelfGoodsList);
		ResponseEntity returnEntity = new ResponseEntity(returnMap);
		return returnEntity;
	}

	/**
   	 * 
   	 * @Title: queryShelfAndShelfGoodsMenuByShelfCodeMethod
   	 * @Description: 根据货架编号查询子货架信息及货架信息上的商品信息并与菜单关联
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@Validation(value="p0000126")
	@SuppressWarnings("unchecked")
	public ResponseEntity queryShelfAndShelfGoodsMenuByShelfCodeMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		//从报文头获取渠道编码
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编码
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
		//从报文头获取货架编码
		String shelfCode = (String)bodyMap.get("shelfCode");//货架编码
		 
		Map<String,Object> returnMap = new HashMap<String, Object>();
		//temp1 根据货架编码查询其下级货架编码 及根据货架编码查询需要展示的货架商品信息
		//子货架信息列表
		List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("parentShelf",shelfCode);//货架编码
		param.put("shelfFlag","00");//货架标志 00-上架
		shelfInfoList = shelfInfoService.queryShelfInfoMethod(param);
		
		if(shelfInfoList == null || shelfInfoList.size() <= 0){
			shelfInfoList = new ArrayList<Map<String,Object>>();
			returnMap.put("shelfInfoList", shelfInfoList);//子货架信息
		}else{
			returnMap.put("shelfInfoList", shelfInfoList);//子货架信息
		}
		
		if( shelfInfoList!=null &&shelfInfoList.size() > 0 ){
			
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
				if(shelfGoodsInfoList != null && shelfGoodsInfoList.size() >0){
					//查询产品属性
					for (int  j = 0;  j < shelfGoodsInfoList.size();  j++) {
						Map<String,Object> shefGoodsInfoMap = shelfGoodsInfoList.get(j);
						String goodsCode = (String) shefGoodsInfoMap.get("goodsCode");//商品编号
						String goodsType = (String) shefGoodsInfoMap.get("goodsType");//商品类型02-产品
						if("02".equals(goodsType)){//所属商品为产品时查询产品属性
							String prdTypeCode = (String) shefGoodsInfoMap.get("prdTypeCode");//产品分类编码
							List<Map<String,Object>>  prodInfoAttriList = productInformationService.queryAttributeInfoMethod(prdTypeCode,goodsCode);
							if(prodInfoAttriList != null && prodInfoAttriList.size() > 0){
								for (int k = 0; k < prodInfoAttriList.size(); k++) {
									String key = (String)prodInfoAttriList.get(k).get("property");
									String value = (String)prodInfoAttriList.get(k).get("setValue");
									shelfGoodsInfoList.get(j).put(key,value);
								}
								String parentCode = (String) prodInfoAttriList.get(0).get("parentCode");
								String parentName = (String) prodInfoAttriList.get(0).get("parentName");
								shelfGoodsInfoList.get(j).put("parentCode", parentCode);
								shelfGoodsInfoList.get(j).put("parentName", parentName);
							}
						}
					}
				}
				//查询出的商品信息放到返回Map中
				returnMap.put(outShelfCode, shelfGoodsInfoList);//子货架上的商品信息
			}
		} 
		//商品信息的List
		List<Map<String,Object>> shelfGoodsInfoList = new ArrayList<Map<String,Object>>();
		Map<String,Object> paramShelfGoodsMap = new HashMap<String,Object>();
		paramShelfGoodsMap.put("shelfCode", shelfCode);//货架编码
		paramShelfGoodsMap.put("goodsFlag", "00");//商品标志00-上架
		paramShelfGoodsMap.put("goodStatus", "00");//商品状态00-展示
		shelfGoodsInfoList = shelfGoodsInfoService.queryShelfGoodsInfoMethod(paramShelfGoodsMap);
		if(shelfGoodsInfoList != null && shelfGoodsInfoList.size() >0){
			//查询产品属性
			for (int  j = 0;  j < shelfGoodsInfoList.size();  j++) {
				Map<String,Object> shefGoodsInfoMap = shelfGoodsInfoList.get(j);
				String goodsCode = (String) shefGoodsInfoMap.get("goodsCode");//商品编号
				String goodsType = (String) shefGoodsInfoMap.get("goodsType");//商品类型02-产品
				if("02".equals(goodsType)){//所属商品为产品时查询产品属性
					String prdTypeCode = (String) shefGoodsInfoMap.get("prdTypeCode");//产品分类编码
					List<Map<String,Object>> prodInfoAttriList = productInformationService.queryAttributeInfoMethod(prdTypeCode,goodsCode);
					if(prodInfoAttriList != null && prodInfoAttriList.size() > 0){
						for (int k = 0; k < prodInfoAttriList.size(); k++) {
							String key = (String)prodInfoAttriList.get(k).get("property");
							String value = (String)prodInfoAttriList.get(k).get("setValue");
							shelfGoodsInfoList.get(j).put(key,value);
						}
						String parentCode = (String) prodInfoAttriList.get(0).get("parentCode");
						String parentName = (String) prodInfoAttriList.get(0).get("parentName");
						shelfGoodsInfoList.get(j).put("parentCode", parentCode);
						shelfGoodsInfoList.get(j).put("parentName", parentName);
					}
				}
			}
		}
		
		//查询出的商品信息放到返回Map中
		returnMap.put(shelfCode, shelfGoodsInfoList); //货架上的商品信息
		 
		//根据法人id、渠道查询菜单与货架关联信息(菜单表与关联表 两个表连表查询)
		Map<String,Object> queryMenuRelMap = new HashMap<String,Object>();
		queryMenuRelMap.put("legalPersonId", legalId);//法人id
		queryMenuRelMap.put("btrchannel", channel);//渠道号
		queryMenuRelMap.put("btrproperties","Y");//菜单性质
		Map<String,Object> menuRelInfoMap = new HashMap<String,Object>();
		menuRelInfoMap = tellerService.queryMenudefPro(headMap, queryMenuRelMap);
		List<Map<String,Object>> menuYList = new ArrayList<Map<String,Object>>();
		if(menuRelInfoMap.get("returnList") != null){
			menuYList = (List<Map<String, Object>>) menuRelInfoMap.get("returnList");
		}
		returnMap.put("menuYList", menuYList);//菜单信息
		 
		entity = new ResponseEntity(returnMap);
		return entity;
	}
	
	/**
   	 * 
   	 * @Title: megerList
   	 * @Description: 合并List
   	 * @param menuNandYList 菜单List
   	 * @param menuYList 菜单与菜单货架关联表List
   	 * @param shelfList 货架List
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	public ResponseEntity megerList(List<Map<String, Object>> menuNandYList,List<Map<String, Object>>menuYList,List<Map<String, Object>>shelfList){
		List<Map<String,Object>> newShelfList = new ArrayList<Map<String,Object>>();
		//查询回的货架信息与查询回的菜单与货架关联信息取交集
		/**
		 * 1、货架已上架，已配置菜单，未配置关联表，前台不返回
		 * 2、货架已上架，未配置菜单，未配置关联表，前台不返回
		 * 3、货架已上架，未配置菜单，已配置关联表，前台不返回
		 * 4、货架已上架，已配置菜单，已配置关联表，前台返回
		 * 5、货架已下架，已配置菜单，已配置关联表，前台不返回
		 * 6、货架已下架，已配置菜单，未配置关联表，前台不返回
		 * 7、货架已下架，未配置菜单，已配置关联表，前台不返回
		 * 8、货架已下架，未配置菜单，未配置关联表，前台不返回
		 */
		if((menuYList != null && menuYList.size() > 0) && (shelfList != null && shelfList.size() > 0)){
			for(int i=0;i<menuYList.size();i++){
				Map<String, Object> shelfMenuMap = new HashMap<String, Object>();
				shelfMenuMap = menuYList.get(i);
				String shelfMenuCode = (String)shelfMenuMap.get("bmprShelfcode");
				String bmprGoodscode = (String)shelfMenuMap.get("bmprGoodscode");
				for (int j = 0; j < shelfList.size(); j++) {
					String shelfCode = (String)shelfList.get(j).get("shelfCode");
					String shelfName = (String)shelfList.get(j).get("shelfName");
					String shelfDesc = (String)shelfList.get(j).get("shelfDesc");
					String parentShelf = (String)shelfList.get(j).get("parentShelf");
					if("".equals(bmprGoodscode) && shelfMenuCode.equals(shelfCode)){
						shelfMenuMap.put("shelfName", shelfName);//货架名称
						shelfMenuMap.put("shelfDesc", shelfDesc);//货架描述
						shelfMenuMap.put("parentShelf", parentShelf);//上级货架编码
						newShelfList.add(shelfMenuMap);
						break;
					}
				}
			}
		}
		
		//获取到货架显示的菜单后与返回所有菜单比较
		/**
		 * 1、菜单性质为N-不与货架关联，则不需要与货架列表比较，直接返回前台
		 * 2、菜单性质为Y-与货架关联，则需判断菜单是否与货架关联，若关联了则返回前台，反之不返回
		 */
		List<Map<String,Object>> newMenuShelfList = new ArrayList<Map<String,Object>>();
		if(menuNandYList != null && menuNandYList.size() > 0){
			for (int i = 0; i < menuNandYList.size(); i++) {
				String menuCode = (String)menuNandYList.get(i).get("bmfCode");
				String menuProperties = (String)menuNandYList.get(i).get("bmfProperties");
				if("N".equals(menuProperties)){
					Map<String,Object> nMenuMap = new HashMap<String,Object>();
					nMenuMap = menuNandYList.get(i);
					nMenuMap.put("bmprId", "");             // 货架菜单关联唯一标识
					nMenuMap.put("bmprMenucode", "");       // 菜单编码
					nMenuMap.put("bmprPrdtypecode", "");    // 产品分类编号
					nMenuMap.put("bmprGoodscode", "");      // 商品编号
					nMenuMap.put("bmprShelfcode", "");      // 货架编号
					nMenuMap.put("shelfName", "");          // 货架名称
					nMenuMap.put("shelfDesc", "");          // 货架描述
					nMenuMap.put("parentShelf", "");        // 上级货架编码
					newMenuShelfList.add(nMenuMap);
				}else if("Y".equals(menuProperties)){
					if(newShelfList != null && newShelfList.size() > 0){
						for (int j = 0; j < newShelfList.size(); j++) {
							String shelfMenuCode = (String)newShelfList.get(j).get("bmfCode");
							if(menuCode.equals(shelfMenuCode)){
								newMenuShelfList.add(newShelfList.get(j));
								break;
							}
						}
					}
				}
			}
		}
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnList", newMenuShelfList);
		ResponseEntity returnEntity = new ResponseEntity(returnMap);
		return returnEntity;
	}
}
