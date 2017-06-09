package bros.manage.bankmanage.operator.controller;

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
 * @Description: 操作员管理控制器
 * @author 吴成龙
 * @date 2016年5月12日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class OperatorController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(OperatorController.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	@Autowired
	private TokenUtil tokenUtil;
	

	@RequestMapping(value = "/operateQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> operateQuery(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			
			String legalCode = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE));
			if(ValidateUtil.isEmpty(legalCode)){
				throw new ServiceException("法人编号不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("legalcode", legalCode);//交易柜员
			 headMap.put("sceneCode", "0001");//场景编码
			
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("查询操作员异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	
	@RequestMapping(value = "/operateDetailQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> operateDetailQuery(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			logger.error("查询操详情作员异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/operateAdd" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> operateAdd(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			
			String legalCode = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE));
			if(ValidateUtil.isEmpty(legalCode)){
				throw new ServiceException("法人编号不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("legalcode", legalCode);//交易柜员
			 bodyMap.put("legalcode", legalCode);
			 headMap.put("sceneCode", "0001");//场景编码
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("增加操作员异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/operateUpdate" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> operateUpdate(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			
			String legalCode = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE));
			if(ValidateUtil.isEmpty(legalCode)){
				throw new ServiceException("法人编号不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("legalcode", legalCode);//交易柜员
			 headMap.put("sceneCode", "0001");//场景编码
			
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("修改操作员异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	
	
	@RequestMapping(value = "/operateDelete" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> operateDelete(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
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
			
			String legalCode = (String) (sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE)==null?"":sessionMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE));
			if(ValidateUtil.isEmpty(legalCode)){
				throw new ServiceException("法人编号不存在");
			}
			
			 headMap.put("legalId", legalId);//法人
			 headMap.put("branchId", branchNo);//机构
			 headMap.put("tranTellerNo", tellerNo);//交易柜员
			 headMap.put("legalcode", legalCode);//交易柜员
			 headMap.put("sceneCode", "0001");//场景编码
			
			
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			
		}catch(ServiceException e){
			logger.error("删除操作员异常"+e);
			this.handleError(e,resultMap);
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/accountQuery" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> accountQuery(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> headMap = this.handleReqHeadData(loginMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
		List<Map<String, Object>> customerList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorAccNo", "123456789");
		map.put("operatorAccAuth", "1");
		map.put("accountName", "张三");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("operatorAccNo", "2242242224");
		map1.put("operatorAccAuth", "0");
		map1.put("accountName", "李四");
		customerList.add(map);
		customerList.add(map1);
		headMap.put("returnCode", "00000000");
		resultMap.put("returnList", customerList);
		resultMap.put("rspHead", headMap);
//			resultMap = clientRouteTransService.route(headMap,bodyMap);
		return resultMap;
	}
	
	

}
