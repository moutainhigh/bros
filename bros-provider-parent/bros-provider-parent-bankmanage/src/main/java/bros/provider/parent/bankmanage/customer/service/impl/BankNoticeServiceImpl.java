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
import bros.provider.parent.bankmanage.customer.service.IBankNoticeService;

/**
 * 
 * @ClassName: BankNoticeServiceImpl 
 * @Description: 银行资讯接口实现
 * @author lichen 
 * @date 2016年10月17日 下午8:06:40 
 * @version 1.0
 */
@Repository(value = "bankNoticeService")
public class BankNoticeServiceImpl implements IBankNoticeService {

	private static final Logger logger = LoggerFactory.getLogger(BankNoticeServiceImpl.class);
	
	/**
	 * SQL语句命名空间
	 */
	private static final String BANKNOTICE_SQL_NAMESPACE ="mybatis.mapper.single.table.banknotice.";
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
	 * @throws ServiceException    设定文件
	 */
	@Override
	public int selectTotalNum(Integer pageNo, Integer pageSize,Map<String, Object> bodyMap) throws ServiceException {
		int totalNo = 0;
		try{
			//查询总记录数
			totalNo = myBatisDaoSysDao.selectTotalNum(BANKNOTICE_SQL_NAMESPACE + "queryPubBankNoticeTotalNo",bodyMap);
		}catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0229,"银行资讯查询总记录数失败", e);
		}
		return totalNo;
	}

	/**
	 * 
	 * @Title: queryBankNotice 
	 * @Description: 银行资讯查询
	 * @param pageNo
	 * @param pageSize
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public List<Map<String, Object>> queryBankNotice(Integer pageNo,Integer pageSize, Map<String, Object> bodyMap)throws ServiceException {
		List<Map<String, Object>> customerVoiceList = null;
		try{
			customerVoiceList=new ArrayList<Map<String, Object>>();
			//查询信息
			customerVoiceList = myBatisDaoSysDao.selectListPage(BANKNOTICE_SQL_NAMESPACE + "queryPubBankNotice",bodyMap, pageNo, pageSize);
		}catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0230,"银行资讯查询失败", e);
		}
		return customerVoiceList;
	}

	/**
	 * 
	 * @Title: addBankNotice 
	 * @Description: 银行资讯添加
	 * @param bodyMap
	 * @throws ServiceException    异常信息
	 */
	@Override
	public void addBankNotice(Map<String, Object> bodyMap)throws ServiceException {
		try {
			
			myBatisDaoSysDao.insertOne(BANKNOTICE_SQL_NAMESPACE + "insertBankNotice", bodyMap);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0231,"银行资讯添加失败", e);
		}
		
	}

	/**
	 * 
	 * @Title: updateBankNotice 
	 * @Description: 银行资讯修改
	 * @param bodyMap
	 * @throws ServiceException    异常信息
	 */
	@Override
	public void updateBankNotice(Map<String, Object> bodyMap)throws ServiceException {
try {
			
			myBatisDaoSysDao.update(BANKNOTICE_SQL_NAMESPACE + "updateBankNotice", bodyMap);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0232,"银行资讯修改失败", e);
		}
			
	}

}
