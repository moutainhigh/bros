package bros.manage.product.mallinfo.controller;

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
 * @ClassName: MallInfoController
 * @Description: 商城信息控制器
 * @author 郭苏伟
 * @date 2016年07月19日15:19:48
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/product")
public class MallInfoController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(MallInfoController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * @Title: addMallInfo 
	 * @Description: 新增商城
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/addMallInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> addMallInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("增加商城异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("增加商城异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: updateMallInfo 
	 * @Description: 修改商城
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/updateMallInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> updateMallInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);

		}catch(ServiceException e){
			logger.error("更新商城异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("更新商城异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: deleteMallInfo 
	 * @Description: 删除商城
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/deleteMallInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> deleteMallInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
				
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			

		}catch(ServiceException e){
			logger.error("删除商城异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("删除商城异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryMallInfo 
	 * @Description: 查询商城
	 * @param requstMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryMallInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryMallInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			

		}catch(ServiceException e){
			logger.error("查询商城异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询商城异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}
	
	/**
	 * @Title: queryMallTreeInfo
	 * @Description:根据法人id查询渠道分组及渠道信息商城拼成树形
	 * @param loginMap
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
		
	@RequestMapping(value = "/queryMallTreeInfo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryMallTreeInfo(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
			
			Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			List<Map<String,Object>> channelSysGroupList = new ArrayList<Map<String,Object>>();
			Object obj1=rspBody.get("channelSysGroupList");
			if (obj1!=null) {
				channelSysGroupList = (List<Map<String, Object>>) rspBody.get("channelSysGroupList");
			}
			List<Map<String,Object>> chlInfoList = new ArrayList<Map<String,Object>>();
			Object obj2=rspBody.get("chlInfoList");
			if (obj2!=null) {
				chlInfoList = (List<Map<String, Object>>) rspBody.get("chlInfoList");
			}
			List<Map<String,Object>> mallInfoList = new ArrayList<Map<String,Object>>();
			Object obj3=rspBody.get("mallInfoList");
			if (obj3!=null) {
				mallInfoList = (List<Map<String, Object>>) rspBody.get("mallInfoList");
			}
			
			
			
//			this.handleTradeResult(resultMap);
//			List<Map<String,Object>> channelSysGroupList = new ArrayList<Map<String,Object>>();
//			if(this.handleRspBodyData(resultMap).get("channelSysGroupList") != null){
//				channelSysGroupList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("channelSysGroupList");
//			}
//			List<Map<String,Object>> chlInfoList = new ArrayList<Map<String,Object>>();
//			if(this.handleRspBodyData(resultMap).get("chlInfoList") != null){
//				chlInfoList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("chlInfoList");
//			}
//			
//			List<Map<String,Object>> mallInfoList = new ArrayList<Map<String,Object>>();
//			if(this.handleRspBodyData(resultMap).get("mallInfoList") != null){
//				mallInfoList = (List<Map<String, Object>>) this.handleRspBodyData(resultMap).get("mallInfoList");
//			}

			StringBuffer result = new StringBuffer();
			result.append("[");
			if(channelSysGroupList != null && channelSysGroupList.size() > 0){
				for (int i = 0; i < channelSysGroupList.size(); i++) {
					Map<String,Object> groupMap = new HashMap<String, Object>();
					groupMap = channelSysGroupList.get(i);
					String chlSysCode = (String)groupMap.get("chlSysCode");//渠道分组编码
					
					result.append("{");
					result.append("showId:'" + "" +"',");
					result.append("legalPersonId:'" + groupMap.get("legalPersonId") +"',");//法人id
					result.append("showCode:'" + groupMap.get("chlSysCode") +"',");//渠道分组编码
					result.append("showName:'" + groupMap.get("chlSysName") +"',");//渠道分组名称
					result.append("showDesc:'" + groupMap.get("chlSysDesc") +"',");//渠道分组描述
					result.append("branchNo:'" + "" +"',");                        //机构号
					result.append("pageName:'" + "" +"',");                        //首次登陆页面名称
					result.append("showSign:'" + "1" +"',");                       //1-渠道分组
					result.append("parentId:'" + "" +"'");                         //父节点id
					result.append("},");
					
					if(chlInfoList != null && chlInfoList.size() > 0){
						Map<String,Object> chlMap = new HashMap<String, Object>();
						for (int j = 0; j < chlInfoList.size(); j++) {
							chlMap = chlInfoList.get(j);
							String chlSysCodeNew = (String)chlMap.get("chlSysCode");
							if(chlSysCode.equals(chlSysCodeNew)){
								result.append("{");
								result.append("showId:'" + chlMap.get("chlCode") +"',");//渠道编码
								result.append("showCode:'" + chlMap.get("chlId") +"',");//渠道唯一id
								result.append("legalPersonId:'" + chlMap.get("legalPersonId") +"',");//法人id
								result.append("showName:'" + chlMap.get("chlName") +"',");//渠道名称
								result.append("showDesc:'" + chlMap.get("chlDesc") +"',");//渠道描述
								result.append("parentId:'" + chlMap.get("chlSysCode") +"',");//渠道分组编码
								result.append("branchNo:'" + "" +"',");                      //机构号
								result.append("pageName:'" + "" +"',");                      //首页名称
								result.append("showSign:'" + "2" +"'");//2-渠道信息
								result.append("},");
								if(mallInfoList != null && mallInfoList.size() > 0){
									String chlId = (String)chlMap.get("chlId");
									Map<String,Object> mallMap = new HashMap<String, Object>();
									for (int k = 0; k < mallInfoList.size(); k++) {
										mallMap = mallInfoList.get(k);
										String chlIdNew = (String)mallMap.get("chlId");
										if(chlId.equals(chlIdNew)){
											result.append("{");
											result.append("showId:'" + mallMap.get("mallId") +"',");//商城id
											result.append("showCode:'" + mallMap.get("mallCode") +"',");//商城编码
											result.append("legalPersonId:'" + "" +"',");                //法人id
											result.append("showName:'" + mallMap.get("mallName") +"',");//商城名称
											result.append("showDesc:'" + mallMap.get("mallDesc") +"',");//商城描述
											result.append("parentId:'" + mallMap.get("chlId") +"',");   //渠道id
											result.append("branchNo:'" + mallMap.get("branchNo") +"',");//机构号
											result.append("pageName:'" + mallMap.get("pageName") +"',");//登陆名
											result.append("chlShowSign:'" + "3" +"'");//3-商城信息
											result.append("},");
										}
									}
								}
							}
						}
					} 
				}
			}
			
			 
			if(',' == result.charAt(result.length()-1)) {
				result.deleteCharAt(result.length()-1);
			}
			result.append("]");
			
			//Map<String,Object> rspBody = this.handleRspBodyData(resultMap);
			rspBody.put("result", result.toString());
			
			//创建渠道私有会话数据
			this.handleSuccess(resultMap);
		}catch(ServiceException e){
			logger.error("查询商城异常"+e);
			//清空会话信息
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("查询商城异常"+ex);
			//清空会话信息
			this.handleError(ex,resultMap);
		}
		return resultMap;
	}

}
