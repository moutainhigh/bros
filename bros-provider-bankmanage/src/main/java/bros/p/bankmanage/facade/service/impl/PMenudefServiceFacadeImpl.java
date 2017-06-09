package bros.p.bankmanage.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPMenudefServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.menudef.service.IMenudEntityService;
import bros.provider.parent.bankmanage.menudef.service.IMenudefService;

/** 
 * @ClassName:PMenudefServiceFacadeImpl  
 * @Description:菜单对外接口实现类
 * @author  haojinhui
 * @date 2016年7月6日 上午10:46:03 
 * @version V1.0  
 */
@Component("pmenudefServiceFacade")
public class PMenudefServiceFacadeImpl implements IPMenudefServiceFacade {
	
	/**
	 * 菜单实现类
	 */
	@Autowired
	private IMenudefService menudefService;
	@Autowired
	private IMenudEntityService menudefEntityService;
	/**
	 * 
	 * @Title: addMenudef
	 * @Description: 增加菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000031")
	public ResponseEntity addMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = menudefService.queryMenudef(headMap,bodyMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0009);
		}else{
			menudefService.addMenudef(headMap,bodyMap);
		}
		return entity;
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
	@Validation(value="p0000032")
	public ResponseEntity queryMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		ResponseEntity entity = new ResponseEntity();
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = menudefService.queryMenudef(headMap,bodyMap);
		if(tellerMap != null && tellerMap.size() > 0){			
			entity = new ResponseEntity(tellerMap);				
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0010);
		}
		return entity;
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
	@Validation(value="p0000033")
	public ResponseEntity updateMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = menudefService.queryMenudef(headMap,bodyMap);
		
		if((Integer)tellerMap.get("totalNum") != 0){
			menudefService.updateMenudef(headMap,bodyMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0010);		
		}
		return entity;
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
	@Validation(value="p0000034")
	public ResponseEntity deleteMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();	
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		tellerMap = menudefService.queryMenudef(headMap,bodyMap);
		if((Integer)tellerMap.get("totalNum") != 0){
			menudefService.deleteMenudef(headMap,bodyMap);
		}else{
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0010);		
		}
		return entity;
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
//	@Validation(value="p0000035")
	public ResponseEntity setMenuRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		//校验角色菜单是否已经关联
		List<Map<String,Object>> parmINList = new ArrayList<Map<String,Object>>();
		parmINList = (List<Map<String, Object>>) bodyMap.get("bodyList");
		StringBuffer errorMsg=new StringBuffer();
		String bmrlRoleCode="";
		if(parmINList!=null){
			for (int i = 0; i < parmINList.size(); i++) {
				int totalNum=0;
				String bmrlMenuId=parmINList.get(i).get("bmrlMenuId")==null?"":(String)parmINList.get(i).get("bmrlMenuId");
				String bmrlRoleId=parmINList.get(i).get("bmrlRoleId")==null?"":(String)parmINList.get(i).get("bmrlRoleId");
				totalNum=menudefService.queryMenuRoleNum(bmrlMenuId, bmrlRoleId);
				if(totalNum!=0){
					bmrlRoleCode=(String)parmINList.get(i).get("bmrlRoleCode");
					errorMsg.append(parmINList.get(i).get("bmrlMenuCode")+",");
				}
			}
		}
		if(!bmrlRoleCode.equals("")&&bmrlRoleCode!=null){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0010,"角色"+bmrlRoleCode+"与菜单"+errorMsg.substring(0, errorMsg.length()-1)+"关联已存在！");
		}
		menudefService.addMenuRole(bodyMap);		
		return entity;
	}
	/**
	 * 修改菜单角色关联关系
	 */
	public ResponseEntity updateMenuRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		menudefEntityService.updateMenuRoleEntity(bodyMap);
		return entity;
	}
	/**
	 * 查询菜单角色关联关系
	 */
	public ResponseEntity queryMenuRole(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> responseMap=new HashMap<String, Object>();
		String bmrlRoleId=bodyMap.get("bmrlRoleId")==null?"":bodyMap.get("bmrlRoleId").toString();
		String bmrlMenuId=bodyMap.get("bmrlMenuId")==null?"":bodyMap.get("bmrlMenuId").toString();
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		returnList=menudefService.queryMenuRole(bmrlMenuId, bmrlRoleId);
		if(returnList!=null){
			responseMap.put("returnList", returnList);
			responseMap.put("totalNum", returnList.size()+"");
		}
		ResponseEntity entity=new ResponseEntity(responseMap);
		return entity;
	}
	
}
