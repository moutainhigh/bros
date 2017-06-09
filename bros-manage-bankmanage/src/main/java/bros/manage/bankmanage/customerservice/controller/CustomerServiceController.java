package bros.manage.bankmanage.customerservice.controller;

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
 * @ClassName: CustomerServiceController 
 * @Description: 客户服务控制器
 * @author lichen 
 * @date 2016年10月12日 下午5:07:38 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class CustomerServiceController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * 
	 * @Title: queryCutomer 
	 * @Description: 客户之声查询
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryCutomer" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryCutomer(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("客户之声查询失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("客户之声查询失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: updateCstleavewords 
	 * @Description: 客户之声更新状态
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updatecustomervoicestate" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateCstleavewords(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("修改状态异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("修改状态异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}

}
