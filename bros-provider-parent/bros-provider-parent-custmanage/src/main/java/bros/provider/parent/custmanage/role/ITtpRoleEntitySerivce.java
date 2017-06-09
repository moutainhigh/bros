package bros.provider.parent.custmanage.role;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITtpRoleEntitySerivce 
 * @Description: 操作员角色实体服务接口
 * @author huangcanhui 
 * @date 2016年7月20日 下午6:31:42 
 * @version 1.0
 */
public interface ITtpRoleEntitySerivce {
	
	/**
	 * 
	 * @Title: queryTtpRoleByRoleIdList 
	 * @Description: 根据角色编号列表查询角色信息列表
	 * @param roleIdList 角色编号列表
	 * @return List<Map<String, Object>> 角色信息列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpRoleByRoleIdList(List<String> roleIdList) throws ServiceException;
}
