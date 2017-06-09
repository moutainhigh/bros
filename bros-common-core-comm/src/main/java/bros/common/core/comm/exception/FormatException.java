package bros.common.core.comm.exception;

import bros.common.core.exception.BrosBaseException;
/**
 * 
 * @ClassName: FormatException 
 * @Description: 格式化异常
 * @author 何鹏
 * @date 2016年7月8日 下午1:06:31 
 * @version 1.0
 */
public class FormatException extends BrosBaseException {
	
	private static final long serialVersionUID = -5215832317720148914L;

	public FormatException(String errorCode) {
		super(errorCode);
	}
	
	public FormatException(String s, Throwable throwable)
    {
        super(s, throwable);
    }
	
	public FormatException(String s, String errmsg)
    {
        super(s, errmsg);
    }
	
	public FormatException(String s, String errmsg, Throwable throwable)
    {
        super(s, errmsg, throwable);
    }
}
