package bros.manage.bankmanage.bmaauthmanage.controller;

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
 * 
 * @ClassName: CustAuthorizeManageController 
 * @Description: 内管授权
 * @author pengxiangnan 
 * @date 2016年7月15日 下午1:23:25 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class BmaAuthManageController extends CommonSessionController{
	private static final  Logger logger = LoggerFactory.getLogger(BmaAuthManageController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	@RequestMapping(value = "/queryInAuthModelListPage" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAuthorizationModelListForPage(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}

	@RequestMapping(value = "/queryInAuthModelDetail" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAuthorizationModelDetail(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	 /**
	  * 
	  * @param requstMap
	  * @param request
	  * @return
	  * @throws ServiceException
	  */
	@RequestMapping(value = "/saveInAuthModel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> saveAuthorizationModel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	
	@RequestMapping(value = "/deleteInAuthModel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> deleteAuthorizationModel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	
	@RequestMapping(value = "/updateInAuthModel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateAuthorizationModel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	
	@RequestMapping(value = "/updateInFunctionAuthModel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateFunctionAuthorizationModel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	@RequestMapping(value = "/queryInAuthModelList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAuthorizationModelList(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	@RequestMapping(value = "/queryInTtpFuncAuthList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryTtpFuncAuthList(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
			Object obj=rspBody.get("resultList");
			if(obj!=null){
				returnList=(List<Map<String, Object>>)rspBody.get("resultList");
				List<Map<String, Object>> responeList=new ArrayList<Map<String,Object>>();
				resultList=sortList(null, returnList, responeList);
				if(returnList!=null&&returnList.size()>0){
					for(Map<String,Object> fieldMap : returnList){
						String cllParentid = (String)fieldMap.get("bmfParentid");
						boolean b=true;
						if(!ValidateUtil.isEmpty(cllParentid)){
							for(Map<String,Object> tmpMap : returnList){
								String cllIdTmp = (String)tmpMap.get("bmfId");
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
			logger.error("查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询失败"+ex);
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
			rootFieldMap.put("bmfId", "");
			sortList(rootFieldMap, returnList,responeList);
		}else{
			for(Map<String,Object> fieldMap : returnList){
				String cllId = (String)parent.get("bmfId");
				String cllParentid = (String)fieldMap.get("bmfParentid");
				
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
