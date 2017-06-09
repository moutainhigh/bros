package bros.common.core.comm.exception;

import java.io.Serializable;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: TimeOutException 
 * @Description: TCPIP通讯超时异常
 * @author 何鹏
 * @date 2016年7月8日 下午1:06:46 
 * @version 1.0
 */
public class TimeOutException extends ServiceException implements Serializable{

	private static final long serialVersionUID = -6386423263806400555L;

	public TimeOutException(){
		super();
	}
	
	public TimeOutException(String errorCode){		
		super(errorCode);
	}
	
	public TimeOutException(String errorCode, String errorMsg){		
		super(errorCode, errorMsg);
	}
	
	public TimeOutException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

	public TimeOutException(String errorCode, String errorMsg, Throwable throwable) {
		super(errorCode, errorMsg, throwable);
	}

}
