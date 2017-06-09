
package bros.provider.parent.activiti.relation.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IAuthRoleRelService 
 * @Description: 授权用关联角色服务接口
 * @author weiyancheng
 * @date 2016年7月21日 下午2:05:31 
 * @version 1.0 
 */
public interface IAuthRoleRelService {
	/**
	 * @Title: qryAuthRoleByRelId 
	 * @Description: 根据角色关联id查询角色id列表
	 * @param relId
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryAuthRoleByRelId(String relId)throws ServiceException;

}
