package bros.provider.parent.security.bank.question.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.security.bank.question.service.IBmaPrivateQuestionGroupEntityService;
import bros.provider.parent.security.constants.SecurityErrorCodeConstants;

/**
 * 
 * @ClassName: BmaPrivateQuestionGroupEntityServiceImpl 
 * @Description: 私密问题分组实体服务
 * @author huangcanhui 
 * @date 2016年10月10日 下午2:15:26 
 * @version 1.0
 */
@Repository(value = "bmaPrivateQuestionGroupEntityService")
public class BmaPrivateQuestionGroupEntityServiceImpl implements IBmaPrivateQuestionGroupEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(BmaPrivateQuestionGroupEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 查询个人私密问题分组列表
	 */
	@Override
	public List<Map<String, Object>> queryBmaPrivateQuestionGroupList() throws ServiceException {
		try{
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tprcstprivatequestion.queryBmaPrivateQuestionGroupList");
		}catch(BrosBaseException se){
			logger.error("查询私密问题分组列表异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询私密问题分组列表失败 ", e);
			throw new ServiceException(SecurityErrorCodeConstants.PPSY0002, "查询私密问题分组列表失败", e);
		}
	}
	
}
