package bros.provider.parent.security.person.question.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.exception.ServiceException;
import bros.provider.parent.security.constants.SecurityErrorCodeConstants;
import bros.provider.parent.security.person.question.service.IPersonPrivateQuestionAssembleService;
import bros.provider.parent.security.person.question.service.ITprCstPrivateQuestionEntityService;
import bros.provider.parent.security.person.question.service.ITprPrivateQuestionEntityService;

/**
 * 
 * @ClassName: PersonPrivateQuestionAssembleServiceImpl 
 * @Description: 个人私密问题组合服务实现类
 * @author huangcanhui 
 * @date 2016年10月10日 下午5:02:23 
 * @version 1.0
 */
@Component(value="personPrivateQuestionAssembleService")
public class PersonPrivateQuestionAssembleServiceImpl implements IPersonPrivateQuestionAssembleService {

	/**
	 * 个人私密问题实体服务
	 */
	@Autowired
	private ITprPrivateQuestionEntityService tprPrivateQuestionEntityService;
	
	/**
	 * 个人客户与私密问题关系实体服务
	 */
	@Autowired
	private ITprCstPrivateQuestionEntityService tprCstPrivateQuestionEntityService;
	
	/**
	 * 批量保存私密问题信息
	 */
	@Override
	public int savePrivateQuestion(List<Map<String, Object>> privateQuestionList, List<Map<String, Object>> cstPrivateQuestionList) 
			throws ServiceException
	{
		try{
			int resultNum = 0;
			//批量更新私密问题列表
			if(null!=privateQuestionList && privateQuestionList.size()>0){
				resultNum = tprPrivateQuestionEntityService.insertBatchTprPrivateQuestion(privateQuestionList);
				
				if(resultNum==0){
					throw new ServiceException(SecurityErrorCodeConstants.PPSY0005, "批量更新私密问题列表异常");
				}
			}
			
			resultNum = tprCstPrivateQuestionEntityService.insertBatchTprCstPrivateQuestion(cstPrivateQuestionList);
			if(resultNum==0){
				throw new ServiceException(SecurityErrorCodeConstants.PPSY0006, "批量更新个人客户与私密问题关系列表异常");
			}
			
			return resultNum;
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0007, "批量保存私密问题信息异常", e);
		}
	}

}
