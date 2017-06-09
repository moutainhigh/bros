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
 * 
 * @ClassName: RecvPersonManageController
 * @Description: 收款人名册调用服务控制器
 * @author gaoyongjing
 * @date 2016年9月23日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/manage")
public class RecvPersonManageController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(RecvPersonManageController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	

	/**
	 * @Title: addRecvPersonInfoMethod
	 * @Description:新增收款人信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addRecvPersonInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addRecvPersonInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			//新增收款人信息
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("新增收款人信息异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("新增收款人信息异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: updateRecvPersonInfoMethod
	 * @Description:修改收款人信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateRecvPersonInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateRecvPersonInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("修改收款人信息异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("修改收款人信息异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: deleteRecvPersonInfoMethod
	 * @Description:删除收款人信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteRecvPersonInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteRecvPersonInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("删除收款人信息异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("删除收款人信息异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:查询该渠道客户的所有收款人名册列表
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryAllRecvPersonInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryAllRecvPersonInfoMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询该渠道客户的所有收款人名册列表异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("查询该渠道客户的所有收款人名册列表异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryDistincRecvPersonInfoPageMethod
	 * @Description:分页查询该渠道客户的所有收款人名册列表去掉账号重复
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryDistinctRecvPersonInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryDistinctRecvPersonInfoPageMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("分页查询该渠道客户的所有收款人名册列表异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("分页查询该渠道客户的所有收款人名册列表异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryRecvPersonInfoPageMethod
	 * @Description:分页查询该渠道客户的所有收款人名册列表
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryRecvPersonInfoPage" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"requstMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryRecvPersonInfoPageMethod(@RequestBody Map<String, Object> requstMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(requstMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(requstMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			this.handleTradeResult(resultMap);
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("分页查询该渠道客户的所有收款人名册列表异常"+e);
			this.handleError(e,resultMap);
		}catch(Exception e){
			logger.error("分页查询该渠道客户的所有收款人名册列表异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
}
