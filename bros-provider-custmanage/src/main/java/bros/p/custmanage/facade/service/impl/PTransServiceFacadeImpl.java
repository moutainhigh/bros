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

import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.constants.PersonConstants;
import bros.common.core.constants.TradeStatusParamsConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.flow.constants.FlowErrorCodeConstants;
import bros.common.core.flow.db.service.IBsnFlowCfgDaoService;
import bros.common.core.flow.jdbc.provider.service.IJDBCProviderService;
import bros.common.core.flow.jdbc.updater.service.IOrderUpdaterService;
import bros.common.core.flow.service.IFlowProcessorService;
import bros.common.core.limit.pb.service.IPbLimitAssembleService;
import bros.common.core.router.service.IHttpClientRouter;
import bros.common.core.util.DateUtil;
import bros.common.core.util.ValidateUtil;
import bros.p.custmanage.facade.service.IPTransServiceFacade;
import bros.provider.custmanage.constants.CustManageErrorCodeConstants;
import bros.provider.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.bankmanage.cst.service.ICstManageService;
import bros.provider.parent.branchmanage.service.IBranchManageEntityService;
import bros.provider.parent.cache.unionbankno.IPubUnionBankNo;
import bros.provider.parent.custmanage.accountManage.service.IAccountFuncService;
import bros.provider.parent.custmanage.accountManage.service.IAccountInfoService;
import bros.provider.parent.custmanage.accountManage.service.IAccountManageService;
import bros.provider.parent.custmanage.customervoice.service.ICustomervoiceService;
import bros.provider.parent.custmanage.limit.service.ICheckCstLimitService;
import bros.provider.parent.custmanage.recvPersonInfo.service.IRecvPersonInfoService;
import bros.provider.parent.custmanage.transferManage.service.IApproveService;
import bros.provider.parent.custmanage.transferManage.service.IEletoSingleService;
/**
 * 
 * @ClassName: TransServiceFacadeImpl 
 * @Description: 转账接口
 * @author gaoyongjing 
 * @date 2016年10月10日 下午2:44:40 
 * @version 1.0
 */
@Component("ptransServiceFacade")
public class PTransServiceFacadeImpl implements IPTransServiceFacade {
	private static final Logger logger = LoggerFactory.getLogger(PTransServiceFacadeImpl.class);
	/**
	 * 账户转账权限功能维护服务
	 */
	@Autowired
	private IAccountFuncService accountFuncService;
	/**
	 * 账户管理服务
	 */
	@Autowired
	private IAccountManageService accountManageService;
	@Autowired
	private IHttpClientRouter httpclientRouter;
	/**
	 *  
	 */
	private IOrderUpdaterService orderUpdater;
	@Autowired
	private IBsnFlowCfgDaoService bsnFlowCfgDaoService;
	@Autowired
	private IFlowProcessorService flowProcessorService;
	@Autowired
	private IPbLimitAssembleService pbLimitAssembleService;
	@Autowired
	private ICheckCstLimitService checkCstLimitService;
	/**
	 * 收款人名册服务
	 */
	@Autowired
	private IRecvPersonInfoService recvPersonInfoService;
	/**
	 * 客户信息服务
	 */
	@Autowired
	private ICstManageService cstManageService;
	
	/**
	 *  
	 */
	@Autowired
	private IOrderUpdaterService singleOrderUpdaterService;
	@Autowired
	private IJDBCProviderService prTransferProviderService;
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
	 * 落地审批服务
	 */
	@Autowired
	private IApproveService approveService;
	/**
	 * 电子回单
	 */
	@Autowired
	private IEletoSingleService eletoSingleService;
	/**
	 * 账号信息管理
	 */
	@Autowired
	private IAccountInfoService accountInfoService;
	/**
	 * 客户服务
	 */
	@Autowired
	private ICustomervoiceService customervoiceService;
	/**
	 * 
	 * @Title: transferPreCheck 
	 * @Description: 汇款预校验
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity transferPreCheck(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		String branchId = (String)headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);//机构id
		String accountNo = (String) (bodyMap.get("accountNo")==null?"":bodyMap.get("accountNo"));//付款账号
		String accountType = (String) (bodyMap.get("accountType")==null?"":bodyMap.get("accountType"));//账号类型
		String password = (String) (bodyMap.get("password")==null?"":bodyMap.get("password"));//密码
		String certType = (String) (bodyMap.get("certType")==null?"":bodyMap.get("certType"));//证件类型
		String certNo = (String) (bodyMap.get("certNo")==null?"":bodyMap.get("certNo"));//证件号码
		String payAmount = (String) (bodyMap.get("payAmount")==null?"":bodyMap.get("payAmount"));//交易金额
		String subPayAccount = (String) (bodyMap.get("subPayAccount")==null?"":bodyMap.get("subPayAccount"));//收款人账号
		String subPayAccountName = (String) (bodyMap.get("subPayAccountName")==null?"":bodyMap.get("subPayAccountName"));//收款人户名
		String transType = (String) (bodyMap.get("transType")==null?"":bodyMap.get("transType"));//转账类型
		String safetool = (String) (bodyMap.get("safetool")==null?"":bodyMap.get("safetool"));//安全工具类型
		 
		/*
		 * 此字段为扩展字段  为手机银行用
		 * 扩展字段的原因：当用户有多个安全工具时，转账单笔限额超过此安全工具（短信验证码）的单笔限额，
		 * 需要走额度大的安全工具（OTP）  。但是当手机银行转账安全工具为OTP时，
		 * 验证的方式为短信验证码，无法实现扣减OTP限额，故加此字段
		 */
		String extend1 = (String) (bodyMap.get("extend1")==null?"":bodyMap.get("extend1"));//
		if( null==extend1 && channel.equals(PersonConstants.ICOP_MB)){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0028,"扩展字段为空，手机银行为必传字段");
		}
		if(channel.equals(PersonConstants.ICOP_MB)){
			safetool = extend1; 
		}
		// 0.判断转入转出账号是否为借记卡：判断账号长度
		int payLength = accountNo.length();
		int subPayLength = subPayAccount.length();
		if (payLength != 16 && payLength != 19) {
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0029, "付款账号非借记卡,请重新输入");
		}else if (subPayLength==10 && "08".equals(transType)) {//不能给一本通转账
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0030, "收款账号非借记卡和存折,请重新输入");
		}
		/**
		 * 说明：该控制条件需要开发人员完善以下三种情况： 
		 * (1)付款账户-本人本行签约账户: 验证网银签约账户、柜面签约、账户有转账权限
		 * (2)付款账户-本行他人账户：验证协议要素;如果指定了收款账户，需要验证收款账户与付款账户的关联关系
		 * (3)付款账户-他行账户：验证协议要素;如果指定了收款账户，需要验证收款账户与付款账户的关联关系
		 */

		String bsnCode = CustManageFormatCodeConstants.TRANSFER_CODE;//转账业务码
		if (!accountFuncService.checkAccRightsByBsnCodeMethod(legalId, cstNo, bsnCode, accountNo, channel)) {
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0031, "账户操作权限不足");
		}
		
		// 获取业务种类, 判断操作业务代码是否包含于客户拥有权限（分类编号）
		String bizType = "";
		 
		Map<String, Object> resultBsn = accountFuncService.queryFuncClassBsnRelInfoByBsnCodeMethod(legalId, bsnCode);
		if (resultBsn != null) {
			Map<String, Object> tmpVO = resultBsn;
			bizType = (String) tmpVO.get("classNo");
		}
		
		// 2.验证付款账户有效性
		 
		Map<String, Object> resultMap = accountManageService.queryAccountInfoHostMethod(headMap, bodyMap);
		//直接把核心校验具体结果返回给前台
		
		//判断交易是否成功还是失败，抛出异常
		String errCode = (String) (resultMap.get(HeadParameterDefinitionConstants.SEN_RETURNCODE)==null?"":resultMap.get(HeadParameterDefinitionConstants.SEN_RETURNCODE));//返回码
		String errMsg = (String) (resultMap.get(HeadParameterDefinitionConstants.SEN_RETURNMSG)==null?"":resultMap.get(HeadParameterDefinitionConstants.SEN_RETURNMSG));//返回错误信息

		if(!BaseParamsConstants.TRADE_SUCCESS_FLAG.equals(errCode)){
			throw new ServiceException(errCode,errMsg);
		}
		
		//校验余额
		String currency = (String) (bodyMap.get("currency")==null?"":bodyMap.get("currency"));//币种
		if(ValidateUtil.isEmpty(currency)){
			currency = "01";
			bodyMap.put("currency", currency);
		}
		bodyMap.put("accountNo", accountNo);
		Map<String,Object> rspAccInfoMap = accountManageService.queryAccountInfoMethod(headMap, bodyMap);
		
		String balanceAvailable = (String) (rspAccInfoMap.get("balanceAvailable")==null?"":rspAccInfoMap.get("balanceAvailable"));//可用余额
		BigDecimal bdAmount = new BigDecimal(payAmount);
		BigDecimal balance = new BigDecimal(balanceAvailable);
		if(bdAmount.compareTo(balance)>0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0032,"账户余额不足!");
		}
         
		// 3.验证付款账户密码
		if(!ValidateUtil.isEmpty(password)){
			bodyMap.put("passwordType", "0"); // 0为交易密码
			bodyMap.put("password", password);
			accountManageService.checkPassword(headMap, bodyMap);
		}else if(PersonConstants.ICOP_MB.equals(channel)){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0033,"交易密码输入有误");
		}
		// 4.限额检查
		// (1). 付款账号是本人本行账号,检查网银限额
		// (2). 付款账号是本行他人账号,检查签约限额和笔数限制
		// (3). 付款账号是他行账号,检查网银互联限额和笔数
		// (4). 付款账号和收款账号都是本人本行账号,不受限额控制

		 
		bodyMap.put("payAccNo", accountNo);
		bodyMap.put("rcvAccNo", subPayAccount);
		bodyMap.put("bizType", bizType);
		bodyMap.put("tansMoney", payAmount);
		if(checkCstLimitService.checkCstLimitBefore(headMap, bodyMap)){
			//查询客户级别
			//TODO 校验限额
//			pbLimitAssembleService.checkLimit(channel, legalId, branchId, cstNo, accountNo, bizType, safetool, "", "", "", payAmount);
		}
		
		String isCheckRepeat = (String) (bodyMap.get("isCheckRepeat")==null?"":bodyMap.get("isCheckRepeat"));//是否验证重复
		String bsnCode1 = (String)headMap.get(HeadParameterDefinitionConstants.REC_BSNCODE);//功能编号
		if(!ValidateUtil.isEmpty(isCheckRepeat) && isCheckRepeat.equals("1")){//做验证交易是否重复
			//根据条件查询交易流水
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("channel",channel);//渠道编号
			param.put("legalId",legalId);//
			param.put("cstNo", cstNo);//客户号
			param.put("subPayAccount", subPayAccount);//收款账号
			param.put("subPayAccountName",subPayAccountName);//收款户名
			param.put("transOperState", TradeStatusParamsConstants.TRADE_STATE_90);//交易状态：成功
			param.put("transType", transType);
			param.put("payAmount",payAmount);
			param.put("bsnCode",bsnCode1);//功能编号
			int num = 0;
			Map<String, Object> bsConfigMap = bsnFlowCfgDaoService.queryBsnFlowCfg(bsnCode, channel);
			if(bsConfigMap!=null && bsConfigMap.size()>0){
				String updaterBeanId = (String)bsConfigMap.get("updaterBeanId");
				String providerBeanId = (String)bsConfigMap.get("providerBeanId");
				
				if(ValidateUtil.isEmpty(updaterBeanId)){
					throw new ServiceException(FlowErrorCodeConstants.CCFW0015, "未配置指令处理器");
				}
				if(ValidateUtil.isEmpty(providerBeanId)){
					throw new ServiceException(FlowErrorCodeConstants.CCFW0016, "未配置SQL提供器");
				}
				
				//生成指令处理器
				orderUpdater = flowProcessorService.createProcessor(updaterBeanId, providerBeanId);
				//添加流水记录
				num = orderUpdater.queryMainFlowByConditions(param);
			}
			if(num>0){
				returnMap.put("isTransRepeat", "1");//交易是否重复:重复
			}else{
				returnMap.put("isTransRepeat", "0");//交易是否重复:不重复
			}
		}
		ResponseEntity entity = new ResponseEntity(returnMap);
		return entity;
	}
	/**
	 * 
	 * @Title: tranProcess 
	 * @Description: 发起单笔转账
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity tranProcess(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException{
		ResponseEntity entity = new ResponseEntity();
		String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
		String payAccount = (String) (bodyMap.get("payAccNo")==null?"":bodyMap.get("payAccNo"));//付款账号
		String payAccountName = (String) (bodyMap.get("payAccName")==null?"":bodyMap.get("payAccName"));//付款人户名
		String password = (String) (bodyMap.get("password")==null?"":bodyMap.get("password"));//交易密码
		String certType = (String) (bodyMap.get("certType")==null?"":bodyMap.get("certType"));//证件类型
		String certNo = (String) (bodyMap.get("certNo")==null?"":bodyMap.get("certNo"));//证件号码
		String payAmount = (String) (bodyMap.get("transAmt")==null?"":bodyMap.get("transAmt"));//交易金额
		String subPayAccount = (String) (bodyMap.get("resiveAccNo")==null?"":bodyMap.get("resiveAccNo"));//收款人账号
		String subPayAccountName = (String) (bodyMap.get("resiveAccName")==null?"":bodyMap.get("resiveAccName"));//收款人名称
		String transType = (String) (bodyMap.get("note1")==null?"":bodyMap.get("note1"));//转账类型
		String currency = (String) (bodyMap.get("currency")==null?"":bodyMap.get("currency"));//币种
		String safetool = (String) (bodyMap.get("safetool")==null?"":bodyMap.get("safetool"));//安全工具
		String sendMessageFlg = (String) (bodyMap.get("sendMessageFlg")==null?"":bodyMap.get("sendMessageFlg"));//短信通知标志(通知收款人)
		String mobileNo = (String) (bodyMap.get("sendMessageMobile")==null?"":bodyMap.get("sendMessageMobile"));//短信通知收款人手机号
		String emailFlag = (String) (bodyMap.get("emailFlag")==null?"":bodyMap.get("emailFlag"));//
		String cnaps2BankName = (String) (bodyMap.get("cnaps2BankName")==null?"":bodyMap.get("cnaps2BankName"));//大小额通道联行名
		String unionBankName = (String) (bodyMap.get("unionBankName")==null?"":bodyMap.get("unionBankName"));//网银互联联行名
		String unionBankNo = (String) (bodyMap.get("unionBankNo")==null?"":bodyMap.get("unionBankNo"));//网银互联联行号
		String cnaps2BankNo = (String) (bodyMap.get("cnaps2BankNo")==null?"":bodyMap.get("cnaps2BankNo"));//大小额通道联行号
		String postscript = (String) (bodyMap.get("purpose")==null?"":bodyMap.get("purpose"));//转账用途
		String lastFeeAll = (String) (bodyMap.get("feeAmt")==null?"":bodyMap.get("feeAmt"));//手续费
		String bankName = "";
		String comitrNo = "";
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		/*
		 * 此字段为扩展字段  为手机银行用
		 * 扩展字段的原因：当用户有多个安全工具时，转账单笔限额超过此安全工具（短信验证码）的单笔限额，
		 * 需要走额度大的安全工具（OTP）  。但是当手机银行转账安全工具为OTP时，
		 * 验证的方式为短信验证码，无法实现扣减OTP限额，故加此字段
		 */
		String extend1 = (String) (bodyMap.get("extend1")==null?"":bodyMap.get("extend1"));//
		if( null==extend1 && channel.equals(PersonConstants.ICOP_MB)){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0028,"扩展字段为空，手机银行为必传字段");
		}
		if(channel.equals(PersonConstants.ICOP_MB)){
			safetool = extend1; 
		}
		if(!ValidateUtil.isEmpty(unionBankName)){
			bankName = unionBankName;
		}else if(!ValidateUtil.isEmpty(cnaps2BankName)){
			bankName = cnaps2BankName;
		}else{
			bankName = PersonConstants.SELF_BANK_ALIAS_NAME;
		}
		if(!ValidateUtil.isEmpty(unionBankNo)){
			comitrNo = unionBankNo;
		}else if(!ValidateUtil.isEmpty(cnaps2BankNo)){
			comitrNo = cnaps2BankNo;
		}else{
			comitrNo = PersonConstants.IBPS_BANK_NO;
		}
		//验证密码非空
		if(ValidateUtil.isEmpty(password)){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0033,"交易密码输入有误");
		}
		
		Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
		if(cstInfoMap == null || cstInfoMap.size()<=0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
		}
		String cstId = String.valueOf(cstInfoMap.get("cstId"));
		//客户账户匹配检查 防止交易过程中，收款账号和付款账号被人颠倒
		List<Map<String, Object>> cstAccMap = cstManageService.queryCstInfByCstNoAndAccNo(legalId,cstId, payAccount, channel);
		if(cstAccMap==null || cstAccMap.size()<1){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0047,"您的账户或者客户信息不存在或已注销");
		}
		/**
		 * 说明：该控制条件需要开发人员完善以下三种情况： 
		 * (1)付款账户-本人本行签约账户: 验证网银签约账户、柜面签约、账户有转账权限
		 * (2)付款账户-本行他人账户：验证协议要素;如果指定了收款账户，需要验证收款账户与付款账户的关联关系
		 * (3)付款账户-他行账户：验证协议要素;如果指定了收款账户，需要验证收款账户与付款账户的关联关系
		 */

		String bsnCode = CustManageFormatCodeConstants.TRANSFER_CODE;//转账业务码
		if (!accountFuncService.checkAccRightsByBsnCodeMethod(legalId, cstNo, bsnCode, payAccount, channel)) {
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0031, "账户操作权限不足");
		}
		
		// 获取业务种类, 判断操作业务代码是否包含于客户拥有权限（分类编号）
		String bizType = "";
		
		Map<String, Object> resultBsn = accountFuncService.queryFuncClassBsnRelInfoByBsnCodeMethod(legalId, bsnCode);
		if (resultBsn != null) {
			Map<String, Object> tmpVO = resultBsn;
			bizType = (String) tmpVO.get("classNo");
		}
		//如果是网银互联汇路，检验密码
		if("01".equals(transType) || "02".equals(transType) || "03".equals(transType)){
			bodyMap.put("passwordType", "0"); // 0为交易密码
			bodyMap.put("password", password);
			bodyMap.put("accountNo", payAccount);
			accountManageService.checkPassword(headMap, bodyMap);
		}
		
		//TODO 限额校验
		//TODO 落地限额审批标记
		String resultFlag = (String) bodyMap.get("resultFlag");
		if(!ValidateUtil.isEmpty(resultFlag)&&"1".equals(resultFlag)){
			return new ResponseEntity();
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//转账
		try {
			resultMap = accountManageService.tranProcess(headMap, bodyMap);
		}catch(ServiceException se){
			logger.info("转账失败",se);
			// 10.转账失败,退回限额
			// (1)TODO 付款账号和收款账号都是本人本行账号,不返还限额
			return entity = new ResponseEntity(resultMap);
		}
		 
		//11.转账成功,保存收款人名册,发送短信/邮件通知
		Map<String,Object> payBookMap = new HashMap<String,Object>();
		//payBookMap.put("legalId",legalId);
		//payBookMap.put("cstNo",cstNo);
		payBookMap.put("cstId",cstId);
		payBookMap.put("accountNo",subPayAccount);
		payBookMap.put("accountName",subPayAccountName);
		if("1".equals(sendMessageFlg)){
			payBookMap.put("mobileNo",mobileNo);
			payBookMap.put("sendMessageMobile",mobileNo);
		}else{
			payBookMap.put("noMobile","0");
		}
		payBookMap.put("bankName",bankName);
				
		//账户类型01:借记卡；02：定期一本通；03:活期一本通；04:信用卡；05:对公账户；08:贷款账户；09：其他；
		String accountType = "";
		//收款人类型 1-行内 2-行外 3-手机收款人
		String payeeType = "";
		if (PersonConstants.TRAN_TYPE_INNER.equals(transType)
				|| PersonConstants.TRAN_TYPE_DEBIT.equals(transType)) {
			accountType = "01";
			payeeType = "1";
		}else{
			accountType = "09";
			payeeType = "2";
		}
		//手机收款人-----是手机收款人的联系人
		/*if(BaseConstants.MBANK_CHANNEL.equals(context.getDataAsStr("channel"))){
			payeeType = "3";
		}*/
				
		payBookMap.put("accountType",accountType);
		payBookMap.put("payeeType",payeeType);
		payBookMap.put("comitrNo",comitrNo);
		payBookMap.put("transType",transType);
		 
		Map<String,Object> vo = recvPersonInfoService.queryRecvPersonInfoByCstNoAccNoMethod(cstId,payeeType, mobileNo, subPayAccount, subPayAccountName);
		if(vo==null){
			payBookMap.put("headPicName", "m1.png");//给默认的头像
			payBookMap.put("commonFlg","0");//是否是常用联系人，这里设置成否,1是常用
			payBookMap.put("accountAlias", subPayAccountName);
			recvPersonInfoService.addRecvPersonInfoMethod(payBookMap);
		}else{
			String payBookId = (String)vo.get("payBookId");
			payBookMap.put("payBookId",vo.get("payBookId"));
			//根据payBookId和transType查询收款人子信息是否存在
			Map<String,Object> subResult = recvPersonInfoService.querySubRecvPersonInfoMethod(payBookId, transType);
			 
			if(subResult != null && subResult.size() > 0){
				//step5 更新收款人信息
				recvPersonInfoService.updateRecvPersonInfoMethod(payBookMap);
				if(!"".equals(transType)){
					//step6 更新子表信息
					recvPersonInfoService.updateSubRecvPersonInfoMethod(payBookId, transType, comitrNo);	
				}
			}else{
				//step5 更新收款人信息
				recvPersonInfoService.updateRecvPersonInfoMethod(payBookMap);
				if(!"".equals(transType)){//转账类型不为空则添加子表信息
					//step6 新增子表信息
					recvPersonInfoService.addSubRecvPersonInfoMethod(payBookId, transType, comitrNo);
				}
			}
		}
		if("1".equals(sendMessageFlg)){//发送短信通知
			Map<String,Object> param = new HashMap<String,Object>();
			//发送短信服务数据
			param.put("messageStrategy", "31007");
//			param.put("appCode", "301");
			String timeNow = DateUtil.getServerTime(null);
			String accno = subPayAccount.substring(subPayAccount.length()-4);
			String time = timeNow.substring(4,6)+"月"+timeNow.substring(6,8)+"日 "+timeNow.substring(8,10)+":"+timeNow.substring(10,12);
			String transData = "FIELD0|"+payAccountName+"|FIELD1|"+time+"|FIELD3|"+accno+"|FIELD4|"+payAmount;
			param.put("transData", transData);
			param.put("mobileNo", mobileNo);
			accountManageService.sendSmsAuthenticationCode(headMap,param);
		}
		entity = new ResponseEntity(resultMap);
		return entity;
	}
	/**
	 * 
	 * @Title: queryAccTransResultInf 
	 * @Description: 根据条件查询转账结果流水记录信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccTransResultInf(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		String transStartTime = (String) (bodyMap.get("beginDate")==null?"":bodyMap.get("beginDate"));//开始日期
		String transEndTime = (String) (bodyMap.get("endDate")==null?"":bodyMap.get("endDate"));//截止日期
		String channel = (String) (bodyMap.get("operChannel")==null?"":bodyMap.get("operChannel"));//渠道号
		String cstNo = (String) (bodyMap.get("cstNo")==null?"":bodyMap.get("cstNo"));//客户号
		String bsnCode = (String) (bodyMap.get("bsnCode")==null?"":bodyMap.get("bsnCode"));//业务编码
		String payAccNo = (String) (bodyMap.get("payAccNo")==null?"":bodyMap.get("payAccNo"));//付款账号
		String payAccName = (String) (bodyMap.get("payAccName")==null?"":bodyMap.get("payAccName"));//付款户名
		String resiveAccNo = (String) (bodyMap.get("resiveAccNo")==null?"":bodyMap.get("resiveAccNo"));//收款人账号
		String resiveAccName = (String) (bodyMap.get("resiveAccName")==null?"":bodyMap.get("resiveAccName"));//收款人户名
		String transBankType = (String) (bodyMap.get("transBankType")==null?"":bodyMap.get("transBankType"));//转账行别0:行内；1:行外；
		String transAmt = (String) (bodyMap.get("transAmt")==null?"":bodyMap.get("transAmt"));// 交易金额
		String sortType = (String) (bodyMap.get("sortType")==null?"":bodyMap.get("sortType"));//
		String transOperState = (String) (bodyMap.get("transOperState")==null?"":bodyMap.get("transOperState"));//转账交易状态
		String pageSize = (String) (bodyMap.get("pageSize")==null?"":bodyMap.get("pageSize"));//每页条数
		String pageNo = (String) (bodyMap.get("pageNo")==null?"":bodyMap.get("pageNo"));//页码
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		 
		List<Map<String,Object>> transResultInfoList = new ArrayList<Map<String,Object>>();
		//查询转账结果列表
		singleOrderUpdaterService.setProvider(prTransferProviderService);
		transResultInfoList = singleOrderUpdaterService.queryFlowByConditions(channel,cstNo,bsnCode,payAccNo,payAccName,transStartTime,transEndTime,resiveAccNo,resiveAccName,transBankType,transOperState,transAmt,sortType,pageNo,pageSize);
		int totalNum = singleOrderUpdaterService.queryFlowNumByConditions(channel,cstNo,bsnCode,payAccNo,payAccName,transStartTime,transEndTime,resiveAccNo,resiveAccName,transBankType,transOperState,transAmt,sortType);
		//查询开户行名
		for(int i=0;i<transResultInfoList.size();i++){
			Map<String,Object> map=transResultInfoList.get(i);
			Map<String,Object> PubUnionBankMap = pubUnionBankNo.queryPubUnionBankNoByBankNo(map.get("accountOpenBranchNo")+"");
			Map<String,Object> subBranch = branchManageEntityService.queryBranchInfo(legalId, map.get("accountOpenBranchNo")+"");
			if(null != PubUnionBankMap){
				map.put("payBankName", PubUnionBankMap.get("bankName"));
			}else if(null != subBranch){
				map.put("payBankName", subBranch.get("branchName"));
			}else{
				map.put("payBankName", "");
			}
		}
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("returnList", transResultInfoList);
		returnMap.put("totalNum", totalNum);
		ResponseEntity entity = new ResponseEntity(returnMap);
		return entity;
	}
	/**
	 * 
	 * @Title: queryCnapsTransResult 
	 * @Description: 大小额转账结果查证
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryCnapsTransResult(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CNAPSTRANSRESULT_CODE);
//		String bizStatus = (String) resultMap.get("bizStatus");
		//查询转账结果列表
//		singleOrderUpdaterService.setProvider(prTransferProviderService);
//		singleOrderUpdaterService.updateFlowInfo(bodyMap, gblflowSeq, bizStatus);
		ResponseEntity entity = new ResponseEntity(resultMap);
		return entity;
	}
	/**
	 * 
	 * @Title: queryTransResult 
	 * @Description: 业务状态查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryTransResult(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
//		try{
			resultMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.TRANSRESULT_CODE);
//		}catch(ServiceException se){
//			 
//		}
		ResponseEntity entity = new ResponseEntity(resultMap);
		return entity;
	}
	
	
	/**
	 * 
	 * @Title: queryApproveByStatMethod
	 * @Description: 条件查询落地审批信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryApproveByStatMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		String payBranchNo = (String) (bodyMap.get("payBranchNo")==null?"":bodyMap.get("payBranchNo"));//付款行行号
		String approveStat = (String) (bodyMap.get("approveStat")==null?"":bodyMap.get("approveStat"));//审批状态
		String cstNo = (String) (bodyMap.get("cstNo")==null?"":bodyMap.get("cstNo"));//客户号
		String tellerNo = (String) (bodyMap.get("tellerNo")==null?"":bodyMap.get("tellerNo"));//操作员
		String cstType = (String) (bodyMap.get("cstType")==null?"":bodyMap.get("cstType"));//客户类型
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道号 
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		String payAccNo = (String) (bodyMap.get("payAccNo")==null?"":bodyMap.get("payAccNo"));//付款账号 
		String resiveAccNo = (String) (bodyMap.get("resiveAccNo")==null?"":bodyMap.get("resiveAccNo"));//收款账号
		
		List<Map<String,Object>> approveInfoList = new ArrayList<Map<String,Object>>();
		int pageNo = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")));
		
		if(pageNo == 0){
			approveInfoList = approveService.queryApproveByStatMethod(payBranchNo, approveStat, cstNo, tellerNo, cstType, channel, legalId, payAccNo, resiveAccNo);
		}else{
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")));
			approveInfoList = approveService.queryApproveByStatMethod(payBranchNo, approveStat, cstNo, tellerNo, cstType, channel, legalId, payAccNo, resiveAccNo, pageNo, pageSize);
		}
		int totalNum = approveService.queryApproveByStatTotalNumMethod(payBranchNo, approveStat, cstNo, tellerNo, cstType, channel, legalId, payAccNo, resiveAccNo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("returnList", approveInfoList);
		resultMap.put("totalNum", totalNum);
		ResponseEntity entity = new ResponseEntity(resultMap);
		return entity;
		 
	}
	
	/**
	 * 
	 * @Title: cancelTransMethod
	 * @Description: 取消转账
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity cancelTransMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		String objectId = (String) (bodyMap.get("objectId")==null?"":bodyMap.get("objectId"));//唯一id
		String approveStat = (String) (bodyMap.get("approveStat")==null?"":bodyMap.get("approveStat"));//审批状态
		String approveOpinion = (String) (bodyMap.get("approveOpinion")==null?"":bodyMap.get("approveOpinion"));//审批意见
		String postscript = (String) (bodyMap.get("postscript")==null?"":bodyMap.get("postscript"));//审批附言
		String approveTime = DateUtil.getServerTime("yyyyMMddHHmmss");
		if("02".equals(approveOpinion)){
			if(ValidateUtil.isEmpty(postscript)){
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0045,"落地审批拒绝请输入拒绝原因");
			}
		}
		approveService.updateApproveInfoMethod(objectId, approveStat, approveOpinion, approveTime, postscript);
		Map<String,Object> approveInfoMap = approveService.queryApproveByObjectIdMethod(objectId);
		String channel = (String)approveInfoMap.get("channel");
		if(PersonConstants.ICOP_EB .equals(channel)){
			Map<String, Object> param = new HashMap<String, Object>();
			String transTime = (String) approveInfoMap.get("transTime");
			transTime = transTime.substring(4,6)+"月"+transTime.substring(6,8)+"日 "+transTime.substring(8,10)+":"+transTime.substring(10,12);
			String payAcc = (String) approveInfoMap.get("payAccNo");
			payAcc = payAcc.substring(payAcc.length()-4);
			String payAmount = approveInfoMap.get("transAmt").toString();
			String transData = "FIELD0|"+payAcc.substring(payAcc.length()-4, payAcc.length())+"|FIELD1|"+transTime+"|FIELD3|"+payAmount;
			param.put("transData", transData);
			//查询用户手机号
			
			String cstNo = (String) approveInfoMap.get("cstNo");
			String legalId = (String) approveInfoMap.get("legalId");
			
			Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
			if(cstInfoMap == null || cstInfoMap.size()<=0){
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
			}
			String cstId = String.valueOf(cstInfoMap.get("cstId"));
			
			List<Map<String, Object>> userInfList = cstManageService.queryCstInfByCstNoAndAccNo(legalId,cstId, "", channel);
			if(userInfList == null || userInfList.size() <= 0){
				return entity;
			}
			String telNo = (String) userInfList.get(0).get("mobile1");//手机号
			
			//发送短信
			if(!ValidateUtil.isEmpty(telNo)){
				param.put("mobileNo", telNo);
				//发送短信服务数据
				if("01".equals(approveOpinion)){//审批通过{
					param.put("messageStrategy", "31052");
				}else if("02".equals(approveOpinion)){//审批拒绝{
					param.put("messageStrategy", "31053");
				}
				accountManageService.sendSmsAuthenticationCode(headMap,param);
			}
		}
		return entity;
	}
	/**
	 * 
	 * @Title: addEletoSingleMethod
	 * @Description: 添加电子回单
	 * @param eletoReceiptInfo 电子回单信息
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity addEletoSingleMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		//根据流水号查询电子回单添加所需要的数据
		String flowNo = (String) (bodyMap.get("eleReceiptNo")==null?"":bodyMap.get("eleReceiptNo"));//流水
		singleOrderUpdaterService.setProvider(prTransferProviderService);
		Map<String,Object> tranFlowNoMap =	singleOrderUpdaterService.queryMainAndDetailFlowByGblflowSeq(flowNo);
		if(tranFlowNoMap == null || tranFlowNoMap.size() <= 0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0048,"无此流水信息");
		}
		eletoSingleService.addEletoSingleMethod(tranFlowNoMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	
	/**
	 * 
	 * @Title: queryEleToSingleinfoMethod
	 * @Description: 查询电子回单信息
	 * @param legalId 法人id
	 * @param cstNo 客户号
	 * @param eleReceiptNo 回单号
	 * @param authCode 验证码
	 * @param transTimestamp 交易时间
	 * @param payAccNo 付款人账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryEleToSingleinfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		List<Map<String,Object>> eletoSingleInfoList = new ArrayList<Map<String,Object>>();
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		String cstNo = (String) (bodyMap.get("cstNo")==null?"":bodyMap.get("cstNo"));//客户号
		String eleReceiptNo = (String) (bodyMap.get("eleReceiptNo")==null?"":bodyMap.get("eleReceiptNo"));//流水号
		String authCode = (String) (bodyMap.get("authCode")==null?"":bodyMap.get("authCode"));//验证码
		String transTimestamp = (String) (bodyMap.get("transTimestamp")==null?"":bodyMap.get("transTimestamp"));//交易时间
		String payAccNo = (String) (bodyMap.get("payAccNo")==null?"":bodyMap.get("payAccNo"));//付款人账号
		if(!ValidateUtil.isEmpty(authCode) && !ValidateUtil.isEmpty(transTimestamp)){
			cstNo = "";
		}
		eletoSingleInfoList = eletoSingleService.queryEleToSingleinfoMethod(legalId, cstNo, eleReceiptNo, authCode, transTimestamp, payAccNo);
		 
		if(eletoSingleInfoList != null && eletoSingleInfoList.size() > 0){
			String accNo=(String) eletoSingleInfoList.get(0).get("payAccNo");
			if(!accNo.isEmpty()){
				 //查询开户行行号
				String payBranchName = "";
				Map<String,Object> subBranchMap = accountInfoService.queryBranchByAccNoMethod(accNo);
				if(subBranchMap != null && subBranchMap.size() > 0){
					payBranchName = (String) subBranchMap.get("brachName");
				}
				eletoSingleInfoList.get(0).put("payBranchName", payBranchName);
			}else{
				eletoSingleInfoList.get(0).put("payBranchName", "");
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("returnList", eletoSingleInfoList);
		entity = new ResponseEntity(resultMap);
		return entity;
	}
	
	/**
	 * 
	 * @Title: updateEletoSinglePrintNumByObjectId
	 * @Description: 更新打印次数
	 * @param objectId  记录唯一id
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity updateEletoSinglePrintNumByObjectId(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		String objectId = (String) (bodyMap.get("objectId")==null?"":bodyMap.get("objectId"));//付款行行号
		eletoSingleService.updateEletoSinglePrintNumByObjectId(objectId);
		ResponseEntity entity = new ResponseEntity();
		return entity;
		 
	}
}
