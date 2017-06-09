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
import bros.provider.parent.security.person.question.service.ITprCstPrivateQuestionEntityService;

/**
 * 
 * @ClassName: TprCstPrivateQuestionEntityServiceImpl 
 * @Description: 个人客户与私密问题关系实体服务实现类
 * @author huangcanhui 
 * @date 2016年10月10日 上午11:36:21 
 * @version 1.0
 */
@Repository(value = "tprCstPrivateQuestionEntityService")
public class TprCstPrivateQuestionEntityServiceImpl implements ITprCstPrivateQuestionEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(TprCstPrivateQuestionEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 根据主键查询指定私密问题答案
	 */
	@Override
	public Map<String, Object> queryTprCstPrivateQuestionByKey(String pqId, String cstId) throws ServiceException {
		try{
			Map<String, Object> paramIN = new HashMap<String, Object>();
			paramIN.put("pqId", pqId);
			paramIN.put("cstId", cstId);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstprivatequestion.queryTprCstPrivateQuestionByKey", paramIN);
		}catch(BrosBaseException se){
			logger.error("查询私密问题答案异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询私密问题答案失败 ", e);
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0008, "查询私密问题答案失败", e);
		}
	}
	
	/**
	 * 根据客户标识查询私密问题关联列表
	 */
	@Override
	public List<Map<String, Object>> queryTprCstPrivateQuestionListByCstId(String cstId) throws ServiceException {
		try{
			Map<String, Object> paramIN = new HashMap<String, Object>();
			paramIN.put("cstId", cstId);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tprcstprivatequestion.queryTprCstPrivateQuestionListByCstId", paramIN);
		}catch(BrosBaseException se){
			logger.error("查询私密问题关联列表异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询私密问题关联列表失败 ", e);
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0003, "查询私密问题关联列表失败", e);
		}
	}
	
	/**
	 * 批量更新个人客户与私密问题关系列表
	 */
	@Override
	public int insertBatchTprCstPrivateQuestion(List<Map<String, Object>> cstQrivateQuestionList) throws ServiceException {
		try{
			return myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.tprcstprivatequestion.insertTprCstPrivateQuestion", cstQrivateQuestionList);
		}catch(BrosBaseException se){
			logger.error("批量更新个人客户与私密问题关系列表异常 ", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("批量更新个人客户与私密问题关系列表失败 ", e);
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0006, "批量更新个人客户与私密问题关系列表失败", e);
		}
	}
	
}
