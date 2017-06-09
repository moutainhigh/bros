package bros.manage.product.producttype.controller;

import java.util.HashMap;
import java.util.List;
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
 * @ClassName: ProductTypeController
 * @Description: 产品管理控制器
 * @author 郭苏伟
 * @date 2016年07月19日09:08:55
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/product")
public class ProductTypeController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(ProductTypeController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * @Title: addProductType 
	 * @Description: 新增产品分类
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addProductType" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addProductType(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("新增产品分类异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("新增产品分类异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateProductType 
	 * @Description: 更新产品分类
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateProductType" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateProductType(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			logger.error("更新产品分类异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteProductType 
	 * @Description: 删除产品分类
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteProductType" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteProductType(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("删除产品分类异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除产品分类异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryProductType 
	 * @Description: 查询产品分类树
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryProductType" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryProductType(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			resultMap = clientRouteTransService.route(headMap,bodyMap);

			//获取渠道信息列表
			List<Map<String,Object>> returnList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("returnList");
		
			//组装成前台需要的格式
			StringBuffer result = new StringBuffer();
			result.append("[");
			if(returnList != null && returnList.size() > 0){
				for (int i = 0; i < returnList.size(); i++) {
					Map<String,Object> groupMap = new HashMap<String, Object>();
					groupMap = returnList.get(i);
	
					result.append("{");
					
//					prdTypeCode	产品分类编号
//					parentCode	上级产品分类编号
//					prdTypeName	产品分类名称
//					prdTypeDesc	分类描述
//					basePrdCategoryId	基础产品编号
//					basePrdCategoryId	基础产品编号
//					categoryName	基础产品名称

					result.append("prdTypeCode:'" + groupMap.get("prdTypeCode") +"',");//产品分类编号
					result.append("parentId:'" + groupMap.get("parentCode") +"',");//上级产品分类编号
					result.append("prdTypeName:'" + groupMap.get("prdTypeName") +"',");//产品分类名称
					result.append("prdTypeDesc:'" + groupMap.get("prdTypeDesc") +"',");//分类描述
					result.append("basePrdCategoryId:'" + groupMap.get("basePrdCategoryId") +"',");//基础产品编号
					result.append("categoryName:'" + groupMap.get("categoryName") +"'");//基础产品名称
					
					result.append("},");
					
				}
			}
			
			if(',' == result.charAt(result.length()-1)) {
				result.deleteCharAt(result.length()-1);
			}
			result.append("]");
			Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			
			rspBody.put("result", result.toString());
			
		}catch(ServiceException e){
			logger.error("查询产品分类异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询产品分类异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryProductTypeSpeAttr 
	 * @Description: 查询产品规格属性
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryProductTypeSpeAttr" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryProductTypeSpeAttr(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("查询产品规格属性异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询产品规格属性异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryProductTypeInsAttr 
	 * @Description: 查询产品实例属性
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryProductTypeInsAttr" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryProductTypeInsAttr(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
		

		}catch(ServiceException e){
			logger.error("查询产品实例属性异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询产品实例属性异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
		
	

}
