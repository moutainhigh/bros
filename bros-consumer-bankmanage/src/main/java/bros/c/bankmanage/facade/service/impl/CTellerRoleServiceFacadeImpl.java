package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICTellerRoleServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPTellerRoleServiceFacade;

/** 
 * @ClassName: PTellerRoleServiceFacadeImpl  
 * @Description:柜员角色对外接口实现类
 * @author  haojinhui
 * @date 2016年6月30日 下午3:34:11 
 * @version V1.0  
 */
@Component("ctellerRoleServiceFacade")
public class CTellerRoleServiceFacadeImpl implements ICTellerRoleServiceFacade {
	
	/**
	 * 柜员角色实现类
	 */
	@Autowired
	private IPTellerRoleServiceFacade ptellerRoleServiceFacade;


	/**
	 * 
	 * @Title: addTellerRole
	 * @Description: 增加柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000043")
	public ResponseEntity addTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		
		return ptellerRoleServiceFacade.addTellerRole(headMap, contextMap);
	}

	/**
	 * 
	 * @Title: updateTellerRole
	 * @Description: 更改柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000044")
	public ResponseEntity updateTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		
		return ptellerRoleServiceFacade.updateTellerRole(headMap, contextMap);
	}

	/**
	 * 
	 * @Title: deleteTellerRole
	 * @Description: 删除柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000045")
	public ResponseEntity deleteTellerRole(Map<String, Object> headMap,
		Map<String, Object> contextMap) throws ServiceException {
		return ptellerRoleServiceFacade.deleteTellerRole(headMap, contextMap);
	}
	
	/**
	 * 
	 * @Title: setTellerRole
	 * @Description: 设置柜员角色关联关系
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000046")
	public ResponseEntity setTellerRole(Map<String, Object> headMap,
		Map<String, Object> contextMap) throws ServiceException {

		return ptellerRoleServiceFacade.setTellerRole(headMap, contextMap);
	}

	/**
	 * 
	 * @Title: queryTellerRole
	 * @Description: 查询柜员角色
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000047")
	public ResponseEntity queryTellerRole(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
		
		return ptellerRoleServiceFacade.queryTellerRole(headMap, contextMap);
	}
	/**
	 * 
	 * @Title: updateSetTellerRole
	 * @Description: 更新柜员角色关联关系
	 * @param headMap  头信息
	 * @param burlTrllerno  柜员号
	 * @param burlRoleid 角色编号
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000122")
	public ResponseEntity updateSetTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {

		return ptellerRoleServiceFacade.updateSetTellerRole(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: querySetTellerRole
	 * @Description: 查询柜员已分配角色信息
	 * @param headMap  头信息
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000123")
	public ResponseEntity querySetTellerRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
	
		return ptellerRoleServiceFacade.querySetTellerRole(headMap, bodyMap);
	}
}
