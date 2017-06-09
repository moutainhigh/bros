package bros.manage.product.channelsysgroup.controller;

import java.util.ArrayList;
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
 * @ClassName: ChannelSysGroupController
 * @Description: 渠道系统分组控制器
 * @author 郭苏伟
 * @date 2016年07月16日13:39:44
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/product")
public class ChannelSysGroupController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(ChannelSysGroupController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: addChannelBaseInfo 
	 * @Description: 增加渠道基本信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addChannelBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addChannelBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);


			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("增加渠道系统分组异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("增加渠道系统分组异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateChannelBaseInfo 
	 * @Description: 更新渠道基本信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateChannelBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateChannelBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("更新渠道系统分组异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("更新渠道系统分组异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteChannelBaseInfo 
	 * @Description: 删除渠道基本信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteChannelBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteChannelBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("删除渠道系统分组异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除渠道系统分组异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryChannelBaseInfo
	 * @Description:查询渠道基本信息
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryChannelBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryChannelBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("查询渠道系统分组异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询渠道系统分组异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	/**
	 * @Title: queryChannelTreeInfo
	 * @Description:根据法人id查询渠道分组及渠道信息且拼成树形
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryChannelTreeInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryChannelTreeInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
			this.handleTradeResult(resultMap);
			//获取渠道分组列表
			List<Map<String,Object>> channelSysGroupList = new ArrayList<Map<String,Object>>();
			if(this.handleRspBodyData(resultMap).get("channelSysGroupList") != null){
				channelSysGroupList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("channelSysGroupList");
			}
			//获取渠道信息列表
			List<Map<String,Object>> chlInfoList = new ArrayList<Map<String,Object>>();
			if(this.handleRspBodyData(resultMap).get("chlInfoList") != null){
				 chlInfoList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("chlInfoList");
			}
		
			//组装成前台需要的格式
			StringBuffer result = new StringBuffer();
			result.append("[");
			if(channelSysGroupList != null && channelSysGroupList.size() > 0){
				for (int i = 0; i < channelSysGroupList.size(); i++) {
					Map<String,Object> groupMap = new HashMap<String, Object>();
					groupMap = channelSysGroupList.get(i);
					String chlSysCode = (String)groupMap.get("chlSysCode");//渠道分组编码
					
					result.append("{");
					result.append("chlShowId:'" + "" +"',");
					result.append("legalPersonId:'" + groupMap.get("legalPersonId") +"',");//法人id
					result.append("chlShowCode:'" + groupMap.get("chlSysCode") +"',");//渠道分组编码
					result.append("chlShowName:'" + groupMap.get("chlSysName") +"',");//渠道分组名称
					result.append("chlShowDesc:'" + groupMap.get("chlSysDesc") +"',");//渠道分组描述
					result.append("chlShowSign:'" + "1" +"',");//1-渠道分组
					result.append("parentId:'" + "" +"'");//上级id
					result.append("},");
					
					if(chlInfoList != null && chlInfoList.size() > 0){
						Map<String,Object> chlMap = new HashMap<String, Object>();
						for (int j = 0; j < chlInfoList.size(); j++) {
							chlMap = chlInfoList.get(j);
							String chlSysCodeNew = (String)chlMap.get("chlSysCode");
							if(chlSysCode.equals(chlSysCodeNew)){
								result.append("{");
								result.append("chlShowId:'" + chlMap.get("chlId") +"',");
								result.append("chlShowCode:'" + chlMap.get("chlCode") +"',");
								result.append("legalPersonId:'" + chlMap.get("legalPersonId") +"',");
								result.append("chlShowName:'" + chlMap.get("chlName") +"',");
								result.append("chlShowDesc:'" + chlMap.get("chlDesc") +"',");
								result.append("parentId:'" + chlMap.get("chlSysCode") +"',");
								result.append("chlShowSign:'" + "2" +"'");//2-渠道信息
								result.append("},");
							}
							
						}
					} 
				}
			}
			
			if(',' == result.charAt(result.length()-1)) {
				result.deleteCharAt(result.length()-1);
			}
			result.append("]");
			Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			
			rspBody.put("result", result.toString());
			
			//创建渠道私有会话数据
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询渠道系统分组异常"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询渠道系统分组异常"+ex);
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}	
	
	/*******************pub_channel******************************/
	
	/**
	 * @Title: addChannelSystemBaseInfo 
	 * @Description: 增加系统渠道基本信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addChannelSystemBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addChannelSystemBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);


			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("增加系统渠道基本信息异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("增加系统渠道基本信息异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteChannelSystemBaseInfo 
	 * @Description: 删除系统渠道基本信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteChannelSystemBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteChannelSystemBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);


			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除系统渠道基本信息异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除系统渠道基本信息异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateChannelSystemBaseInfo 
	 * @Description: 修改系统渠道基本信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateChannelSystemBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateChannelSystemBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);


			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("修改系统渠道基本信息异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("修改系统渠道基本信息异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryChannelSystemBaseInfo 
	 * @Description: 查询系统渠道基本信息
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryChannelSystemBaseInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryChannelSystemBaseInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);


			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("查询系统渠道基本信息异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询系统渠道基本信息异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	

}
