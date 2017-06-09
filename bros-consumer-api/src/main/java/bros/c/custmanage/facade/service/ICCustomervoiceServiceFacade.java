package bros.c.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:ICCustomervoiceServiceFacade  
 * @Description:TODO客户之声提交
 * @author  haojinhui
 * @date 2016年9月7日 下午1:37:23 
 * @version V1.0  
 */
public interface ICCustomervoiceServiceFacade {
	/**
	 * 
	 * @Title: addCustomervVoice
	 * @Description: 客户之声提交
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity addCustomervVoice(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: queryBankAnnouncement
	 * @Description: 查询银行公告
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryBankAnnouncement(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: querySmsManage
	 * @Description: 批量查询短信签约
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity querySmsManage(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: queryPinblock
	 * @Description: 密码控件加密PIN转换成PINBLOCK
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryPinblock(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: queryRandomNum
	 * @Description: 防重放随机数服务
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryRandomNum(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: addCheckPassword
   	 * @Description: 校验账户密码
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity addCheckPassword(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: updateSmsSigning
   	 * @Description: 短信签约开通/修改/关闭
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity updateSmsSigning(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: updateCustomerNickname
   	 * @Description: 客户昵称设置
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity updateCustomerNickname(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: updatePassword
   	 * @Description: 静态密码修改服务
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity updatePassword(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryReservedinf
   	 * @Description: 预留信息查询
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryReservedinf(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: updateReservedinf
   	 * @Description: 预留信息设置
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity updateReservedinf(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryInquiryAgreementApp
   	 * @Description: 它行账户查询协议申请（网银互联）
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryInquiryAgreementApp(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryPaymentAgreementApp
   	 * @Description: 它行账户支付协议申请（网银互联）
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryPaymentAgreementApp(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryInquiryAgreementNot
   	 * @Description: 它行账户查询协议通知（网银互联）
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryInquiryAgreementNot(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryPaymentAgreementNot
   	 * @Description: 它行账户支付协议通知（网银互联）
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryPaymentAgreementNot(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryAccountProtocol
   	 * @Description: 它行账户查询协议查询（网银互联）
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryAccountProtocol(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryAccountPayment
   	 * @Description: 它行账户支付协议查询（网银互联）
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryAccountPayment(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryAccountProPayment
   	 * @Description: 它行账户查询协议查询（网银互联）+ 它行账户支付协议查询（网银互联）的合集
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryAccountProPayment(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryAllAccountProPayment
   	 * @Description: 为了前端调用方便，根据不同条件调用queryAccountProtocol，queryAccountPayment，queryAccountProPayment
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryAllAccountProPayment(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryHeDidBalance
   	 * @Description: 它行账户余额查询
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryHeDidBalance(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryHeDidDetail
   	 * @Description: 它行账户明细列表查询
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryHeDidDetail(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryFreeAmt
   	 * @Description: 平台手续费试算
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryFreeAmt(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: onlineBankingDebit
   	 * @Description: 网银借记业务（他行转入）
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity onlineBankingDebit(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryPagingInstruction
   	 * @Description: 分页查询资金统筹计划指令信息
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryPagingInstruction(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: deleteFundInstruction
   	 * @Description: 撤销资金归集指令
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity deleteFundInstruction(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: addFundInstruction
   	 * @Description: 创建资金归集计划
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity addFundInstruction(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryChannelCustomerInf
   	 * @Description: 查询渠道客户信息
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryChannelCustomerInf(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: updateTprCstCstinf
   	 * @Description: 更改客户昵称
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity updateTprCstCstinf(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: queryOperationLogList
   	 * @Description: 查询操作日志列表
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity queryOperationLogList(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
   	 * 
   	 * @Title: updateElectronicManage
   	 * @Description: 电子渠道管理
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
    public ResponseEntity updateElectronicManage(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
