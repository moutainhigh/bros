package bros.provider.parent.sys.apppar.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IAppparEntityService 
 * @Description: 查询引用参数表服务
 * @author 何鹏
 * @date 2016年9月1日 下午2:29:11 
 * @version 1.0
 */
public interface IAppparEntityService {
	/**
	 * 
	 * @Title: queryAppparByTypeCode 
	 * @Description: 根据参数分类查询参数数据
	 * @param typeCodeList 参数分类list
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAppparByTypeCode(List<Map<String,Object>> typeCodeList) throws ServiceException;
}
