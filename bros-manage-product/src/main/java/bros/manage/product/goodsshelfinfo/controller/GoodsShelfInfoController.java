package bros.manage.product.goodsshelfinfo.controller;

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
 * @ClassName: GoodsShelfInfoController
 * @Description:  货架控制器
 * @author 郭苏伟
 * @date 2016年07月16日13:39:44
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/product")
public class GoodsShelfInfoController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(GoodsShelfInfoController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: addGoodsShelf 
	 * @Description: 新增货架
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addGoodsShelf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addGoodsShelf(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("增加货架异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("增加货架异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateGoodsShelf 
	 * @Description: 修改货架
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateGoodsShelf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateGoodsShelf(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
	
		}catch(ServiceException e){
			logger.error("更新货架异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("更新货架异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteGoodsShelf 
	 * @Description: 删除货架
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteGoodsShelf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteGoodsShelf(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除货架异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除货架异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryGoodsShelf 
	 * @Description: 查询货架
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryGoodsShelf" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryGoodsShelfInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
			//获取货架信息列表
			List<Map<String,Object>> returnList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("returnList");
		
			//组装成前台需要的格式
			StringBuffer result = new StringBuffer();
			result.append("[");
			if(returnList != null && returnList.size() > 0){
				for (int i = 0; i < returnList.size(); i++) {
					Map<String,Object> groupMap = new HashMap<String, Object>();
					groupMap = returnList.get(i);
					String chlSysCode = (String)groupMap.get("chlSysCode");//渠道分组编码
					
					
					
					result.append("{");

					result.append("shelfCode:'" + groupMap.get("shelfCode") +"',");//货架编码
					result.append("shelfName:'" + groupMap.get("shelfName") +"',");//货架名称
					result.append("shelfDesc:'" + groupMap.get("shelfDesc") +"',");//货架描述
					result.append("parentId:'" + groupMap.get("parentShelf") +"',");//上级货架编码
					result.append("mallId:'" + groupMap.get("mallId") +"',");//商城ID
					result.append("shelfFlag:'" + groupMap.get("shelfFlag") +"',");//货架标志
					
					result.append("showSign:'" + "4" +"'");//1-渠道分组

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
			logger.error("查询货架异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询货架异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
		
	

}
