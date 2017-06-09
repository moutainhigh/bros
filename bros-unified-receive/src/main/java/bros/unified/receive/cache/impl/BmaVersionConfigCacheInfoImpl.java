package bros.unified.receive.cache.impl;

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
import bros.unified.receive.cache.IBmaVersionConfigCacheInfo;
import bros.unified.receive.constants.RecieveErrorCodeConstants;

/**
 * 
 * @ClassName: BmaVersionConfigCacheInfoImpl 
 * @Description: 版本号查询操作实现
 * @author 何鹏
 * @date 2016年7月26日 上午11:40:38 
 * @version 1.0
 */
@Repository("bmaVersionConfigCacheInfo")
public class BmaVersionConfigCacheInfoImpl implements IBmaVersionConfigCacheInfo {
	
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;

	/**
	 * 
	 * @Title: queryVersionCongfig 
	 * @Description: 根据法人id和服务名称查询服务版本信息
	 * @param legalId	法人id
	 * @param serviceName	服务名称
	 * @return
	 * @throws ServiceException
	 */
	@Override
	@Cacheable(value="BmaVersionConfigCacheInfo")
	public List<Map<String, Object>> queryVersionCongfig(String legalId,String serviceName) throws ServiceException {
		List<Map<String, Object>> versionConfigList = null;
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("legalId", legalId);
			params.put("serviceName", serviceName);
			versionConfigList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmaversionconfig.queryBmaVersionConfig",params);
		} catch (BrosBaseException be) {
			throw new ServiceException(be.getErrorCode(), be.getErrorMsg(), be);
		} catch (Exception e) {
			throw new ServiceException(RecieveErrorCodeConstants.UREE0013, "服务版本获取失败", e);
		}
		return versionConfigList;
	}
}
