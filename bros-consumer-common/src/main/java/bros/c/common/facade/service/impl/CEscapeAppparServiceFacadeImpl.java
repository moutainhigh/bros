package bros.c.common.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.common.facade.service.ICEscapeAppparServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPEscapeAppparServiceFacade;
/**
 * 
 * @ClassName: CEscapeAppparServiceFacadeImpl 
 * @Description: 数据转义服务接口实现
 * @author 何鹏
 * @date 2016年9月1日 下午2:50:49 
 * @version 1.0
 */
@Component(value="cescapeAppparServiceFacade")
public class CEscapeAppparServiceFacadeImpl implements ICEscapeAppparServiceFacade {
	@Autowired
	private IPEscapeAppparServiceFacade pescapeAppparServiceFacade;
	/**
	 * 
	 * @Title: getEscapeData 
	 * @Description: 根据条件获取转义数据
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000208")
	@Override
	public ResponseEntity getEscapeData(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pescapeAppparServiceFacade.getEscapeData(headMap, bodyMap);
	}

}
