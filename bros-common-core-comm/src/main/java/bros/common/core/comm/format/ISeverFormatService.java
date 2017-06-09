package bros.common.core.comm.format;

import java.util.Map;

import bros.common.core.comm.exception.FormatException;
/**
 * 
 * @ClassName: ISeverFormatService 
 * @Description: 解包、拆包接口
 * @author 何鹏
 * @date 2016年7月8日 下午1:05:25 
 * @version 1.0
 */
public interface ISeverFormatService {
	/**
	 * 
	 * @Title: format 
	 * @Description: 报文处理打包方法，根据提供的数据和模板定义名称，打包成字符串
	 * @param map
	 * @return
	 * @throws FormatException
	 */
	public String format(Map<String,Object> map) throws FormatException;
	/**
	 * 
	 * @Title: unformat 
	 * @Description: 报文处理解包方法
	 * @param str
	 * @return
	 * @throws FormatException
	 */
	public Map<String,Object> unformat(String str) throws FormatException;
	
}
