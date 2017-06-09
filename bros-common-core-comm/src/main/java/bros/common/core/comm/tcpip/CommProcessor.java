package bros.common.core.comm.tcpip;

import java.io.IOException;

import bros.common.core.comm.exception.FormatException;

/**
 * 
 * @ClassName: CommProcessor 
 * @Description: 通讯协议处理接口，通信程序会调用它的 readPackage(InputStream)来从输入流中
 *  读取数据包，具体的通讯协议也需要它处理。同时通讯程序会调用它的
 *  byte[] wrapeMessagePackage(byte[]) 来对发送数据包进行打包处理（如加入通信协议报头）。
 * @author huangcanhui
 * @date 2015年6月14日 下午3:32:42 
 * @version 3.0
 */
public interface CommProcessor {	
	/**
	 * 
	 * @Title: readPackage 
	 * @Description: 从输入流中读入一个标准数据包，它需要处理通信协议。
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public byte[] readPackage(java.io.InputStream in) throws IOException, FormatException;	
	/**
	 * 
	 * @Title: wrapMessagePackage 
	 * @Description: 根据通信协议对数据包进行打包，如加入通信报文头
	 * @param msg
	 * @return
	 */
	public byte[] wrapMessagePackage(byte[] msg);
	/**
	 * 
	 * @Title: getHeadLengthMethod 
	 * @Description: 获取报文头长度
	 * @return
	 */
	public int getHeadLengthMethod();
	/**
	 * 
	 * @Title: getEncodingMethod 
	 * @Description: 获取编码集
	 * @return
	 */
	public String getEncodingMethod();
}
