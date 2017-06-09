package bros.provider.parent.bankmanage.menudef.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.menudef.service.IMenudEntityService;
import bros.provider.parent.bankmanage.menudef.service.IMenudefService;
import bros.provider.parent.bankmanage.teller.service.impl.TellerServiceImpl;

/** 
 * @ClassName:MenudefServiceImpl  
 * @Description:菜单管理接口
 * @author  haojinhui
 * @date 2016年7月6日 上午10:57:36 
 * @version V1.0  
 */
@Repository(value = "menudefEntityService")
public class MenudefEntityServiceImpl implements IMenudEntityService{

	private static final Logger logger = LoggerFactory.getLogger(MenudefEntityServiceImpl.class);

	@Autowired
	private IMenudefService menudefService;

	/**
	 * 修改菜单角色关联
	 */
	public void updateMenuRoleEntity(Map<String, Object> bodyMap)
			throws ServiceException {
		int totalNum=0;
		try {
			List<Map<String,Object>> parmINList = new ArrayList<Map<String,Object>>();
			String bmrlRoleId=bodyMap.get("bmrlRoleId")==null?"":bodyMap.get("bmrlRoleId").toString();
			//查询是否存在关联信息
			totalNum=menudefService.queryMenuRoleNum("", bmrlRoleId);
			if(totalNum!=0){
				menudefService.deleteMenuRole("", bmrlRoleId);
			}
			parmINList = (List<Map<String, Object>>) bodyMap.get("bodyList");
			if(parmINList!=null&&parmINList.size()>0){//添加关联信息
				menudefService.addMenuRole(bodyMap);
			}
		} catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "updateMenuRoleEntity", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "updateMenuRoleEntity", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改菜单角色关联失败", ex);
		}
	}
	
	
}
