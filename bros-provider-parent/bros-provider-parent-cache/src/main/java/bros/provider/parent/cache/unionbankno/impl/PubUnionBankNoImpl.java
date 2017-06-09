package bros.provider.parent.cache.unionbankno.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import bros.common.core.db.impl.MyBatisDaoSysDaoImpl;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.cache.constants.CacheErrorCodeConstants;
import bros.provider.parent.cache.unionbankno.IPubUnionBankNo;
/**
 * 
 * @ClassName: PubUnionBankNoImpl 
 * @Description: 网银互联联行号操作类
 * @author haojinhui
 * @date 2016年9月20日
 * @version 1.0
 */
@Repository("pubUnionBankNo")
public class PubUnionBankNoImpl implements IPubUnionBankNo {
	private static final  Logger logger = LoggerFactory.getLogger(PubUnionBankNoImpl.class);
	
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;

	/**
	 * 
	 * @Title: queryAllPubUnionBankNo
	 * @Description: 查询全部网银互联联行号信息
	 * @return PubUnionBankNoList
	 * @throws ServiceException 
	 */
	@Cacheable(value="pubUnionBankNo")
	@Override
	public List<Map<String, Object>> queryAllPubUnionBankNo() throws ServiceException {
		List<Map<String, Object>> pubUnionBankNoList = null;
		try {
			pubUnionBankNoList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubunionbankno.queryAllPubUnionBankNo");

		} catch (BrosBaseException be) {
			logger.error("查询所有网银互联联行号信息失败   " + this.getClass() + ".queryAllPubUnionBankNo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001, "数据库执行异常", be);
		} catch (Exception e) {
			logger.error("查询所有网银互联联行号信息失败   " + this.getClass() + ".queryAllPubUnionBankNo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000, "默认错误码", e);
		}
		return pubUnionBankNoList;
	}

	/**
	 * 
	 * @Title: queryPubUnionBankNoByName
	 * @Description: 根据名称模糊查询网银互联联行号信息
	 * @param name
	 * @return PubUnionBankNoList
	 * @throws ServiceException
	 */
	@Cacheable(value="pubUnionBankNo")
	@Override
	public List<Map<String, Object>> queryPubUnionBankNoByName(String name) throws ServiceException {
		List<Map<String, Object>> pubUnionBankNoList = null;
		try {
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("bankName", name);
			pubUnionBankNoList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubunionbankno.queryPubUnionBankNoByName",param);

		} catch (BrosBaseException be) {
			logger.error("查询所有网银互联联行号信息失败   " + this.getClass() + ".queryPubUnionBankNoByName");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001, "数据库执行异常", be);
		} catch (Exception e) {
			logger.error("查询所有网银互联联行号信息失败   " + this.getClass() + ".queryPubUnionBankNoByName");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000, "默认错误码", e);
		}
		return pubUnionBankNoList;
	}
	/**
	 * 
	 * @Title: queryPubUnionBankNoByBankNo
	 * @Description: 根据行号查行别信息
	 * @param bankNo
	 * @return PubUnionBankNoList
	 * @throws ServiceException
	 */
	@Cacheable(value="pubUnionBankNo")
	@Override
	public Map<String, Object> queryPubUnionBankNoByBankNo(String bankNo) throws ServiceException {
		Map<String, Object> pubUnionBankNoMap = null;
		try {
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("bankNo", bankNo);
			pubUnionBankNoMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.pubunionbankno.queryPubUnionBankNoByBankNo",param);

		} catch (BrosBaseException be) {
			logger.error("根据行号查行别信息失败   " + this.getClass() + ".queryPubUnionBankNoByBankNo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001, "数据库执行异常", be);
		} catch (Exception e) {
			logger.error("根据行号查行别信息失败   " + this.getClass() + ".queryPubUnionBankNoByBankNo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000, "默认错误码", e);
		}
		return pubUnionBankNoMap;
	}

}
