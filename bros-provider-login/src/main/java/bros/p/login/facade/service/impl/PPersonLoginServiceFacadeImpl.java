package bros.p.login.facade.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.crypto.service.IEncryptAndDecryptService;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.session.manager.SessionManager;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.DateUtil;
import bros.common.core.util.ValidateUtil;
import bros.p.login.facade.service.IPPersonLoginServiceFacade;
import bros.provider.login.constants.LoginErrorCodeConstants;
import bros.provider.login.constants.LoginParamsConstants;
import bros.provider.parent.account.persion.service.ITprAccChannelInfoEntityService;
import bros.provider.parent.bankmanage.legal.service.ILegalEntityService;
import bros.provider.parent.customer.person.service.ITprCstChannelInfEntityService;
import bros.provider.parent.customer.person.service.ITprCstInfoEntityService;
import bros.provider.parent.login.person.service.ITprCstLogonCtrlEntityService;
import bros.provider.parent.security.person.question.service.ITprCstPrivateQuestionEntityService;

/**
 * 
 * @ClassName: PPersonLoginServiceFacadeImpl 
 * @Description: 个人登录服务实现类
 * @author huangcanhui 
 * @date 2016年10月8日 下午2:10:35 
 * @version 1.0
 */
@Component("pPersonLoginServiceFacade")
public class PPersonLoginServiceFacadeImpl implements IPPersonLoginServiceFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(PPersonLoginServiceFacadeImpl.class);
	
	/**
	 * 个人客户基本信息实体服务
	 */
	@Autowired
	private ITprCstInfoEntityService tprCstInfoEntityService;
	
	/**
	 * 个人客户渠道信息实体服务
	 */
	@Autowired
	private ITprCstChannelInfEntityService tprCstChannelInfEntityService;
	
	/**
	 * 个人账户渠道信息实体服务
	 */
	@Autowired
	private ITprAccChannelInfoEntityService tprAccChannelInfoEntityService;
	
	/**
	 * 个人客户登录控制信息实体服务
	 */
	@Autowired
	private ITprCstLogonCtrlEntityService tprCstLogonCtrlEntityService;
	
	/**
	 * 个人客户与私密问题关系实体服务
	 */
	@Autowired
	private ITprCstPrivateQuestionEntityService tprCstPrivateQuestionEntityService;
	
	/**
	 * 法人信息实体服务
	 */
	@Autowired
	private ILegalEntityService legalEntityService;
	
	/**
	 * 数据加密解密服务
	 */
	@Autowired
	private IEncryptAndDecryptService encryptAndDecryptService;

	/**
	 * Shiro会话管理器
	 */
	@Autowired
	private SessionManager shareShiroSessionManager;
	
	/** 
	 * 登录
	 */
	@Validation(value="p0000210")
	@Override
	public ResponseEntity login(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			//法人id
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			//渠道编号
			String channel = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
			
			//登录信息
			String logonInfo = (String) (bodyMap.get("logonInfo")==null?"":bodyMap.get("logonInfo"));
			//登录密码
			String logonPwd = (String) (bodyMap.get("logonPwd")==null?"":bodyMap.get("logonPwd"));
			//登录方式（1-昵称[别名]；2-证件号码；3-账号；4-手机号）
			String logonType = (String) (bodyMap.get("logonType")==null?"":bodyMap.get("logonType"));
			
			//查询法人信息
			String stt = "0";//法人状态（0：正常 1：清算 2:撤销）
			Map<String,Object> legalMap = legalEntityService.queryLegalInfoById(legalId, stt);
			if(legalMap == null || legalMap.size()<=0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0003, "法人信息不存在");
			}
			
			//法人编码
			String legalCode = (String) (legalMap.get("cllCode")==null?"":legalMap.get("cllCode"));
			if(ValidateUtil.isEmpty(legalCode)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0016, "法人编码不存在");
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//根据登录信息进行登录处理
			if(ValidateUtil.isAlias(logonInfo)){
				//1-昵称[别名]（别名登录(以字母开头，至少6个字符，由字母、数字、连字符(-)、下划线(_)、数点(.)组成)）
				resultMap = tprCstChannelInfEntityService.queryTprCstChannelInfByAlias(legalId, channel, logonInfo);
				if(ValidateUtil.isEmpty(logonType)){
					logonType = LoginParamsConstants.PERSON_LOGIN_TYPE_1;
				}
			}else if(ValidateUtil.isEmpty(ValidateUtil.isIDCard(logonInfo))){
				//2-证件号码
				resultMap = tprCstInfoEntityService.queryTprCstInfoByCtfNo(legalId, channel, logonInfo);
				if(ValidateUtil.isEmpty(logonType)){
					logonType = LoginParamsConstants.PERSON_LOGIN_TYPE_2;
				}
			}else if(ValidateUtil.isMobileNo(logonInfo)){
				//4-手机号
				resultMap = tprCstChannelInfEntityService.queryTprCstChannelInfByMachineCode(legalId, channel, logonInfo);
				if(ValidateUtil.isEmpty(logonType)){
					logonType = LoginParamsConstants.PERSON_LOGIN_TYPE_4;
				}
			}else if(ValidateUtil.isAcc(logonInfo)){
				//3-账号
				resultMap = tprAccChannelInfoEntityService.queryTprAccChannelInfoByAccNo(legalId, channel, logonInfo);
				if(ValidateUtil.isEmpty(logonType)){
					logonType = LoginParamsConstants.PERSON_LOGIN_TYPE_3;
				}
			}else{
				throw new ServiceException(LoginErrorCodeConstants.PLON0017, "登录方式不正确");
			}
			
			if(resultMap == null || resultMap.size() <= 0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0018, "该客户未开通电子银行");
			}
			
			//客户标识
			String cstId = (String)resultMap.get("cstId");
			//根据客户标识查询个人客户基本信息和渠道信息
			Map<String, Object> cstInfoMap = null;
			if(!LoginParamsConstants.PERSON_LOGIN_TYPE_2.equals(logonType)){
				cstInfoMap = tprCstInfoEntityService.queryTprCstInfoByCstId(cstId);
			}else{
				cstInfoMap = resultMap;
			}
			if(cstInfoMap == null || cstInfoMap.size() <= 0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0018, "该客户未开通电子银行");
			}
			
			Map<String, Object> cstChannelInfoMap = null;
			if(LoginParamsConstants.PERSON_LOGIN_TYPE_2.equals(logonType)
					|| LoginParamsConstants.PERSON_LOGIN_TYPE_3.equals(logonType))
			{
				cstChannelInfoMap = tprCstChannelInfEntityService.queryTprCstChannelInfByCstIdAndChannel(cstId, channel);
			}else{
				cstChannelInfoMap = resultMap;
			}
			if(cstChannelInfoMap == null || cstChannelInfoMap.size() <= 0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0018, "该客户未开通电子银行");
			}
			
			//检查客户状态
			String userState = (String) (cstChannelInfoMap.get("stt")==null? "":cstChannelInfoMap.get("stt"));
			if(LoginParamsConstants.PERSON_CUSTOMER_STATE_1.equals(userState)){ //1-暂停
				logger.error("客户[{}]已暂停使用电子银行", logonInfo);
				throw new ServiceException(LoginErrorCodeConstants.PLON0019, "客户已暂停使用电子银行");
			}else if(LoginParamsConstants.PERSON_CUSTOMER_STATE_2.equals(userState)){ //2-冻结
				logger.error("客户[{}]已冻结", logonInfo);
				throw new ServiceException(LoginErrorCodeConstants.PLON0020, "客户已冻结");
			}else if(LoginParamsConstants.PERSON_CUSTOMER_STATE_3.equals(userState)){ //3-注销
				logger.error("客户[{}]已注销", logonInfo);
				throw new ServiceException(LoginErrorCodeConstants.PLON0021, "客户已注销");
			}else{
				if(!LoginParamsConstants.PERSON_CUSTOMER_STATE_0.equals(userState)){
					logger.error("客户[{}]状态不正常", logonInfo);
					throw new ServiceException(LoginErrorCodeConstants.PLON0022, "客户状态不正常");
				}
			}
			
			//登录控制处理
			Map<String, Object> cstLogonCtrlMap = tprCstLogonCtrlEntityService.queryTprCstLogonCtrlByCstIdAndChannel(cstId, channel);
			//首次登录时间
			String firstLogon = null;
			if(cstLogonCtrlMap != null){
				firstLogon = (String) (cstLogonCtrlMap.get("firstLogon")==null? "":cstLogonCtrlMap.get("firstLogon"));
			}
			
			//0-正常登录
			String logonResult = LoginParamsConstants.PERSON_LOGON_RESULT_0;
			
			//客户类型
			String cstType = (String) (cstChannelInfoMap.get("cstType")==null? "":cstChannelInfoMap.get("cstType"));
			if(ValidateUtil.isEmpty(firstLogon)
					&& LoginParamsConstants.PERSON_CUSTOMER_TTYPE_TL.equals(cstType))
			{
				//1-首次登录
				logonResult = LoginParamsConstants.PERSON_LOGON_RESULT_1;
				//首次登录时间
				firstLogon = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);
			}
			
			//保存个人客户登录控制信息
			if(null==cstLogonCtrlMap || cstLogonCtrlMap.size()==0){
				String objectId = BaseUtil.createUUID();
				int failTodayInit = 0; //当日连续登录失败次数
				int failSumInit = 0; //总计连续登录失败次数
				int countInit = 0; //成功登录次数
				int resultNum = tprCstLogonCtrlEntityService.insertTprCstLogonCtrl(objectId, cstId, channel, failTodayInit, failSumInit, countInit);
				if(resultNum!=1){
					throw new ServiceException(LoginErrorCodeConstants.PLON0024, "保存个人客户登录控制信息异常");
				}
			}

			//冻结日期
			String freezeDate = (String) (cstLogonCtrlMap.get("freezeDate")==null? "":cstLogonCtrlMap.get("freezeDate"));
			//当日连续登录失败次数
			int failToday = ( (Integer)(cstLogonCtrlMap.get("failToday")==null? 0:cstLogonCtrlMap.get("failToday")) ).intValue();
			//当前日期
			String todayDate = DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT);
			
			//检查电子银行是否为临时冻结，当日连续登录失败5次
			if (failToday>LoginParamsConstants.PERSON_DAY_PWD_MAX_ERR_TIMES
					&& (!freezeDate.equals("") && freezeDate.equals(todayDate)) ) 
			{
				throw new ServiceException(LoginErrorCodeConstants.PLON0025, "客户已临时冻结");
			}
			
			//验证错误时还可验证次数
			int validTimes = 0;
			//总计连续错误次数
			int errorTimes = 0;
			//最近成功登录时间
			String lastLogonNext = "";
			
			String password = (String) (cstChannelInfoMap.get("password")==null? "":cstChannelInfoMap.get("password"));
			//加密登录密码
			logger.info("加密前密码为："+logonPwd);
//			logonPwd = encryptAndDecryptService.encryptData(logonPwd);
			logger.info("加密后密码为："+logonPwd);
			//校验登录密码
			if(password.equals(logonPwd)){
				int failTodayNext = 0; //当日连续登录失败次数
				int failSumNext = 0; //总计连续登录失败次数
				String freezeDateNext = null; //冻结日期
				lastLogonNext = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);
				
				//更新个人客户登录控制信息（成功登录次数 = 成功登录次数 + 1）
				tprCstLogonCtrlEntityService.updateTprCstLogonCtrl(cstId, channel, failTodayNext, failSumNext, firstLogon, lastLogonNext, freezeDateNext);
				
				//更新个人客户渠道状态
				userState = LoginParamsConstants.PERSON_CUSTOMER_STATE_0;
				tprCstChannelInfEntityService.updateTprCstChannelSttByCstIdAndChannel(cstId, channel, userState);
			}else{
				//最近一次登录失败日期
				String lastFail = (String) (cstLogonCtrlMap.get("lastFail")==null? "":cstLogonCtrlMap.get("lastFail"));
				int failTodayNext = 1;
				if(!lastFail.equals("") && lastFail.equals(todayDate) ){
					failTodayNext = failToday + 1;
				}
				//总计连续登录失败次数 = 总计连续登录失败次数 + 1
				int failSum = ( (Integer)(cstLogonCtrlMap.get("failSum")==null? 0:cstLogonCtrlMap.get("failSum")) ).intValue();
				errorTimes = failSum + 1;
				
				//登录失败日期
				String lastFailNext = DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT);
				//验证错误时还可验证次数
				validTimes = (LoginParamsConstants.PERSON_DAY_PWD_MAX_ERR_TIMES - failTodayNext)>=0 ? (LoginParamsConstants.PERSON_DAY_PWD_MAX_ERR_TIMES - failTodayNext) : 0;

				//检查是否达到临时冻结次数
				if(failTodayNext>=LoginParamsConstants.PERSON_DAY_PWD_MAX_ERR_TIMES
						&& failTodayNext<LoginParamsConstants.PERSON_SUM_PWD_MAX_ERR_TIMES){
					freezeDate = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);
				}
				
				//更新个人客户登录控制信息（总计连续登录失败次数 = 总计连续登录失败次数 + 1）
				tprCstLogonCtrlEntityService.updateTprCstLogonCtrlFail(cstId, channel, failTodayNext, lastFailNext, freezeDate);
				
				if(failTodayNext>=LoginParamsConstants.PERSON_SUM_PWD_MAX_ERR_TIMES){
					//更新个人客户渠道状态为客户已冻结
					tprCstChannelInfEntityService.updateTprCstChannelSttByCstIdAndChannel(cstId, channel, LoginParamsConstants.PERSON_CUSTOMER_STATE_2);
				}
				
				if(failTodayNext>=LoginParamsConstants.PERSON_DAY_PWD_MAX_ERR_TIMES
						&& failTodayNext<LoginParamsConstants.PERSON_SUM_PWD_MAX_ERR_TIMES)
				{
					throw new ServiceException(LoginErrorCodeConstants.PLON0025, "客户已临时冻结");
				}else if(failTodayNext>=LoginParamsConstants.PERSON_SUM_PWD_MAX_ERR_TIMES){
					throw new ServiceException(LoginErrorCodeConstants.PLON0026, "登录名或密码不正确,密码登错五次,系统会自动冻结一天,如果连续输误九次,请到柜台解锁");
				}

				throw new ServiceException(LoginErrorCodeConstants.PLON0023, "登录名或密码错误");
			}
			
            //临时停用开始日期
			String stopStart = (String) (cstLogonCtrlMap.get("stopStart")==null? "":cstLogonCtrlMap.get("stopStart"));
			//临时停用停止日期
            String stopEnd = (String) (cstLogonCtrlMap.get("stopEnd")==null? "":cstLogonCtrlMap.get("stopEnd"));
            
			//判断是否为临时停用
			if( (!stopStart.equals("") && !stopStart.equals("99999999"))
					&& (!stopEnd.equals("") && !stopEnd.equals("99999999")) )
			{
				Date date2 = DateUtil.getDate(todayDate, DateUtil.DEFAULT_DATE_FORMAT);
				Date date3 = DateUtil.getDate(stopStart, DateUtil.DEFAULT_DATE_FORMAT);
				Date date4 = DateUtil.getDate(stopEnd, DateUtil.DEFAULT_DATE_FORMAT);
				if((date2.compareTo(date3)>=0) && (date2.compareTo(date4)<=0)) {
					logonResult = LoginParamsConstants.PERSON_LOGON_RESULT_2;
				}
			}

			//密码设置时间
			String pwdTime = (String) (cstChannelInfoMap.get("pwdTime")==null? "":cstChannelInfoMap.get("pwdTime"));
			String pwdDate = DateUtil.formatDate(pwdTime, DateUtil.DEFAULT_DATE_FORMAT);
			pwdDate = DateUtil.addMonth(pwdDate, 3);
			Date date5 = DateUtil.getDate(todayDate, DateUtil.DEFAULT_DATE_FORMAT);
			Date date6 = DateUtil.getDate(pwdDate, DateUtil.DEFAULT_DATE_FORMAT);
			//判断是否3个月未修改密码
			if(date6.compareTo(date5)>=0){
				logonResult = LoginParamsConstants.PERSON_LOGON_RESULT_3;
			}
			
			//是否重设私密问题标志 0-否；1-是
			String pqReSet = (String) (cstChannelInfoMap.get("pqReSet")==null? "":cstChannelInfoMap.get("pqReSet"));
			//判断是否重置私密问题
			if( !pqReSet.equals("") 
					&& LoginParamsConstants.PERSON_QUESTION_RESET_1.equals(pqReSet) )
			{
				logonResult = LoginParamsConstants.PERSON_LOGON_RESULT_4;
			}
			
			//是否强制修改密码  0-否；1-是
			String pwdReSet = (String) (cstChannelInfoMap.get("pwdReSet")==null? "":cstChannelInfoMap.get("pwdReSet"));
			//判断是否强制修改密码
			if( !pwdReSet.equals("")
					&& LoginParamsConstants.PERSON_QUESTION_RESET_1.equals(pwdReSet) )
			{
				logonResult = LoginParamsConstants.PERSON_LOGON_RESULT_5;
			}
			
			//渠道共享会话数据
			Map<String, Object> shareSessionMap = new HashMap<String, Object>();
			shareSessionMap.put("cstId", cstId);

			//创建会话对象
			shareShiroSessionManager.addSession(BaseParamsConstants.SHARE_SESSION_KEY_PREFIX + cstId, shareSessionMap);
			
			//客户姓名
			String userName = (String) (cstInfoMap.get("nameCN")==null? "":cstInfoMap.get("nameCN"));
			//证件类型
			String certType = (String) (cstInfoMap.get("certType")==null? "":cstInfoMap.get("certType"));
			//证件号码
			String certNo = (String) (cstInfoMap.get("certNo")==null? "":cstInfoMap.get("certNo"));
			//客户昵称/别名
			String customerAlias = (String) (cstChannelInfoMap.get("alias")==null? "":cstChannelInfoMap.get("alias"));
			//预留信息
			String pretentInfo = (String) (cstChannelInfoMap.get("pretentInfo")==null? "":cstChannelInfoMap.get("pretentInfo"));
			//签约网点
			String signBranchNo = (String) (cstChannelInfoMap.get("branchNo")==null? "":cstChannelInfoMap.get("branchNo"));
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			//组装返回数据
			returnMap.put("cstId", cstId);
			returnMap.put("logonResult", logonResult);
			returnMap.put("userName", userName); 
			returnMap.put("certType", certType); 
			returnMap.put("certNo", certNo); 
			returnMap.put("customerAlias", customerAlias); 
			returnMap.put("pretentInfo", pretentInfo); 
			returnMap.put("signBranchNo", signBranchNo);
			returnMap.put("userChannelState", userState);
			returnMap.put("validTimes", validTimes);
			returnMap.put("errorTimes", errorTimes);
			returnMap.put("lastLogon", lastLogonNext);
			
			return new ResponseEntity(returnMap);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(LoginErrorCodeConstants.PLON0014, "登录异常", e);
		}
	}

	/**
	 * 签退
	 */
	@Override
	public ResponseEntity logout(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			return new ResponseEntity();
		}catch(Exception e){
			throw new ServiceException(LoginErrorCodeConstants.PLON0015, "安全签退异常", e);
		}
	}
	
	/**
	 * 首次登录
	 */
	@Validation(value="p0000211")
	@Override
	public ResponseEntity firstLogin(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			//法人id
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			//客户编号
			String cstNo = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CSTNO)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
			//渠道编号
			String channel = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
			
			//原登录密码
			String oldPassword = (String) (bodyMap.get("oldPassword")==null?"":bodyMap.get("oldPassword"));
			//新登录密码
			String newPassword = (String) (bodyMap.get("newPassword")==null?"":bodyMap.get("newPassword"));
			//确认登录密码
			String cfmPassword = (String) (bodyMap.get("cfmPassword")==null?"":bodyMap.get("cfmPassword"));
			//客户昵称
			String customerAlias = (String) (bodyMap.get("customerAlias")==null?"":bodyMap.get("customerAlias"));
			
			//比较两次输入密码
			if( (ValidateUtil.isEmpty(newPassword) 
					|| ValidateUtil.isEmpty(cfmPassword))
					&& !newPassword.equals(cfmPassword) )
			{
				throw new ServiceException(LoginErrorCodeConstants.PLON0028, "两次输入密码不一致");
			}
			
			//验证个人客户昵称/别名
			boolean result = tprCstChannelInfEntityService.checkAliasIsExist(customerAlias);
			if(result){
				throw new ServiceException(LoginErrorCodeConstants.PLON0027, "客户昵称已存在");
			}
			
			//加密登录密码
			oldPassword = encryptAndDecryptService.encryptData(oldPassword);
			newPassword = encryptAndDecryptService.encryptData(newPassword);

			//根据法人ID+客户编号+渠道编号查询个人客户基本信息
			Map<String, Object> cstInfoMap = tprCstInfoEntityService.queryTprCstInfoByCstNo(legalId, cstNo, channel);
			if(null==cstInfoMap || cstInfoMap.size()<=0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0029, "客户信息不存在");
			}
			
			//客户唯一标识
			String cstId = (String)cstInfoMap.get("cstId");
			//密码修改时间
			String pwdTime = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);
			
			//修改客户昵称+登录密码
			int resultNum = tprCstChannelInfEntityService.updateTprCstChannelAliasAndPwdByCstIdAndChannel(cstId, channel, customerAlias, newPassword, oldPassword, pwdTime);
			if(resultNum!=1){
				throw new ServiceException(LoginErrorCodeConstants.PLON0030, "更新个人客户渠道信息异常");
			}
			
			return new ResponseEntity();
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(LoginErrorCodeConstants.PLON0031, "首次登录异常", e);
		}
	}
	
	/**
	 * 临时停用登录
	 */
	@Validation(value="p0000212")
	@Override
	public ResponseEntity pauseLogin(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			//法人id
			String legalId = (String) (headMap.get(HeadParameterDefinitionConstants.REC_LEGALID)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			//客户编号
			String cstNo = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CSTNO)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
			//渠道编号
			String channel = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
			
			//私密问题编号
			String questionNo = (String) (bodyMap.get("questionNo")==null?"":bodyMap.get("questionNo"));
			//私密问题答案
			String customerAnswer = (String) (bodyMap.get("answer")==null?"":bodyMap.get("answer"));

			//根据法人ID+客户编号+渠道编号查询个人客户基本信息
			Map<String, Object> cstInfoMap = tprCstInfoEntityService.queryTprCstInfoByCstNo(legalId, cstNo, channel);
			if(null==cstInfoMap || cstInfoMap.size()<=0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0029, "客户信息不存在");
			}
			
			//客户唯一标识
			String cstId = (String)cstInfoMap.get("cstId");
			
			//验证私密问题答案
			Map<String, Object> questionMap = tprCstPrivateQuestionEntityService.queryTprCstPrivateQuestionByKey(questionNo, cstId);
			if(null==questionMap || questionMap.size()<=0){
				throw new ServiceException(LoginErrorCodeConstants.PLON0032, "私密问题不存在");
			}
			
			String answer = (String)questionMap.get("answer");
			if(!customerAnswer.equals(answer)){
				throw new ServiceException(LoginErrorCodeConstants.PLON0033, "私密问题答案错误");
			}
			
			//清空个人客户登录控制表中临时停用开始日期+临时停用结束日期
			tprCstLogonCtrlEntityService.updateTprCstLogonCtrlStopDate(cstId, channel, "", "");

			return new ResponseEntity();
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(LoginErrorCodeConstants.PLON0034, "临时停用登录异常", e);
		}
	}

}
