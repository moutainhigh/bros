package bros.provider.parent.cache.provinceandcityinfo.impl;

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
import bros.provider.parent.cache.provinceandcityinfo.IPubProvinceCacheInfo;
@Repository("pubProvinceCacheInfo")
public class PubProvinceCacheInfoImpl implements IPubProvinceCacheInfo {
	
	private static final  Logger logger = LoggerFactory.getLogger(PubProvinceCacheInfoImpl.class);
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;
	/**
	 * 
	 * @Title: queryAllPubProvinceCacheInfo 
	 * @Description: 查询全部省份信息
	 * @return PubProvinceCacheInfoList
	 * @throws ServiceException
	 */
	@Override
	@Cacheable(value="PubProvinceCacheInfo")
	public List<Map<String, Object>> queryAllPubProvinceCacheInfo() throws ServiceException {
		List<Map<String,Object>> PubProvinceCacheInfoList=null;
		try{
			PubProvinceCacheInfoList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubprovince.queryAllPubProvince");
			
		}catch(BrosBaseException be){
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryAllPubProvinceCacheInfo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001,"数据库执行异常",be);
		}catch(Exception e) {
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryAllPubProvinceCacheInfo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000,"默认错误码",e);
		}
		return PubProvinceCacheInfoList;
	}

	/**
	 * 
	 * @Title: queryPubProvinceCacheInfoByName 
	 * @Description: 根据名称模糊查询省份信息
	 * @param name
	 * @return PubProvinceCacheInfoList
	 * @throws ServiceException
	 */
	@Override
	@Cacheable(value="PubProvinceCacheInfo")
	public List<Map<String, Object>> queryPubProvinceCacheInfoByName(String name) throws ServiceException {
		List<Map<String,Object>> PubProvinceCacheInfoList=null;
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("name", name);
			PubProvinceCacheInfoList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubprovince.queryPubProvinceByName",param);
			
		}catch(BrosBaseException be){
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryPubProvinceCacheInfoByName");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001,"数据库执行异常",be);
		}catch(Exception e) {
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryPubProvinceCacheInfoByName");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000,"默认错误码",e);
		}
		return PubProvinceCacheInfoList;
	}

}
