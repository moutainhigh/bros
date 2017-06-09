package bros.c.cst.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.cstmanage.facade.service.ICCstManageServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.cst.facade.service.IPCstManageServiceFacade;

/**
 * 
 * @ClassName: CCstManageServiceFacadeImpl 
 * @Description: 渠道客户信息管理服务实现类
 * @author MacPro 
 * @date 2016年9月12日 下午2:08:46 
 * @version 1.0
 */
@Component("ccstManageServiceFacade")
public class CCstManageServiceFacadeImpl implements ICCstManageServiceFacade{
	
	@Autowired
	private IPCstManageServiceFacade cstManageServiceFacade;
	
	/**
	 * 
	 * @Title: queryCstInfo 
	 * @Description: 客户信息查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity queryCstInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return cstManageServiceFacade.queryCstInfo(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: saveCstInfoChannel 
	 * @Description: 渠道客户签约-开户
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity saveCstInfoChannel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return cstManageServiceFacade.saveCstInfo(headMap, bodyMap);
	}

}
