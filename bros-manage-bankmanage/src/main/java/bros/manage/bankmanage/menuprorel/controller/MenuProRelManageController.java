package bros.manage.bankmanage.menuprorel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import bros.pre.common.web.controller.CommonSessionController;

/**
 * @ClassName: MenuProRelManageController 
 * @Description: 菜单与货架商品管理控制器
 * @author huangdazhou 
 * @date 2016年7月14日 下午1:47:21 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class MenuProRelManageController extends CommonSessionController{
	private static final  Logger logger = LoggerFactory.getLogger(MenuProRelManageController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: addMenuProRel 
	 * @Description: 添加菜单与货架商品controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/menuProRellAdd" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addMenuProRel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("创建菜单与货架商品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("创建菜单与货架商品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * @Title: updateMenuProRel 
	 * @Description: 修改菜单与货架商品controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/menuProRelUpdate" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateMenuProRel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("修改菜单与货架商品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("修改菜单与货架商品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * @Title: deleteMenuProRel 
	 * @Description: 删除菜单与货架商品controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/menuProRelDelete" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> deleteMenuProRel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("删除菜单与货架商品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除菜单与货架商品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * @Title: queryMenuProRel 
	 * @Description: 查询菜单与货架商品controller
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/menuProRelQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryMenuProRel(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			List<Map<String,Object>> branchList = new ArrayList<Map<String,Object>>();
		}catch(ServiceException e){
			logger.error("查询菜单与货架商品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询菜单与货架商品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}

}
