package bros.manage.product.channelsysinfo.controller;

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
 * @ClassName: ChannelSysInfoController
 * @Description: 渠道信息控制器
 * @author 郭苏伟
 * @date 2016年07月16日13:39:44
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/product")
public class ChannelSysInfoController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(ChannelSysInfoController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;

	/**
	 * @Title: addChannel 
	 * @Description: 增加渠道
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addChannel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addChannel(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("增加渠道异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("增加渠道异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateChannel 
	 * @Description: 更新渠道
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateChannel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateChannel(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("更新渠道异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("更新渠道异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteChannel 
	 * @Description: 删除渠道
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteChannel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteChannel(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除渠道异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除渠道异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryChannel 
	 * @Description: 查询渠道
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryChannel" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryChannel(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("查询渠道异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询渠道异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
		
	

}
