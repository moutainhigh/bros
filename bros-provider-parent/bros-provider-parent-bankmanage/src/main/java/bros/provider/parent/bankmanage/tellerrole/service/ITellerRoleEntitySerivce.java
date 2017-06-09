package bros.provider.parent.bankmanage.tellerrole.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITellerRoleEntitySerivce 
 * @Description: 柜员角色实体服务接口
 * @author huangcanhui 
 * @date 2016年7月19日 上午10:44:10 
 * @version 1.0
 */
public interface ITellerRoleEntitySerivce {
	
	/**
	 * 
	 * @Title: queryBmaRoleByRoleIdList 
	 * @Description: 根据角色编号列表查询角色信息列表
	 * @param roleIdList 角色编号列表
	 * @return List<Map<String, Object>> 角色信息列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaRoleByRoleIdList(List<String> roleIdList) throws ServiceException;
}
