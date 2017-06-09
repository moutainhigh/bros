package bros.common.core.comm.format.impl;

import java.util.Map;

import bros.common.core.comm.exception.FormatException;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.util.JsonUtil;
/**
 * 
 * @ClassName: JsonFormatServiceImpl 
 * @Description: json格式转换
 * @author 何鹏
 * @date 2016年7月8日 下午2:46:33 
 * @version 1.0
 */
public class JsonFormatServiceImpl implements ISeverFormatService {

	/**
	 * 
	 * @Title: format 
	 * @Description: 将对象转换为json字符串
	 * @param map
	 * @return
	 * @throws FormatException
	 */
	@Override
	public String format(Map<String, Object> map) throws FormatException {
		return JsonUtil.mapToJsonStr(map);
	}

	/**
	 * 
	 * @Title: unformat 
	 * @Description: 将json字符串转换为Map对象
	 * @param message
	 * @return
	 * @throws FormatException
	 */
	@Override
	public Map<String, Object> unformat(String message) throws FormatException {
		return JsonUtil.jsonStrToMap(message);
	}

}
