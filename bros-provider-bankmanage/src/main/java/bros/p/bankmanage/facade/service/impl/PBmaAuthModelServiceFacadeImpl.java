package bros.p.bankmanage.facade.service.impl;

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

import bros.common.core.annotation.Validation;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.DataUtil;
import bros.common.core.util.ValidateUtil;
import bros.p.bankmanage.facade.service.IPBmaAuthModelSerivceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.authorize.service.IBmaActRoleRelSerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelEntitySerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelRuleGpEntitySerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelSerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaBsnAuthEntitySerivce;
import bros.provider.parent.bankmanage.authorize.service.IBmaMenudefEntitySerivce;
import bros.provider.parent.bankmanage.constants.BankmanageParamsConstants;
import bros.provider.parent.bankmanage.tellerrole.service.ITellerRoleEntitySerivce;

/** 
 * 
 * @ClassName: PBmaAuthModelServiceFacadeImpl 
 * @Description: 内部授权模型管理对外发布服务
 * @author pengxiangnan 
 * @date 2016年7月18日 下午2:34:35 
 * @version 1.0
 */
@Component("pbmaAuthModelSerivceFacade")
public class PBmaAuthModelServiceFacadeImpl implements IPBmaAuthModelSerivceFacade {
	
	private static final  Logger logger = LoggerFactory.getLogger(PBmaAuthModelServiceFacadeImpl.class);
	
	/**
	 * 内部授权模型管理服务
	 */
	@Autowired
	private IBmaAuthModelSerivce bmaAuthModelSerivce;
	
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
	 * 柜员角色实体服务
	 */
	@Autowired
	private ITellerRoleEntitySerivce tellerRoleEntitySerivce;
	
	/**
	 * 关联角色实体服务
	 */
	@Autowired
	private IBmaActRoleRelSerivce bmaActRoleRelSerivce;
	
	/**
	 * 功能分配配置实体服务
	 */
	@Autowired
	private IBmaBsnAuthEntitySerivce bmaBsnAuthEntitySerivce;
	
	/**
	 * 授权菜单实体服务
	 */
	@Autowired
	private IBmaMenudefEntitySerivce bmaMenudefEntitySerivce;
	
	/**
	 * 新增授权模型
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000333")
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public ResponseEntity saveAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			ResponseEntity resultEntity = new ResponseEntity();
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String authMode = (String) bodyMap.get("authMode");
			String name = (String) bodyMap.get("name");
			String orderly = (String)bodyMap.get("orderly");
			String state = BankmanageParamsConstants.AUTHMODEL_STATE_1;
			String authType = (String)bodyMap.get("authType");
			String authShape = (String)bodyMap.get("authShape");
			String authLevel = (String)bodyMap.get("authLevel");
			//生成授权模型编号
			String authorizeId = BaseUtil.createUUID();
			
			//校验名称是否存在
			bmaAuthModelSerivce.checkAuthModelIsExist(channel, legalId, name);
			
			//组装Map
			Map<String, Object> authModelMap = new HashMap<String, Object>();
			authModelMap.put("authorizeId", authorizeId);
			authModelMap.put("name", name);
			authModelMap.put("orderly", orderly);
			authModelMap.put("state", state);
			authModelMap.put("legalId", legalId);
			authModelMap.put("channel", channel);
			authModelMap.put("authMode", authMode);
			authModelMap.put("authType", authType);
			authModelMap.put("authShape", authShape);
			authModelMap.put("authLevel", authLevel);

			List<Map<String,Object>> authRuleList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> authRuleListParam  = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> roleList  = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> roleCheckedList  = new ArrayList<Map<String,Object>>();
			Map<Integer, Map<String, BigDecimal>> listAmount = new HashMap<Integer, Map<String, BigDecimal>>();
			Map<String, Object> paramOut = new HashMap<String, Object>();
			
			//授权类型 立即生效 不存角色 不存规则
			if(!ValidateUtil.isEmpty(authType) && "0".equals(authType)){
				//保存授权模型和授权规则
				bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
				return resultEntity;
			}
			
			//多人模式下处理授权模型
			if(!ValidateUtil.isEmpty(authLevel) && "1".equals(authLevel)){
				if(!ValidateUtil.isEmpty(authShape) && "0".equals(authShape)){//审核 不存角色	关联规则
					if(!ValidateUtil.isEmpty(authMode) && ("0".equals(authMode)||"1".equals(authMode))){//授权方式（0：额度，1：强制(无金额)，2：条件）
						
						authRuleList =  (List<Map<String, Object>>) bodyMap.get("authRuleList");
						
						//检查数据一致性
						paramOut = bmaAuthModelSerivce.checkAuthorizeModelRule(authRuleList, authMode, authorizeId,authLevel);
						listAmount= (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
						authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
						roleList = (List<Map<String, Object>>) paramOut.get("roleListParam");
						
						//校验金额2（校验金额区间是否完整）
						bmaAuthModelSerivce.checkAmountRange(authMode, listAmount);
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}
				}
			}
			
			
			//单人模式下处理授权模型
			if(!ValidateUtil.isEmpty(authLevel) && "0".equals(authLevel)){
				if(!ValidateUtil.isEmpty(authType) && "1".equals(authType)){//授权类型 1：互为授权
					if(!ValidateUtil.isEmpty(authMode) && "0".equals(authMode)){//额度 不存角色	关联规则
						
						 authRuleList =  (List<Map<String, Object>>) bodyMap.get("authRuleList");
						
						//检查数据一致性
						paramOut = bmaAuthModelSerivce.checkAuthorizeModelRuleNotCheckPersonNum(authRuleList, authMode, authorizeId,authLevel);
						listAmount = (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
						authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
						
						//校验金额2（校验金额区间是否完整）
						bmaAuthModelSerivce.checkAmountRange(authMode, listAmount);
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}else if(!ValidateUtil.isEmpty(authMode) && ("1".equals(authMode)||"2".equals(authMode))){//强制（无金额）、条件	不存角色	不关联规则
						
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}
				}else if(!ValidateUtil.isEmpty(authType) && "2".equals(authType)){//2：指定授权 
					if(!ValidateUtil.isEmpty(authMode) && "0".equals(authMode)){//额度 不存角色	关联规则
						
					 	authRuleList =  (List<Map<String, Object>>) bodyMap.get("authRuleList");
					 	roleCheckedList =  (List<Map<String, Object>>) bodyMap.get("roleCheckedList");
						
						//检查数据一致性
					 	paramOut = bmaAuthModelSerivce.checkAuthorizeModelRuleAndRoleList(authRuleList, authMode, authorizeId, roleCheckedList,authLevel);
					 	listAmount = (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
					 	authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
					 	roleList = (List<Map<String, Object>>) paramOut.get("roleListParam");
						//校验金额2（校验金额区间是否完整）
						bmaAuthModelSerivce.checkAmountRange(authMode, listAmount);
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}else if(!ValidateUtil.isEmpty(authMode) && ("1".equals(authMode)||"2".equals(authMode))){//强制（无金额）、条件	存角色	不关联规则
						
						roleList =  (List<Map<String, Object>>) bodyMap.get("roleList");
						String barId = BaseUtil.createUUID();
						for (Map<String, Object> map : roleList) {
							map.put("barId", barId);
						}
						authModelMap.put("authRole", barId);
						
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam,roleList);
						return resultEntity;
					}
				}
			}
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModel method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0173, "新增授权模型失败", ex);
		}
	}
    
	/**
	 * 删除授权模型
	 */
	@Validation(value="p0000334")
	public ResponseEntity deleteAuthorizationModel(Map<String, Object> headMap,	Map<String, Object> bodyMap) throws ServiceException {
		try{
			ResponseEntity resultEntity = new ResponseEntity();
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String authorizeId = (String)bodyMap.get("authorizeId");
			String authRole="";
			//根据授权模型编号查询授权模型信息
			Map<String, Object> authModeMap = bmaAuthModelEntitySerivce.queryBmaAuthModelById(channel, legalId, authorizeId);
			if (authModeMap!=null && authModeMap.size()!=0) {
			     authRole = (String)authModeMap.get("authRole");
			}
			bmaAuthModelSerivce.deleteAuthorizationModel(channel, legalId, authorizeId, authRole);
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAuthorizationModel method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0174, "删除授权模型失败", ex);
		}
	}
    
	/**
	 * 修改授权模型
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000335")
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public ResponseEntity updateAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			ResponseEntity resultEntity = new ResponseEntity();
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String authMode = (String) bodyMap.get("authMode");
			String name = (String) bodyMap.get("name");
			String orderly = (String)bodyMap.get("orderly");
			String authorizeId = (String)bodyMap.get("authorizeId");
			String authType = (String)bodyMap.get("authType");
			String authShape = (String)bodyMap.get("authShape");
			String authRole = (String)bodyMap.get("authRole");
			String authLevel = (String)bodyMap.get("authLevel");
			String state = BankmanageParamsConstants.AUTHMODEL_STATE_1;
			
			//根据授权模型编号查询授权模型信息
			String authRole1="";
			Map<String, Object> authModeMap = bmaAuthModelEntitySerivce.queryBmaAuthModelById(channel, legalId, authorizeId);
			if (authModeMap!=null && authModeMap.size()!=0) {
				String authModelName = (String) authModeMap.get("name");
				 authRole1 = (String)authModeMap.get("authRole");
				if(!name.equals(authModelName)){
					//校验名称是否存在
					bmaAuthModelSerivce.checkAuthModelIsExist(channel, legalId, name);
				}
			}
			
			//删除数据
			bmaAuthModelSerivce.deleteAuthorizationModel(channel, legalId, authorizeId, authRole1);
			
			//组装Map
			Map<String, Object> authModelMap = new HashMap<String, Object>();
			authModelMap.put("authorizeId", authorizeId);
			authModelMap.put("name", name);
			authModelMap.put("orderly", orderly);
			authModelMap.put("state", state);
			authModelMap.put("legalId", legalId);
			authModelMap.put("channel", channel);
			authModelMap.put("authMode", authMode);
			authModelMap.put("authType", authType);
			authModelMap.put("authShape", authShape);
			authModelMap.put("authRole", authRole);
			authModelMap.put("authLevel", authLevel);

			List<Map<String,Object>> authRuleList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> authRuleListParam  = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> roleList  = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> roleCheckedList  = new ArrayList<Map<String,Object>>();
			Map<Integer, Map<String, BigDecimal>> listAmount = new HashMap<Integer, Map<String, BigDecimal>>();
			Map<String, Object> paramOut = new HashMap<String, Object>();
			
			//授权类型 立即生效 不存角色 不存规则
			if(!ValidateUtil.isEmpty(authType) && "0".equals(authType)){
				//保存授权模型和授权规则
				bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
				return resultEntity;
			}
			
			//多人模式下处理授权模型
			if(!ValidateUtil.isEmpty(authLevel) && "1".equals(authLevel)){
				if(!ValidateUtil.isEmpty(authShape) && "0".equals(authShape)){//审核 不存角色	关联规则
					if(!ValidateUtil.isEmpty(authMode) && ("0".equals(authMode)||"1".equals(authMode))){//授权方式（0：额度，1：强制(无金额)，2：条件）
						
						authRuleList =  (List<Map<String, Object>>) bodyMap.get("authRuleList");
						
						//检查数据一致性
						paramOut = bmaAuthModelSerivce.checkAuthorizeModelRule(authRuleList, authMode, authorizeId,authLevel);
						listAmount= (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
						authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
						roleList = (List<Map<String, Object>>) paramOut.get("roleListParam");
						
						//校验金额2（校验金额区间是否完整）
						bmaAuthModelSerivce.checkAmountRange(authMode, listAmount);
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}
				}
			}
			
			
			//单人模式下处理授权模型
			if(!ValidateUtil.isEmpty(authLevel) && "0".equals(authLevel)){
				if(!ValidateUtil.isEmpty(authType) && "1".equals(authType)){//授权类型 1：互为授权
					if(!ValidateUtil.isEmpty(authMode) && "0".equals(authMode)){//额度 不存角色	关联规则
						
						 authRuleList =  (List<Map<String, Object>>) bodyMap.get("authRuleList");
						
						//检查数据一致性
						paramOut = bmaAuthModelSerivce.checkAuthorizeModelRuleNotCheckPersonNum(authRuleList, authMode, authorizeId,authLevel);
						listAmount = (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
						authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
						
						//校验金额2（校验金额区间是否完整）
						bmaAuthModelSerivce.checkAmountRange(authMode, listAmount);
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}else if(!ValidateUtil.isEmpty(authMode) && ("1".equals(authMode)||"2".equals(authMode))){//强制（无金额）、条件	不存角色	不关联规则
						
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}
				}else if(!ValidateUtil.isEmpty(authType) && "2".equals(authType)){//2：指定授权 
					if(!ValidateUtil.isEmpty(authMode) && "0".equals(authMode)){//额度 不存角色	关联规则
						
					 	authRuleList =  (List<Map<String, Object>>) bodyMap.get("authRuleList");
					 	roleCheckedList =  (List<Map<String, Object>>) bodyMap.get("roleCheckedList");
						
						//检查数据一致性
					 	paramOut = bmaAuthModelSerivce.checkAuthorizeModelRuleAndRoleList(authRuleList, authMode, authorizeId, roleCheckedList,authLevel);
					 	listAmount = (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
					 	authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
					 	roleList = (List<Map<String, Object>>) paramOut.get("roleListParam");
						//校验金额2（校验金额区间是否完整）
						bmaAuthModelSerivce.checkAmountRange(authMode, listAmount);
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam, roleList);
						return resultEntity;
					}else if(!ValidateUtil.isEmpty(authMode) && ("1".equals(authMode)||"2".equals(authMode))){//强制（无金额）、条件	存角色	不关联规则
						
						roleList =  (List<Map<String, Object>>) bodyMap.get("roleList");
						String barId = BaseUtil.createUUID();
						for (Map<String, Object> map : roleList) {
							map.put("barId", barId);
						}
						authModelMap.put("authRole", barId);
						
						bmaAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam,roleList);
						return resultEntity;
					}
				}
			}
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0175, "修改授权模型失败", ex);
		}
	}
    
	/**
	 * 分页查询授权模型列表
	 */
	@Validation(value="p0000336")
	public ResponseEntity queryAuthorizationModelListForPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			
			int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")));
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")));
			
			//分页查询授权模型列表
			List<Map<String, Object>> resultList = bmaAuthModelEntitySerivce.queryBmaAuthModelListForPage(channel, legalId, pageNo, pageSize);
			
			//查询总记录数
			int totalNum = bmaAuthModelEntitySerivce.queryBmaAuthModelTotalNum(channel, legalId);
			
			//计算总页数
			int totalPage = DataUtil.getTotalPage(totalNum, pageSize);

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("resultList", resultList);
            resultMap.put("totalNum", totalNum);
            resultMap.put("totalPage", totalPage);
			
			ResponseEntity resultEntity = new ResponseEntity(resultMap);
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelListForPage method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelListForPage method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0176, "查询授权模型失败", ex);
		}
	}
    
	/**
	 * 根据授权模型编号查询授权模型详细信息
	 */
	@Validation(value="p0000337")
	public ResponseEntity queryAuthorizationModelDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String authorizeId  = (String) bodyMap.get("authorizeId");
			String authRole = (String)bodyMap.get("authRole");
			
			//根据授权模型编号查询授权模型信息
			Map<String, Object> authModeMap = bmaAuthModelEntitySerivce.queryBmaAuthModelById(channel, legalId, authorizeId);
			
			List<Map<String, Object>> roleListMap = new  ArrayList<Map<String,Object>>();
			List<Map<String,Object>> authModelRuleGpList = new  ArrayList<Map<String,Object>>();
		
			//如果临柜角色不为空
			if(!ValidateUtil.isEmpty(authRole)){
				roleListMap = bmaAuthModelSerivce.queryBmaRoleByRole(authRole);
				authModeMap.put("roleList", roleListMap);
				
			}
				
			//根据授权模型编号查询授权模型规则列表
			authModelRuleGpList = bmaAuthModelRuleGpEntitySerivce.queryBmaAuthModelRuleByAuthModelId(authorizeId);
			
			if(null!=authModelRuleGpList&&authModelRuleGpList.size()>0){
				//角色编号列表
				for(Map<String, Object> authModelRuleGpMap : authModelRuleGpList) {
					String authRnrole1 = (String)authModelRuleGpMap.get("authRnrole1");
					String authRnrole2 = (String)authModelRuleGpMap.get("authRnrole2");
					String authRnrole3 = (String)authModelRuleGpMap.get("authRnrole3");
					String authRnrole4 = (String)authModelRuleGpMap.get("authRnrole4");
					String authRnrole5 = (String)authModelRuleGpMap.get("authRnrole5");
	                
	                if( !ValidateUtil.isEmpty(authRnrole1)){
	                	roleListMap = bmaAuthModelSerivce.queryBmaRoleByRole(authRnrole1);
	                	authModelRuleGpMap.put("authRnrole1",roleListMap );
	                }

	                if( !ValidateUtil.isEmpty(authRnrole2)){
	                	roleListMap = bmaAuthModelSerivce.queryBmaRoleByRole(authRnrole2);
	                	authModelRuleGpMap.put("authRnrole2",roleListMap );
	                }
	                
	                if( !ValidateUtil.isEmpty(authRnrole3)){
	                	roleListMap = bmaAuthModelSerivce.queryBmaRoleByRole(authRnrole3);
	                	authModelRuleGpMap.put("authRnrole3",roleListMap );
	                }
	                
	                if( !ValidateUtil.isEmpty(authRnrole4)){
	                	roleListMap = bmaAuthModelSerivce.queryBmaRoleByRole(authRnrole4);
	                	authModelRuleGpMap.put("authRnrole4",roleListMap );
	                }
	                
	                if( !ValidateUtil.isEmpty(authRnrole5)){
	                	roleListMap = bmaAuthModelSerivce.queryBmaRoleByRole(authRnrole5);
	                	authModelRuleGpMap.put("authRnrole5",roleListMap );
	                }
				}
			}
			
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			if(null!=authModeMap && authModeMap.size()>0){
				resultMap.put("authModeMap", authModeMap);
			}
			if(null!=authModelRuleGpList && authModelRuleGpList.size()>0){
				resultMap.put("authModelRuleGpList", authModelRuleGpList);
			}
			ResponseEntity  responseEntity =  new  ResponseEntity(resultMap);
			return responseEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelDetail method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelDetail method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0176, "查询授权模型失败", ex);
		}
	}
    
	/**
	 * 授权模型分配
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public ResponseEntity updateFunctionAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			List<Map<String, Object>> funcAuthList = (List<Map<String, Object>>) bodyMap.get("funcAuthList");
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			List<Map<String, Object>> updateListTmp = new ArrayList<Map<String,Object>>();//更新list
			List<Map<String, Object>> insertListTmp = new ArrayList<Map<String,Object>>();//添加list
			
			if(funcAuthList!=null&&funcAuthList.size()>0){
				for(Map<String, Object> map : funcAuthList){
					Map<String, Object> authMapTmp=new HashMap<String, Object>();
					String bsnId = (String) map.get("bsnCode");//业务编码id
					String authModelId = (String) map.get("modelId");//授权模型id
					String procdefId="";//授权流程名称
					String authFlag="1";//授权标识0：否1：是
					//查询授权模型信息
					Map<String, Object> authModeMap=new HashMap<String, Object>();
					authModeMap = bmaAuthModelEntitySerivce.queryBmaAuthModelById(channel, legalId, authModelId);
					if(!ValidateUtil.isEmpty(authModelId)){
						if(authModeMap!=null&&authModeMap.size()>0){
							String authShape=authModeMap.get("authShape")==null?"0":(String)authModeMap.get("authShape");
							if(authShape.equals("0")){//审核式授权
								procdefId="auditAuthProcess";
							}else{//临柜授权
								procdefId="counterAuthProcess";
							}
						}else{
							throw new ServiceException(BankManageErrorCodeConstants.PPBE0176, "该授权模型信息不存在！");
						}
					}else{
						authFlag="0";
					}
					authMapTmp.put("bsnCode", bsnId);			//业务编号联合主键
					authMapTmp.put("channel", channel);			//发布渠道 联合主键
					authMapTmp.put("legalId", legalId);			//法人ID 联合主键
					authMapTmp.put("authFlag", authFlag);		//授权标识0：否1：是
					authMapTmp.put("modelId", authModelId);	//授权模型ID对应授权模型定义表中的ID字段
					authMapTmp.put("procdefId", procdefId);		//授权流程ID 	与ACT_RE_PROCDEF表中ID_字段对应
					int totalNum=0;
					totalNum = bmaBsnAuthEntitySerivce.queryBsbAuthNum(channel, legalId, bsnId);
					if(totalNum>0){
						updateListTmp.add(authMapTmp);
					}else{
						insertListTmp.add(authMapTmp);
					}
				}
			}
			if(updateListTmp!=null&&updateListTmp.size()>0){//修改
				bmaBsnAuthEntitySerivce.updateBmaBsnAuth(updateListTmp);
			}
			if(insertListTmp!=null&&insertListTmp.size()>0){//添加
				bmaBsnAuthEntitySerivce.insertBmaBsnAuth(insertListTmp);
			}
			
			ResponseEntity  responseEntity =  new  ResponseEntity();
			return responseEntity;
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModel method.", e);
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0176, "业务授权模型分配失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModel method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PPBE0176, "业务授权模型分配失败", ex);
		}
	}

	@Override
	public ResponseEntity queryAuthorizationModelList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			
			List<Map<String, Object>> resultList = bmaAuthModelEntitySerivce.queryBmaAuthModelList(channel, legalId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("resultList", resultList);
			ResponseEntity resultEntity = new ResponseEntity(resultMap);
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelList method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelList method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0135, "查询授权模型失败", ex);
		}
	}
    
	/**
	 * 查询菜单信息
	 */
	@Override
	public ResponseEntity queryMenuUnionFuncion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			
//			//根据条件查询关联菜单功能信息
//			List<Map<String, Object>> funcList = bmaMenudefEntitySerivce.queryBmaBsnDefAndBmaBsnAuthAndBmaMenuData(channel, legalId);
//			List<String> menuIdList = new ArrayList<String>();
//			for (Map<String, Object> map : funcList) {
//				String bmfId = (String) map.get("bmfId");
//				if(!menuIdList.contains(bmfId)){
//					menuIdList.add(bmfId);
//				}
//			}
//			
//			//查询业务功能的菜单及其所有父亲节点
//			List<Map<String, Object>>  menuList = bmaMenudefEntitySerivce.queryMenudefToTree(channel, legalId, menuIdList);
//			
//			//数据返回处理
//			for (int i = 0; i < menuList.size(); i++) {
//				String bmfId = (String) menuList.get(i).get("bmfId");
//				for (Map<String, Object> funcMap : funcList) {
//					String bmfIdFunc = (String) funcMap.get("bmfId");
//					if(bmfId.equals(bmfIdFunc)){
//						menuList.get(i).put("bmfName", (String)funcMap.get("bsnName"));
//						menuList.get(i).put("bsnCode",(String)funcMap.get("bsnCode"));
//						menuList.get(i).put("bsnName", (String)funcMap.get("bsnName"));
//						menuList.get(i).put("authModeId",(String)funcMap.get("authModelId"));
//					}
//				}
//			}
			//查询业务功能及菜单信息
			List<Map<String, Object>> menuAndBsnList=new ArrayList<Map<String,Object>>();
			menuAndBsnList = bmaMenudefEntitySerivce.queryBsnDefAndMenuByAll(channel, legalId);
			//查询模型分配信息
			List<Map<String, Object>>  authFpList=new ArrayList<Map<String,Object>>();
			authFpList=bmaBsnAuthEntitySerivce.queryBsbAuth(channel, legalId);
			
			if(menuAndBsnList!=null&&menuAndBsnList.size()>0){
				for (int i=0;i<menuAndBsnList.size();i++) {
					if(authFpList!=null&&authFpList.size()>0){
						for(Map<String, Object> authFpMap : authFpList){
							String bsnId=menuAndBsnList.get(i).get("bsnId")==null?"":(String)menuAndBsnList.get(i).get("bsnId");
							String baBsnId=authFpMap.get("baBsnId")==null?"":(String)authFpMap.get("baBsnId");
							if(bsnId.equals(baBsnId)){
								menuAndBsnList.get(i).put("authModelId", authFpMap.get("authModelId")==null?"":(String)authFpMap.get("authModelId"));
							}else{
								if(!menuAndBsnList.get(i).containsKey("authModelId")){
									menuAndBsnList.get(i).put("authModelId", "");
								}
							}
						}
					}else{
						menuAndBsnList.get(i).put("authModelId", "");
					}
					String bsnName=menuAndBsnList.get(i).get("bsnName")==null?"":(String)menuAndBsnList.get(i).get("bsnName"); 
					if(!ValidateUtil.isEmpty(bsnName)){
						menuAndBsnList.get(i).put("bmfName", bsnName);
					}
				}
			}
			Map<String, Object> parmOut  = new  HashMap<String, Object>();
			parmOut.put("resultList", menuAndBsnList);
			ResponseEntity resultEntity = new ResponseEntity(parmOut);
            return 	resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMenuUnionFuncion method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMenuUnionFuncion method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0136, "查询菜单信息失败", ex);
		}
	}

}
