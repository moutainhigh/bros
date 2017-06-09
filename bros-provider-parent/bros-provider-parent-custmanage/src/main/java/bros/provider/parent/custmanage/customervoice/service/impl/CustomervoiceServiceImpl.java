package bros.provider.parent.custmanage.customervoice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.customervoice.service.ICustomervoiceService;

/**
 * 
 * @ClassName: CustomervoiceServiceImpl 
 * @Description: 客户服务接口实现
 * @author haojinhui
 * @date 2016年9月7日 下午2:07:37 
 * @version 1.0
 */
@Repository(value = "customervoiceService")
public class CustomervoiceServiceImpl implements ICustomervoiceService{

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
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
	public Map<String,Object> addCustomervoice(String functionType,String functionName,String customerVoiceInfo,String customerVoiceState,String timestamp,String processDate,String linkMobileNo) throws ServiceException{
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
	
			parmIn.put("functionType", functionType);
			parmIn.put("functionName", functionName);
			parmIn.put("customerVoiceInfo", customerVoiceInfo);
			parmIn.put("customerVoiceState", customerVoiceState);
			parmIn.put("timestamp", timestamp);
			parmIn.put("processDate", processDate);
			parmIn.put("linkMobileNo", linkMobileNo);
			
			result.put("returnCode", "00000000");
			
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0034,"客户之声提交失败", e);
		}
		return result;
	}
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
	public Map<String,Object> querybankannouncement(String afficheTitle,String afficheContent,String afficheClass,String operChannel,String releaseTimestamp,String afficheRelStatus,String startDate,String endDate,String pageNo,String pageSize) throws ServiceException{
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("afficheTitle", afficheTitle);
			parmIn.put("afficheContent", afficheContent);
			parmIn.put("afficheClass", afficheClass);
			parmIn.put("operChannel", operChannel);
			parmIn.put("releaseTimestamp", releaseTimestamp);
			parmIn.put("afficheRelStatus", afficheRelStatus);
			parmIn.put("startDate", startDate);
			parmIn.put("endDate", endDate);
			parmIn.put("pageNo", pageNo);
			parmIn.put("pageSize", pageSize);
			
			List<Map<String,Object>> accInfoList = new ArrayList<Map<String,Object>>();
			Map<String,Object> accInfoMap = new HashMap<String,Object>();
			accInfoMap.put("uuid","1234567");
			accInfoMap.put("afficheTitle","测试-公告标题");
			accInfoMap.put("afficheContent","测试-公共内容");
			accInfoMap.put("afficheClass","公告分类");
			accInfoMap.put("operChannel","渠道");
			accInfoMap.put("releaseTimestamp","发布时间戳0");
			accInfoMap.put("afficheRelStatus","公告发布状态");
			accInfoMap.put("createTimestamp","创建时间戳");
			accInfoList.add(accInfoMap);
			result.put("afficheList", accInfoList);
			result.put("totalNo", "1");
			result.put("totalPage", "1");
			
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0035,"银行公告查询失败", e);
		}
		return result;
	}
	/**
	 * 
	 * @Title: querysmsmanage 
	 * @Description: 批量查询短信签约
	 * @param cstNo	渠道平台客户号
	 * @return
	 */
	public Map<String,Object> querysmsmanage(String cstNo) throws ServiceException{
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("cstNo", cstNo);
			
			List<Map<String,Object>> accInfoList = new ArrayList<Map<String,Object>>();
			Map<String,Object> accInfoMap = new HashMap<String,Object>();
			accInfoMap.put("accountNo","账号12321");
			accInfoMap.put("signMessageObjectFlg","短信签约数据有校标记");
			accInfoMap.put("mobileNo","手机号码131");
			accInfoMap.put("certType","证件类型11");
			accInfoMap.put("certNo","证件号码423");
			accInfoMap.put("ocflag","开通关闭修改标志");
			accInfoList.add(accInfoMap);
			result.put("messageSignedList", accInfoList);
			result.put("totalNo", "1");
			
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0036,"批量查询短信签约失败", e);
		}
		return result;
	}
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
	public Map<String,Object> querypinblock(String accountNo,String passwordValue,String pwdKeyIndex,String randomNumber,String originAppCode) throws ServiceException{
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("accountNo", accountNo);
			parmIn.put("passwordValue", passwordValue);
			parmIn.put("pwdKeyIndex", pwdKeyIndex);
			parmIn.put("randomNumber", randomNumber);
			parmIn.put("originAppCode", originAppCode);
			
			result.put("pinBlock", "ADAjhkdfag213");
			
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0037,"密码控件加密PIN转换成PINBLOCK失败", e);
		}
		return result;
	}
	/**
	 * 
	 * @Title: queryrandomnum 
	 * @Description: 防重放随机数服务
	 * @param randomLength	随机数长度
	 * @return
	 */
	public Map<String,Object> queryrandomnum(String randomLength) throws ServiceException{
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("randomLength", randomLength);
			
			result.put("randomNumber", "23112");
			
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0038,"防重放随机数服务失败", e);
		}
		return result;
	}
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
	public Map<String,Object> addcheckpassword(String accountNo,String accountType,String password,String certType,String certNo,String randomNumber) throws ServiceException{
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("accountNo", accountNo);
			parmIn.put("accountType", accountType);
			parmIn.put("password", password);
			parmIn.put("certType", certType);
			parmIn.put("certNo", certNo);
			parmIn.put("randomNumber", randomNumber);
			
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0039,"校验账户密码失败", e);
		}
		return result;
	}
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
	public Map<String,Object> updatesmssigning(String ocflag,String accountNo,String mobileNo,String certType,String certNo,String password,String randomNumber) throws ServiceException{
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("ocflag", ocflag);
			parmIn.put("accountNo", accountNo);
			parmIn.put("mobileNo", mobileNo);
			parmIn.put("certType", certType);
			parmIn.put("certNo", certNo);
			parmIn.put("password", password);
			parmIn.put("randomNumber", randomNumber);
			
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0040,"短信签约开通/修改/关闭失败", e);
		}
		return result;
	}
	/**
	 * 
	 * @Title: queryChannelCustomerInf 
	 * @Description: 查询渠道客户信息
	 * @param channel	渠道
	 * @param cstId	客户id;
	 * @return
	 */
	public List<Map<String,Object>> queryChannelCustomerInf(String channel,String cstId) throws ServiceException{
		List<Map<String,Object>> tprCstInfChannelList = new ArrayList<Map<String,Object>>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("channel", channel);
			parmIn.put("cstId", cstId);
			tprCstInfChannelList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tprcstchannelinf.queryTprCstChannelInfByCstIdAndChannel",parmIn);

		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0044,"查询渠道客户信息失败", e);
		}
		return tprCstInfChannelList;
	}
	/**
	 * 
	 * @Title: queryTprCstCstinf 
	 * @Description: 查询客户基本信息
	 * @param legalId	法人id
	 * @param cstNo	客户号;
	 * @return
	 */
	public Map<String,Object> queryTprCstCstinf(String legalId,String cstNo) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("cstNo", cstNo);
			resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstinfo.queryTprCstCstinf",parmIn);

		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0045,"查询客户基本信息失败", e);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: updateTprCstCstinf 
	 * @Description: 修改客户昵称
	 * @param cstId	客户id
	 * @param alias	客户昵称
	 * @return
	 */
	public Map<String,Object> updateTprCstCstinf(String cstId,String alias) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("cstId", cstId);
			parmIn.put("alias", alias);
			myBatisDaoSysDao.update("mybatis.mapper.single.table.tprcstcstinf.updateTprCstCstinf",parmIn);

		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0045,"修改客户昵称失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: checkCustomerAliasByAlias 
	 * @Description: 校验客户昵称是否合法
	 * @param legalId	法人id
	 * @param alias	客户昵称;
	 * @param channel 渠道
	 * @return
	 */
	public Map<String,Object> checkCustomerAliasByAlias(String legalId,String alias,String channel) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> tprCstInfChannelList = new ArrayList<Map<String,Object>>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("alias", alias);
			parmIn.put("channel", channel);
			tprCstInfChannelList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tprcstcstinf.checkCustomerAliasByAlias",parmIn);
			if(tprCstInfChannelList != null && tprCstInfChannelList.size() > 0){
				throw new ServiceException(CustmanageErrorCodeConstants.PPCG0046,"该客户昵称已存在!"); 
			}
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0047,"客户昵称校验失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryPbCstInfoByCstNo 
	 * @Description: 根据客户号查询客户信息
	 * @param legalId	法人id
	 * @param cstNo	客户号;
	 * @return
	 */
	public Map<String,Object> queryPbCstInfoByCstNo(String legalId,String cstNo) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("cstNo", cstNo);
			resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstcstinf.queryPbCstInfoByCstNo",parmIn);

		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0051,"查询客户基本信息失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryPbCstInfoByCtfTypeAndCtfNo 
	 * @Description: 根据客户证件类型+证件号码查询客户基本信息
	 * @param legalId	法人id
	 * @param certType	证件类型;
	 * @param certType	证件号码;
	 * @return
	 */
	public Map<String,Object> queryPbCstInfoByCtfTypeAndCtfNo(String legalId,String certType,String certNo) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("certType", certType);
			parmIn.put("certNo", certNo);
			resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstcstinf.queryPbCstInfoByCtfTypeAndCtfNo",parmIn);

		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0052,"根据客户证件类型+证件号码查询客户基本信息失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryTprAccInfChannelByAccNo 
	 * @Description: 根据账号查询客户信息
	 * @param legalId	法人id
	 * @param accNo	账号;
	 * @return
	 */
	public List<Map<String,Object>> queryTprAccInfChannelByAccNo(String legalId,String accNo) throws ServiceException{
		List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("accNo", accNo);
			dataList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tpraccchannelinfo.queryPbAccInfChannelByAccNo",parmIn);
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0053,"根据账号查询客户信息失败", e);
		}
		return dataList;
	}
	/**
	 * 
	 * @Title: queryTprCstChannelInfByCstNo 
	 * @Description: 查询客户预留信息
	 * @param legalId	法人id
	 * @param cstId	客户id;
	 * @param channel	渠道;
	 * @return
	 */
	public Map<String,Object> queryTprCstChannelInfByCstNo(String legalId,String cstId,String channel) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("cstId", cstId);
			parmIn.put("channel", channel);
			resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstchannelinf.queryTprCstChannelInfByCstNo",parmIn);
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0054,"查询客户预留信息失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: updateTprCstChannelInfo 
	 * @Description: 根据客户证件类型+证件号码查询客户基本信息
	 * @param legalId	法人id
	 * @param cstId	客户id;
	 * @param pretentInfo	预留信息;
	 * @return
	 */
	public Map<String,Object> updateTprCstChannelInfo(String legalId,String cstId,String pretentInfo) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("cstId", cstId);
			parmIn.put("pretentInfo", pretentInfo);
			resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstchannelinf.updateTprCstChannelInfo",parmIn);
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0055,"修改渠道客户信息-预留信息失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryTprCstChannelInfo 
	 * @Description: 条件查询渠道客户信息-客户号+渠道
	 * @param legalId	法人id
	 * @param cstId	客户id;
	 * @param pretentInfo	预留信息;
	 * @return
	 */
	public Map<String,Object> queryTprCstChannelInfo(String legalId,String channel,String cstNo) throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalId", legalId);
			parmIn.put("channel", channel);
			parmIn.put("cstNo", cstNo);
			resultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstchannelinf.queryTprCstChannelInfo",parmIn);
		} catch (Exception e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0056,"条件查询渠道客户信息-客户号+渠道失败", e);
		}
		return resultMap;
	}
}
