package bros.p.custmanage.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.constants.PersonConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.custmanage.facade.service.IPAccountManageServiceFacade;
import bros.provider.custmanage.constants.AccountParamsConstants;
import bros.provider.custmanage.constants.CustManageErrorCodeConstants;
import bros.provider.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.bankmanage.cst.service.ICstManageService;
import bros.provider.parent.branchmanage.service.IBranchManageEntityService;
import bros.provider.parent.cache.unionbankno.IPubUnionBankNo;
import bros.provider.parent.custmanage.accountManage.service.IAccountDefaultService;
import bros.provider.parent.custmanage.accountManage.service.IAccountFuncService;
import bros.provider.parent.custmanage.accountManage.service.IAccountInfoService;
import bros.provider.parent.custmanage.accountManage.service.IAccountManageService;

/**
 * 
 * @ClassName: PAccountManageServiceFacadeImpl 
 * @Description: 账户管理接口
 * @author gaoyongjing 
 * @date 2016年9月7日 下午2:44:40 
 * @version 1.0
 */
@Component("paccountManageServiceFacade")
public class PAccountManageServiceFacadeImpl implements
		IPAccountManageServiceFacade {
	
	private static final  Logger logger = LoggerFactory.getLogger(PAccountManageServiceFacadeImpl.class);
	/**
	 * 默认账户维护服务
	 */
	@Autowired
	private IAccountDefaultService accountDefaultService;
	/**
	 * 账户转账权限功能维护服务
	 */
	@Autowired
	private IAccountFuncService accountFuncService;
	/**
	 * 账户信息服务
	 */
	@Autowired
	private IAccountInfoService accountInfoService;
	/**
	 * 网银互联联行号表（总行表）操作接口服务
	 */
	@Autowired
	private IPubUnionBankNo pubUnionBankNo;
	/**
	 * 机构实体接口服务
	 */
	@Autowired
	private IBranchManageEntityService branchManageEntityService;
	/**
	 * 账户管理服务
	 */
	@Autowired
	private IAccountManageService accountManageService;
	/**
	 * 客户信息查询服务
	 */
	@Autowired
	private ICstManageService cstManageService;
	/**
	 * 通讯
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;
	/**
	 * 
	 * @Title: queryAccountInfoList 
	 * @Description: 签约账户列表(卡折)查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccountInfoList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try {
			Map<String, Object> recvMap = new HashMap<String, Object>();
//			recvMap = httpClientRouter.send(headMap, bodyMap,CustManageFormatCodeConstants.QUERYACCOUNTINFO_CODE);
//			String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
//			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道号
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));//法人ID
			String cstId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTID));//客户ID
			
			//账户类型: 01:借记卡； 02：定期一本通；03：活期一本通；08：贷款账户；09：其他；"":全部）
			String accountType = (String) (bodyMap.get("accountType")==null?"":bodyMap.get("accountType"));
			String accountState = (String) (bodyMap.get("accountState")==null?"":bodyMap.get("accountState"));// 账户状态（0：正常；5：注销；"":全部）	
			
			/**
			 * 通过客户号查询账号
			 */
			List<Map<String,Object>> queryPbaccinfChannel= accountInfoService.queryAccInfChannelBySttAccNoMethod(cstId, "", accountState, accountType);
			if(null==queryPbaccinfChannel){
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0026,"该客户下无符合账户，请您到附近网点办理加挂");
			}
			//查询开户行名
			for(int i=0;i<queryPbaccinfChannel.size();i++){
				Map<String,Object> map=queryPbaccinfChannel.get(i);
				String accountStateHost = "";
				Map<String,Object> PubUnionBankMap = pubUnionBankNo.queryPubUnionBankNoByBankNo(map.get("accountOpenBranchNo")+"");
				Map<String,Object> subBranch = branchManageEntityService.queryBranchInfo(legalId, map.get("accountOpenBranchNo")+"");
				if(null != PubUnionBankMap){
					map.put("accountOpenBranchName", PubUnionBankMap.get("bankName"));
				}else if(null != subBranch){
					map.put("accountOpenBranchName", subBranch.get("branchName"));
				}else{
					map.put("accountOpenBranchName", "");
				}
				//获取客户是否有转账权限
				List<Map<String,Object>> accinfFunc= accountFuncService.queryAccFuncInfoNoChannelMethod(cstId);
				if(null!=accinfFunc){
					map.put("transCloseFlg", PersonConstants.TRANSCLOSEFLGOPER);
				}else{
					map.put("transCloseFlg", PersonConstants.TRANSCLOSEFLGCLOSE);
				}
				 
			//查询账号状态
			String accountNo=(String) map.get("accountNo");
			bodyMap.put("accountNo", accountNo);
				//调用核心查询账户状态
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap = accountManageService.checkAccountMassageMethod(headMap, bodyMap);
			accountStateHost = (String) (resultMap.get("accountState")==null?"":resultMap.get("accountState"));
			/*
			 * 账户余额
			 */
			String accountBalance =String.valueOf(resultMap.get("accountBalance"));
			map.put("accountBalance", accountBalance);
			/*
			 * 借账户都与核心交互进行查询。查询出来更新到数据库中账户状态字段。、
			 * 定期一本通、不显示账户状态记卡、普通存折，每个下挂。
			 * 获取核心账户状态 返回前台
			 */
			if("".equals(accountState)||accountState.equals(accountStateHost)){
				if(map.get("accountType").equals(PersonConstants.FIXEDSAVINGACCOUNTPAWWBOOK)){
					map.put("accountState", "");
				}else{
					map.put("accountState", accountStateHost);
					if(accountStateHost.equals(PersonConstants.ACCOUNT_STATE_CANCEL)){
						queryPbaccinfChannel.remove(i);
						i--;
					}
					if(accountStateHost.equals(PersonConstants.ACCOUNT_STATE_HOST)){
						accountStateHost = PersonConstants.ACCOUNT_STATE_CANCEL;
						queryPbaccinfChannel.remove(i);
						i--;
					}
				}			
			}else{
					queryPbaccinfChannel.remove(i);
					i--;
				}
				 
				
				/*判断是否是默认账户
				 * 0:是
				 * 1：否
				 */
				Map<String , Object> defaultMap=accountDefaultService.queryAccInfDefaultByAccNoandCstNoMethod(cstId, accountNo);
				if(null!=defaultMap){
					map.put("defAccFlg", PersonConstants.DEFAULTFLAGNO);
				}else{
					map.put("defAccFlg", PersonConstants.DEFAULTFLAGYES);
				}

				//Update到数据库
				accountInfoService.updateAccInfChannelSttMethod(cstId, accountNo, accountStateHost);
			}
			recvMap.put("accList", queryPbaccinfChannel);
			ResponseEntity entity = new ResponseEntity(recvMap);
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccountInfoList method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccountInfoList method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0007,"签约账户列表(卡折)查询失败", ex);
		}
	}
	/**
	 * 
	 * @Title: queryAccDetailList 
	 * @Description: 动账交易明细查询,交易明细查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccDetailList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try {
				Map<String,Object> recvMap = new HashMap<String, Object>();
				recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.QUERYACCDETAIL_CODE);
				ResponseEntity entity = new ResponseEntity(recvMap);
				return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccDetailList method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccDetailList method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0008,"动账交易明细查询失败", ex);
		}
	}
	/**
	 * 
	 * @Title: suspendAccount 
	 * @Description: 账户挂失
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity suspendAccount(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			//获取数据
			String cstId=String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTID));
			String accNo=(String)bodyMap.get("accNo");			
			String stt=AccountParamsConstants.ACCOUNTSTT_3;
			
			/**
			 * 根据证件类型。证件号码  查询客户信息
			 */

			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
			String certType=(String)bodyMap.get("certType");
			String certNo=(String)bodyMap.get("certNo");
			
			Map<String,Object> revMap = cstManageService.queryCstInfoByCertNo(legalId, certType, certNo);
			String hostNo = revMap.get("hostNo").toString();
			
			bodyMap.put("hostNo", hostNo);
			
			/**
			 * 修改账户渠道信息-账户状态
			 * 挂失
			 */
			accountInfoService.updateAccInfChannelSttMethod(cstId, accNo, stt);
			Map<String,Object> recvMap = new HashMap<String, Object>();
			recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.SUSPENDACCOUNT_CODE);
			ResponseEntity entity = new ResponseEntity(recvMap);
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s suspendAccount method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s suspendAccount method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0012,"账户挂失失败", ex);
		}
	}
	/**
	 * 
	 * @Title: addAccountInfo 
	 * @Description: 账户加挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity addAccountInfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			Map<String,Object> recvMap = new HashMap<String, Object>();
			
			recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.ADDACCOUNTINFO_CODE);
			
			ResponseEntity entity = new ResponseEntity(recvMap);
			
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addAccountInfo method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s addAccountInfo method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0013,"账户加挂失败", ex);
		}
	}
	/**
	 * 
	 * @Title: deleteAccountInfo 
	 * @Description: 账户解挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity deleteAccountInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			//获取数据
			String cstId=String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTID));
			String accNo=(String)bodyMap.get("accNo");
			
			/*
			 * 判断账号是否存在
			 */
			List<Map<String,Object>> accountinfList= accountInfoService.queryAccInfChannelBySttAccNoMethod(cstId, accNo, "", "");
			if(accountinfList==null || accountinfList.size()==0 ){
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0023,"账号不存在");
			}
			/*
			 * 判断此账号是否有在途交易
			 */
			
			/*
			 * 查询tpr_acc_info_default
			 * 判断此账户是否为默认账户
			 * 默认账户不允许解挂
			 */
			Map<String,Object> accountDefInfo=accountDefaultService.queryAccInfDefaultByAccNoandCstNoMethod(cstId, accNo);
			if(accountDefInfo!=null && accountDefInfo.size()>0 ){
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0034,"解挂账户失败-默认账户不允许解挂。");
			}
			
			//删除账户渠道信息表
			accountInfoService.deleteTprAccChannelInfoByCstIdAndAccNoMethod(cstId, accNo);
			//删除账户功能表
			accountInfoService.deleteTprAccFuncByCstIdAndAccNoMethod(cstId, accNo);
			//删除账户表
			accountInfoService.deleteTprAccInfoByCstIdAndAccNoMethod(cstId, accNo);
				
			
			Map<String,Object> recvMap = new HashMap<String, Object>();
			ResponseEntity entity = new ResponseEntity(recvMap);
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAccountInfo method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteAccountInfo method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0014,"账户解挂失败", ex);
		}
	}
	/**
	 * 
	 * @Title: setAccountAlias 
	 * @Description: 账户别名设置
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity setAccountAlias(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			//取参
			
			String cstId=String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTID));
			String accNo=(String)bodyMap.get("accNo");
			String accAlias=(String)bodyMap.get("accAlias");
			//调用数据库更改账户别名
			accountInfoService.updateAccountAliasMethod(cstId, accNo, accAlias);
			
			//返回数据为空
			return  new ResponseEntity();
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s setAccountAlias method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s setAccountAlias method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0015,"账户别名设置失败", ex);
		}
	}
	/**
	 * 
	 * @Title: updateDefaultAccountInfo 
	 * @Description: 默认账户修改
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity updateDefaultAccountInfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			
			Map<String,Object> recvMap = new HashMap<String, Object>();
			
			recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.UPDATEDEFAULTACCOUNTINFO_CODE);
			
			ResponseEntity entity = new ResponseEntity(recvMap);
			
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateDefaultAccountInfo method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateDefaultAccountInfo method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0017,"默认账户修改失败", ex);
		}
	}
	
	/**
	 * 
	 * @Title: queryAccountInfoMethod
	 * @Description: 账户信息查询
	 * @param accountNo 账号
	 * @param currency 币种
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccountInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			
			Map<String,Object> recvMap = new HashMap<String, Object>();
			
			recvMap =  accountManageService.queryAccountInfoMethod(headMap, bodyMap);
			
			ResponseEntity entity = new ResponseEntity(recvMap);
			
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccountInfoMethod method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccountInfoMethod method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0027,"账户信息查询失败", ex);
		}
	}
	/**
	 * 
	 * @Title: queryAccountInfoSubList 
	 * @Description: 子账户列表查询（查询定期、活期子账户）
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity queryAccountInfoSubList(Map<String,Object> headMap,
			Map<String,Object> bodyMap) throws ServiceException{
		try{
			
			Map<String,Object> recvMap = new HashMap<String, Object>();
			
			recvMap =  accountManageService.queryAccountInfoSubListHost(headMap, bodyMap);
			
			List<Map<String, Object>> subAccList = (List<Map<String, Object>>) recvMap.get("subAccList");
			
			recvMap.put("subAccList", subAccList);
			
			ResponseEntity entity = new ResponseEntity(recvMap);
			
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccountInfoSubListHost method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAccountInfoSubListHost method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0035,"子账户列表查询失败", ex);
		}
	}
	
}
