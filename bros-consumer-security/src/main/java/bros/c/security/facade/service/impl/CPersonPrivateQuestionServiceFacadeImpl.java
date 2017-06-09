package bros.c.security.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.security.facade.service.ICPersonPrivateQuestionServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.security.facade.service.IPPersonPrivateQuestionServiceFacade;

/**
 * 
 * @ClassName: CPersonPrivateQuestionServiceFacadeImpl 
 * @Description: 个人私密问题服务消费方实现类
 * @author huangcanhui 
 * @date 2016年10月10日 下午11:25:24 
 * @version 1.0
 */
@Component("cPersonPrivateQuestionServiceFacade")
public class CPersonPrivateQuestionServiceFacadeImpl implements ICPersonPrivateQuestionServiceFacade {
	
	/**
	 * 个人私密问题服务
	 */
	@Autowired
	private IPPersonPrivateQuestionServiceFacade pPersonPrivateQuestionServiceFacade;
	
	/**
	 * 查询个人私密问题分组列表
	 */
	@Override
	public ResponseEntity queryPrivateQuestionGroupList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pPersonPrivateQuestionServiceFacade.queryPrivateQuestionGroupList(headMap, bodyMap);
	}
	
	/**
     * 筛选个人私密问题
     */
	@Override
    public ResponseEntity filterPrivateQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
    	return pPersonPrivateQuestionServiceFacade.filterPrivateQuestion(headMap, bodyMap);
    }
    
    /**
     * 根据分组编号查询个人私密问题列表
     */
	@Override
    public ResponseEntity queryPrivateQuestionList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
    	return pPersonPrivateQuestionServiceFacade.queryPrivateQuestionList(headMap, bodyMap);
    }
	
}
