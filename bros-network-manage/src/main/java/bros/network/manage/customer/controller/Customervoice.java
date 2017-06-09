package bros.network.manage.customer.controller;

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
import bros.network.manage.menu.controller.ShopCartConstants;
import bros.pre.common.web.controller.CommonSessionController;

/** 
 * @ClassName:Customervoice  
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author  haojinhui
 * @date 2016年9月8日 上午9:11:56 
 * @version V1.0  
 */
@Controller
@RequestMapping(value = "/manage")
public class Customervoice  extends CommonSessionController{
	
	private static final  Logger logger = LoggerFactory.getLogger(ShopCartConstants.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: addCustomervoice 
	 * @Description: 客户之声
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addCustomervoice" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addCustomervoice(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("客户之声提交异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("客户之声提交异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: querybankannouncement 
	 * @Description: 查询银行公告
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/querybankannouncement" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> querybankannouncement(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询银行公告异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询银行公告异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: querysmsmanage 
	 * @Description: 批量查询短信签约信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/querysmsmanage" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> querysmsmanage(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("批量查询短信签约异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("批量查询短信签约异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: querypinblock 
	 * @Description: 密码控件加密PIN转换成PINBLOCK
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/querypinblock" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> querypinblock(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("密码控件加密PIN转换成PINBLOCK异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("密码控件加密PIN转换成PINBLOCK异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryrandomnum 
	 * @Description: 防重放随机数服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryrandomnum" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryrandomnum(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("防重放随机数服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("防重放随机数服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: addcheckpassword 
	 * @Description: 校验账户密码
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addcheckpassword" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addcheckpassword(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("校验账户密码异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("校验账户密码异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: updatesmssigning 
	 * @Description: 短信签约开通/修改/关闭
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updatesmssigning" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updatesmssigning(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("短信签约开通/修改/关闭异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("短信签约开通/修改/关闭异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: updatecustomernickname 
	 * @Description: 客户昵称设置
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updatecustomernickname" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updatecustomernickname(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("客户昵称设置异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("客户昵称设置异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryOperationLogList 
	 * @Description: 查询操作日志列表
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryOperationLogList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryOperationLogList(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询操作日志列表异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询操作日志列表异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
}
