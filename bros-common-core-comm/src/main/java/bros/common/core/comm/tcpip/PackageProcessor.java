package bros.common.core.comm.tcpip;

import java.net.Socket;

import bros.common.core.comm.tcpip.service.ITCPIPService;

/**
 * 
 * @ClassName: PackageProcessor 
 * @Description: 接收数据包处理接口定义，用于那些需要处理对方请求包的通信服务，
 * 当通信服务接收到数据包时，会调用此接口的processNewPackage(byte[]，InputStream) 
 * 方法来处理新接收的数据包。
 * @author huangcanhui
 * @date 2015年6月14日 下午3:31:01 
 * @version 3.0
 */
public interface PackageProcessor {
	/**
	 * 
	 * @Title: processNewPackage 
	 * @Description: 接收到新数据包处理接口，当通信服务接收到新的数据包时，调用此接口，
	 * 如果是请求报文则此接口处理数据请求报文，返回null否则原包返回。
	 * 其中OutputStream是当通信服务是双工通信时的响应输出流。
	 * @param msg
	 * @param service
	 * @param socket
	 * @return
	 * @throws Exception
	 */
	public byte[] processNewPackage(byte[] msg, ITCPIPService service, Socket socket)throws Exception;
	
	/**
	 * 
	 * @Title: isRequestPackage 
	 * @Description: 用于判断接收到的信息是否是作为接入的请求进行处理
	 * @param msg
	 * @return
	 */
	public boolean isRequestPackage(byte[] msg );
}
