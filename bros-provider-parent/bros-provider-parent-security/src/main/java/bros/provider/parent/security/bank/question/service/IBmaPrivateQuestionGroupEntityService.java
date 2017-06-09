package bros.provider.parent.security.bank.question.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaPrivateQuestionGroupEntityService 
 * @Description: 私密问题分组实体服务接口
 * @author huangcanhui 
 * @date 2016年10月10日 下午2:11:18 
 * @version 1.0
 */
public interface IBmaPrivateQuestionGroupEntityService {
	
	/**
	 * 
	 * @Title: queryBmaPrivateQuestionGroupList 
	 * @Description: 查询个人私密问题分组列表
	 * @return List<Map<String, Object>> 个人私密问题分组列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaPrivateQuestionGroupList() throws ServiceException;

}
