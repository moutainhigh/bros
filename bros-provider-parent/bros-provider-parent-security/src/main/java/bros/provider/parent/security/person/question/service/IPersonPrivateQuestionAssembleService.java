package bros.provider.parent.security.person.question.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPersonPrivateQuestionAssembleService 
 * @Description: 个人私密问题组合服务接口
 * @author huangcanhui 
 * @date 2016年10月10日 下午4:58:10 
 * @version 1.0
 */
public interface IPersonPrivateQuestionAssembleService {
	
	/**
	 * 
	 * @Title: savePrivateQuestion 
	 * @Description: 批量保存私密问题信息
	 * @param privateQuestionList 私密问题列表
	 * @param cstPrivateQuestionList 私密问题关联列表
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int savePrivateQuestion(List<Map<String, Object>> privateQuestionList, List<Map<String, Object>> cstPrivateQuestionList) 
			throws ServiceException;

}
