package bros.provider.parent.customer.person.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITprCstChannelInfEntityService 
 * @Description: 个人客户渠道信息实体服务接口
 * @author huangcanhui 
 * @date 2016年10月8日 下午2:41:47 
 * @version 1.0
 */
public interface ITprCstChannelInfEntityService {
	
	/**
	 * 
	 * @Title: queryTprCstChannelInfByAlias 
	 * @Description: 根据法人ID+渠道编号+别名查询个人客户渠道信息
	 * @param legalId 法人ID
	 * @param channel 渠道编号
	 * @param alias 别名
	 * @return Map<String, Object> 个人客户渠道信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstChannelInfByAlias(String legalId, String channel, String alias) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTprCstChannelInfByMachineCode 
	 * @Description: 根据法人ID+渠道编号+手机号查询个人客户渠道信息
	 * @param legalId 法人ID
	 * @param channel 渠道编号
	 * @param machineCode 手机号
	 * @return Map<String, Object> 个人客户渠道信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstChannelInfByMachineCode(String legalId, String channel, String machineCode) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTprCstChannelInfByCstIdAndChannel 
	 * @Description: 根据客户标识查询个人客户渠道信息
	 * @param cstId 客户标识
	 * @param channel 渠道编号
	 * @return Map<String, Object> 个人客户渠道信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstChannelInfByCstIdAndChannel(String cstId, String channel) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTprCstChannelSttByCstIdAndChannel 
	 * @Description: 更新个人客户渠道状态
	 * @param cstId 客户标识
	 * @param channel 渠道编号
	 * @param stt 客户渠道状态
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTprCstChannelSttByCstIdAndChannel(String cstId, String channel, String stt) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAliasIsExist 
	 * @Description: 验证个人客户昵称
	 * @param alias 客户昵称/别名
	 * @return boolean 结果 true-存在; false-不存在
	 * @throws ServiceException
	 */
	public boolean checkAliasIsExist(String alias) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTprCstChannelAliasAndPwdByCstIdAndChannel 
	 * @Description: 更新个人客户昵称和登录密码
	 * @param cstId 客户唯一标识
	 * @param channel 渠道编号
	 * @param alias 客户昵称/别名
	 * @param newPassword 新登录密码
	 * @param oldPassword 原登录密码
	 * @param pwdTime 密码设置时间
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTprCstChannelAliasAndPwdByCstIdAndChannel(String cstId, String channel, String alias, 
			String newPassword, String oldPassword, String pwdTime) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTprCstChannelPwdByKey 
	 * @Description: 更新个人客户登录密码
	 * @param cstId 客户唯一标识
	 * @param channel 渠道编号
	 * @param newPassword 新登录密码
	 * @param oldPassword 原登录密码
	 * @param pwdTime 密码设置时间
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTprCstChannelPwdByKey(String cstId, String channel, String newPassword, String oldPassword, 
			String pwdTime) throws ServiceException;
	
}
