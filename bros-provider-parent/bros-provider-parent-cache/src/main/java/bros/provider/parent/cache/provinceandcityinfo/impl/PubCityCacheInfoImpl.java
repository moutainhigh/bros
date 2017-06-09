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
import bros.provider.parent.cache.provinceandcityinfo.IPubCityCacheInfo;

/**
 * 
 * @ClassName: PubCityInfo 
 * @Description: 城市信息数据库及缓存操作
 * @author mazhilei 
 * @date 2016年5月27日 下午3:32:56 
 * @version 1.0
 */
@Repository("pubCityCacheInfo")
public class PubCityCacheInfoImpl implements IPubCityCacheInfo {
	
	private static final  Logger logger = LoggerFactory.getLogger(PubCityCacheInfoImpl.class);
	
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;
	/**
	 * 
	 * @Title: queryAllPubCityInfo 
	 * @Description: 查询所有城市信息
	 * @return PubCityInfoList
	 * @throws BrosBaseException 
	 */
	@Cacheable(value="pubCityCacheInfo")
	@Override
	public List<Map<String,Object>> queryAllPubCityInfo() throws ServiceException{
		List<Map<String,Object>> PubCityInfoList=null;
		try{
			PubCityInfoList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubcity.queryPubCity");
			
		}catch(BrosBaseException be){
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryAllPubCityInfo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001,"数据库执行异常",be);
		}catch(Exception e) {
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryAllPubCityInfo");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000,"默认错误码",e);
		}
		return PubCityInfoList;
		
	}
	/**
	 * 
	 * @Title: queryAllPubCityInfo 
	 * @Description: 根据名称模糊查询城市信息
	 * @return PubCityInfoByNameList
	 * @throws BrosBaseException 
	 */
	@Cacheable(value="pubCityCacheInfo")
	@Override
	public List<Map<String,Object>> queryPubCityInfoByName(String Name) throws ServiceException{
		List<Map<String,Object>> PubCityInfoByNameList=null;
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("name", Name);
			PubCityInfoByNameList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubcity.queryPubCityByName",param);
			
		}catch(BrosBaseException be){
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryPubCityInfoByName");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001,"数据库执行异常",be);
		}catch(Exception e) {
			logger.error("查询所有城市信息失败   "+this.getClass()+".queryPubCityInfoByName");
			
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000,"默认错误码",e);
		}
		return PubCityInfoByNameList;
		
	}
	/**
	 * 
	 * @Title: queryPubCityInfoByCode 
	 * @Description: 根据省份code查询城市信息服务
	 * @return PubCityInfoByNameList
	 * @throws BrosBaseException 
	 */
	@Cacheable(value="pubCityCacheInfo")
	@Override
	public List<Map<String,Object>> queryPubCityInfoByCode(String provinceId) throws ServiceException{
		List<Map<String,Object>> PubCityInfoByCodeList=null;
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("provinceId", provinceId);
			PubCityInfoByCodeList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubcity.queryPubCityByCode",param);
			
		}catch(BrosBaseException be){
			logger.error("根据省份code查询城市信失败   "+this.getClass()+".queryPubCityInfoByCode");
			throw new ServiceException(CacheErrorCodeConstants.PPCE0001,"数据库执行异常",be);
		}catch(Exception e) {
			logger.error("根据省份code查询城市信失败   "+this.getClass()+".queryPubCityInfoByCode");
			
			throw new ServiceException(CacheErrorCodeConstants.PPCE0000,"默认错误码",e);
		}
		return PubCityInfoByCodeList;
		
	}
}
