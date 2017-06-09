package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICTellerServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPTellerServiceFacade;

/** 
 * @ClassName: PTellerServiceFacadeImpl  
 * @Description:柜员系统对外接口实现类
 * @author  haojinhui
 * @date 2016年6月28日 上午9:45:58 
 * @version V1.0  
 */

@Component("ctellerServiceFacade")
public class CTellerServiceFacadeImpl implements ICTellerServiceFacade {
	
	/**
	 * 柜员系统实现类
	 */
	@Autowired
	private IPTellerServiceFacade ptellerServiceFacade;
	
	/**
	 * 
	 * @Title: addTeller
	 * @Description: 增加柜员
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000037")
	public ResponseEntity addTeller(Map<String,Object> headMap,Map<String, Object> contextMap) throws ServiceException {
		
		return ptellerServiceFacade.addTeller(headMap, contextMap);
	}
	
	/**
	 * 
	 * @Title: queryTellerById
	 * @Description: 柜员信息查询
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000038")
	public ResponseEntity queryTellerById(Map<String,Object> headMap,Map<String, Object> contextMap) throws ServiceException {
		
		return ptellerServiceFacade.queryTellerById(headMap, contextMap);
	}
	
	/**
	 * 
	 * @Title: updateTeller
	 * @Description: 柜员信息修改
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000039")
	public ResponseEntity updateTeller(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {

		return ptellerServiceFacade.updateTeller(headMap, contextMap);
	}
	
	/**
	 * 
	 * @Title: deleteTeller
	 * @Description: 删除柜员信息
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000040")
	public ResponseEntity deleteTeller(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {

		return ptellerServiceFacade.deleteTeller(headMap, contextMap);
	}
	
	/**
	 * 
	 * @Title: tellerLogin
	 * @Description: 柜员登录
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000041")
	public ResponseEntity updateTellerLogin(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {
	
		return ptellerServiceFacade.tellerLogin(headMap, contextMap);
	}
	
	/**
	 * 
	 * @Title: tellerLogout
	 * @Description: 柜员签退
	 * @param headMap  头信息
	 * @param contextMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000042")
	public ResponseEntity updateTellerLogout(Map<String, Object> headMap,
			Map<String, Object> contextMap) throws ServiceException {

		return ptellerServiceFacade.tellerLogout(headMap, contextMap);
	}
	/**
	 * 
	 * @Title: queryTellerRole
	 * @Description: 查询柜员角色
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000120")
	public ResponseEntity queryTellerRoleMenu(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {

		return ptellerServiceFacade.queryTellerRoleMenu(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryMenudefPro
	 * @Description: 根据法人id，菜单性质，系统标识查询货架编码
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000121")
	public ResponseEntity queryMenudefPro(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return ptellerServiceFacade.queryMenudefPro(headMap, bodyMap);
	}

}
