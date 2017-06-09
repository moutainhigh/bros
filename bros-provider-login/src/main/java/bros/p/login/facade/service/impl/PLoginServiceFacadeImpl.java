package bros.p.login.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.constants.UserStatusConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.session.manager.SessionManager;
import bros.common.core.util.ValidateUtil;
import bros.p.login.facade.service.IPLoginServiceFacade;
import bros.provider.login.constants.LoginErrorCodeConstants;
import bros.provider.parent.bankmanage.legal.service.ILegalEntityService;
import bros.provider.parent.bankmanage.teller.service.ITellerEntityService;
import bros.provider.parent.branchmanage.service.IBranchManageEntityService;

/**
 * 
 * @ClassName: PLoginServiceFacadeImpl 
 * @Description: 登录
 * @author 何鹏
 * @date 2016年7月19日 下午4:02:43 
 * @version 1.0
 */
@Component("ploginServiceFacade")
public class PLoginServiceFacadeImpl implements IPLoginServiceFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(PLoginServiceFacadeImpl.class);
	/**
	 * 柜员信息服务
	 */
	@Autowired
	private ITellerEntityService tellerEntityService;
	/**
	 * 法人服务
	 */
	@Autowired
	private ILegalEntityService legalEntityService;
	/**
	 * 机构服务
	 */
	@Autowired
	private IBranchManageEntityService branchManageEntityService;
	/**
	 * Shiro会话管理器
	 */
	@Autowired
	private SessionManager shareShiroSessionManager;
	
	/** 
	 * @Title: manageLogin 
	 * @Description: 内管登录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000204")
	@Override
	public ResponseEntity manageLogin(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		try{
			//数据获取
			String tellerCode = (String) (bodyMap.get("tellerCode")==null?"":bodyMap.get("tellerCode"));//登录柜员号
			String password = (String) (bodyMap.get("pwd")==null?"":bodyMap.get("pwd"));//登录密码
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));//法人id
			
			//查询法人信息
			String stt = "0";//法人状态（0：正常 1：清算 2:撤销）
			Map<String,Object> legalMap = legalEntityService.queryLegalInfoById(legalId, stt);
			if(legalMap == null || legalMap.size()<=0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0003,"法人信息不存在【"+legalId+"】");
			}
			
			String legalCode = (String) (legalMap.get("cllCode")==null?"":legalMap.get("cllCode"));//法人编码
			if(ValidateUtil.isEmpty(legalCode)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0016,"法人编码不存在");
			}
			
			
			//查询柜员信息
			Map<String,Object> tellerMap = tellerEntityService.queryTellerInfo(legalId, "", tellerCode);
			if(tellerMap == null || tellerMap.size() <= 0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0005,"柜员信息不存在");
			}
			
			//柜员状态校验
			String tellerStt = (String) (tellerMap.get("btrStt")==null?"":tellerMap.get("btrStt"));
			if(UserStatusConstants.TELLER_STAT_0.equals(tellerStt)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0006,"柜员状态未激活");
			}else if(UserStatusConstants.TELLER_STAT_2.equals(tellerStt)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0007,"柜员状态日常");
			}else if(UserStatusConstants.TELLER_STAT_3.equals(tellerStt)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0008,"柜员状态完工");
			}else if(UserStatusConstants.TELLER_STAT_5.equals(tellerStt)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0009,"柜员状态注销");
			}else if(!UserStatusConstants.TELLER_STAT_1.equals(tellerStt) && 
						!UserStatusConstants.TELLER_STAT_4.equals(tellerStt)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0010,"柜员状态不正确");
			}
			
			//柜员密码校验
			String btrPwd = (String) (tellerMap.get("btrPwd")==null?"":tellerMap.get("btrPwd"));
			if(!password.equals(btrPwd)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0011,"柜员密码不正确");
			}
			
			String branchCode = (String) (tellerMap.get("btrBrancode")==null?"":tellerMap.get("btrBrancode"));
			//查询机构信息
			Map<String,Object> branchMap = branchManageEntityService.queryBranchInfo(legalId, branchCode);
			if(branchMap == null || branchMap.size()<=0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0013,"柜员所属机构信息不存在");
			}
			
			//柜员记录唯一标识
			String btrId = (String)tellerMap.get("btrId");
			//渠道共享会话数据
			Map<String, Object> shareSessionMap = new HashMap<String, Object>();
			shareSessionMap.put("btrId", btrId);
			//创建会话对象
			shareShiroSessionManager.addSession(BaseParamsConstants.SHARE_SESSION_KEY_PREFIX + btrId, shareSessionMap);
			
			//更新柜员密码状态
			tellerEntityService.updateTellerStt(legalId, branchCode, tellerCode, UserStatusConstants.TELLER_STAT_2);
			
			//返回map
			Map<String,Object> returnMap = new HashMap<String, Object>();
			//组装返回数据
			returnMap.put(HeadParameterDefinitionConstants.REC_LEGALID, legalId);//法人id
			returnMap.put(HeadParameterDefinitionConstants.REC_LEGALCODE, legalCode);//法人编码
			returnMap.put("tellerMap", tellerMap);
			returnMap.put("branchMap", branchMap);
			
			return new ResponseEntity(returnMap);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass() + ".manageLogin", se);
			throw se;
		}catch(Exception e){
			logger.error("Exception from " + this.getClass() + ".manageLogin", e);
			throw new ServiceException(LoginErrorCodeConstants.PLON0014, "登录失败", e);
		}
	}

	/**
	 * 
	 * @Title: manageLogout 
	 * @Description: 内管登出
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity manageLogout(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		try{
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));//法人id
			String tellerCode = (String) (headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));//登录柜员号
			String branchCode = (String) (headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID));//登录机构号
			String stt = "4";//柜员签退状态
			tellerEntityService.updateTellerStt(legalId, branchCode, tellerCode, stt);
			return new ResponseEntity();
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass() + ".manageLogout", se);
			throw se;
		}catch(Exception e){
			logger.error("Exception from " + this.getClass() + ".manageLogout", e);
			throw new ServiceException(LoginErrorCodeConstants.PLON0015, "登出失败", e);
		}
	}

}
