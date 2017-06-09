package bros.manage.bankmanage.bannerinfo.controller;

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
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.TokenUtil;
import bros.common.core.util.ValidateUtil;
import bros.manage.bankmanage.webfront.controller.WebFrontController;
import bros.pre.common.web.controller.CommonSessionController;
/**
 * 
 * @ClassName: WebFrontController
 * @Description: 广告栏用服务控制器
 * @author wuchenglong
 * @date 2016年7月14日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class BannerinfoController extends CommonSessionController{
	private static final  Logger logger = LoggerFactory.getLogger(WebFrontController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@RequestMapping(value = "/bannerinfoAdd" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> bannerinfoAdd(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			Map<String,Object> sessionMap = tokenUtil.getSessionMap(headMap);
			String branchNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID));
			if(ValidateUtil.isEmpty(branchNo)){
				throw new ServiceException("机构号不存在");
			}
			
			String legalId = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			if(ValidateUtil.isEmpty(legalId)){
				throw new ServiceException("法人不存在");
			}
			
			String tellerNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));
			if(ValidateUtil.isEmpty(tellerNo)){
				throw new ServiceException("交易柜员不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("sceneCode", "0001");//场景编码
			 bodyMap.put("bannerLegal", legalId);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("增加广告栏异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/bannerinfoDelete" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> bannerinfoDelete(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			Map<String,Object> sessionMap = tokenUtil.getSessionMap(headMap);
			String branchNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID));
			if(ValidateUtil.isEmpty(branchNo)){
				throw new ServiceException("机构号不存在");
			}
			
			String legalId = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			if(ValidateUtil.isEmpty(legalId)){
				throw new ServiceException("法人不存在");
			}
			
			String tellerNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));
			if(ValidateUtil.isEmpty(tellerNo)){
				throw new ServiceException("交易柜员不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("sceneCode", "0001");//场景编码
			 bodyMap.put("bannerLegal", legalId);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除广告栏异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/bannerinfoUpdate" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> bannerinfoUpdate(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			Map<String,Object> sessionMap = tokenUtil.getSessionMap(headMap);
			String branchNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID));
			if(ValidateUtil.isEmpty(branchNo)){
				throw new ServiceException("机构号不存在");
			}
			
			String legalId = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			if(ValidateUtil.isEmpty(legalId)){
				throw new ServiceException("法人不存在");
			}
			
			String tellerNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));
			if(ValidateUtil.isEmpty(tellerNo)){
				throw new ServiceException("交易柜员不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("sceneCode", "0001");//场景编码
			 bodyMap.put("bannerLegal", legalId);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("修改广告栏异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/bannerinfoQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> bannerinfoQuery(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			Map<String,Object> sessionMap = tokenUtil.getSessionMap(headMap);
			String branchNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_BRANCHID));
			if(ValidateUtil.isEmpty(branchNo)){
				throw new ServiceException("机构号不存在");
			}
			
			String legalId = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			if(ValidateUtil.isEmpty(legalId)){
				throw new ServiceException("法人不存在");
			}
			
			String tellerNo = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));
			if(ValidateUtil.isEmpty(tellerNo)){
				throw new ServiceException("交易柜员不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("sceneCode", "0001");//场景编码
			 bodyMap.put("bannerLegal", legalId);
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("查询广告栏异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}

}
