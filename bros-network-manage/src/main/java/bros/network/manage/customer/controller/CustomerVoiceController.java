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
 * @ClassName:CustomerVoiceController
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author  haojinhui
 * @date 2016年9月8日 上午9:11:56 
 * @version V1.0  
 */
@Controller
@RequestMapping(value = "/manage")
public class CustomerVoiceController  extends CommonSessionController{
	
	private static final  Logger logger = LoggerFactory.getLogger(CustomerVoiceController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: addCustomervVoice 
	 * @Description: 客户之声
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addCustomervVoice" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addCustomervVoice(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: queryBankAnnouncement 
	 * @Description: 查询银行公告
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryBankAnnouncement" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryBankAnnouncement(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: querySmsManage 
	 * @Description: 批量查询短信签约信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/querySmsManage" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> querySmsManage(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: queryPinblock 
	 * @Description: 密码控件加密PIN转换成PINBLOCK
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPinblock" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPinblock(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: queryRandomNum 
	 * @Description: 防重放随机数服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryRandomNum" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryRandomNum(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: addCheckPassword 
	 * @Description: 校验账户密码
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addCheckPassword" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addCheckPassword(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: updateSmsSigning 
	 * @Description: 短信签约开通/修改/关闭
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateSmsSigning" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateSmsSigning(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: updateCustomerNickname 
	 * @Description: 客户昵称设置
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateCustomerNickname" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateCustomerNickname(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
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
	 * @Title: updatepassword 
	 * @Description: 静态密码修改服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updatePassword" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updatePassword(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("静态密码修改服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("静态密码修改服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryreservedinf 
	 * @Description: 预留信息查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryReservedinf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryReservedinf(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("预留信息查询异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("预留信息查询异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: updatereservedinf 
	 * @Description: 预留信息设置
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateReservedinf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateReservedinf(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("预留信息设置异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("预留信息设置异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryAllPubUnionBankNo 
	 * @Description: 查询所有网银互联联行号信息服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAllPubUnionBankNo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAllPubUnionBankNo(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询所有网银互联联行号信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询所有网银互联联行号信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPubUnionBankNoByName 
	 * @Description: 根据名称模糊查询网银互联联行号信息服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPubUnionBankNoByName" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPubUnionBankNoByName(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("根据名称模糊查询网银互联联行号信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("根据名称模糊查询网银互联联行号信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryInquiryAgreementApp 
	 * @Description: 它行账户查询协议申请（网银互联）
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryInquiryAgreementApp" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryInquiryAgreementApp(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("它行账户查询协议申请（网银互联）异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("它行账户查询协议申请（网银互联）异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPaymentAgreementApp 
	 * @Description: 它行账户支付协议申请（网银互联）
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPaymentAgreementApp" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPaymentAgreementApp(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("它行账户支付协议申请（网银互联）异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("它行账户支付协议申请（网银互联）异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryInquiryAgreementNot 
	 * @Description: 它行账户查询协议通知（网银互联）
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryInquiryAgreementNot" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryInquiryAgreementNot(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("它行账户查询协议通知（网银互联）异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("它行账户查询协议通知（网银互联）异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPaymentAgreementNot 
	 * @Description: 它行账户支付协议通知（网银互联）
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPaymentAgreementNot" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPaymentAgreementNot(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("它行账户支付协议通知（网银互联）异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("它行账户支付协议通知（网银互联）异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryAccountProtocol 
	 * @Description: 它行账户查询协议查询（网银互联）
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAccountProtocol" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAccountProtocol(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("他行账户管理查询异常异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("他行账户管理查询异常异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryAccountPayment 
	 * @Description: 它行账户支付协议查询（网银互联）
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAccountPayment" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAccountPayment(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("他行账户管理查询异常异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("他行账户管理查询异常异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryAccountProPayment 
	 * @Description: 它行账户查询协议查询（网银互联）+ 它行账户支付协议查询（网银互联）的合集
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAccountProPayment" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAccountProPayment(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("他行账户管理查询异常异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("他行账户管理查询异常异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryAllAccountProPayment 
	 * @Description: 为了前端调用方便，根据不同条件调用queryAccountProtocol，queryAccountPayment，queryAccountProPayment
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAllAccountProPayment" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAllAccountProPayment(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("他行账户管理查询异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("他行账户管理查询异常异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPubCityInfoByCode 
	 * @Description: 根据省份code查询城市信息服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPubCityInfoByCode" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPubCityInfoByCode(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("根据省份code查询城市信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("根据省份code查询城市信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPubPmsBankNoByName 
	 * @Description: 根据模糊名称，城市代码，行别代码查询人行联行号信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPubPmsBankNoByName" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPubPmsBankNoByName(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("根据模糊名称，城市代码，行别代码查询人行联行号信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("根据模糊名称，城市代码，行别代码查询人行联行号信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPubBankType 
	 * @Description: 查询全部行别信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPubBankType" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPubBankType(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询全部行别信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询全部行别信息信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryAllPubProvince 
	 * @Description: 查询全部省份信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAllPubProvince" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryAllPubProvince(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询全部行别信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询全部行别信息信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryHeDidBalance 
	 * @Description: 它行账户余额查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryHeDidBalance" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryHeDidBalance(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("它行账户余额查询服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("它行账户余额查询服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryHeDidDetail 
	 * @Description: 它行账户明细列表查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryHeDidDetail" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryHeDidDetail(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("它行账户明细列表查询服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("它行账户明细列表查询服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryFreeAmt 
	 * @Description: 平台手续费试算
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryFreeAmt" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryFreeAmt(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("平台手续费试算服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("平台手续费试算服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: onlineBankingDebit 
	 * @Description: 网银借记业务（他行转入）
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/onlineBankingDebit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> onlineBankingDebit(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("网银借记业务（他行转入）服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("网银借记业务（他行转入）服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPubUnionBankNoByBankNo 
	 * @Description: 根据行号查行别信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPubUnionBankNoByBankNo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPubUnionBankNoByBankNo(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("根据行号查行别信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("根据行号查行别信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryPagingInstruction 
	 * @Description: 分页查询资金统筹计划指令信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryPagingInstruction" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryPagingInstruction(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("分页查询资金统筹计划指令信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("分页查询资金统筹计划指令信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: deleteFundInstruction 
	 * @Description: 撤销资金归集指令
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteFundInstruction" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> deleteFundInstruction(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("撤销资金归集指令服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("撤销资金归集指令服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: addFundInstruction 
	 * @Description: 创建资金归集计划
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addFundInstruction" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addFundInstruction(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("创建资金归集计划服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("创建资金归集计划服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: queryChannelCustomerInf 
	 * @Description: 查询渠道客户信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryChannelCustomerInf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryChannelCustomerInf(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询渠道客户信息服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询渠道客户信息服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: updateTprCstCstinf 
	 * @Description: 更改客户昵称
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateTprCstCstinf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateTprCstCstinf(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("校验客户昵称是否合法服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("校验客户昵称是否合法服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
	/**
	 * @Title: updateElectronicManage 
	 * @Description: 电子渠道管理 
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateElectronicManage" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateElectronicManage(@RequestBody Map<String, Object> requstMap,HttpServletRequest request)throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			 
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("电子渠道管理服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("电子渠道管理服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;	
	}
}
