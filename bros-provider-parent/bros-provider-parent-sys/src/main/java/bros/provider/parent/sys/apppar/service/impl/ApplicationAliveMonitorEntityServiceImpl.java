package bros.provider.parent.sys.apppar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.sys.apppar.service.IApplicationAliveMonitorEntityService;
import bros.provider.parent.sys.constants.SysErrorCodeConstants;
/**
 * 
 * @ClassName: ApplicationAliveMonitorEntityServiceImpl
 * @Description: 监听数据库是否存活接口实现类
 * @author 何鹏
 * @date 2016年12月19日 上午9:45:35 
 * @version 1.0
 */
@Repository(value="applicationAliveMonitorEntityService")
public class ApplicationAliveMonitorEntityServiceImpl implements IApplicationAliveMonitorEntityService{

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	/**
	 * 
	 * @Title: makeSureDbIsAlive 
	 * @Description: 确保数据库是存活的
	 * @throws ServiceException
	 */
	@Override
	public void makeSureDbIsAlive() throws ServiceException {
		try {
			myBatisDaoSysDao.selectTotalNum("mybatis.mapper.relational.table.pubcommconfiginfo.selectCountPubCommConfig");
		} catch (BrosBaseException e) {
			throw new ServiceException(SysErrorCodeConstants.PPSS0002,"数据库操作不正常",e);
		}
	}

}
