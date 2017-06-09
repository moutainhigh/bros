package bros.c.security.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICPersonSecurityServiceFacade 
 * @Description: 个人安全设置服务消费方接口
 * @author huangcanhui 
 * @date 2016年10月10日 下午5:54:55 
 * @version 1.0
 */
public interface ICPersonSecurityServiceFacade {
   
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
