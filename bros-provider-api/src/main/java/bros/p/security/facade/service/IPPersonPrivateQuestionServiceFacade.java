package bros.p.security.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPPersonPrivateQuestionServiceFacade 
 * @Description: 个人私密问题服务提供方对外暴露服务接口
 * @author huangcanhui 
 * @date 2016年10月10日 下午9:47:42 
 * @version 1.0
 */
public interface IPPersonPrivateQuestionServiceFacade {
   
    /**
     * 
     * @Title: queryPrivateQuestionGroupList 
     * @Description: 查询个人私密问题分组列表
     * @param headMap 报文头数据集合
     * @param bodyMap 报文体数据集合
     * @return ResponseEntity 返回结果对象
     * @throws ServiceException
     */
    public ResponseEntity queryPrivateQuestionGroupList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
    
    /**
     * 
     * @Title: filterPrivateQuestion 
     * @Description: 筛选个人私密问题
     * @param headMap 报文头数据集合
     * @param bodyMap 报文体数据集合
     * @return ResponseEntity 返回结果对象
     * @throws ServiceException
     */
    public ResponseEntity filterPrivateQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
    
    /**
     * 
     * @Title: queryPrivateQuestionList 
     * @Description: 根据分组编号查询个人私密问题列表
     * @param headMap 报文头数据集合
     * @param bodyMap 报文体数据集合
     * @return ResponseEntity 返回结果对象
     * @throws ServiceException
     */
    public ResponseEntity queryPrivateQuestionList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
   
}
