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
import bros.pre.common.web.controller.CommonSessionController;

/** 
 * @ClassName:TransferController
 * @Description:TODO(转账汇款)
 * @author  gaoyongjing
 * @date 2016年10月13日 上午9:11:56 
 * @version V1.0  
 */
@Controller
@RequestMapping(value = "/manage")
public class TransferController  extends CommonSessionController{
	
	private static final  Logger logger = LoggerFactory.getLogger(TransferController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: transferPreCheck 
	 * @Description: 汇款预校验
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/transferPreCheck" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> transferPreCheck(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("汇款预校验异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("汇款预校验异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: transfer 
	 * @Description: 转账汇款
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/transfer" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> transfer(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("转账汇款异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("转账汇款异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryAccTransResultInf 
	 * @Description: 转账历史查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAccTransResultInf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAccTransResultInf(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("转账历史查询异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("转账历史查询异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryCnapsTransResult 
	 * @Description: 大小额转账结果查证
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryCnapsTransResult" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryCnapsTransResult(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("大小额转账结果查证异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("大小额转账结果查证异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryTransResult 
	 * @Description: 业务状态查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryTransResult" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryTransResult(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("业务状态查询异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("业务状态查询异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * @Title: queryApproveByStatMethod 
	 * @Description: 落地审批查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryApproveByStat" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryApproveByStatMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("落地审批查询异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("落地审批查询异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: cancelTransMethod 
	 * @Description: 取消转账
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/cancelTrans" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> cancelTransMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("取消转账异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("取消转账异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: addEletoSingleMethod 
	 * @Description: 添加电子回单
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addEletoSingle" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addEletoSingleMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("添加电子回单异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("添加电子回单异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryEleToSingleinfoMethod 
	 * @Description: 查询电子回单信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryEletoSingleinfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryEleToSingleinfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询电子回单信息异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询电子回单信息异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: updateEletoSinglePrintNumByObjectId 
	 * @Description: 更新电子回单打印次数
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updatePrintNumByObjectId" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateEletoSinglePrintNumByObjectId(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("更新电子回单打印次数异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("更新电子回单打印次数异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
}
