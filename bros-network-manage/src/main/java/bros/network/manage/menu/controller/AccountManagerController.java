package bros.network.manage.menu.controller;

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
 * @ClassName: AccountManagerController
 * @Description: 账户管理调用服务控制器
 * @author gaoyongjing
 * @date 2016年9月8日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/manage")
public class AccountManagerController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(AccountManagerController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	

	/**
	 * @Title: queryAccountInfoList
	 * @Description:签约账户列表(卡折)查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getAccountInfoList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryAccountInfoList(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			//查询菜单信息
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("签约账户列表(卡折)查询异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("签约账户列表(卡折)查询异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryAccDetailList
	 * @Description:动账交易明细查询,交易明细查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getAccDetailList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryAccDetailList(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("交易明细查询异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("交易明细查询异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: suspendAccountMethod
	 * @Description:账户挂失
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/suspendAccount" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> suspendAccountMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("账户挂失异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("账户挂失异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: addAccountInfoMethod
	 * @Description:账户加挂
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addAccountInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addAccountInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("账户加挂异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("账户加挂异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: deleteAccountInfoMethod
	 * @Description:账户解挂
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteAccountInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteAccountInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("账户解挂异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("账户解挂异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: setAccountAliasMethod
	 * @Description:账户别名设置
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/setAccountAlias" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> setAccountAliasMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("账户别名设置异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("账户别名设置异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: updateDefaultAccountInfoMethod
	 * @Description:默认账户修改
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateDefaultAccountInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateDefaultAccountInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("默认账户修改异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("默认账户修改异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryAccountInfoMethod
	 * @Description:账户信息查询
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAccountInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryAccountInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("账户信息查询异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("账户信息查询异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
}
