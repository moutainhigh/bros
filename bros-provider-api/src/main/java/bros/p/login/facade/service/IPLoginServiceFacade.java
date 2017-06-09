package bros.p.login.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPLoginServiceFacade 
 * @Description: 登录服务提供方对外暴露服务接口
 * @author 何鹏
 * @date 2016年5月12日 上午9:27:28 
 * @version 1.0
 */
public interface IPLoginServiceFacade {
    /**
     * 
     * @Title: manageLogin 
     * @Description: 内管登录
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException
     */
    public ResponseEntity manageLogin(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
    /**
     * 
     * @Title: manageLogout 
     * @Description: 内管登出
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException
     */
    public ResponseEntity manageLogout(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
