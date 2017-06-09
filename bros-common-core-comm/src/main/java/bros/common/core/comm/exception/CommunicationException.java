package bros.common.core.comm.exception;

import java.io.Serializable;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: CommunicationException 
 * @Description: 通讯超时异常
 * @author 何鹏
 * @date 2016年7月8日 下午1:06:15 
 * @version 1.0
 */
public class CommunicationException  extends ServiceException implements Serializable{

	private static final long serialVersionUID = 3274108556032106924L;
	
	public CommunicationException(){
		super();
	}
	
	public CommunicationException(String errorCode){		
		super(errorCode);
	}
	
	public CommunicationException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public CommunicationException(String errorCode, String s, Throwable throwable) {
		super(errorCode, s, throwable);
	}
}
