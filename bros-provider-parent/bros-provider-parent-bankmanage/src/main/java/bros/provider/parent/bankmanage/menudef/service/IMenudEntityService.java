package bros.provider.parent.bankmanage.menudef.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:IMenudEntityService 
 * @Description:菜单关联组合接口
 * @author  haojinhui
 * @date 2016年7月6日 上午10:57:07 
 * @version V1.0  
 */
public interface IMenudEntityService {

	/**
	 * 
	 * @Title: updateMenuRoleEntity
	 * @Description: 修改菜单角色关联
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public void updateMenuRoleEntity(Map<String, Object> bodyMap)throws ServiceException;
	

}
