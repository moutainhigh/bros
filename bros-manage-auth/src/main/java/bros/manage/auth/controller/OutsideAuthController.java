package bros.manage.auth.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import bros.common.core.auth.detail.match.AuthDataMatchTemplate;
import bros.common.core.comm.route.service.IClientRouteTransService;
import bros.common.core.exception.ServiceException;
import bros.common.core.ftp.service.IFtpService;
import bros.common.core.redis.util.GetCacheDataUtil;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * 
 * @ClassName: OutsideAuthController 
 * @Description: 对客授权
 * @author huangdazhou 
 * @date 2016年9月7日 下午2:29:48 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/auth")
public class OutsideAuthController extends CommonSessionController{
	private static final  Logger logger = LoggerFactory.getLogger(OutsideAuthController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * ftp服务
	 */
	@Autowired
	private IFtpService ftpClientServer;
	/**
	 * 
	 * @Title: qryOutsideTaskCenterView 
	 * @Description: 待授权指令概览统计服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/qryOutsideTaskCenterView" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> qryOutsideTaskCenterView(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询待授权指令概览统计服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询待授权指令概览统计服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * 
	 * @Title: qryOutsideTaskCenterViewByBsnType 
	 * @Description: 根据业务类型查询待授权指令概览统计服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/qryOutsideTaskCenterViewByBsnType" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> qryOutsideTaskCenterViewByBsnType(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询待授权指令概览统计服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询待授权指令概览统计服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * 
	 * @Title: qryOutsideAuthQueueList 
	 * @Description: 根据业务编号查询待授权指令列表服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/qryOutsideAuthQueueList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> qryOutsideAuthQueueList(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询待授权指令列表服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询待授权指令列表服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * 
	 * @Title: unOutsideClaimTaskJob 
	 * @Description: 退回任务服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/unOutsideClaimTaskJob" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> unOutsideClaimTaskJob(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("退回任务服务失败"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("退回任务服务失败"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: qryOrderDetailByBusinessKey 
	 * @Description: 根据业务流水号（指令流水号）+业务编号查询指令详情服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/qryOutsideOrderDetail" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> qryOrderDetailByBusinessKey(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询指令详情服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询指令详情服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: singleOutsideCompleteTask 
	 * @Description: 单笔授权（临柜）服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/singleOutsideCompleteTask" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> singleOutsideCompleteTask(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("单笔授权（临柜）服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("单笔授权（临柜）服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: authOutsideSingleCompleteTask 
	 * @Description: 单笔授权（审核式）服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/authOutsideSingleCompleteTask" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> authOutsideSingleCompleteTask(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("单笔授权（审核式）服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("单笔授权（审核式）服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: batchOutsideCompleteTask 
	 * @Description: 批量授权服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/batchOutsideCompleteTask" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> batchOutsideCompleteTask(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("批量授权服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("批量授权服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: qryOutsideOrderDetailListByBatchNo 
	 * @Description: 根据批次号查询指令详情列表（用于凭证打印）服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/qryOutsideOrderDetailListByBatchNo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> qryOutsideOrderDetailListByBatchNo(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询指令详情列表（用于凭证打印）服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询指令详情列表（用于凭证打印）服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: qryOutsideCommentListByTaskId 
	 * @Description: 根据业务流水号（businessKey）查询审批意见历史信息服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/qryOutsideCommentListByTaskId" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> qryOutsideCommentListByTaskId(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询审批意见历史信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询审批意见历史信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: queryOutsideCancelProcessList 
	 * @Description: 根据法人记录唯一标识+任务提交柜员ID查询可撤销流程实例列表
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryOutsideCancelProcessList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryOutsideCancelProcessList(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "CB88888888");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00007");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询撤销授权申请信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询撤销授权申请信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: queryOutsideCancelProcess 
	 * @Description: 根据法人记录唯一标识+任务提交柜员ID+30位全局流水号撤销授权申请
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryOutsideCancelProcess" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryOutsideCancelProcess(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "CB88888888");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00007");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("撤销授权申请信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("撤销授权申请信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: queryOutsideAuthHistory 
	 * @Description: 根据条件，查询授权历史信息列表(已办事宜列表查询)
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryOutsideAuthHistory" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryOutsideAuthHistory(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("撤销授权申请信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("撤销授权申请信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * 
	 * @Title: qryAuthDetailPageDataByBusinessKey 
	 * @Description: 根据业务流水号（指令流水号）+业务编号查询授权详情页面展示数据服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/qryOutsideAuthDetailPage" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> qryAuthDetailPageDataByBusinessKey(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			String bsnCode = String.valueOf(bodyMap.get("funcCode"));
			/*****************写死数据（开始）***************************/
			headMap.put("branchId", "4444");
			headMap.put("bsnCode", "");
			headMap.put("tranTellerNo", "462");
			headMap.put("operatorNo", "00004");
			headMap.put("consumerId", "6666");
			headMap.put("tranDate", "20150910");
			headMap.put("tranTime", "8888888");
			headMap.put("channelDate", "99999999");
			headMap.put("channel", "1003");
			headMap.put("sceneCode", "1111");
			headMap.put("legalCode", "branchLegal-01");
			headMap.put("legalId", "80fb68c1-fce5-440d-85a3-9c392ba1ba83");
			headMap.put("flag", "1");
			headMap.put("tradeType", "2");
			headMap.put("version", "1.0.0");
			headMap.put("cstNo", "C888889137");
			/*******************写死数据（结束）***************************/
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			Map<String,Object> responseBody = this.handleRspBodyData(resultMap);
			if(responseBody != null && responseBody.size()>0){
				//授权详情数据模板
				Object modelObject = GetCacheDataUtil.getAuthTemplateByCache(bsnCode);
				List<Object> template = null;
				if(modelObject == null){
					throw new ServiceException("授权详情数据模板不存在【"+bsnCode+"】");
				}
				if(!(modelObject instanceof List)){
					throw new ServiceException("授权详情数据模板格式不正确【"+bsnCode+"】");
				}else{
					template = (List<Object>) modelObject;
				}
				
				String bsnCodeCategroy = bsnCode+"categroy";//模板对应的转义字典名
				Object transferObject = GetCacheDataUtil.getAuthTemplateByCache(bsnCodeCategroy);
				Map<String,Object> transferMapResult = new HashMap<String, Object>();
				if(transferObject != null && transferObject instanceof Map){
					Map<String,Object> transferMap = (Map<String, Object>) transferObject;
					Set<String> keySet = transferMap.keySet();
					for(Iterator<String> it = keySet.iterator();it.hasNext();){
						String key = it.next();
						List<Map<String,Object>> listTransfer = GetCacheDataUtil.getAppParByCache(key);
						if(listTransfer != null && listTransfer.size()>0){
							transferMapResult.put(key, listTransfer);
						}
					}
				}
				
				List<Map<String,Object>> matchList = AuthDataMatchTemplate.dataMatchTemplate(responseBody, template, transferMapResult);
				
				responseBody.put("recordList", matchList);
			}
		}catch(ServiceException e){
			logger.error("查询授权详情页面展示数据服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询授权详情页面展示数据服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	
	/**
	 * 
	 * @Title: queryAuthProcessNodeDetail 
	 * @Description: 根据任务id获取节点展示信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryOutSideAuthProcessNodeDetail" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryOutSideAuthProcessNodeDetail(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		}catch(ServiceException e){
			logger.error("查询授权流程节点信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询授权流程节点信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
}
