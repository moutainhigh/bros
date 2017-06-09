package bros.p.common.facade.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPEscapeAppparServiceFacade;
import bros.provider.parent.sys.apppar.service.IAppparEntityService;
/**
 * 
 * @ClassName: IPEscapeAppparServiceFacade 
 * @Description: 数据转义服务接口实现
 * @author 何鹏
 * @date 2016年9月1日 下午2:50:49 
 * @version 1.0
 */
@Component(value="pescapeAppparServiceFacade")
public class PEscapeAppparServiceFacadeImpl implements IPEscapeAppparServiceFacade {
	@Autowired
	private IAppparEntityService appparEntityService;
	/**
	 * 
	 * @Title: getEscapeData 
	 * @Description: 根据条件获取转义数据
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000208")
	@Override
	public ResponseEntity getEscapeData(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		//获取转义数据
		List<Map<String,Object>> appparList = (List<Map<String, Object>>) bodyMap.get("appparList");
		Map<String,Object> result = appparEntityService.queryAppparByTypeCode(appparList);
		return new ResponseEntity(result);
	}

}
