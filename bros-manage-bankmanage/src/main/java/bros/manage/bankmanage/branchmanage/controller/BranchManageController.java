package bros.manage.bankmanage.branchmanage.controller;

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
import bros.common.core.util.ValidateUtil;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * @ClassName: BranchManageController 
 * @Description: 机构管理控制器
 * @author huangdazhou 
 * @date 2016年7月14日 下午1:47:21 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class BranchManageController extends CommonSessionController{
	private static final  Logger logger = LoggerFactory.getLogger(BranchManageController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	
	/**
	 * @Title: addBranch 
	 * @Description: 添加机构controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/branchAdd" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addBranch(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("创建机构异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("创建机构异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * @Title: updateBranch 
	 * @Description: 修改机构controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/branchUpdate" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateBranch(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("修改机构异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("修改机构异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * @Title: deleteBranch 
	 * @Description: 删除机构controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/branchDelete" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> deleteBranch(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("删除机构异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除机构异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * @Title: queryAllBaranch 
	 * @Description: 查询机构controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/branchQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAllBaranch(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
			Object obj=rspBody.get("returnList");
			if(obj!=null){
				returnList=(List<Map<String, Object>>)rspBody.get("returnList");
				List<Map<String, Object>> responeList=new ArrayList<Map<String,Object>>();
				resultList=sortList(null, returnList, responeList);
				if(returnList!=null&&returnList.size()>0){
					for(Map<String,Object> fieldMap : returnList){
						String cllParentid = (String)fieldMap.get("branchParentid");
						boolean b=true;
						if(!ValidateUtil.isEmpty(cllParentid)){
							for(Map<String,Object> tmpMap : returnList){
								String cllIdTmp = (String)tmpMap.get("branchId");
								if(cllIdTmp.equals(cllParentid)){
									b=false;
									continue;
								}
							}
						}else{
							continue;
						}
						if(b){
							List<Map<String, Object>> resutlListParent=new ArrayList<Map<String,Object>>();
							List<Map<String, Object>> responeListTmp=new ArrayList<Map<String,Object>>();
							resultList.add(fieldMap);
							List<Map<String, Object>> resutlListChild=new ArrayList<Map<String,Object>>();
							resutlListChild=sortList(fieldMap, returnList, responeListTmp);
							if(resutlListChild!=null&&resutlListChild.size()>0){
								resultList.addAll(resutlListChild);
							}
						}
						
					}
				}
			}
			
			rspBody.put("result", JSONArray.fromObject(resultList));
		}catch(ServiceException e){
			logger.error("查询机构异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询机构异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
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
			rootFieldMap.put("branchId", "");
			sortList(rootFieldMap, returnList,responeList);
		}else{
			for(Map<String,Object> fieldMap : returnList){
				String cllId = (String)parent.get("branchId");
				String cllParentid = (String)fieldMap.get("branchParentid");
				
				if((ValidateUtil.isEmpty(cllId) && ValidateUtil.isEmpty(cllParentid))
						|| cllId.equals(cllParentid)) {
					responeList.add(fieldMap);
					sortList(fieldMap, returnList,responeList);
				}
			}
		}
		return responeList;
	}
}
