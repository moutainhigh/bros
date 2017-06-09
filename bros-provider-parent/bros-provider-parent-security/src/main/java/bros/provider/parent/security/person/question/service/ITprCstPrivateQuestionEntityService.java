package bros.provider.parent.security.person.question.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ITprCstPrivateQuestionEntityService 
 * @Description: 个人客户与私密问题关系实体服务接口
 * @author huangcanhui 
 * @date 2016年10月10日 上午11:30:58 
 * @version 1.0
 */
public interface ITprCstPrivateQuestionEntityService {
	
	/**
	 * 
	 * @Title: queryTprCstPrivateQuestionByKey 
	 * @Description: 根据主键查询指定私密问题答案
	 * @param pqId 问题编号
	 * @param cstId 客户标识
	 * @return Map<String, Object> 问题答案
	 * @throws ServiceException
	 */
	public Map<String, Object> queryTprCstPrivateQuestionByKey(String pqId, String cstId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTprCstPrivateQuestionListByCstId 
	 * @Description: 根据客户标识查询私密问题关联列表
	 * @param cstId 客户标识
	 * @return List<Map<String, Object>> 私密问题关联列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTprCstPrivateQuestionListByCstId(String cstId) throws ServiceException;
	
	/**
	 * 
	 * @Title: insertBatchTprCstPrivateQuestion 
	 * @Description: 批量更新个人客户与私密问题关系列表
	 * @param cstQrivateQuestionList 个人客户与私密问题关系列表
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int insertBatchTprCstPrivateQuestion(List<Map<String, Object>> cstQrivateQuestionList) throws ServiceException;

}
