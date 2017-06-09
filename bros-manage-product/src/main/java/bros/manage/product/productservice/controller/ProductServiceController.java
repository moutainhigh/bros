package bros.manage.product.productservice.controller;

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
 * @ClassName: ProductServiceController
 * @Description: 产品服务管理控制器
 * @author 郭苏伟
 * @date 2016年07月25日13:36:01
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/product")
public class ProductServiceController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(ProductServiceController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * @Title: addProductService 
	 * @Description: 新增产品服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addProductService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addProductService(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("新增产品服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("新增产品服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateProductService 
	 * @Description: 修改产品服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateProductService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateProductService(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("更新产品类型异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("更新产品服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteProductService 
	 * @Description: 删除产品服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteProductService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteProductService(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除产品服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除产品服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryProductService 
	 * @Description: 查询产品服务
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryProductService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryProductService(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("查询产品服务异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询产品服务异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
		
	

}
