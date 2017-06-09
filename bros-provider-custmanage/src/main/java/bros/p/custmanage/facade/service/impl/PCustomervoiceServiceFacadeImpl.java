package bros.p.custmanage.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.custmanage.facade.service.IPCustomervoiceServiceFacade;
import bros.provider.custmanage.constants.CustManageErrorCodeConstants;
import bros.provider.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.bankmanage.teller.service.ITellerService;
import bros.provider.parent.custmanage.accountManage.service.IAccountFuncService;
import bros.provider.parent.custmanage.customervoice.service.ICustomervoiceService;
import bros.provider.parent.customer.person.service.ITprCstChannelInfEntityService;

/** 
 * @ClassName:PCustomervoiceServiceFacadeImpl  
 * @Description:TODO客户之声提交
 * @author  haojinhui
 * @date 2016年9月7日 下午1:43:43 
 * @version V1.0  
 */
@Component("pcustomervoiceServiceFacade")
public class PCustomervoiceServiceFacadeImpl implements IPCustomervoiceServiceFacade{

		/**
		 * 渠道系统分组服务
		 */
		@Autowired
		private ICustomervoiceService customervoiceService;
		@Autowired
		private IHttpClientRouter httpclientRouter;
		/**
		 * 账户转账权限功能维护服务
		 */
		@Autowired
		private IAccountFuncService accountFuncService;
		/**
		 * 柜员系统实现类
		 */
		@Autowired
		private ITellerService tellerService;
		/**
		 * 个人客户渠道信息实现类
		 */
		@Autowired
		private ITprCstChannelInfEntityService tprCstChannelInfEntityService;
	    
		/**
		 * 
		 * @Title: addCustomervVoice 
		 * @Description: 客户之声提交
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity addCustomervVoice(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();	
			httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0000);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryBankAnnouncement 
		 * @Description: 银行公告
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryBankAnnouncement(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0001);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: querySmsManage 
		 * @Description: 批量查询短信签约
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity querySmsManage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0002);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryPinblock 
		 * @Description: 密码控件加密PIN转换成PINBLOCK
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryPinblock(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0003);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryRandomNum 
		 * @Description: 防重放随机数服务
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryRandomNum(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0004);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: addCheckPassword 
		 * @Description: 校验密码
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity addCheckPassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0005);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: updateSmsSigning 
		 * @Description: 短信签约开通/修改/关闭
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity updateSmsSigning(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0006);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: updateCustomerNickname 
		 * @Description: 客户昵称设置
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity updateCustomerNickname(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0007);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: updatePassword 
		 * @Description: 静态密码修改服务
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity updatePassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0008);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryReservedinf 
		 * @Description: 预留信息查询
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryReservedinf(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity = new ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			String cstId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTID));//客户id
			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道
			//预留信息查询
			returnMap = customervoiceService.queryTprCstChannelInfByCstNo(legalId, cstId,channel);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: updateReservedinf 
		 * @Description: 预留信息设置
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity updateReservedinf(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity = new ResponseEntity();
			String cstId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTID));//客户id
			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
			String pretentInfo = (String)bodyMap.get("pretentInfo");//预留信息
			//预留信息设置
			customervoiceService.updateTprCstChannelInfo(legalId, cstId,pretentInfo);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryInquiryAgreementApp 
		 * @Description: 它行账户查询协议申请（网银互联）
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryInquiryAgreementApp(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0011);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryPaymentAgreementApp 
		 * @Description: 它行账户支付协议申请（网银互联）
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryPaymentAgreementApp(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0012);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryInquiryAgreementNot 
		 * @Description: 它行账户查询协议通知（网银互联）
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryInquiryAgreementNot(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0013);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryPaymentAgreementNot 
		 * @Description: 它行账户支付协议通知（网银互联）
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryPaymentAgreementNot(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0014);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryAccountProtocol 
		 * @Description: 它行账户查询协议查询（网银互联）
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryAccountProtocol(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0015);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryAccountPayment 
		 * @Description: 它行账户支付协议查询（网银互联）
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryAccountPayment(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0016);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryAccountProPayment 
		 * @Description: 它行账户查询协议查询（网银互联）+ 它行账户支付协议查询（网银互联）的合集
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryAccountProPayment(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMapf = new HashMap<String, Object>();
			Map<String, Object> returnMapd = new HashMap<String, Object>();
			returnMapf = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0015);
			returnMapd = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0016);
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> returnList1=  (List<Map<String, Object>>) returnMapf.get("totalList");
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> returnList2=  (List<Map<String, Object>>) returnMapd.get("totalList");
			List<Map<String,Object>> totalList = new ArrayList<Map<String ,Object>>();
			if(null!=returnList1 && !"".equals(returnList1)){
				if(null!=returnList2 && !"".equals(returnList2)){
					for(int i=0;i<returnList2.size();i++){
						for(int j=0;j<returnList1.size();j++){
							if(returnList1.get(j).get("opponencyAccount").equals(returnList2.get(i).get("payAccount"))){
								returnList2.get(i).put("qryprotocalNo", returnList1.get(j).get("protocalNo"));
								totalList.add(returnList2.get(i));
							}
						}
					}
				}	
			}
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("totalList", totalList);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryAllAccountProPayment 
		 * @Description: 为了前端调用方便，根据不同条件调用queryAccountProtocol，queryAccountPayment，queryAccountProPayment
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryAllAccountProPayment(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			String protocolType = (String) (bodyMap.get("protocolType")==null?"":bodyMap.get("protocolType"));//协议类型
			if(protocolType.equals("1")){
				responseEntity = queryAccountProtocol(headMap,bodyMap);
			}else if(protocolType.equals("2")){
				responseEntity = queryAccountPayment(headMap,bodyMap);
			}else if(protocolType.equals("3")){
				responseEntity = queryAccountProPayment(headMap,bodyMap);
			}
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryHeDidBalance 
		 * @Description: 它行账户余额查询
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryHeDidBalance(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0017);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryHeDidDetail 
		 * @Description: 它行账户明细列表查询
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryHeDidDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0018);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryFreeAmt 
		 * @Description: 平台手续费试算
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryFreeAmt(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0019);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: onlineBankingDebit 
		 * @Description: 网银借记业务（他行转入）
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity onlineBankingDebit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0020);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryPagingInstruction 
		 * @Description: 分页查询资金统筹计划指令信息
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryPagingInstruction(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0021);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: deleteFundInstruction 
		 * @Description: 撤销资金归集指令
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity deleteFundInstruction(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0022);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: addFundInstruction 
		 * @Description: 创建资金归集计划
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity addFundInstruction(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity =  new  ResponseEntity();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = httpclientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0023);
			responseEntity = new ResponseEntity(returnMap);	
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryChannelCustomerInf 
		 * @Description: 查询渠道客户信息
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryChannelCustomerInf(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {

			Map<String,Object> customerMap = new HashMap<String, Object>();
			String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号ID
			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道
			//查询客户基本信息
			customerMap = customervoiceService.queryTprCstCstinf(legalId, cstNo);
			//获取客户是否有转账权限
			List<Map<String,Object>> accinfFunc= accountFuncService.queryAccFuncInfoNoChannelMethod(cstNo);
			if(null!=accinfFunc){
				customerMap.put("transCloseFlg", "0");
			}else{
				customerMap.put("transCloseFlg","1");
			}
			String cstId = (String)customerMap.get("cstId");
			//客户号查询渠道信息
			List<Map<String,Object>> cstChannelList = customervoiceService.queryChannelCustomerInf(channel,cstId);
			customerMap.put("cstChannelList", cstChannelList);
			ResponseEntity  responseEntity = new ResponseEntity(customerMap);
			return responseEntity;
		}
		/**
		 * 
		 * @Title: updateTprCstCstinf 
		 * @Description: 修改客户昵称信息
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity updateTprCstCstinf(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity = new ResponseEntity();
			String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道
			
			String alias = (String)bodyMap.get("customerAlias");//客户昵称
			
			Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
			if(cstInfoMap == null || cstInfoMap.size()<=0){
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
			}
			String cstId = String.valueOf(cstInfoMap.get("cstId"));
			
			//校验客户昵称是否合法
			customervoiceService.checkCustomerAliasByAlias(cstId, alias,channel);
			//客户号查询渠道信息
			customervoiceService.updateTprCstCstinf(cstId, alias);
			
			return responseEntity;
		}
		/**
		 * 
		 * @Title: queryOperationLogList 
		 * @Description: 查询操作日志列表
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryOperationLogList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity = new ResponseEntity();
			//校验柜员是否存在
			Map<String, Object> tellerMap = new HashMap<String, Object>();
			Map<String, Object> context = new HashMap<String, Object>();
			
			tellerMap = tellerService.queryTellerById(headMap,bodyMap);
			if(tellerMap == null || tellerMap.size() < 1){			
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0046,"查询柜员不存在");			
			}
			String userType = (String)bodyMap.get("userType");//客户类型
			String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道
			String certType = (String) (bodyMap.get("certType")==null?"":bodyMap.get("certType"));//证件类型
			String certNo = (String) (bodyMap.get("certNo")==null?"":bodyMap.get("certNo"));//证件号码
			String accNo = (String) (bodyMap.get("accNo")==null?"":bodyMap.get("accNo"));//账号
			//个人
			if("2".equals(userType)){
				//客户号必送
				//根据客户号查询客户信息
				Map<String,Object> cstMap = new HashMap<String, Object>();
				Map<String,Object> cstMap1 = new HashMap<String, Object>();
				Map<String, Object> accChannelMap = new HashMap<String, Object>();
				cstMap = customervoiceService.queryPbCstInfoByCstNo(legalId, cstNo);
				if(cstMap == null || cstMap.size()<1){
					throw new ServiceException(CustManageErrorCodeConstants.PCUE0036,"该客户号不存在");
				}
				//根据客户证件类型+证件号码查询客户基本信息
				if(!"".equals(certType) && !"".equals(certNo)){
					cstMap1 = customervoiceService.queryPbCstInfoByCtfTypeAndCtfNo(legalId, certType,certNo);
					if(cstMap1 == null || cstMap1.size()<1){
						throw new ServiceException(CustManageErrorCodeConstants.PCUE0037,"该证件不存在客户，请核对您的证件类型及证件号码是否正确！");
					}
				}else if(!"".equals(certType)){
					if("".equals(certNo)){
						throw new ServiceException(CustManageErrorCodeConstants.PCUE0038,"证件类型或证件号码不能为空!");
					}
				}else if(!"".equals(certNo)){
					if("".equals(certType)){
						throw new ServiceException(CustManageErrorCodeConstants.PCUE0038,"证件类型或证件号码不能为空!");
					}
				}
				//根据账号查询客户信息
				if(!"".equals(accNo)){
					List<Map<String,Object>> accList = customervoiceService.queryTprAccInfChannelByAccNo(legalId, accNo);
					if(accList!=null && accList.size()>0){
						accChannelMap = accList.get(0);
					}
					if(accChannelMap == null || accChannelMap.size()<1){
						throw new ServiceException(CustManageErrorCodeConstants.PCUE0039,"该账号不存在或者未开通电子渠道!");
					}
				}
				//校验返回的客户信息是否匹配
				if(cstMap.size()>0){//客户号信息
					if(cstMap1.size()>0){//证件信息
						if(!cstMap.get("cstNo").equals(cstMap1.get("cstNo"))){
							throw new ServiceException(CustManageErrorCodeConstants.PCUE0040,"客户号和证件不匹配!");
						}
						if(accChannelMap.size()>0){//账号信息
							if(!cstMap.get("cstNo").equals(accChannelMap.get("cstNo"))){
								throw new ServiceException(CustManageErrorCodeConstants.PCUE0041,"客户号和账号不匹配!");
							}else{
								context.put("cstNo", cstMap.get("cstNo"));
							}
						}else{
							context.put("cstNo", cstMap.get("cstNo"));
						}
					}else{
						if(accChannelMap.size()>0){
							if(!cstMap.get("cstNo").equals(accChannelMap.get("cstNo"))){
								throw new ServiceException(CustManageErrorCodeConstants.PCUE0041,"客户号和账号不匹配!");
							}
						}
						context.put("cstNo", cstMap.get("cstNo"));
					}
				}else{
					if(cstMap1.size()>0){
						if(accChannelMap.size()>0){
							if(!cstMap1.get("cstNo").equals(accChannelMap.get("cstNo"))){
								throw new ServiceException(CustManageErrorCodeConstants.PCUE0042,"证件和账号不匹配!");
							}
						}
						context.put("cstNo", cstMap1.get("cstNo"));
					}else{
						if(accChannelMap.size()>0){
							context.put("cstNo", accChannelMap.get("cstNo"));
						}else{
							throw new ServiceException(CustManageErrorCodeConstants.PCUE0043,"查询操作日志条件不能全部为空!");
						}
					}
				}
				
			}
			//查询操作日志方法
			/*现在没有地方可以查询操作日志，后面有表存储时再做查询*/
			
			return responseEntity;
		}
		/**
		 * 
		 * @Title: updateElectronicManage 
		 * @Description: 电子渠道管理
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity updateElectronicManage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			ResponseEntity  responseEntity = new ResponseEntity();
			String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道
			
			
			Map<String,Object> cstMap = new HashMap<String, Object>();
			//客户号查询渠道信息
			cstMap = customervoiceService.queryTprCstChannelInfo(legalId, channel,cstNo);
			if(cstMap.get("stt").equals("1")){
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0044,"该渠道已注销，不允许停止渠道服务!");   //该渠道已注销，不允许停止渠道服务
			}
			String cstId = (String)cstMap.get("cstId");//客户id
			String stt = (String)bodyMap.get("stt");//目的状态
			tprCstChannelInfEntityService.updateTprCstChannelSttByCstIdAndChannel(cstId,channel,stt);
			return responseEntity;
		}
}
