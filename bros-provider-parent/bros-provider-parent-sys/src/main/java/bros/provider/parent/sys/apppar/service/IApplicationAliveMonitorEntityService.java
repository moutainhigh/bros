package bros.provider.parent.sys.apppar.service;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IApplicationAliveMonitorEntityService 
 * @Description: 监听数据库是否存活接口
 * @author 何鹏
 * @date 2016年12月19日 上午9:45:35 
 * @version 1.0
 */
public interface IApplicationAliveMonitorEntityService {
	/**
	 * 
	 * @Title: makeSureDbIsAlive 
	 * @Description: 确保数据库是存活的
	 * @throws ServiceException
	 */
	public void makeSureDbIsAlive() throws ServiceException;
	
}
