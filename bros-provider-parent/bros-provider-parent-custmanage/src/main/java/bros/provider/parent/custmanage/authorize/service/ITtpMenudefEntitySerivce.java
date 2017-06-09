package bros.provider.parent.custmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITtpMenudefEntitySerivce 
 * @Description: 菜单综合接口实现
 * @author pengxiangnan 
 * @date 2016年7月27日 下午6:51:55 
 * @version 1.0
 */
public interface ITtpMenudefEntitySerivce {
	/**
	 * 
	 * @Title: queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData
	 * @Description: 关联信息查询
	 * @param channel  渠道唯一标识
	 * @param legalId  法人唯一标识
	 * @param cstNo  客户号
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData(String channel, String legalId, String cstNo) throws ServiceException;
	/**
	 * 
	 * @Title: queryTTBsnDefAndMenuByAll 
	 * @Description: 查询业务功能及菜单信息
	 * @param channel 渠道编号
	 * @param legalId 法人id
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTTBsnDefAndMenuByAll(String channel,
			String legalId) throws ServiceException;
	/**
	 * 
	 * @Title: queryTTBsbAuth 
	 * @Description: 查询模型分配信息
	 * @param cstno
	 * @param legalId
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTTBsbAuth(String cstNo, String legalId)
			throws ServiceException;
}
