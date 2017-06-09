package bros.common.core.comm.route.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICommunicationService 
 * @Description: 客户端通讯访问服务接口
 * @author 何鹏
 * @date 2016年7月11日 上午11:06:11 
 * @version 1.0
 */
public interface ICommunicationService {
	/**
	 * 
	 * @Title: client 
	 * @Description: 客户端通讯方法
	 * @param sendMap 上送数据
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> client(Map<String,Object> sendMap) throws ServiceException;
}
