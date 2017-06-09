package bros.common.core.comm.tcpip;

import java.net.*;

/**
 * 
 * @ClassName: SocketListener 
 * @Description: 新套接字连接监听器接口。当监听到新的连接时调用newSocketAccepted方法
 * @author huangcanhui
 * @date 2015年6月14日 上午10:30:13 
 * @version 3.0
 */
public interface SocketListener {
	
	/**
	 * 
	 * @Title: newSocketAccepted 
	 * @Description: 标示监听到新的连接
	 * @param socket
	 */
	public void newSocketAccepted(Socket socket);

	/**
	 * 
	 * @Title: socketClosed 
	 * @Description: 关闭连接
	 * @param socket
	 */
	public void socketClosed(Socket socket);

}
