package bros.provider.parent.cache.pmsbanknoinfo.impl;

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
import bros.provider.parent.cache.pmsbanknoinfo.IPubPmsBankNoCacheInfo;
/**
 * 
 * @ClassName: PubPmsBankNoCacheInfoImpl 
 * @Description: 联行号操作类
 * @author mazhilei 
 * @date 2016年5月30日 下午2:44:36 
 * @version 1.0
 */
@Repository("pubPmsBankNoCacheInfo")
public class PubPmsBankNoCacheInfoImpl implements IPubPmsBankNoCacheInfo {
	private static final  Logger logger = LoggerFactory.getLogger(PubPmsBankNoCacheInfoImpl.class);
	
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;

	/**
	 * 
	 * @Title: queryAllPubPmsBankNo
	 * @Description: 查询全部人行联行号信息
	 * @return PubPmsBankNoList
	 * @throws ServiceException 
	 */
	@Cacheable(value="pubPmsBankNoCacheInfo")
	@Override
	public List<Map<String, Object>> queryAllPubPmsBankNo() throws ServiceException {
		List<Map<String, Object>> PubPmsBankNoList = null;
		try {
			PubPmsBankNoList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubpmsbankno.queryAllPubPmsBankNo");

		} catch (BrosBaseException be) {
			logger.error("查询所有城市信息失败   " + this.getClass() + ".queryAllPubPmsBankNo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001, "数据库执行异常", be);
		} catch (Exception e) {
			logger.error("查询所有城市信息失败   " + this.getClass() + ".queryAllPubPmsBankNo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000, "默认错误码", e);
		}
		return PubPmsBankNoList;
	}

	/**
	 * 
	 * @Title: queryPubPmsBankNoByName
	 * @Description: 根据模糊名称，城市代码，行别代码查询人行联行号信息
	 * @param name 模糊行名
	 * @param bankType 行别代码
	 * @param cityCode 城市代码
	 * @return PubPmsBankNoList
	 * @throws ServiceException
	 */
	@Cacheable(value="pubPmsBankNoCacheInfo")
	@Override
	public List<Map<String, Object>> queryPubPmsBankNoByName(String name,String bankType,String cityCode) throws ServiceException {
		List<Map<String, Object>> PubPmsBankNoList = null;
		try {
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("bankName", name);
			param.put("bankType", bankType);
			param.put("cityCode", cityCode);
			PubPmsBankNoList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubpmsbankno.queryPubPmsBankNoByName",param);

		} catch (BrosBaseException be) {
			logger.error("根据名称模糊查询人行联行号信息失败   " + this.getClass() + ".queryPubPmsBankNoByName");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001, "数据库执行异常", be);
		} catch (Exception e) {
			logger.error("根据名称模糊查询人行联行号信息失败   " + this.getClass() + ".queryPubPmsBankNoByName");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000, "默认错误码", e);
		}
		return PubPmsBankNoList;
	}
	/**
	 * 
	 * @Title: queryPubBankType
	 * @Description: 查询全部行别信息
	 * @return PubPmsBankNoList
	 * @throws ServiceException 
	 */
	@Cacheable(value="pubPmsBankNoCacheInfo")
	@Override
	public List<Map<String, Object>> queryPubBankType() throws ServiceException {
		List<Map<String, Object>> PubBankTypeList = null;
		try {
			PubBankTypeList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubbanktype.queryPubBankType");

		} catch (BrosBaseException be) {
			logger.error("查询全部行别信息失败   " + this.getClass() + ".queryPubBankType");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001, "数据库执行异常", be);
		} catch (Exception e) {
			logger.error("查询全部行别信息失败   " + this.getClass() + ".queryPubBankType");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000, "默认错误码", e);
		}
		return PubBankTypeList;
	}

}
