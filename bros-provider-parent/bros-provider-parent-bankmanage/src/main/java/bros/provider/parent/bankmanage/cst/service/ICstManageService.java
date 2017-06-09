package bros.provider.parent.bankmanage.cst.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICstManageService 
 * @Description: 渠道客户信息接口
 * @author MacPro 
 * @date 2016年9月19日 下午5:42:25 
 * @version 1.0
 */
public interface ICstManageService {
	
	/**
	 * 
	 * @Title: queryCstInfoByCstNo 
	 * @Description: 客户信息查询
	 * @param legalId	法人Id
	 * @param certType	证件类型
	 * @param certNo	证件号码
	 * @return
	 * @throws ServiceException    异常信息
	 */
	public Map<String, Object> queryCstInfoByCertNo(String legalId,String certType, String certNo) throws ServiceException;
	/**
	 * 
	 * @Title: queryCstInfoByCstNo 
	 * @Description: 客户信息查询
	 * @param certType	证件类型
	 * @param certNo	证件号码
	 * @param nameCn	客户姓名
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryCstInfoByCstNo(String legalId,String certType, String certNo, String nameCn) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryCstInfoChannelByPhoneNo 
	 * @Description: 渠道客户签约信息查询-手机号
	 * @param cstId		客户Id
	 * @param payPhoneNo	手机号
	 * @param channel		签约渠道
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryCstInfoChannelInfo(String cstId,String payPhoneNo,String channel) throws ServiceException;
	
	/**
	 * @Title: saveCstInfoChannel 
	 * @Description: 保存渠道客户签约信息
	 * @param cstChannelMap
	 * @throws ServiceException
	 */
	public void saveCstInfoChannel(Map<String,Object> cstChannelMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAlias 
	 * @Description: 验证昵称是否可用
	 * @param legalId		法人Id
	 * @param alias	别名
	 * @param channel	渠道标识
	 * @return
	 * @throws ServiceException
	 */
	public String checkAlias(String legalId,String alias, String channel)throws ServiceException;
	

	/**
	 * 
	 * @Title: queryCstInfByMachine 
	 * @Description: 检查手机号是否可用
	 * @param legalId	法人Id
	 * @param channel	渠道标识
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryCstInfByMachine(String machineCode,String channel) throws ServiceException;
	/**
	 * 
	 * @Title: queryCstInfByCstNoAndAccNo 
	 * @Description: 通过客户号和账号查询客户以及账户信息，用于转账过程中校验
	 * @param legalId	法人Id
	 * @param cstId	客户Id
	 * @param accNo		账号
	 * @param channel	渠道标识
	 * @return
	 * @throws ServiceException    异常信息
	 */
	public List<Map<String,Object>> queryCstInfByCstNoAndAccNo(String legalId,String cstId,String accNo, String channel) throws ServiceException;
}
