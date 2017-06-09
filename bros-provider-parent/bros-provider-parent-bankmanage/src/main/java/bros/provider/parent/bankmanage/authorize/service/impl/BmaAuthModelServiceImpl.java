package bros.provider.parent.bankmanage.authorize.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.bankmanage.authorize.service.IBmaActRoleRelSerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelEntitySerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelRuleGpEntitySerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelSerivce;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.constants.BankmanageParamsConstants;
import bros.provider.parent.bankmanage.tellerrole.service.ITellerRoleEntitySerivce;

/**
 * 
 * @ClassName: BmaAuthModelServiceImpl 
 * @Description: 内部授权模型管理服务
 * @author pengxiangnan 
 * @date 2016年7月18日 下午1:17:16 
 * @version 1.0
 */
@Component(value="bmaAuthModelSerivce")
public class BmaAuthModelServiceImpl implements IBmaAuthModelSerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(BmaAuthModelServiceImpl.class);
	
	
	/**
	 * 内部授权模型实体服务
	 */
	@Autowired
	private IBmaAuthModelEntitySerivce bmaAuthModelEntitySerivce;
	
	/**
	 * 内部授权模型规则实体服务
	 */
	@Autowired
	private IBmaAuthModelRuleGpEntitySerivce bmaAuthModelRuleGpEntitySerivce;
	
	/**
	 * 授权关联表
	 */
	@Autowired
	private IBmaActRoleRelSerivce bmaActRoleRelSerivce;
	
	/**
	 * 柜员角色实体服务
	 */
	@Autowired
	private ITellerRoleEntitySerivce tellerRoleEntitySerivce;

	
	/**
	 * 保存授权模型
	 */
	@Override
	public void saveAuthorizationModel(Map<String, Object> authModelMap) throws ServiceException {
		try{
			//新增授权模型
			if(null!=authModelMap && authModelMap.size()>0){
				
				String authorizeId = (String) authModelMap.get("authorizeId");
				String name = (String) authModelMap.get("name");
				String orderly = (String) authModelMap.get("orderly");
				String state = (String) authModelMap.get("state");
				String legalId = (String) authModelMap.get("legalId");
				String channel = (String) authModelMap.get("channel");
				String authMode = (String) authModelMap.get("authMode");
				String authType = (String) authModelMap.get("authType");
				String authShape = (String) authModelMap.get("authShape");
				String authRole = (String) authModelMap.get("authRole");
				String authLevel = (String) authModelMap.get("authLevel");
			
				 int resultNum = bmaAuthModelEntitySerivce.saveBmaAuthModel(authorizeId, name, orderly, state, 
						 legalId, channel, authMode, authType, authShape, authRole, authLevel);
				 
				 if(resultNum!=1){
					 throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "授权模型新增失败");
				 }
			}else{
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "授权模型新增失败");
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "授权模型新增失败", ex);
		}
		
	}
    
    /**
     * 授权规则新增
     */
	@Override
	public void saveAuthModelRuleGp(List<Map<String, Object>> authRuleList) throws ServiceException {
		try{
			//新增授权规则
			if(null!=authRuleList && authRuleList.size()>0){
				int resultNum = bmaAuthModelRuleGpEntitySerivce.saveBmaAuthModelRuleGp(authRuleList);
				if(resultNum!=authRuleList.size()){
					throw new ServiceException(BankmanageErrorCodeConstants.PPBM0212, "授权规则新增失败");
				}
			}else{
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0212, "授权规则新增失败");
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthModelRuleGp method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthModelRuleGp method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0212, "授权规则新增失败", ex);
		}
	}
 
	
	/**
	 * 删除授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public void deleteAuthorizationModel(String channel, String legalId, String authorizeId, String authRole) throws ServiceException {
		try{
			//删除授权模型基本信息
			int resultNum = bmaAuthModelEntitySerivce.deleteBmaAuthModel(channel, legalId, authorizeId);
			if(resultNum!=1){
				 throw new ServiceException(BankmanageErrorCodeConstants.PPBM0202, "删除授权模型失败");
			}
			//授权类型角色不为空
			if(!ValidateUtil.isEmpty(authRole)){
				//删除授权模型
				bmaActRoleRelSerivce.deleteBmaActRoleRel(authRole);
			}else{
				
				//根据授权模型编号查询授权模型规则列表
				List<Map<String,Object>> authModelRuleGpList = bmaAuthModelRuleGpEntitySerivce.queryBmaAuthModelRuleByAuthModelId(authorizeId);
				List<String> roleList  = new  ArrayList<String>();
				if(authModelRuleGpList!=null&&authModelRuleGpList.size()>0){
					for(Map<String, Object> authModelRuleGpMap : authModelRuleGpList) {
						String authRnrole1 = (String)authModelRuleGpMap.get("authRnrole1");
						String authRnrole2 = (String)authModelRuleGpMap.get("authRnrole2");
						String authRnrole3 = (String)authModelRuleGpMap.get("authRnrole3");
						String authRnrole4 = (String)authModelRuleGpMap.get("authRnrole4");
						String authRnrole5 = (String)authModelRuleGpMap.get("authRnrole5");
						
						if( !ValidateUtil.isEmpty(authRnrole1) ){
							 roleList.add(authRnrole1);
		                }
	
		                if( !ValidateUtil.isEmpty(authRnrole2)){
		                	roleList.add(authRnrole2);
		                }
		                
		                if( !ValidateUtil.isEmpty(authRnrole3) ){
		                	roleList.add(authRnrole3);
		                }
		                
		                if( !ValidateUtil.isEmpty(authRnrole4)){
		                	roleList.add(authRnrole4);
		                }
		                
		                if( !ValidateUtil.isEmpty(authRnrole5)){
		                	roleList.add(authRnrole5);
		                }
					}
				
				if(null!=roleList && roleList.size()>0){
					bmaActRoleRelSerivce.deleteBatchBmaActRoleRel(roleList);
				}
			
				//删除授权模型规则信息
				resultNum = bmaAuthModelRuleGpEntitySerivce.deleteBmaAuthModelRuleGp(authorizeId);
				}
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAuthorizationModel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0202, "授权模型删除失败", ex);
		}
	}
    
	/**
	 * 修改授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public void updateAuthorizationModel(Map<String, Object> authModelMap, List<Map<String,Object>> authRuleList) throws ServiceException {
		try{
			String authorizeId = (String) authModelMap.get("authorizeId");
			String name = (String) authModelMap.get("name");
			String orderly = (String) authModelMap.get("orderly");
			String state = (String) authModelMap.get("state");
			String legalId = (String) authModelMap.get("legalId");
			String channel = (String) authModelMap.get("channel");
			String authMode = (String) authModelMap.get("authMode");
			String authType = (String) authModelMap.get("authType");
			String authShape = (String) authModelMap.get("authShape");
			String authRole = (String) authModelMap.get("authRole");
			String authLevel = (String) authModelMap.get("authLevel");
			
			//修改授权模型
			int resultNum = bmaAuthModelEntitySerivce.updateBmaAuthModel(authorizeId, name, orderly, state, 
					legalId, channel, authMode, authType, authShape, authRole, authLevel);
			
			if(resultNum!=1){
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0203, "授权模型修改失败");
			}
			
			//删除旧授权模型规则定义
			bmaAuthModelRuleGpEntitySerivce.deleteBmaAuthModelRuleGp(authorizeId);
			//插入新授权模型规则定义
			if(null!=authRuleList && authRuleList.size()>0){
				 resultNum = bmaAuthModelRuleGpEntitySerivce.saveBmaAuthModelRuleGp(authRuleList);
				 if(resultNum!=authRuleList.size()){
					throw new ServiceException(BankmanageErrorCodeConstants.PPBM0212, "修改授权模型规则失败");
				 }
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0203, "修改授权模型失败", ex);
		}
	}
	
	/**
	 * 
	 * @Title: checkAuthorizeModelRule 
	 * @Description:检查规则数据及组装数据
	 * @param authRuleList 授权规则定义列表
	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
	 * @param authorizeId 授权模型编号
	 * @return Map<String, Object> 返回结果集
	 * @throws ServiceException
	 */
    @SuppressWarnings("unchecked")
	public Map<String, Object> checkAuthorizeModelRule(List<Map<String,Object>> authRuleList, String authMode, 
    		 String authorizeId,String authLevel)  throws ServiceException 
     {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	//金额区间列表
 		Map<Integer, Map<String, BigDecimal>>  amountMap = new HashMap<Integer, Map<String,BigDecimal>>();
 		//授权关联角色
 		List<Map<String,Object>> roleList = new ArrayList<Map<String,Object>>();
 		Map<String, Object> roleMap = new HashMap<String, Object>() ;
 		//保存规则列表
 		List<Map<String,Object>> authRuleListParam = new ArrayList<Map<String,Object>>();
		if(null!=authRuleList && authRuleList.size()>0){
			//无金额校验
			if (!BankmanageParamsConstants.AUTH_MODEL_0.equals(authMode) 
					&& authRuleList.size()>1) 
			{
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0206, "无金额模式下，只能有1条规则");
			}
		
			for(Map<String ,Object> authRuleMap : authRuleList){
				Map<String,Object> authModeRulegpMap = new HashMap<String, Object>();
				authModeRulegpMap.put("argId", BaseUtil.createUUID());
				authModeRulegpMap.put("authModelId", authorizeId);
				String argRanking = (String) authRuleMap.get("ranking");
				authModeRulegpMap.put("argRanking", argRanking);
				//校验金额
				this.checkAmount(authMode, amountMap, authRuleMap, authModeRulegpMap, argRanking,authLevel);
				List<Map<String,Object>> authList = (List<Map<String, Object>>) authRuleMap.get("authList"); //授权列表
				if(null!=authList && authList.size()>0){
					for(Map<String,Object> authMap : authList){
						String modelRoleLevel = (String)authMap.get("modelRoleLevel");
						String personNum=(String)authMap.get("personNum");
						String roleId=(String)authMap.get("roleId");
						//校验人数
						checkPersonNum(personNum,roleId);
						//组装数据
						if ("1".equals(modelRoleLevel)) {
							roleMap = new HashMap<String, Object>();
							String barId = BaseUtil.createUUID();
							authModeRulegpMap.put("authRnrole1", barId);
							authModeRulegpMap.put("roleNumber1", personNum);
							roleMap.put("barId", barId);
   							roleMap.put("roleId", roleId);
   							roleList.add(roleMap);
						}
						if ("2".equals(modelRoleLevel)) {
							roleMap = new HashMap<String, Object>();
							String barId = BaseUtil.createUUID();
							authModeRulegpMap.put("authRnrole2", barId);
							authModeRulegpMap.put("roleNumber2", personNum);
							roleMap.put("barId", barId);
   							roleMap.put("roleId", roleId);
   							roleList.add(roleMap);
						}
						if ("3".equals(modelRoleLevel)) {
							roleMap = new HashMap<String, Object>();
							String barId = BaseUtil.createUUID();
							authModeRulegpMap.put("authRnrole3", barId);
							authModeRulegpMap.put("roleNumber3", personNum);
							roleMap.put("barId", barId);
   							roleMap.put("roleId", roleId);
   							roleList.add(roleMap);
						}
						if ("4".equals(modelRoleLevel)) {
							roleMap = new HashMap<String, Object>();
							String barId = BaseUtil.createUUID();
							authModeRulegpMap.put("authRnrole4", barId);
							authModeRulegpMap.put("roleNumber4", personNum);
							roleMap.put("barId", barId);
   							roleMap.put("roleId", roleId);
   							roleList.add(roleMap);
						}
						if ("5".equals(modelRoleLevel)) {
							roleMap = new HashMap<String, Object>();
							String barId = BaseUtil.createUUID();
							authModeRulegpMap.put("authRnrole5", barId);
							authModeRulegpMap.put("roleNumber5", personNum);
							roleMap.put("barId", barId);
   							roleMap.put("roleId", roleId);
   							roleList.add(roleMap);
						}
					}
				}
				authRuleListParam.add(authModeRulegpMap);
			}
       }
		resultMap.put("roleListParam", roleList);
		resultMap.put("listAmount", amountMap);
		resultMap.put("authRuleListParam", authRuleListParam);
		return resultMap;
	}
    /**
   	 * 
   	 * @Title: checkAuthorizeModelRuleNotCheckPersonNum 
   	 * @Description:检查规则数据及组装数据
   	 * @param authRuleList 授权规则定义列表
   	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
   	 * @param authorizeId 授权模型编号
   	 * @return Map<String, Object> 返回结果集
   	 * @throws ServiceException
   	 */
       @SuppressWarnings("unchecked")
   	public Map<String, Object> checkAuthorizeModelRuleNotCheckPersonNum(List<Map<String,Object>> authRuleList, String authMode, 
       		 String authorizeId,String authType)  throws ServiceException 
        {
       	Map<String, Object> resultMap = new HashMap<String, Object>();
       	//金额区间列表
    		Map<Integer, Map<String, BigDecimal>>  amountMap = new HashMap<Integer, Map<String,BigDecimal>>();
    		//保存规则列表
    		List<Map<String,Object>> authRuleListParam = new ArrayList<Map<String,Object>>();
   		if(null!=authRuleList && authRuleList.size()>0){
   			//无金额校验
   			if (!BankmanageParamsConstants.AUTH_MODEL_0.equals(authMode) 
   					&& authRuleList.size()>1) 
   			{
   				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0206, "无金额模式下，只能有1条规则");
   			}
   			for(Map<String ,Object> authRuleMap : authRuleList){
   				Map<String,Object> authModeRulegpMap = new HashMap<String, Object>();
   				authModeRulegpMap.put("argId", BaseUtil.createUUID());
   				authModeRulegpMap.put("authModelId", authorizeId);
   				String argRanking = (String) authRuleMap.get("ranking");
   				authModeRulegpMap.put("argRanking", argRanking);
   				//校验金额
   				this.checkAmount(authMode, amountMap, authRuleMap, authModeRulegpMap, argRanking,authType);
   				List<Map<String,Object>> authList = (List<Map<String, Object>>) authRuleMap.get("authList"); //授权列表
   				if(null!=authList && authList.size()>0){
   					for(Map<String,Object> authMap : authList){
   						String modelRoleLevel = (String)authMap.get("modelRoleLevel");
   						String roleId=(String)authMap.get("roleId");
   						//组装数据
   						if ("1".equals(modelRoleLevel)) {
   							authModeRulegpMap.put("authRnrole1", roleId);
   							
   						}
   						if ("2".equals(modelRoleLevel)) {
   							authModeRulegpMap.put("authRnrole2", roleId);
   							
   						}
   						if ("3".equals(modelRoleLevel)) {
   							authModeRulegpMap.put("authRnrole3", roleId);
   							
   						}
   						if ("4".equals(modelRoleLevel)) {
   							authModeRulegpMap.put("authRnrole4", roleId);
   							
   						}
   						if ("5".equals(modelRoleLevel)) {
   							authModeRulegpMap.put("authRnrole5", roleId);
   						}
   					}
   				}
   				authRuleListParam.add(authModeRulegpMap);
   			}
          }
   		resultMap.put("listAmount", amountMap);
   		resultMap.put("authRuleListParam", authRuleListParam);
   		return resultMap;
   	}
    
    /**
	 * 
	 * @Title: checkAuthorizeModelRuleAndRoleList 
	 * @Description:检查规则数据及组装数据
	 * @param authRuleList 授权规则定义列表
	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
	 * @param authorizeId 授权模型编号
	 * @return Map<String, Object> 返回结果集
	 * @throws ServiceException
	 */
    @SuppressWarnings("unchecked")
	public Map<String, Object> checkAuthorizeModelRuleAndRoleList(List<Map<String,Object>> authRuleList, String authMode, 
    		 String authorizeId , List<Map<String, Object>> roleCheckedList,String authLevel)  throws ServiceException 
     {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	//金额区间列表
 		Map<Integer, Map<String, BigDecimal>>  amountMap = new HashMap<Integer, Map<String,BigDecimal>>();
 		//保存规则列表
 		List<Map<String,Object>> authRuleListParam = new ArrayList<Map<String,Object>>();
 		List<Map<String,Object>> roleList = new ArrayList<Map<String,Object>>();
		if(null!=authRuleList && authRuleList.size()>0){
			//无金额校验
			if (!BankmanageParamsConstants.AUTH_MODEL_0.equals(authMode) 
					&& authRuleList.size()>1) 
			{
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0206, "无金额模式下，只能有1条规则");
			}
			for(Map<String ,Object> authRuleMap : authRuleList){
				Map<String,Object> authModeRulegpMap = new HashMap<String, Object>();
				authModeRulegpMap.put("argId", BaseUtil.createUUID());
				authModeRulegpMap.put("authModelId", authorizeId);
				String argRanking = (String) authRuleMap.get("ranking");
				authModeRulegpMap.put("argRanking", argRanking);
				//校验金额
				this.checkAmount(authMode, amountMap, authRuleMap, authModeRulegpMap, argRanking,authLevel);
				List<Map<String,Object>> authList = (List<Map<String, Object>>) authRuleMap.get("authList"); //授权列表
				if(null!=authList && authList.size()>0){
					for(Map<String,Object> authMap : authList){
						String modelRoleLevel = (String)authMap.get("modelRoleLevel");
						//组装数据
						if ("1".equals(modelRoleLevel)) {
							String barId = BaseUtil.createUUID();
							authModeRulegpMap.put("authRnrole1", barId);
						    List<Map<String, Object>>  roleListNew= (List<Map<String, Object>>) roleCheckedList.get(Integer.parseInt(argRanking)-1);
						    for (Map<String, Object> map : roleListNew) {
						    	 map.put("barId", barId);
						    	 roleList.add(map);
							}
						}
						if ("2".equals(modelRoleLevel)) {
							authModeRulegpMap.put("authRnrole2", "");
						}
						if ("3".equals(modelRoleLevel)) {
							authModeRulegpMap.put("authRnrole3", "");
						}
						if ("4".equals(modelRoleLevel)) {
							authModeRulegpMap.put("authRnrole4", "");
						}
						if ("5".equals(modelRoleLevel)) {
							authModeRulegpMap.put("authRnrole5", "");
						}
					}
				}
				authRuleListParam.add(authModeRulegpMap);
			}
       }
		resultMap.put("roleListParam", roleList);
		resultMap.put("listAmount", amountMap);
		resultMap.put("authRuleListParam", authRuleListParam);
		return resultMap;
	}
     
    /**
     * 
     * @Title: checkAmount 
     * @Description: 校验金额输入的合法性
     * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
     * @param amountMap 金额区间集合
     * @param authRuleMap 授权规则定义集合
     * @param authModeMapRulegp
     * @param ranking 规则所在授权模型序号
     * @throws ServiceException
     */
 	public void checkAmount(String authMode, Map<Integer, Map<String, BigDecimal>> amountMap, Map<String, Object> authRuleMap,
 			Map<String, Object> authModeMapRulegp, String ranking,String authLevel) throws ServiceException 
 	{
 		String startAmount = (String) authRuleMap.get("startAmount");
 		String endAmount = (String) authRuleMap.get("endAmount");
 		
 		BigDecimal minAmountBigDecimal = new BigDecimal("0.00");
 		BigDecimal maxAmountBigDecimal = new BigDecimal("0.00");
 		
 		if (BankmanageParamsConstants.AUTH_MODEL_0.equals(authMode)) {
 			if(!authLevel.equals("0")){
 				if(ValidateUtil.isEmpty(startAmount) || ValidateUtil.isEmpty(endAmount)){
 	 				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0209, "有金额模式下，模型规则的金额区间不能为空");
 	 			}
 			}
 			
 			
 			
 			//有金额模式下校验金额输入是否合法
 	 		minAmountBigDecimal = new BigDecimal(startAmount);
 	 		maxAmountBigDecimal = new BigDecimal(endAmount);
 	 		
 			Map<String, BigDecimal> mapAmount = new HashMap<String, BigDecimal>();
 			if(!authLevel.equals("0")){
	 			if("1".equals(ranking) && minAmountBigDecimal.compareTo(BigDecimal.ZERO)!=0){
	 				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0210, "有金额模式下，模型规则的起始金额必须从零开始");
	 			}
 			}
 			if (maxAmountBigDecimal.compareTo(minAmountBigDecimal)!=1) {
 				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0211, "有金额模式下，模型规则的金额上限必须大于下限");
 			}
 			mapAmount.put("minAmount", minAmountBigDecimal);
 			mapAmount.put("maxAmount", maxAmountBigDecimal);
 			amountMap.put(Integer.parseInt(ranking), mapAmount);
 		}
 		
 		authModeMapRulegp.put("minAmount", minAmountBigDecimal);
 		authModeMapRulegp.put("maxAmount", maxAmountBigDecimal);
 	}
 	
	/**
	 * 
	 * @Title: checkAmountRange 
	 * @Description: 校验金额区间的完整性 
	 * @param authMode 授权方式（0：额度; 1：强制(无金额); 2：条件）
	 * @param amountMap 金额区间集合
	 * @throws ServiceException
	 */
	public void checkAmountRange(String authMode, Map<Integer, Map<String, BigDecimal>> amountMap) throws ServiceException {
		if (BankmanageParamsConstants.AUTH_MODEL_0.equals(authMode)) {
			for (int i = 1; i <=amountMap.size(); i++) {
				if (i!=amountMap.size()) {
					Map<String, BigDecimal> mapAmountTemp1 = amountMap.get(i);
					Map<String, BigDecimal> mapAmountTemp2 = amountMap.get(i+1);
					if (mapAmountTemp1.get("maxAmount").compareTo(mapAmountTemp2.get("minAmount"))!=0) {
						throw new ServiceException(BankmanageErrorCodeConstants.PPBM0208, "有金额模式下，模型规则的金额区间不连续");
					}	
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: checkPersonNum 
	 * @Description: 校验人数
	 * @param personNum 人数
	 * @param roleId 角色编号
	 * @throws ServiceException
	 */
	public void checkPersonNum(String personNum, String roleId) throws ServiceException {
		if(ValidateUtil.isEmpty(roleId)){
			personNum = null;
			return;
		}else {
			personNum = personNum==null ? "" : personNum;
		}
		
		if (!BankmanageParamsConstants.PATTERN_PERSON_NUM.matcher(personNum).find()) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0207, "角色人数输入错误");
		}
		if (Integer.parseInt(personNum)<=0) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0207, "角色人数输入错误");
		}
	}
    
	/**
	 * 
	 * @Title: checkAuthModelNameExist 
	 * @Description: 检查授权模型是否存在
	 * @param channel 渠道标识
	 * @param legalId 法人唯一标识
	 * @param authorize 授权标识
	 * @throws ServiceException 
	 */
	public void checkAuthModelIsExist(String channel, String legalId, String authModelName) throws ServiceException {
		Map<String, Object> authModelMap = bmaAuthModelEntitySerivce.queryBmaAuthModelByName(channel, legalId, authModelName);
		if (authModelMap!=null && authModelMap.size()!=0) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0205, "授权模型名称已经存在");
		}
	}
     
	/**
	 * 保存授权模型规则角色列表
	 */
	@Override
	public void saveAuthorizationModel(Map<String, Object> authModelMap, List<Map<String, Object>> authRuleList, 
			List<Map<String, Object>> roleList) throws ServiceException 
    {
		try{
			//新增授权模型
			if(null!=authModelMap && authModelMap.size()>0){
				String authorizeId = (String) authModelMap.get("authorizeId");
				String name = (String) authModelMap.get("name");
				String orderly = (String) authModelMap.get("orderly");
				String state = (String) authModelMap.get("state");
				String legalId = (String) authModelMap.get("legalId");
				String channel = (String) authModelMap.get("channel");
				String authMode = (String) authModelMap.get("authMode");
				String authType = (String) authModelMap.get("authType");
				String authShape = (String) authModelMap.get("authShape");
				String authRole = (String) authModelMap.get("authRole");
				String authLevel = (String) authModelMap.get("authLevel");
				int resultNum = bmaAuthModelEntitySerivce.saveBmaAuthModel(authorizeId, name, orderly, state, 
						 legalId, channel, authMode, authType, authShape, authRole, authLevel);
				 
				if(resultNum!=1){
					throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "授权模型新增失败");
				}
			}else{
				throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "授权模型新增失败");
			}
			//新增授权规则
			if(null!=authRuleList && authRuleList.size()>0){
				int resultNum = bmaAuthModelRuleGpEntitySerivce.saveBmaAuthModelRuleGp(authRuleList);
				if(resultNum!=authRuleList.size()){
					throw new ServiceException(BankmanageErrorCodeConstants.PPBM0212, "授权规则新增失败");
				}
			}
			//新增关联授权角色列表
			if(null!=roleList && roleList.size()>0){
				int resultNum = bmaActRoleRelSerivce.saveBmaActRoleRel(roleList);
				if(resultNum!=roleList.size()){
					throw new ServiceException(BankmanageErrorCodeConstants.PPBM0212, "新增关联授权角色列表");
				}
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModelAndRoleList method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModelAndRoleList method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "授权模型新增失败", ex);
		}
		
	}
    
	/**
	 * 查询角色列表
	 */
	@Override
	public List<Map<String, Object>> queryBmaRoleByRole(String authRole)	throws ServiceException {
		try{
			
			List<Map<String, Object>> roleListMap = new  ArrayList<Map<String,Object>>();
			Map<String, Object> rolesMap = new HashMap<String, Object>();
			List<String> roleIdList = new ArrayList<String>();	
			//查询临柜角色
			List<Map<String, Object>> bmaActRoleList = bmaActRoleRelSerivce.queryBmaActRoleRefById(authRole);
			if(null!=bmaActRoleList && bmaActRoleList.size()>0){
				for(Map<String, Object> bmaActRole : bmaActRoleList) {
					String roleId = (String)bmaActRole.get("roleId");
					roleIdList.add(roleId);
				}
			}
			
			if(null!=roleIdList && roleIdList.size()>0){
				//批量查询角色ID
				List<Map<String, Object>> roleList = tellerRoleEntitySerivce.queryBmaRoleByRoleIdList(roleIdList);
				if(null!=roleList){
					for (Map<String, Object> map : roleList) {
						rolesMap = new HashMap<String, Object>();
						rolesMap.put("barId", authRole);
						rolesMap.put("name", (String)map.get("name"));
						rolesMap.put("roleId", (String)map.get("roleId"));
						roleListMap.add(rolesMap);
					}
				}
			}
			return roleListMap;
	}catch(ServiceException e){
		logger.error("Exception from " + this.getClass().getName() + "'s queryBmaRoleByRole method.", e);
		throw e;
	}catch(Exception ex){
		logger.error("Exception from " + this.getClass().getName() + "'s queryBmaRoleByRole method.", ex);
		throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "查询角色列表失败", ex);
	}
		
	}
  
}
