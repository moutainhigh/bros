package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICCustomervoiceServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPCustomervoiceServiceFacade;

/** 
 * @ClassName:CCustomervoiceServiceFacadeImpl  
 * @Description:TODO客户之声提交
 * @author  haojinhui
 * @date 2016年9月7日 下午1:43:43 
 * @version V1.0  
 */
@Component("ccustomervoiceServiceFacade")
public class CCustomervoiceServiceFacadeImpl implements ICCustomervoiceServiceFacade{

		/**
		 * 渠道系统分组服务
		 */
		@Autowired
		private IPCustomervoiceServiceFacade pcustomervoiceServiceFacade;
	    
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
			return pcustomervoiceServiceFacade.addCustomervVoice(headMap,bodyMap);
		}
		/**
		 * 
		 * @Title: queryBankAnnouncement 
		 * @Description: 查询银行公告
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity queryBankAnnouncement(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			return pcustomervoiceServiceFacade.queryBankAnnouncement(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.querySmsManage(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryPinblock(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryRandomNum(headMap,bodyMap);
		}
		/**
		 * 
		 * @Title: addCheckPassword 
		 * @Description: 校验账户密码
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity addCheckPassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			return pcustomervoiceServiceFacade.addCheckPassword(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.updateSmsSigning(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.updateCustomerNickname(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.updatePassword(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryReservedinf(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.updateReservedinf(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryInquiryAgreementApp(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryPaymentAgreementApp(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryInquiryAgreementNot(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryPaymentAgreementNot(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryAccountProtocol(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryAccountPayment(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryAccountProPayment(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryAllAccountProPayment(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryHeDidBalance(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryHeDidDetail(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryFreeAmt(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.onlineBankingDebit(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryPagingInstruction(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.deleteFundInstruction(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.addFundInstruction(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryChannelCustomerInf(headMap,bodyMap);
		}
		/**
		 * 
		 * @Title: updateTprCstCstinf 
		 * @Description: 更改客户昵称
		 * @param headMap 公共报文头
		 * @param bodyMap 报文体
		 * @throws ServiceException
		 */
		@Override
		public ResponseEntity updateTprCstCstinf(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			return pcustomervoiceServiceFacade.updateTprCstCstinf(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.queryOperationLogList(headMap,bodyMap);
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
			return pcustomervoiceServiceFacade.updateElectronicManage(headMap,bodyMap);
		}
}
