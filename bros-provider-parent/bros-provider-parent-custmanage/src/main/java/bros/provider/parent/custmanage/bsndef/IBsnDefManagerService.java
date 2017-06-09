package bros.provider.parent.custmanage.bsndef;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBsnDefManagerService 
 * @Description: 交易功能定义信息接口 可根据后续需求扩充
 * @author mazhilei 
 * @date 2016年6月29日 下午2:05:12 
 * @version 1.0
 */
public interface IBsnDefManagerService {

	/**
	 * 
	 * @return 
	 * @Title: querySysFuncId 
	 * @Description: 查询系统功能定义编码
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> querySysFuncId(String roleAuth) throws ServiceException;
}
