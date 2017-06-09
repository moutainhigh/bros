package bros.provider.parent.custmanage.customervoice.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICustomervoiceService 
 * @Description: 客户之声提交
 * @author haojinhui
 * @date 2016年9月7日 下午2:11:45 
 * @version 1.0
 */
public interface ICustomervoiceService {
	/**
	 * 
	 * @Title: addCustomervoice 
	 * @Description: 客户之声提交
	 * @param functionType	功能类型 01：系统操作反馈；02：业务咨询；03：其它；
	 * @param functionName	功能名称
	 * @param customerVoiceInfo	客户留言
	 * @param customerVoiceState	客户之声处理状态 	1:已提交；2:已处理；3:已作废
	 * @param timestamp	时间戳 yyyyMMddHHmmssSSS
	 * @param processDate	处理日期 	yyyymmdd
	 * @param linkMobileNo	联系人手机号
	 * @return
	 */
	public Map<String,Object> addCustomervoice(String functionType,String functionName,String customerVoiceInfo,String customerVoiceState,String timestamp,String processDate,String linkMobileNo) throws ServiceException;
	/**
	 * 
	 * @Title: querybankannouncement 
	 * @Description: 银行公告查询
	 * @param afficheTitle	公告标题
	 * @param afficheContent	公告内容
	 * @param afficheClass	公告分类 01：系统公告；02：业务公告；03：其它；
	 * @param operChannel	操作渠道 AL:全渠道;EB:网上银行;MB:手机银行;TB:Pad银行
	 * @param releaseTimestamp	发布时间
	 * @param afficheRelStatus	公告发布状态
	 * @param startDate	起开始间
	 * @param endDate 结束时间
	 * @param pageNo 起始页数
	 * @param pageSize 每页条数
	 * @return
	 */
	public Map<String,Object> querybankannouncement(String afficheTitle,String afficheContent,String afficheClass,String operChannel,String releaseTimestamp,String afficheRelStatus,String startDate,String endDate,String pageNo,String pageSize) throws ServiceException;
	/**
	 * 
	 * @Title: querysmsmanage 
	 * @Description: 批量查询短信签约
	 * @param cstNo	渠道平台客户号
	 * @return
	 */
	public Map<String,Object> querysmsmanage(String cstNo) throws ServiceException;
	/**
	 * 
	 * @Title: querypinblock 
	 * @Description: 密码控件加密PIN转换成PINBLOCK
	 * @param accountNo	账号
	 * @param passwordValue	密码的RSA密文
	 * @param pwdKeyIndex	密钥索引
	 * @param randomNumber	密码对应的随机数
	 * @param originAppCode	原始应用编码
	 * @return
	 */
	public Map<String,Object> querypinblock(String accountNo,String passwordValue,String pwdKeyIndex,String randomNumber,String originAppCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryrandomnum 
	 * @Description: 防重放随机数服务
	 * @param randomLength	随机数长度
	 * @return
	 */
	public Map<String,Object> queryrandomnum(String randomLength) throws ServiceException;
	/**
	 * 
	 * @Title: addcheckpassword 
	 * @Description: 校验账户密码
	 * @param accountNo	账号
	 * @param accountType	账号类型	"01:借记卡；02:定期一本通；03:活期一本通；04:信用卡；05:对公账户；06:普通存折08:贷款账户09：其他
	 * @param password	交易密码
	 * @param certType	证件类型
	 * @param certNo	证件号码
	 * @param randomNumber	随机数16位随机数	
	 * @return
	 */
	public Map<String,Object> addcheckpassword(String accountNo,String accountType,String password,String certType,String certNo,String randomNumber) throws ServiceException;
	/**
	 * 
	 * @Title: updatesmssigning 
	 * @Description: 短信签约开通/修改/关闭
	 * @param ocflag	开通关闭修改标志0-关;1-开;2-改;
	 * @param accountNo	账号
	 * @param mobileNo  手机号码
	 * @param certType	证件类型
	 * @param certNo	证件号码
	 * @param password	交易密码
	 * @param randomNumber	随机数16位随机数	
	 * @return
	 */
	public Map<String,Object> updatesmssigning(String ocflag,String accountNo,String mobileNo,String certType,String certNo,String password,String randomNumber) throws ServiceException;
	/**
	 * 
	 * @Title: queryChannelCustomerInf 
	 * @Description: 查询渠道客户信息
	 * @param channel	渠道
	 * @param cstId	客户id;
	 * @return
	 */
	public List<Map<String,Object>> queryChannelCustomerInf(String channel,String cstId) throws ServiceException;
	/**
	 * 
	 * @Title: queryTprCstCstinf 
	 * @Description: 查询客户基本信息
	 * @param legalId	法人id
	 * @param cstNo	客户号;
	 * @return
	 */
	public Map<String,Object> queryTprCstCstinf(String legalId,String cstNo) throws ServiceException;
	/**
	 * 
	 * @Title: updateTprCstCstinf 
	 * @Description: 修改客户昵称
	 * @param cstId	客户id
	 * @param alias	客户昵称
	 * @return
	 */
	public Map<String,Object> updateTprCstCstinf(String cstId,String alias) throws ServiceException;
	/**
	 * 
	 * @Title: checkCustomerAliasByAlias 
	 * @Description: 校验客户昵称是否合法
	 * @param legalId	法人id
	 * @param alias	客户昵称;
	 * @param channel 渠道；
	 * @return
	 */
	public Map<String,Object> checkCustomerAliasByAlias(String legalId,String alias,String channel) throws ServiceException;
	/**
	 * 
	 * @Title: queryPbCstInfoByCstNo 
	 * @Description: 根据客户号查询客户信息
	 * @param legalId	法人id
	 * @param cstNo	客户号;
	 * @return
	 */
	public Map<String,Object> queryPbCstInfoByCstNo(String legalId,String cstNo) throws ServiceException;
	/**
	 * 
	 * @Title: queryPbCstInfoByCtfTypeAndCtfNo 
	 * @Description: 根据客户证件类型+证件号码查询客户基本信息
	 * @param legalId	法人id
	 * @param certType	证件类型;
	 * @param certType	证件号码;
	 * @return
	 */
	public Map<String,Object> queryPbCstInfoByCtfTypeAndCtfNo(String legalId,String certType,String certNo) throws ServiceException;
	/**
	 * 
	 * @Title: queryTprAccInfChannelByAccNo 
	 * @Description: 根据账号查询客户信息
	 * @param legalId	法人id
	 * @param accNo	账号;
	 * @return
	 */
	public List<Map<String,Object>> queryTprAccInfChannelByAccNo(String legalId,String accNo) throws ServiceException;
	/**
	 * 
	 * @Title: queryPbCstInfoByCtfTypeAndCtfNo 
	 * @Description: 查询客户预留信息
	 * @param legalId	法人id
	 * @param cstId	客户id;
	 * @param channel 渠道;
	 * @return
	 */
	public Map<String,Object> queryTprCstChannelInfByCstNo(String legalId,String cstId,String channel) throws ServiceException;
	/**
	 * 
	 * @Title: updateTprCstChannelInfo 
	 * @Description: 修改渠道客户信息-预留信息
	 * @param legalId	法人id
	 * @param cstId	客户id;
	 * @param pretentInfo 预留信息;
	 * @return
	 */
	public Map<String,Object> updateTprCstChannelInfo(String legalId,String cstId,String pretentInfo) throws ServiceException;
	/**
	 * 
	 * @Title: queryTprCstChannelInfo 
	 * @Description: 条件查询渠道客户信息-客户号+渠道
	 * @param legalId	法人id
	 * @param channel	渠道;
	 * @param cstNo 客户号;
	 * @return
	 */
	public Map<String,Object> queryTprCstChannelInfo(String legalId,String channel,String cstNo) throws ServiceException;
}
