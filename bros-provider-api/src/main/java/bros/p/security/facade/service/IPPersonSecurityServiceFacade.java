package bros.p.security.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPPersonSecurityServiceFacade 
 * @Description: 个人安全设置服务提供方对外暴露服务接口
 * @author huangcanhui 
 * @date 2016年10月10日 下午3:38:45 
 * @version 1.0
 */
public interface IPPersonSecurityServiceFacade {
   
    /**
     * 
     * @Title: modifyPassword 
     * @Description: 修改登录密码
     * @param headMap 报文头数据集合
     * @param bodyMap 报文体数据集合
     * @return ResponseEntity 返回结果对象
     * @throws ServiceException
     */
    public ResponseEntity modifyPassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
    
    /**
     * 
     * @Title: resetPrivateQuestion 
     * @Description: 重置私密问题
     * @param headMap 报文头数据集合
     * @param bodyMap 报文体数据集合
     * @return ResponseEntity 返回结果对象
     * @throws ServiceException
     */
    public ResponseEntity resetPrivateQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
