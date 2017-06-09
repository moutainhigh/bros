package bros.manage.product.productinfomation.controller;

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
 * @ClassName: ProductInfomationController
 * @Description: 产品管理控制器
 * @author 郭苏伟
 * @date 2016年07月25日13:46:36
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/product")
public class ProductInfomationController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(ProductInfomationController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * @Title: addProduct 
	 * @Description: 新增产品
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addProduct" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addProduct(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("新增产品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("新增产品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateProduct 
	 * @Description: 更新产品
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateProduct" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateProduct(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("更新产品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("更新产品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteProduct 
	 * @Description: 删除产品
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteProduct" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteProduct(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除产品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除产品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryProduct 
	 * @Description: 查询产品
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryProduct" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryProduct(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
			this.handleTradeResult(resultMap);
			
			//创建渠道私有会话数据
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询产品异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询产品异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
		
	/**
	 * @Title: queryProductInfoAttr 
	 * @Description: 查询产品实例属性
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryProductInfoAttr" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryProductInfoAttr(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
			this.handleTradeResult(resultMap);
			
			//创建渠道私有会话数据
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询产品属性参数异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询产品属性参数异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
		
	

}
