package bros.network.manage.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bros.common.core.annotation.MethodAttribute;
import bros.common.core.annotation.NeedInSessionType;
import bros.common.core.comm.route.service.IClientRouteTransService;
import bros.common.core.exception.ServiceException;
import bros.common.core.mvc.constants.CoreMvcParamsConstants;
import bros.common.core.util.TreeItem;
import bros.common.core.util.ValidateUtil;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * 
 * @ClassName: WebFrontController
 * @Description: 前端登录时调用服务控制器
 * @author gaoyongjing
 * @date 2016年7月14日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/manage")
public class WebFrontController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(WebFrontController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	

	/**
	 * @Title: queryPbankLoginMenu
	 * @Description:个人网银登录查询菜单信息
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pbankLoginMenuInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryPbankLoginMenu(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			//查询菜单信息
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			
			//获取返回的菜单列表
			List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
			if(this.handleRspBodyData(resultMap).get("returnList") != null){
				menuList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("returnList");
			}
			
			//菜单列表根据序号及菜单编号排序
			List<Map<String,Object>> sortMenuList = menuOrder(menuList);
			
			//获取一级菜单列表
			List<Object> parentMenuList = new ArrayList<Object>();
			TreeItem<Map<String,Object>> parent = null;
			if(sortMenuList != null && sortMenuList.size() > 0) {
				for(Map<String,Object> map : sortMenuList) {
					if(map.get("bmfParentid") == null || ((String)map.get("bmfParentid")).equals("")) {
						parent = new TreeItem<Map<String,Object>>();
						parent.setItemValue(map);
						parentMenuList.add(parent);
					}
				}
			}
			
			TreeItem<Map<String,Object>> allMenuList = new TreeItem<Map<String,Object>>();
			TreeItem<Map<String,Object>> childMenuList = new TreeItem<Map<String,Object>>();
			if(parentMenuList != null && parentMenuList.size() > 0){
				//循环获取菜单的子集菜单列表
				for (int i = 0; i < parentMenuList.size(); i++) {
					TreeItem<Map<String,Object>> parent1 = (TreeItem<Map<String, Object>>) parentMenuList.get(i);
					if(parent1 != null) {
						//根据层级排序
						childMenuList = productMenuTree(parent1,sortMenuList);
						
						//根据层级排好后，放到返回列表中
						allMenuList.addChildrenItem(childMenuList);
					}
				}
			}
			
			Map<String, Object> rspBody = this.handleRspBodyData(resultMap);
			
			 //覆盖原来没有排序的菜单列表信息
			rspBody.put("returnList", allMenuList);
			
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询登录菜单异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("查询登录菜单异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryGoodsMenu
	 * @Description:根据货架编码查询货架的子货架信息以及子货架上的商品、菜单信息
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/shelfGoodsMenuByShelfCode" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryGoodsMenu(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			//获取子货架信息
			List<Map<String,Object>> shelfInfoList = new ArrayList<Map<String,Object>>();
			if(this.handleRspBodyData(resultMap).get("shelfInfoList") != null){
				shelfInfoList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("shelfInfoList");
			}
			//获取菜单信息
			List<Map<String,Object>> menuYList = new ArrayList<Map<String,Object>>();
			if(this.handleRspBodyData(resultMap).get("menuYList") != null){
				menuYList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("menuYList");
			}
			TreeItem<Map<String,Object>> returnTree = new TreeItem<Map<String,Object>>();;
			TreeItem<Map<String,Object>> parent = new TreeItem<Map<String,Object>>();;
			if(shelfInfoList != null && shelfInfoList.size() > 0){
				for (int i = 0; i < shelfInfoList.size(); i++) {
					Map<String,Object> shelfMap = new HashMap<String,Object>();
					parent = new TreeItem<Map<String,Object>>();
					shelfMap = shelfInfoList.get(i);
					parent.setItemValue(shelfMap);
					
					String outShelfCode = (String)shelfMap.get("shelfCode");
					//获取子货架上的商品信息
					List<Map<String, Object>>shelfGoodsList = new ArrayList<Map<String,Object>>();
					if(this.handleRspBodyData(resultMap).get(outShelfCode) != null){
						shelfGoodsList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get(outShelfCode);
					}
//					if(shelfGoodsList != null){
						TreeItem<Map<String,Object>> megerTree = megerShelfGoodsListMethod(parent,menuYList,shelfGoodsList);
						//货架对应商品信息放入返回map，货架编码为key值
						returnTree.addChildrenItem(megerTree);
//					}
				}
			}
			
			String shelfCode = (String)(this.handleReqBodyData(loginMap).get("shelfCode"));
			//上送货架的商品信息放入返回map
			List<Map<String, Object>>shelfGoodsList = new ArrayList<Map<String,Object>>();
			if(this.handleRspBodyData(resultMap).get(shelfCode) != null){
				shelfGoodsList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get(shelfCode);
			}
			
			if(shelfGoodsList != null && shelfGoodsList.size() > 0){
				shelfGoodsList = megerShelfGoodsListMethod(menuYList,shelfGoodsList);
			}
			Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			rspBody.clear();
			rspBody.put("returnList", returnTree);
			rspBody.put("returnGoodsList", shelfGoodsList);
			this.handleSuccess(resultMap);
			resultMap.put(CoreMvcParamsConstants.RSP_DATA_BODY_MAP, rspBody);
		}catch(ServiceException e){
			logger.error("查询商品信息异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("查询商品信息异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryMenudef 
	 * @Description: 查询菜单controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/menuQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryMenudef(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
			Object obj=rspBody.get("returnList");
			if(obj!=null){
				returnList=(List<Map<String, Object>>)rspBody.get("returnList");
				List<Map<String, Object>> resutlList=new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> responeList=new ArrayList<Map<String,Object>>();
				returnList=sortList(null, returnList, responeList);
			}
			rspBody.put("result", JSONArray.fromObject(returnList));
		}catch(ServiceException e){
			logger.error("查询菜单异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询菜单异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	public List<Map<String,Object>> megerShelfGoodsListMethod(List<Map<String, Object>> menuYList,List<Map<String, Object>>shelfGoodsList){
		List<Map<String,Object>> newShelfGoodsList = new ArrayList<Map<String,Object>>();
//		TreeItem<Map<String,Object>> parent = null;
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
		if((menuYList != null && menuYList.size() > 0) && (shelfGoodsList != null && shelfGoodsList.size() > 0)){
			for(int i=0;i<menuYList.size();i++){
				Map<String, Object> shelfMenuMap = new HashMap<String, Object>(); 
				shelfMenuMap = menuYList.get(i);
				String bmprGoodsCode = (String)shelfMenuMap.get("bmprGoodscode");//商品编码
				String bmprShelfcode = (String)shelfMenuMap.get("bmprShelfcode");//货架编码
				for (int j = 0; j < shelfGoodsList.size(); j++) {
					String qshelfCode = (String)shelfGoodsList.get(j).get("shelfCode");//商品编码
					String goodsCode = (String)shelfGoodsList.get(j).get("goodsCode");//货架编码
					String goodsType = (String)shelfGoodsList.get(j).get("goodsType");//商品类型
					if("02".equals(goodsType)){
						newShelfGoodsList.add(shelfGoodsList.get(j));
						shelfGoodsList.remove(j);
						break;
					}
					if((bmprShelfcode.equals(qshelfCode)) && (bmprGoodsCode.equals(goodsCode)) ){
//						String goodsName = (String)shelfGoodsList.get(j).get("goodsName");//商品名称
//						String prdTypeCode = (String)shelfGoodsList.get(j).get("prdTypeCode");//产品分类编号
//						String description = (String)shelfGoodsList.get(j).get("description");//商品描述
//						String goodsType = (String)shelfGoodsList.get(j).get("goodsType");//商品类型
//						if("02".equals(goodsType)){//所属商品为产品时查询产品属性
//							List<Map<String,Object>> prodInfoAttriList = (List<Map<String, Object>>) shelfGoodsList.get(j).get("prodInfoAttriList");//产品属性
//							shelfMenuMap.put("prodInfoAttriList", prodInfoAttriList);//产品属性
//						}
//						shelfMenuMap.put("goodsName", goodsName);//商品名称
//						shelfMenuMap.put("prdTypeCode", prdTypeCode);//产品分类编号
//						shelfMenuMap.put("description", description);//商品描述
//						shelfMenuMap.put("goodsType", goodsType);//商品类型
						shelfMenuMap.putAll(shelfGoodsList.get(j));
						shelfGoodsList.remove(j);
						newShelfGoodsList.add(shelfMenuMap);
						break;
					}
				}
			}
		}
		return newShelfGoodsList;
	}
	
	public TreeItem<Map<String,Object>> megerShelfGoodsListMethod(TreeItem<Map<String,Object>> parent,List<Map<String, Object>> menuYList,List<Map<String, Object>>shelfGoodsList){
//		List<Map<String,Object>> newShelfGoodsList = new ArrayList<Map<String,Object>>();
//		TreeItem<Map<String,Object>> parent = null;
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
		if((menuYList != null && menuYList.size() > 0) && (shelfGoodsList != null && shelfGoodsList.size() > 0)){
			for(int i=0;i<menuYList.size();i++){
				Map<String, Object> shelfMenuMap = new HashMap<String, Object>(); 
				shelfMenuMap = menuYList.get(i);
				String bmprGoodsCode = (String)shelfMenuMap.get("bmprGoodscode");//商品编码
				String bmprShelfcode = (String)shelfMenuMap.get("bmprShelfcode");//货架编码
				for (int j = 0; j < shelfGoodsList.size(); j++) {
					String qshelfCode = (String)shelfGoodsList.get(j).get("shelfCode");//商品编码
					String goodsCode = (String)shelfGoodsList.get(j).get("goodsCode");//货架编码
					String goodsType = (String)shelfGoodsList.get(j).get("goodsType");//商品类型
					if("02".equals(goodsType)){
						TreeItem<Map<String,Object>> item = new TreeItem<Map<String,Object>>();
						item.setItemValue(shelfGoodsList.get(j));
						parent.addChildrenItem(item);
						shelfGoodsList.remove(j);
//						newShelfGoodsList.add(shelfMenuMap);
						break;
					}
					if((bmprShelfcode.equals(qshelfCode)) && (bmprGoodsCode.equals(goodsCode)) ){
//						String goodsName = (String)shelfGoodsList.get(j).get("goodsName");//商品名称
//						String prdTypeCode = (String)shelfGoodsList.get(j).get("prdTypeCode");//产品分类编号
//						String description = (String)shelfGoodsList.get(j).get("description");//商品描述
//						String goodsType = (String)shelfGoodsList.get(j).get("goodsType");//商品类型
//						if("02".equals(goodsType)){//所属商品为产品时查询产品属性
//							List<Map<String,Object>> prodInfoAttriList = (List<Map<String, Object>>) shelfGoodsList.get(j).get("prodInfoAttriList");//产品属性
//							shelfMenuMap.put("prodInfoAttriList", prodInfoAttriList);//产品属性
//						}
//						shelfMenuMap.put("goodsName", goodsName);//商品名称
//						shelfMenuMap.put("prdTypeCode", prdTypeCode);//产品分类编号
//						shelfMenuMap.put("description", description);//商品描述
//						shelfMenuMap.put("goodsType", goodsType);//商品类型
						shelfMenuMap.putAll(shelfGoodsList.get(j));
						shelfGoodsList.remove(j);
						TreeItem<Map<String,Object>> item = new TreeItem<Map<String,Object>>();
						item.setItemValue(shelfMenuMap);
						parent.addChildrenItem(item);
//						newShelfGoodsList.add(shelfMenuMap);
						break;
					}
				}
			}
		}
		return parent;
	}
	private static TreeItem<Map<String,Object>> productMenuTree(TreeItem<Map<String,Object>> parent, List<Map<String,Object>> fieldSetList) {
		if(parent == null) {
			parent = new TreeItem<Map<String,Object>>();
			Map<String,Object> rootFieldSet = new HashMap<String,Object>();
			rootFieldSet.put("bmfId", "");
			parent.setItemValue(rootFieldSet);
			
			productMenuTree(parent,fieldSetList);
		} else {
			for(Map<String,Object> fieldSet : fieldSetList){
				String code = (String)parent.getItemValue().get("bmfId");
				String parentCode = (String)fieldSet.get("bmfParentid");
				
				if(((code==null && parentCode==null))
						|| ( code!=null && code.equals(parentCode)) ) {
					TreeItem<Map<String,Object>> item = new TreeItem<Map<String,Object>>();
					item.setItemValue(fieldSet);
					parent.addChildrenItem(item);
					productMenuTree(item,fieldSetList);
				}
			}
		}
		
		return parent;
	}
	
	//将菜单进行排序
	private List<Map<String,Object>> menuOrder(List<Map<String,Object>> menuList){
			Map<String,Object> tempMap;//存放临时变量
			int menuSize = menuList.size();
			
			int firstMenu_id;
			int firstMenu_seqNo;
			
			int secondMenu_id;
			int secondMenu_seqNo;
			
			for(int i=0;i<menuSize-1;i++){
				firstMenu_id=Integer.parseInt(String.valueOf( menuList.get(i).get("bmfCode")));
				firstMenu_seqNo=Integer.parseInt(String.valueOf( menuList.get(i).get("bmfSeqno")));//(Integer) menuList.get(i).get("bmfSeqno");
				for(int j=i+1;j<menuSize;j++){
					secondMenu_id=Integer.parseInt(String.valueOf( menuList.get(j).get("bmfCode")));
					secondMenu_seqNo=Integer.parseInt(String.valueOf(menuList.get(j).get("bmfSeqno")));//(Integer) menuList.get(j).get("bmfSeqno");
					if(secondMenu_seqNo<firstMenu_seqNo){//根据排序号进行判断
						tempMap=menuList.get(i);
						menuList.set(i, menuList.get(j));
						menuList.set(j, tempMap);
						firstMenu_id=Integer.parseInt(String.valueOf( menuList.get(i).get("bmfCode")));//重新赋值
						firstMenu_seqNo=Integer.parseInt(String.valueOf( menuList.get(i).get("bmfSeqno")));//(Integer) menuList.get(i).get("bmfSeqno");//重新赋值
					}else if(secondMenu_seqNo==firstMenu_seqNo){//如果排序号相同，则根据id进行排序
						if(secondMenu_id<firstMenu_id){
							tempMap=menuList.get(i);
							menuList.set(i, menuList.get(j));
							menuList.set(j, tempMap);
							firstMenu_id=Integer.parseInt(String.valueOf( menuList.get(i).get("bmfCode")));//重新赋值
							firstMenu_seqNo=Integer.parseInt(String.valueOf( menuList.get(i).get("bmfSeqno")));//(Integer) menuList.get(i).get("bmfSeqno");//重新赋值
						}
					}
				}
				
			}
			return menuList;
		}
	
	/**
	 * 
	 * @Title: sortList 
	 * @Description: list按树排序
	 * @param parent
	 * @param returnList
	 * @param responeList
	 * @return
	 */
	public List<Map<String, Object>> sortList(Map<String, Object> parent,List<Map<String, Object>> returnList,List<Map<String, Object>> responeList){
		if(parent == null) {
			Map<String,Object> rootFieldMap = new HashMap<String,Object>();
			rootFieldMap.put("bmfId", "");
			sortList(rootFieldMap, returnList,responeList);
		}else{
			for(Map<String,Object> fieldMap : returnList){
				String bmfId = (String)parent.get("bmfId");
				String bmfParentid = (String)fieldMap.get("bmfParentid");
				
				if((ValidateUtil.isEmpty(bmfId) && ValidateUtil.isEmpty(bmfParentid))
						|| bmfId.equals(bmfParentid)) {
					responeList.add(fieldMap);
					sortList(fieldMap, returnList,responeList);
				}
			}
		}
		return responeList;
	}
}
