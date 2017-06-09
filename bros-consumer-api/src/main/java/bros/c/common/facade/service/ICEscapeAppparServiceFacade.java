package bros.c.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICEscapeAppparServiceFacade 
 * @Description: 数据转义服务接口
 * @author 何鹏
 * @date 2016年9月1日 下午2:50:49 
 * @version 1.0
 */
public interface ICEscapeAppparServiceFacade {
	/**
	 * 
	 * @Title: getEscapeData 
	 * @Description: 根据条件获取转义数据
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity getEscapeData(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
