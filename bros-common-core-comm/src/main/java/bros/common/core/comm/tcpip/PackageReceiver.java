package bros.common.core.comm.tcpip;

import java.net.Socket;

/**
 * 
 * @ClassName: PackageReceiver 
 * @Description: 服务端数据包处理接口定义，当readThread接收到数据包时，会调用此接口的newPackageReceived方法
 * @author huangcanhui
 * @date 2015年6月14日 上午10:31:13 
 * @version 3.0
 */
public interface PackageReceiver {
	/**
	 * 
	 * @Title: newPackageReceived 
	 * @Description: 服务端数据包处理
	 * @param aPackage
	 * @param socket
	 */
	public void newPackageReceived(byte[] aPackage, Socket socket) throws Exception;
}
