package bros.manage.bankmanage.role.controller;

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
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.TokenUtil;
import bros.common.core.util.ValidateUtil;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * 
 * @ClassName: LogonController
 * @Description: 角色管理控制器
 * @author 吴成龙
 * @date 2016年5月12日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class RoleController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	
	@RequestMapping(value = "/queryCutomerList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryCustomer(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> headMap = this.handleReqHeadData(loginMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);

		List<Map<String, Object>> customerList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cstNo", "C000000003");
		customerList.add(map);
		headMap.put("returnCode", "00000000");
		resultMap.put("customerList", customerList);
		resultMap.put("rspHead", headMap);
//			resultMap = clientRouteTransService.route(headMap,bodyMap);
		return resultMap;
	}
	

	@RequestMapping(value = "/roleQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> logon(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("查询角色异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/roleAdd" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> roleAdd(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap = (Map<String, Object>) bodyMap.get("roleMap");
			
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
			
			 String work = String.valueOf(roleMap.get("work"));
			 String auth =String.valueOf(roleMap.get("auth"));
			 String manage =String.valueOf(roleMap.get("manage"));
			 if("".endsWith(work)||"null".equals(work)){
				 work = "0";
			 }
			 if("".endsWith(auth)||"null".equals(auth)){
				 auth = "0";
			 }
			 if("".endsWith(manage)||"null".equals(manage)){
				 manage = "0";
			 }
			 String roleAuth =work+auth+manage+"0";
			 bodyMap.put("roleAuth", roleAuth);
			 roleMap.put("roleAuth", roleAuth);
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("增加角色异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/roleUpdate" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> roleUpdate(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			
			 String work = String.valueOf(bodyMap.get("work"));
			 String auth =String.valueOf(bodyMap.get("auth"));
			 String manage =String.valueOf(bodyMap.get("manage"));
			 if("".endsWith(work)||"null".equals(work)){
				 work = "0";
			 }
			 if("".endsWith(auth)||"null".equals(auth)){
				 auth = "0";
			 }
			 if("".endsWith(manage)||"null".equals(manage)){
				 manage = "0";
			 }
			 String roleAuth =work+auth+manage+"0";
			 bodyMap.put("roleAuth", roleAuth);
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("修改角色异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	
	@RequestMapping(value = "/roleDelete" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> roleDelete(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除角色异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/queryFunctionList" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryFunctionList(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> headMap = this.handleReqHeadData(loginMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
		List<Map<String, Object>> customerList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("funcitonNumber", "001");
		map.put("funcitonName", "经办");
		map.put("roleFunctionId", "CB00000012");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("funcitonNumber", "002");
		map1.put("funcitonName", "授权");
		map1.put("roleFunctionId", "CB00000013");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("funcitonNumber", "003");
		map2.put("funcitonName", "管理");
		map2.put("roleFunctionId", "CB00000015");
		
		customerList.add(map);
		customerList.add(map1);
		customerList.add(map2);
		headMap.put("returnCode", "00000000");
		resultMap.put("roleFunctionList", customerList);
		resultMap.put("rspHead", headMap);
//			resultMap = clientRouteTransService.route(headMap,bodyMap);
		return resultMap;
	}
	
	
	
	@RequestMapping(value = "/queryRoleDetail" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> queryRoleDetail(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("查询角色详情"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	

}
