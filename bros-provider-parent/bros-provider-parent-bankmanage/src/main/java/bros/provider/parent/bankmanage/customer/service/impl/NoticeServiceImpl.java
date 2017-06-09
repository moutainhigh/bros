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
import bros.provider.parent.bankmanage.customer.service.INoticeService;

/**
 * 
 * @ClassName: NoticeServiceImpl 
 * @Description: 公告管理接口实现
 * @author lichen 
 * @date 2016年10月21日 上午11:02:00 
 * @version 1.0
 */
@Repository(value = "noticeService")
public class NoticeServiceImpl implements INoticeService {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	/**
	 * SQL语句命名空间
	 */
	private static final String NOTICE_SQL_NAMESPACE ="mybatis.mapper.single.table.notice.";
	/**
	 * 数据库Dao
	 */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: selectTotalNum 
	 * @Description: 查询总页数
	 * @param pageNo
	 * @param pageSize
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public int selectTotalNum(Integer pageNo, Integer pageSize,Map<String, Object> bodyMap) throws ServiceException {
		int totalNo = 0;
		try{
			//查询总记录数
			totalNo = myBatisDaoSysDao.selectTotalNum(NOTICE_SQL_NAMESPACE + "queryPubNoticeTotalNo",bodyMap);
		}catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0233,"公告查询总记录数失败", e);
		}
		return totalNo;
	}

	/**
	 * 
	 * @Title: queryNotice 
	 * @Description: 查询公告
	 * @param pageNo
	 * @param pageSize
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public List<Map<String, Object>> queryNotice(Integer pageNo,Integer pageSize, Map<String, Object> bodyMap)throws ServiceException {
		List<Map<String, Object>> customerVoiceList = null;
		try{
			customerVoiceList=new ArrayList<Map<String, Object>>();
			//查询信息
			customerVoiceList = myBatisDaoSysDao.selectListPage(NOTICE_SQL_NAMESPACE + "queryPubNotice",bodyMap, pageNo, pageSize);
		}catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0234,"公告查询失败", e);
		}
		return customerVoiceList;
	}

	/**
	 * 
	 * @Title: addNotice 
	 * @Description: 增加公告
	 * @param bodyMap
	 * @throws ServiceException    异常信息
	 */
	@Override
	public void addNotice(Map<String, Object> bodyMap) throws ServiceException {
		try {
			
			myBatisDaoSysDao.insertOne(NOTICE_SQL_NAMESPACE + "insertNotice", bodyMap);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0235,"公告添加失败", e);
		}
		
	}

	/**
	 * 
	 * @Title: updateNotice 
	 * @Description: 更改公告
	 * @param bodyMap
	 * @throws ServiceException    异常信息
	 */
	@Override
	public void updateNotice(Map<String, Object> bodyMap)throws ServiceException {
		try {
			
			myBatisDaoSysDao.update(NOTICE_SQL_NAMESPACE + "updateNotice", bodyMap);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0236,"公告修改失败", e);
		}
			
	}

}
