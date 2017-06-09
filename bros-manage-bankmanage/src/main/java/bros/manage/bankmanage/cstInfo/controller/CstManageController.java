package bros.manage.bankmanage.cstInfo.controller;

import java.util.HashMap;
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
 * 
 * @ClassName: CstManageController 
 * @Description: 客户信息管理控制器
 * @author liwei 
 * @date 2016年9月12日 下午1:36:15 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class CstManageController extends CommonSessionController{
	
	private static final  Logger logger = LoggerFactory.getLogger(CstManageController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * 
	 * @Title: queryCstInfo 
	 * @Description: 渠道客户信息查询
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryCstInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryCstInfo(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("渠道客户查询失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("渠道客户查询失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: saveCstInfoChannel 
	 * @Description: 渠道客户签约
	 * @param requestMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveOpenChannelCstInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> saveCstInfoChannel(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requestMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
			
			//客户信息查询
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("渠道客户签约失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("渠道客户签约失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}

}
