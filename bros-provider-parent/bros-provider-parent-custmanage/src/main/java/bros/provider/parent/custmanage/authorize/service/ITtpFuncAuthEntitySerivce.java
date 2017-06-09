package bros.provider.parent.custmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITtpFuncAuthEntitySerivce 
 * @Description: 开通功能与授权模型关系实体服务接口
 * @author huangcanhui 
 * @date 2016年7月20日 下午6:51:55 
 * @version 1.0
 */
public interface ITtpFuncAuthEntitySerivce {
	
	/**
	 * 
	 * @Title: queryTtpFuncAuthListByCstNoAndLegalId 
	 * @Description: 根据客户号与法人唯一标识查找功能关系列表
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @return List<Map<String, Object>> 功能与授权模型关系列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpFuncAuthListByCstNoAndLegalId(String legalId, String cstNo) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateTtpFuncAuth 
	 * @Description: 修改开通功能与授权模型关系
	 * @param funcAuthList 开通功能与授权模型关系列表
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int updateTtpFuncAuth(List<Map<String, Object>> funcAuthList) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpFuncAuthListByCstNoAndLegalId 
	 * @Description: 根据客户号与法人唯一标识查找功能关系列表
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @return List<Map<String, Object>> 功能与授权模型关系列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpFuncAuthList(String legalId, String cstNo) throws ServiceException;
	/**
	 * 
	 * @Title: insertTtpFunAuth 
	 * @Description: 添加授权配置信息
	 * @param funcAuthList
	 * @return
	 * @throws ServiceException
	 */
	public int insertTtpFunAuth(List<Map<String, Object>> funcAuthList)
			throws ServiceException;
	/**
	 * 
	 * @Title: queryTtpFunAuthNum 
	 * @Description: 授权模型配置信息笔数
	 * @param cstNo 客户号
	 * @param legalId 法人id
	 * @param bsnCode 业务功能id
	 * @return
	 * @throws ServiceException
	 */
	public int queryTtpFunAuthNum(String cstNo, String legalId, String bsnCode)
			throws ServiceException;
}
