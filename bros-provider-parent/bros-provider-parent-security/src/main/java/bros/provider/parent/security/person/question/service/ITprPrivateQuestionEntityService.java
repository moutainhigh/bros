package bros.provider.parent.security.person.question.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITprPrivateQuestionEntityService 
 * @Description: 个人私密问题实体服务接口
 * @author huangcanhui 
 * @date 2016年10月10日 下午3:09:37 
 * @version 1.0
 */
public interface ITprPrivateQuestionEntityService {
	
	/**
	 * 
	 * @Title: queryTprPrivateQuestionListByPqId 
	 * @Description: 根据问题编号查询私密问题列表
	 * @param pqIdList 问题编号列表
	 * @return List<Map<String, Object>> 私密问题列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTprPrivateQuestionListByPqId(List<String> pqIdList) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTprPrivateQuestionListByPqGroupId 
	 * @Description: 根据分组编号+语种查询私密问题列表
	 * @param pqGroupId 分组编号
	 * @param languag 语种
	 * @return List<Map<String, Object>> 私密问题列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTprPrivateQuestionListByPqGroupId(String pqGroupId, String languag) throws ServiceException;
	
	/**
	 * 
	 * @Title: insertBatchTprPrivateQuestion 
	 * @Description: 批量更新私密问题列表
	 * @param qrivateQuestionList 私密问题列表
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int insertBatchTprPrivateQuestion(List<Map<String, Object>> qrivateQuestionList) throws ServiceException;

}
