package bros.provider.parent.custmanage.limit.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * @ClassName: ICheckCstLimitService 
 * @Description: TODO(统一限额检查服务类接口,包括：本行本人账户限额和本行他人账户限额) 
 * @author gaoyongjing
 * @date 2016-10-11 下午04:42:42 
 *
 */
public interface ICheckCstLimitService {
	
	/**
	 * 
	 * @Title: checkCstLimit 
	 * @Description: TODO(限额检查) 
	 * @param cstNo 客户号
	 * @param channel 渠道标志
	 * @param bizType 业务类型标志
	 * @param safeTool 安全工具标志
	 * @param payAccNo 付款账号
	 * @param rcvAccNo 收款账号
	 * @param tansMoney 转账金额
	 * @param protocolNo 协议号(协议账户的协议号,检查/更新本行他人协议限额时使用) --!!!
	 * @param checkLocalProtocolResultObject 协议检查结果VO(检查/更新本行他人协议限额成功后,得到该vo,供限额回滚时使用) --!!!
	 * @return void
	 * @throws ServiceException 检查/更新限额失败，抛出异常
	 * 
	 */
	public  boolean checkCstLimitBefore(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException;
}
