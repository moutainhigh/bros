package bros.provider.parent.bankmanage.customer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.customer.service.ICstLeaveWordTranService;

/**
 * 
 * @ClassName: CstLeaveWordsTranServiceImpl 
 * @Description: 客户之声接口实现
 * @author lichen 
 * @date 2016年10月9日 上午9:52:57 
 * @version 1.0
 */
@Repository(value = "cstLeaveWordTranService")
public class CstLeaveWordsTranServiceImpl implements ICstLeaveWordTranService{
	
	
	private static final Logger logger = LoggerFactory.getLogger(CstLeaveWordsTranServiceImpl.class);
	
	/**
	 * SQL语句命名空间
	 */
    private static final String CSTLEAVEWORD_SQL_NAMESPACE = "mybatis.mapper.single.table.cstleavewords.";
	
	/**
	 * 数据库Dao
	 */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	@Override
	public int selectTotalNum(Integer pageNo, Integer pageSize,Map<String, Object> bodyMap) throws ServiceException {
		int totalNo = 0;
		try{
			//查询总记录数
			totalNo = myBatisDaoSysDao.selectTotalNum(CSTLEAVEWORD_SQL_NAMESPACE + "queryPbcstLeaveWordsTotalNo",bodyMap);
		}catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0226,"客户之声查询总记录数失败", e);
		}
		return totalNo;
	}
	
	/**
	 * 
	 * @Title: queryTranCstleaveWords 
	 * @Description: 客户之声查询
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public List<Map<String, Object>> queryTranCstleaveWords(Integer pageNo,Integer pageSize,Map<String, Object> bodyMap) throws ServiceException {
		try{
			//查询信息
			return myBatisDaoSysDao.selectListPage(CSTLEAVEWORD_SQL_NAMESPACE + "queryPbcstLeaveWords",bodyMap, pageNo, pageSize);
		}catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0224,"客户之声查询失败", e);
		}
	}
	
	/**
	 * 
	 * @Title: addTranCstleaveWords 
	 * @Description: 客户之声添加留言
	 * @param bodyMap 入参
	 * @throws ServiceException 异常信息
	 */
	@Override
	public void addTranCstleaveWords(Map<String, Object> bodyMap)throws ServiceException {
		
		try {
			
			myBatisDaoSysDao.insertOne(CSTLEAVEWORD_SQL_NAMESPACE + "insertCstLeaveWords", bodyMap);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0227,"客户之声添加失败", e);
		}
		
	}

	/**
	 * 
	 * @Title: updateCstleavewords 
	 * @Description: 客户之声处理状态更新
	 * @param bodyMap
	 * @throws ServiceException    异常信息
	 */
	@Override
	public void updateCstleavewords(Map<String, Object> bodyMap)throws ServiceException {
	
try {
			
			myBatisDaoSysDao.update(CSTLEAVEWORD_SQL_NAMESPACE + "updateCstleavewords", bodyMap);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0228,"客户之声处理状态更新失败", e);
		}
		
		
		
	}
}
