package bros.unified.receive.cache;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IBmaVersionConfigCacheInfo 
 * @Description: 版本号查询操作接口
 * @author 何鹏
 * @date 2016年7月26日 上午11:43:52 
 * @version 1.0
 */
public interface IBmaVersionConfigCacheInfo {
	/**
	 * 
	 * @Title: queryVersionCongfig 
	 * @Description: 根据法人id和服务名称查询服务版本信息
	 * @param legalId	法人id
	 * @param serviceName	服务名称
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryVersionCongfig(String legalId,String serviceName) throws ServiceException;
}
