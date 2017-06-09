package bros.common.core.comm.tcpip.service;

import bros.common.core.comm.tcpip.CommProcessor;
import bros.common.core.exception.ServiceException;

public interface IConnectToHostService {
    /**
     * 
     * @Title: send 
     * @Description: 发送报文
     * @param msg
     * @return void
     * @throws ServiceException
     */
	public void send(byte[] msg) throws ServiceException;
	/**
	 * 
	 * @Title: receive 
	 * @Description: 接收报文
	 * @param identity
	 * @param timeOut
	 * @return byte[]
	 * @throws ServiceException
	 */
	public byte[] receive(Object identity, int timeOut) throws ServiceException;
	/**
	 * 
	 * @Title: sendAndWait 
	 * @Description: 发送和接收报文
	 * @param msg
	 * @param timeOut
	 * @return byte[]
	 * @throws ServiceException
	 */
	public byte[] sendAndWait(byte[] msg, int timeOut ) throws ServiceException;
	/**
	 * 
	 * @Title: terminate 
	 * @Description: 终止连接
	 * @return void
	 * @throws
	 */
	public void terminate();
	/**
	 * 
	 * @Title: clone 
	 * @Description: 克隆
	 * @return Object
	 * @throws
	 */
	public Object clone();
	/**
	 * 
	 * @Title: setCommProcessor 
	 * @Description: 设置报文处理器 
	 * @return void 
	 * @throws
	 */
	public void setCommProcessor(CommProcessor commProcessor);
	/**
	 * 
	 * @Title: setHostAddr 
	 * @Description: 设置主机地址
	 * @param hostAddr 主机地址
	 */
	public void setHostAddr(String hostAddr); 
}
