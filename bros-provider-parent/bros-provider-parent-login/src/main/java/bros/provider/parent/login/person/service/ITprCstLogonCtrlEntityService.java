package bros.provider.parent.login.person.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITprCstLogonCtrlEntityService 
 * @Description: 个人客户登录控制信息实体服务接口
 * @author huangcanhui 
 * @date 2016年10月9日 上午10:19:42 
 * @version 1.0
 */
public interface ITprCstLogonCtrlEntityService {
	
	/**
	 * 
	 * @Title: queryTprCstLogonCtrlByCstIdAndChannel 
	 * @Description: 根据客户标识+渠道编号查询个人客户登录控制信息
	 * @param cstId 客户标识
	 * @param channel 渠道编号
	 * @return Map<String, Object> 个人客户登录控制信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstLogonCtrlByCstIdAndChannel(String cstId, String channel) throws ServiceException;
	
	/**
	 * 
	 * @Title: insertTprCstLogonCtrl 
	 * @Description: 保存个人客户登录控制信息
	 * @param id 记录ID
	 * @param cstId 客户标识
	 * @param channel 渠道编号
	 * @param failToday 当日连续登录失败次数
	 * @param failSum 总计连续登录失败次数
	 * @param count 成功登录次数
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int insertTprCstLogonCtrl(String id, String cstId, String channel, int failToday, int failSum, int count) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTprCstLogonCtrl 
	 * @Description: 更新个人客户登录控制信息
	 * @param cstId 客户标识
	 * @param channel 渠道编号
	 * @param failToday 当日连续登录失败次数
	 * @param failSum 总计连续登录失败次数
	 * @param firstLogon 首次登录时间
	 * @param lastLogon 最近成功登录时间
	 * @param freezeDate 冻结日期
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTprCstLogonCtrl(String cstId, String channel, int failToday, int failSum, String firstLogon, 
			String lastLogon, String freezeDate) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTprCstLogonCtrlFail 
	 * @Description: 密码验证失败，更新个人客户登录控制信息
	 * @param cstId 客户标识
	 * @param channel 渠道编号
	 * @param failToday 当日连续登录失败次数
	 * @param lastFail 最近失败登录时间
	 * @param freezeDate 冻结日期
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTprCstLogonCtrlFail(String cstId, String channel, int failToday, String lastFail, String freezeDate) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTprCstLogonCtrlStopDate 
	 * @Description: 更新个人客户临时停用日期
	 * @param cstId 客户标识
	 * @param channel 渠道编号
	 * @param stopStart 临时停用开始日期
	 * @param stopEnd 临时停用结束日期
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTprCstLogonCtrlStopDate(String cstId, String channel, String stopStart, String stopEnd) throws ServiceException;
	
}
