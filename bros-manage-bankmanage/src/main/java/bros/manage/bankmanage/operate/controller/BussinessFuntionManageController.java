package bros.manage.bankmanage.operate.controller;

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
 * @ClassName: BussinessFuntionManageController 
 * @Description: 业务功能管理控制器
 * @author huangdazhou 
 * @date 2016年12月24日 上午10:19:19 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class BussinessFuntionManageController extends CommonSessionController{
	private static final  Logger logger = LoggerFactory.getLogger(BussinessFuntionManageController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: queryBsnFun 
	 * @Description: 查询业务功能controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryBsnFun" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryBsnFun(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询业务功能异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询业务功能异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryBsnFunRelMenudef 
	 * @Description: 查询业务功能关联菜单controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryBsnFunRelMenudef" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryBsnFunRelMenudef(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询业务功能关联菜单异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询业务功能关联菜单异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryBsnFunRelMenudef 
	 * @Description: 查询业务功能关联菜单controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateBsnFunRelMenu" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateBsnFunRelMenu(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("修改业务功能关联菜单异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("修改业务功能关联菜单异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: insertOneBsndef 
	 * @Description: 添加业务功能信息controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/insertOneBsndef" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> insertOneBsndef(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("添加业务功能信息异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("添加业务功能信息异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: updateOneBsndef 
	 * @Description: 修改业务功能信息controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateOneBsndef" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateOneBsndef(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("修改业务功能信息异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("修改业务功能信息异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: deleteOneBsndef 
	 * @Description: 删除业务功能信息controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteOneBsndef" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> deleteOneBsndef(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("删除业务功能信息异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除业务功能信息异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
}
