package bros.manage.bankmanage.limit.controller;

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
import bros.manage.bankmanage.customerservice.controller.CustomerServiceController;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * 
 * @ClassName: LimitManageController 
 * @Description: 客户限额管理
 * @author liwei 
 * @date 2016年10月17日 下午1:40:50 
 * @version 1.0
 */


@Controller
@RequestMapping(value = "/bankmanage")
public class LimitManageController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * 
	 * @Title: queryCstLimit
	 * @Description: 查询客户类限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryCstLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryCstLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置查询客户类限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置查询客户类限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: saveSingleBankingLimit
	 * @Description: 设置个人客户限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveSingleBankingLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> saveSingleBankingLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置设置个人客户限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置设置个人客户限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: updateSingleCstingLimit
	 * @Description: 更新个人客户限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateSingleCstingLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateSingleCstingLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置更新个人客户限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置更新个人客户限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: saveSinglePbCstingLimitCustom
	 * @Description: 新增个人客户自定义限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveSinglePbCstingLimitCustom" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> saveSinglePbCstingLimitCustom(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置新增个人客户自定义限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置新增个人客户自定义限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: updatePbCstingLimitCustom
	 * @Description: 修改个人客户自定义限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updatePbCstingLimitCustom" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updatePbCstingLimitCustom(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置修改个人客户自定义限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置修改个人客户自定义限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryLandLimit
	 * @Description: 查询银行落地限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryLandLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryLandLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置查询银行落地限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置查询银行落地限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: addSingleLandLimit
	 * @Description: 设置银行落地限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addSingleLandLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addSingleLandLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置设置银行落地限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置设置银行落地限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: updateSingleLandLimit
	 * @Description: 更新银行落地限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateSingleLandLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateSingleLandLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置更新银行落地限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置更新银行落地限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryBankLimit
	 * @Description: 查询银行类限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryBankLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryBankLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置查询银行类限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置查询银行类限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: addSingleBankLimit
	 * @Description: 设置银行类限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addSingleBankLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addSingleBankLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置设置银行类限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置设置银行类限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: updateSingleBankLimit
	 * @Description: 更新银行限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateSingleBankLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateSingleBankLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置更新银行类限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置更新银行类限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: queryCstLandingLimit
	 * @Description: 查询银行自定义客户落地限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryCstLandingLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryCstLandingLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置查询银行自定义客户落地限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置查询银行自定义客户落地限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: addSingleCstLandingLimit
	 * @Description: 设置银行自定义客户落地限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addSingleCstLandingLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addSingleCstLandingLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置设置银行自定义客户落地限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置设置银行自定义客户落地限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: updateSingleCstLandingLimit
	 * @Description: 更新银行自定义客户落地限额
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateSingleCstLandingLimit" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateSingleCstLandingLimit(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("bankmanage前置更新银行自定义客户落地限额失败"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("bankmanage前置更新银行自定义客户落地限额失败"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
}
