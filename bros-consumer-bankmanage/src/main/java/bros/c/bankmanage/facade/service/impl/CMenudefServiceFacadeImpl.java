package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICMenudefServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPMenudefServiceFacade;

/** 
 * @ClassName:PMenudefServiceFacadeImpl  
 * @Description:菜单对外接口实现类
 * @author  haojinhui
 * @date 2016年7月6日 上午10:46:03 
 * @version V1.0  
 */
@Component("cmenudefServiceFacade")
public class CMenudefServiceFacadeImpl implements ICMenudefServiceFacade {
	/**
	 * 菜单实现类
	 */
	@Autowired
	private IPMenudefServiceFacade pmenudefServiceFacade;
	/**
	 * 
	 * @Title: addMenudef
	 * @Description: 增加菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000031")
	public ResponseEntity addMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return pmenudefServiceFacade.addMenudef(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryMenudef
	 * @Description: 菜单查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000032")
	public ResponseEntity queryMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return pmenudefServiceFacade.queryMenudef(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: updateMenudef
	 * @Description: 菜单修改
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000033")
	public ResponseEntity updateMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {

		return pmenudefServiceFacade.updateMenudef(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: deleteMenudef
	 * @Description: 删除菜单信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000034")
	public ResponseEntity deleteMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {

		return pmenudefServiceFacade.deleteMenudef(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: setMenuRole
	 * @Description: 设置菜单角色关联关系
	 * @param headMap  头信息
	 * @param bodyMap  报文体
	 * @return
	 * @throws ServiceException
	 */
//	@Validation(value="c0000035")
	public ResponseEntity setMenuRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {

		return pmenudefServiceFacade.setMenuRole(headMap, bodyMap);
	}
	/**
	 * 修改菜单角色关联关系
	 */
	public ResponseEntity updateMenuRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pmenudefServiceFacade.updateMenuRole(headMap, bodyMap);
	}
	/**
	 * 查询菜单角色关联关系
	 */
	public ResponseEntity queryMenuRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pmenudefServiceFacade.queryMenuRole(headMap, bodyMap);
	}
	
}
