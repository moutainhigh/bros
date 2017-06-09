package bros.provider.parent.security.person.question.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.security.constants.SecurityErrorCodeConstants;
import bros.provider.parent.security.person.question.service.ITprPrivateQuestionEntityService;

/**
 * 
 * @ClassName: TprCstPrivateQuestionEntityServiceImpl 
 * @Description: 个人私密问题实体服务实现类
 * @author huangcanhui 
 * @date 2016年10月10日 上午11:36:21 
 * @version 1.0
 */
@Repository(value = "tprPrivateQuestionEntityService")
public class TprPrivateQuestionEntityServiceImpl implements ITprPrivateQuestionEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(TprPrivateQuestionEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 根据问题编号查询私密问题列表
	 */
	@Override
	public List<Map<String, Object>> queryTprPrivateQuestionListByPqId(List<String> pqIdList) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			String[] pqIdArray = pqIdList.toArray(new String[pqIdList.size()]);
			parmIN.put("pqIdArray", pqIdArray);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tprprivatequestion.queryTprPrivateQuestionListByPqId", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询私密问题列表异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询私密问题列表失败 ", e);
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0004, "查询私密问题列表失败", e);
		}
	}
	
	/**
	 * 根据分组编号+语种查询私密问题列表
	 */
	@Override
	public List<Map<String, Object>> queryTprPrivateQuestionListByPqGroupId(String pqGroupId, String languag) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("pqGroupId", pqGroupId);
			parmIN.put("languag", languag);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tprprivatequestion.queryTprPrivateQuestionListByPqGroupId", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询私密问题列表异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询私密问题列表失败 ", e);
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0004, "查询私密问题列表失败", e);
		}
	}
	
	/**
	 * 批量更新私密问题列表
	 */
	@Override
	public int insertBatchTprPrivateQuestion(List<Map<String, Object>> qrivateQuestionList) throws ServiceException {
		try{
			return myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.tprprivatequestion.insertTprPrivateQuestion", qrivateQuestionList);
		}catch(BrosBaseException se){
			logger.error("批量更新私密问题列表异常 ", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("批量更新私密问题列表失败 ", e);
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0005, "批量更新私密问题列表失败", e);
		}
	}
	
}
