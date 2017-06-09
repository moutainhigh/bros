package bros.p.custmanage.facade.service.impl;

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
import bros.p.custmanage.facade.service.IPTtpAuthModelSerivceFacade;
import bros.provider.parent.bankmanage.authorize.service.IBmaMenudefEntitySerivce;
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelEntitySerivce;
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelRuleGpEntitySerivce;
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelSerivce;
import bros.provider.parent.custmanage.authorize.service.ITtpFuncAuthEntitySerivce;
import bros.provider.parent.custmanage.authorize.service.ITtpMenudefEntitySerivce;
import bros.provider.parent.custmanage.constants.CustmanageConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.role.ITtpRoleEntitySerivce;

/**
 * 
 * @ClassName: PTtpAuthModelServiceFacadeImpl 
 * @Description: 对客授权模型管理对外发布服务
 * @author pengxiangnan 
 * @date 2016年7月20日 上午10:18:45 
 * @version 1.0
 */
@Component("pttpAuthModelSerivceFacade")
public class PTtpAuthModelServiceFacadeImpl implements IPTtpAuthModelSerivceFacade {
	
	private static final  Logger logger = LoggerFactory.getLogger(PTtpAuthModelServiceFacadeImpl.class);
	
	/**
	 * 对客授权模型组合服务
	 */
	@Autowired
	private ITtpAuthModelSerivce ttpAuthModelSerivce; 
	
	/**
	 * 对客授权模型实体服务
	 */
	@Autowired
	private ITtpAuthModelEntitySerivce ttpAuthModelEntitySerivce; 
	
	/**
	 * 对客授权模型规则实体服务
	 */
	@Autowired
	private ITtpAuthModelRuleGpEntitySerivce ttpAuthModelRuleGpEntitySerivce;
	
	/**
	 * 操作员角色实体服务
	 */
	@Autowired
	private ITtpRoleEntitySerivce ttpRoleEntitySerivce;
	
	/**
	 * 开通功能与授权模型关系实体服务
	 */
	@Autowired
	private ITtpFuncAuthEntitySerivce ttpFuncAuthEntitySerivce;
	
	/**
	 * 对客关联菜单实体服务
	 */
	@Autowired
	private ITtpMenudefEntitySerivce ttpMenudefEntitySerivce;
	
	
	/**
	 * 授权菜单实体服务
	 */
	@Autowired
	private IBmaMenudefEntitySerivce bmaMenudefEntitySerivce;
    
	/**
	 * 新增授权模型
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000322")
	public ResponseEntity saveAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			String moneyType = (String) bodyMap.get("moneyType");
			String name = (String) bodyMap.get("name");
			String orderly = (String)bodyMap.get("orderly");
			String state = CustmanageConstants.AUTHMODEL_STATE_1;
			String send = CustmanageConstants.IS_SEND_TYPE_1;
			
			//生成授权模型编号
			String authorizeId = BaseUtil.createUUID();
			
			//校验名称是否存在
			ttpAuthModelSerivce.checkAuthModelIsExist(channel, legalId, cstNo, name);

			List<Map<String,Object>> authRuleList1=  (List<Map<String, Object>>) bodyMap.get("authRuleList");
			Map<String, Object> paramOut = ttpAuthModelSerivce.checkAuthorizeModelRule(authRuleList1, moneyType, authorizeId);
			Map<Integer, Map<String, BigDecimal>> listAmount = (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
			List<Map<String,Object>> authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
			 
			//校验金额2（校验金额区间是否完整）
			ttpAuthModelSerivce.checkAmountRange(moneyType, listAmount);
			
			Map<String, Object> authModelMap = new HashMap<String, Object>();
			authModelMap.put("authorizeId", authorizeId);
			authModelMap.put("name", name);
			authModelMap.put("orderly", orderly);
			authModelMap.put("state", state);
			authModelMap.put("legalId", legalId);
			authModelMap.put("channel", channel);
			authModelMap.put("cstNo", cstNo);
			authModelMap.put("moneyType", moneyType);
			authModelMap.put("send", send);

			//保存授权模型和授权规则
			ttpAuthModelSerivce.saveAuthorizationModel(authModelMap, authRuleListParam);
			
			ResponseEntity resultEntity = new ResponseEntity();
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveAuthorizationModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0014, "新增授权模型失败", ex);
		}
	}
    
	/**
	 * 删除授权模型
	 */
	@Validation(value="p0000323")
	public ResponseEntity deleteAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			String authorizeId = (String) bodyMap.get("authorizeId");
			
			//删除授权模型和授权规则定义
			ttpAuthModelSerivce.deleteAuthorizationModel(channel, legalId, cstNo, authorizeId);
			
			ResponseEntity resultEntity = new ResponseEntity();
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAuthorizationModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0015, "删除授权模型失败", ex);
		}
	}
    
	/**
	 * 修改授权模型
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000324")
	public ResponseEntity updateAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			String authorizeId = (String) bodyMap.get("authorizeId");
			String moneyType = (String) bodyMap.get("moneyType");
			String name = (String) bodyMap.get("name");
			String orderly = (String)bodyMap.get("orderly");
			String state = (String)bodyMap.get("state");
			String send = (String)bodyMap.get("send");
			
			//根据授权模型编号查询授权模型信息
			Map<String, Object> authModeMap = ttpAuthModelEntitySerivce.queryTtpAuthModelById(channel, legalId, cstNo, authorizeId);
			if (authModeMap!=null && authModeMap.size()!=0) {
				String authModelName = (String) authModeMap.get("name");
				if(!name.equals(authModelName)){
					//校验名称是否存在
					ttpAuthModelSerivce.checkAuthModelIsExist(channel, legalId, cstNo, name);
				}
			}
			
			//检查上送规则数据和组装数据
			List<Map<String,Object>> authRuleList =  (List<Map<String, Object>>) bodyMap.get("authRuleList");
			Map<String, Object> paramOut = ttpAuthModelSerivce.checkAuthorizeModelRule(authRuleList, moneyType, authorizeId);
			Map<Integer, Map<String, BigDecimal>> listAmount = (Map<Integer, Map<String, BigDecimal>>) paramOut.get("listAmount");
			List<Map<String,Object>> authRuleListParam = (List<Map<String, Object>>) paramOut.get("authRuleListParam");
			 
			//校验金额2（校验金额区间是否完整）
			ttpAuthModelSerivce.checkAmountRange(moneyType, listAmount);
			
			Map<String, Object> authModelMap = new HashMap<String, Object>();
			authModelMap.put("authorizeId", authorizeId);
			authModelMap.put("name", name);
			authModelMap.put("orderly", orderly);
			authModelMap.put("state", state);
			authModelMap.put("legalId", legalId);
			authModelMap.put("channel", channel);
			authModelMap.put("cstNo", cstNo);
			authModelMap.put("moneyType", moneyType);
			authModelMap.put("send", send);
			
			//修改授权模型
			ttpAuthModelSerivce.updateAuthorizationModel(authModelMap, authRuleListParam);
			
			ResponseEntity resultEntity = new ResponseEntity();
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0016, "修改授权模型失败", ex);
		}
	}
    
	/**
	 * 分页查询授权模型列表
	 */
	@Validation(value="p0000325")
	public ResponseEntity queryAuthorizationModelListForPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			
			int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")));
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")));
			
			//分页查询授权模型列表
			List<Map<String, Object>> resultList = ttpAuthModelEntitySerivce.queryTtpAuthModelListForPage(channel, legalId, cstNo, pageNo, pageSize);
			
			//查询总记录数
			int totalNum = ttpAuthModelEntitySerivce.queryTtpAuthModelTotalNum(channel, legalId, cstNo);
			
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
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
	
	/**
	 * 根据授权模型编号查询授权模型详细信息
	 */
	@Validation(value="p0000326")
	public ResponseEntity queryAuthorizationModelDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			String authorizeId  = (String) bodyMap.get("authorizeId");
			
			//根据授权模型编号查询授权模型信息
			Map<String, Object> authModeMap = ttpAuthModelEntitySerivce.queryTtpAuthModelById(channel, legalId, cstNo, authorizeId);
			
			//根据授权模型编号查询授权模型规则列表
			List<Map<String, Object>> authModelRuleGpList = ttpAuthModelRuleGpEntitySerivce.queryTtpAuthModelRuleByAuthModelId(authorizeId);
			
			//角色编号列表
			List<String> roleIdList = new ArrayList<String>();	
			for(Map<String, Object> authModelRuleGpMap : authModelRuleGpList) {
				String authRnrole1 = (String)authModelRuleGpMap.get("authRnrole1");
				String authRnrole2 = (String)authModelRuleGpMap.get("authRnrole2");
				String authRnrole3 = (String)authModelRuleGpMap.get("authRnrole3");
				String authRnrole4 = (String)authModelRuleGpMap.get("authRnrole4");
				String authRnrole5 = (String)authModelRuleGpMap.get("authRnrole5");
				
                int roleNumber1 = authModelRuleGpMap.get("roleNumber1")==null ? 0 : Integer.parseInt((String) authModelRuleGpMap.get("roleNumber1"));
                int roleNumber2 = authModelRuleGpMap.get("roleNumber2")==null ? 0 : Integer.parseInt((String) authModelRuleGpMap.get("roleNumber2"));
                int roleNumber3 = authModelRuleGpMap.get("roleNumber3")==null ? 0 : Integer.parseInt((String) authModelRuleGpMap.get("roleNumber3"));
                int roleNumber4 = authModelRuleGpMap.get("roleNumber4")==null ? 0 : Integer.parseInt((String) authModelRuleGpMap.get("roleNumber4"));
                int roleNumber5 = authModelRuleGpMap.get("roleNumber5")==null ? 0 : Integer.parseInt((String) authModelRuleGpMap.get("roleNumber5"));
                
                if( !ValidateUtil.isEmpty(authRnrole1) && roleNumber1>0){
                	roleIdList.add(authRnrole1);
                }

                if( !ValidateUtil.isEmpty(authRnrole2) && roleNumber2>0){
                	roleIdList.add(authRnrole2);
                }
                
                if( !ValidateUtil.isEmpty(authRnrole3) && roleNumber3>0){
                	roleIdList.add(authRnrole3);
                }
                
                if( !ValidateUtil.isEmpty(authRnrole4) && roleNumber4>0){
                	roleIdList.add(authRnrole4);
                }
                
                if( !ValidateUtil.isEmpty(authRnrole5) && roleNumber5>0){
                	roleIdList.add(authRnrole5);
                }
			}
			
			if(null!=roleIdList && roleIdList.size()>0){
				//根据角色编号列表查询角色列表
				List<Map<String, Object>> roleList = ttpRoleEntitySerivce.queryTtpRoleByRoleIdList(roleIdList);
				if(null!=roleList && roleList.size()>0){
					for(Map<String, Object> authModelRuleGpMap : authModelRuleGpList){
						String authRnrole1 = (String)authModelRuleGpMap.get("authRnrole1");
						String authRnrole2 = (String)authModelRuleGpMap.get("authRnrole2");
						String authRnrole3 = (String)authModelRuleGpMap.get("authRnrole3");
						String authRnrole4 = (String)authModelRuleGpMap.get("authRnrole4");
						String authRnrole5 = (String)authModelRuleGpMap.get("authRnrole5");
						
						for(Map<String, Object> roleMap : roleList){
							String roleId = (String)roleMap.get("treId");
							String roleName = (String)roleMap.get("treName");
						
							if(roleId.equals(authRnrole1)){
								authModelRuleGpMap.put("roleName1", roleName);
							}
							if(roleId.equals(authRnrole2)){
								authModelRuleGpMap.put("roleName2", roleName);
							}
							if(roleId.equals(authRnrole3)){
								authModelRuleGpMap.put("roleName3", roleName);
							}
							if(roleId.equals(authRnrole4)){
								authModelRuleGpMap.put("roleName4", roleName);
							}
							if(roleId.equals(authRnrole5)){
								authModelRuleGpMap.put("roleName5", roleName);
							}
						}
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
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
    
	/**
	 * 授权模型分配
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000327")
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public ResponseEntity updateFunctionAuthorizationModel(Map<String, Object> headMap,	Map<String, Object> bodyMap) throws ServiceException {
		try{
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			
			
			List<Map<String, Object>> funcAuthList = (List<Map<String, Object>>) bodyMap.get("funcAuthList");
			List<Map<String, Object>> updateListTmp = new ArrayList<Map<String,Object>>();//更新list
			List<Map<String, Object>> insertListTmp = new ArrayList<Map<String,Object>>();//添加list
			
			if(funcAuthList!=null&&funcAuthList.size()>0){
				for(Map<String, Object> map : funcAuthList){
					Map<String, Object> authMapTmp=new HashMap<String, Object>();
					String bsnId = (String) map.get("bsnCode");//业务编码id
					String authModelId = (String) map.get("modelId");//授权模型id
					String id = BaseUtil.createUUID();
					authMapTmp.put("id", id);					//id
					authMapTmp.put("legalId", legalId);			//法人id
					authMapTmp.put("cstNo", cstNo);			//客户编号
					authMapTmp.put("bsnCode", bsnId);			//业务id
					authMapTmp.put("authModel", authModelId);    //授权模型id
					int totalNum=0;
					totalNum = ttpFuncAuthEntitySerivce.queryTtpFunAuthNum(cstNo, legalId, bsnId);
					if(totalNum>0){
						updateListTmp.add(authMapTmp);
					}else{
						insertListTmp.add(authMapTmp);
					}
				}
			}
			if(updateListTmp!=null&&updateListTmp.size()>0){//修改
				ttpFuncAuthEntitySerivce.updateTtpFuncAuth(updateListTmp);
			}
			if(insertListTmp!=null&&insertListTmp.size()>0){//添加
				ttpFuncAuthEntitySerivce.insertTtpFunAuth(insertListTmp);
			}
			
			ResponseEntity  responseEntity =  new  ResponseEntity();
			return responseEntity;
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModel method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0029, "业务授权模型分配失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0029, "业务授权模型分配失败", ex);
		}
	}
    
	/**
	 * 查询列表
	 */
	@Override
	public ResponseEntity queryAuthorizationModelList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			
			List<Map<String, Object>> resultList = ttpAuthModelEntitySerivce.queryTtpAuthModelList(channel, legalId, cstNo);
			Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("resultList", resultList);
			ResponseEntity resultEntity = new ResponseEntity(resultMap);
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelListForPage method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthorizationModelListForPage method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
    
	/**
	 * 查询模型功能列表
	 */
	@Override
	public ResponseEntity queryTtpFuncAuthList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			
			List<Map<String, Object>> resultList = ttpFuncAuthEntitySerivce.queryTtpFuncAuthList(legalId, cstNo);
			Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("resultList", resultList);
			ResponseEntity resultEntity = new ResponseEntity(resultMap);
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthList method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthList method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询模型功能列表", ex);
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
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			
			//根据条件查询关联菜单功能信息
			List<Map<String, Object>> menuAndBsnList=new ArrayList<Map<String,Object>>();
			menuAndBsnList = ttpMenudefEntitySerivce.queryTTBsnDefAndMenuByAll(channel, legalId);
			//查询模型分配信息
			List<Map<String, Object>>  authFpList=new ArrayList<Map<String,Object>>();
			authFpList=ttpMenudefEntitySerivce.queryTTBsbAuth(cstNo, legalId);
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
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
}
