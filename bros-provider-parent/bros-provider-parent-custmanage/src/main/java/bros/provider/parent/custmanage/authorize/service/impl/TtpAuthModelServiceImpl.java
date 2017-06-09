package bros.provider.parent.custmanage.authorize.service.impl;

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
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelEntitySerivce;
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelRuleGpEntitySerivce;
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelSerivce;
import bros.provider.parent.custmanage.constants.CustmanageConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: TtpAuthModelServiceImpl 
 * @Description: 对客授权模型组合服务
 * @author pengxiangnan 
 * @date 2016年7月20日 上午9:51:01 
 * @version 1.0
 */
@Component(value="ttpAuthModelService")
public class TtpAuthModelServiceImpl implements ITtpAuthModelSerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(TtpAuthModelServiceImpl.class);
	
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
	 * 新增授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public void saveAuthorizationModel(Map<String, Object> authModelMap, List<Map<String,Object>> authRuleList) throws ServiceException {
		try{
			//新增授权模型
			if(null!=authModelMap && authModelMap.size()>0){
				 String authorizeId = (String) authModelMap.get("authorizeId");
				 String name = (String) authModelMap.get("name");
				 String orderly = (String) authModelMap.get("orderly");
				 String state = (String) authModelMap.get("state");
				 String legalId = (String) authModelMap.get("legalId");
				 String channel = (String) authModelMap.get("channel");
				 String cstNo = (String) authModelMap.get("cstNo");
				 String moneyType = (String) authModelMap.get("moneyType");
				 String send = (String) authModelMap.get("send");			
			
				 int resultNum = ttpAuthModelEntitySerivce.saveTtpAuthModel(authorizeId, name, legalId, channel, cstNo, 
						 moneyType, orderly, send, state);
				 
				 if(resultNum!=1){
					 throw new ServiceException(CustmanageErrorCodeConstants.PPCG0014, "新增授权模型失败");
				 }
			}else{
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0014, "新增授权模型失败");
			}
			//新增授权规则
			if(null!=authRuleList && authRuleList.size()>0){
				int resultNum = ttpAuthModelRuleGpEntitySerivce.saveTtpAuthModelRuleGp(authRuleList);
				if(resultNum!=authRuleList.size()){
					throw new ServiceException(CustmanageErrorCodeConstants.PPCG0025, "新增授权规则失败");
				}
			}
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
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public void deleteAuthorizationModel(String channel, String legalId , String cstNo, String authorizeId) throws ServiceException {
		try{
			//删除授权模型基本信息
			int resultNum = ttpAuthModelEntitySerivce.deleteTtpAuthModel(channel, legalId, authorizeId);
			if(resultNum!=1){
				 throw new ServiceException(CustmanageErrorCodeConstants.PPCG0015, "删除授权模型失败");
			}
			//删除授权模型规则信息
			ttpAuthModelRuleGpEntitySerivce.deleteTtpAuthModelRuleGp(authorizeId);
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
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public void updateAuthorizationModel(Map<String, Object> authModelMap, List<Map<String,Object>> authRuleList) throws ServiceException {
		try{
			String authorizeId = (String) authModelMap.get("authorizeId");
			String name = (String) authModelMap.get("name");
			String orderly = (String) authModelMap.get("orderly");
			String state = (String) authModelMap.get("state");
			String legalId = (String) authModelMap.get("legalId");
			String channel = (String) authModelMap.get("channel");
			String cstNo = (String) authModelMap.get("cstNo");
			String moneyType = (String) authModelMap.get("moneyType");
			String send = (String) authModelMap.get("send");
			
			//修改授权模型
			int resultNum = ttpAuthModelEntitySerivce.updateTtpAuthModel(authorizeId, name, legalId, channel, 
					cstNo, moneyType, orderly, send, state);
			
			if(resultNum!=1){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0016, "修改授权模型失败");
			}
			
			//删除旧授权模型规则定义
			ttpAuthModelRuleGpEntitySerivce.deleteTtpAuthModelRuleGp(authorizeId);
			//插入新授权模型规则定义
			if(null!=authRuleList && authRuleList.size()>0){
				 resultNum = ttpAuthModelRuleGpEntitySerivce.saveTtpAuthModelRuleGp(authRuleList);
				 if(resultNum!=authRuleList.size()){
					throw new ServiceException(CustmanageErrorCodeConstants.PPCG0025, "新增授权模型规则失败");
				 }
			}
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateAuthorizationModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0016, "修改授权模型失败", ex);
		}
	}
	
	/**
	 * 
	 * @Title: checkAuthorizeModelRule 
	 * @Description: 检查规则数据及组装数据
	 * @param authRuleList 授权规则定义列表
	 * @param moneyType 是否有金额（0：无金额，1：有金额）
	 * @param authorizeId 授权模型编号
	 * @return Map<String, Object> 返回结果集
	 * @throws ServiceException
	 */
    @SuppressWarnings("unchecked")
	public Map<String, Object> checkAuthorizeModelRule(List<Map<String,Object>> authRuleList, String moneyType , 
    		String authorizeId)  throws ServiceException 
    {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	//金额区间列表
 		Map<Integer, Map<String, BigDecimal>>  amountMap = new HashMap<Integer, Map<String,BigDecimal>>();
 		//保存规则列表
 		List<Map<String,Object>> authRuleListParam = new ArrayList<Map<String,Object>>();
		if(null!=authRuleList && authRuleList.size()>0){
			//无金额校验
			if (CustmanageConstants.IS_MONEY_TYPE_0.equals(moneyType) 
					&& authRuleList.size()>1) 
			{
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0019, "无金额模式下，只能有1条规则");
			}
			
			for(Map<String ,Object> authRuleMap : authRuleList){
				Map<String,Object> ttpAuthModeMapRulegp = new HashMap<String, Object>();//保存模型规则信息  
				ttpAuthModeMapRulegp.put("argId", BaseUtil.createUUID());
				ttpAuthModeMapRulegp.put("authModelId", authorizeId);
				String ranking = (String) authRuleMap.get("ranking");
				ttpAuthModeMapRulegp.put("argRanking", ranking);
				//校验金额
				checkAmount(moneyType, amountMap, authRuleMap, ttpAuthModeMapRulegp, ranking);
				List<Map<String,Object>> authList= (List<Map<String, Object>>) authRuleMap.get("authList");//授权列表
				if(null!=authList && authList.size()>0){
					for(Map<String,Object> authMap : authList){
						String modelRoleLevel=(String)authMap.get("modelRoleLevel");
						String personNum=(String)authMap.get("personNum");
						String roleId=(String)authMap.get("roleId");
						//校验人数
						checkPersonNum(personNum,roleId);
						//组装数据
						if ("1".equals(modelRoleLevel)) {
							ttpAuthModeMapRulegp.put("authRnrole1", roleId);
							ttpAuthModeMapRulegp.put("roleNumber1", personNum);
						}
						if ("2".equals(modelRoleLevel)) {
							ttpAuthModeMapRulegp.put("authRnrole2", roleId);
							ttpAuthModeMapRulegp.put("roleNumber2", personNum);
						}
						if ("3".equals(modelRoleLevel)) {
							ttpAuthModeMapRulegp.put("authRnrole3", roleId);
							ttpAuthModeMapRulegp.put("roleNumber3", personNum);
						}
						if ("4".equals(modelRoleLevel)) {
							ttpAuthModeMapRulegp.put("authRnrole4", roleId);
							ttpAuthModeMapRulegp.put("roleNumber4", personNum);
						}
						if ("5".equals(modelRoleLevel)) {
							ttpAuthModeMapRulegp.put("authRnrole5", roleId);
							ttpAuthModeMapRulegp.put("roleNumber5", personNum);
						}
					}
				}
				authRuleListParam.add(ttpAuthModeMapRulegp);
			}
       }
		resultMap.put("listAmount", amountMap);
		resultMap.put("authRuleListParam", authRuleListParam);
		return resultMap;
	}
    
    /**
     * 
     * @Title: checkAmount 
     * @Description: 校验金额输入的合法性
     * @param moneyType 是否有金额
     * @param amountMap 金额区间集合
     * @param authRuleMap 授权规则定义集合
     * @param authModeMapRulegp
     * @param ranking 规则所在授权模型序号
     * @throws ServiceException
     */
 	public void checkAmount(String moneyType, Map<Integer, Map<String, BigDecimal>> amountMap, Map<String, Object> authRuleMap,
 			Map<String, Object> authModeMapRulegp, String ranking) throws ServiceException 
 	{
 		String startAmount = (String) authRuleMap.get("startAmount");
 		String endAmount = (String) authRuleMap.get("endAmount");
 		//有金额模式下校验金额输入是否合法
 		BigDecimal minAmountBigDecimal = new BigDecimal("0.00");
 		BigDecimal maxAmountBigDecimal = new BigDecimal("0.00");
 		
 		if (CustmanageConstants.IS_MONEY_TYPE_1.equals(moneyType)) {
 			if (ValidateUtil.isEmpty(startAmount) || ValidateUtil.isEmpty(endAmount)){
 				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0022, "有金额模式下，模型规则的金额区间不能为空");
 			}
 			
 			minAmountBigDecimal = new BigDecimal(startAmount);
 	 		maxAmountBigDecimal = new BigDecimal(endAmount);
 	 		
 			Map<String, BigDecimal> mapAmount=new HashMap<String, BigDecimal>();
 			
 			if("1".equals(ranking) && minAmountBigDecimal.compareTo(BigDecimal.ZERO)!=0){
 				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0023, "有金额模式下，模型规则的起始金额必须从零开始");
 			}
 			if (maxAmountBigDecimal.compareTo(minAmountBigDecimal)!=1) {
 				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0024, "有金额模式下，模型规则的金额上限必须大于下限");
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
	 * @param moneyType 是否有金额
	 * @param amountMap 金额区间集合
	 * @throws ServiceException
	 */
	public void checkAmountRange(String moneyType, Map<Integer, Map<String, BigDecimal>> listAmount) throws ServiceException {
		if (CustmanageConstants.IS_MONEY_TYPE_1.equals(moneyType)) {
			for (int i = 1; i <=listAmount.size(); i++) {
				if (i!=listAmount.size()) {
					Map<String, BigDecimal> mapAmountTemp1 = listAmount.get(i);
					Map<String, BigDecimal> mapAmountTemp2 = listAmount.get(i+1);
					if (mapAmountTemp1.get("maxAmount").compareTo(mapAmountTemp2.get("minAmount"))!=0) {
						throw new ServiceException(CustmanageErrorCodeConstants.PPCG0021, "有金额模式下，模型规则的金额区间不连续");
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
			personNum=null;
			return;
		}else {
			personNum=personNum==null?"":personNum;
		}
		
		if (!CustmanageConstants.PATTERN_PERSON_NUM.matcher(personNum).find()) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0020, "角色人数输入错误");
		}
		if (Integer.parseInt(personNum)<=0) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0020, "角色人数输入错误");
		}
	}
    
	/**
	 * 检查授权模型是否存在
	 */
	public void checkAuthModelIsExist(String channel, String legalId, String cstNo, String authModelName) throws ServiceException {
		Map<String, Object> authModelMap = ttpAuthModelEntitySerivce.queryTtpAuthModelByName(channel, legalId, cstNo, authModelName);
		if (authModelMap!=null && authModelMap.size()!=0) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0018, "授权模型名称已经存在");
		}
	}
    
}
