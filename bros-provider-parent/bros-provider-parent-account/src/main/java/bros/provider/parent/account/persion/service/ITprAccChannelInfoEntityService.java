package bros.provider.parent.account.persion.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;


/**
 * 
 * @ClassName: ITprAccChannelInfoEntityService 
 * @Description: 个人账户渠道信息实体服务接口
 * @author huangcanhui 
 * @date 2016年10月8日 下午9:41:25 
 * @version 1.0
 */
public interface ITprAccChannelInfoEntityService {
	
	/**
	 * 
	 * @Title: queryTprAccChannelInfoByAccNo 
	 * @Description: 根据法人ID+渠道编号+账号查询个人账户渠道信息
	 * @param legalId 法人ID
	 * @param channel 渠道编号
	 * @param accNo 账号
	 * @return Map<String, Object> 个人账户渠道信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprAccChannelInfoByAccNo(String legalId, String channel, String accNo) throws ServiceException;
}
