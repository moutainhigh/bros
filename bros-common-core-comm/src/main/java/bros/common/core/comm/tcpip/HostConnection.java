package bros.common.core.comm.tcpip;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.exception.CommunicationException;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: HostConnection
 * @Description: 代表与某个TCP/IP服务器的连接
 * @author huangcanhui
 * @date 2016年7月8日 下午1:09:48
 * @version 1.0
 */
public class HostConnection {

	private static final Logger logger = LoggerFactory.getLogger(HostConnection.class);

	private CommProcessor commProcessor = null;

	private boolean isInUse = false;

	private String hostAddr;
	private int port;

	private Socket socket;
	private OutputStream out;
	private InputStream in;

	private boolean keepAlive = true;
	private SocketListener socketListener = null;
	private String encode = "utf-8";//编码
	private int headLength = 8;

	public HostConnection() {
		super();
	}

	public HostConnection(String hostAddr, int port,
			CommProcessor commProcessor, boolean keepAlive,String encode,int headLength) {
		super();
		this.hostAddr = hostAddr;
		this.port = port;
		this.keepAlive = keepAlive;
		this.commProcessor = commProcessor;
		this.encode = encode;
		this.headLength = headLength;
	}

	/**
	 * 发送请求
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void send(byte[] msg) throws ServiceException {
		try {
			connect();
			byte[] wrapedMsg = this.commProcessor.wrapMessagePackage(msg);
			//out.write(wrapedMsg);
			sendMsgMethod(wrapedMsg);
			if (!keepAlive) {
				close();
			}
		} catch (IOException ie) {
			close();
			logger.error("IO Exception in send socket to " + hostAddr + ":"
					+ port);
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常",
					ie);
		} catch (Exception e) {
			close();
			logger.error("Exception in send socket to " + hostAddr + ":" + port);
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常",
					e);

		}

	}

	/**
	 * 发送请求，并等待返回
	 * 
	 * @param msg
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public byte[] sendAndWait(byte[] msg, int timeOut) throws ServiceException {
		try {
			connect();
			byte[] wrapedMsg = this.commProcessor.wrapMessagePackage(msg);
			socket.setSoTimeout(timeOut);
			//out.write(wrapedMsg);
			sendMsgMethod(wrapedMsg);
			byte[] retMsg = this.commProcessor.readPackage(in);
			if (!keepAlive) {
				close();
			}

			return retMsg;
		} catch (InterruptedIOException ire){
			close(); // should close the socket when timeout???
			// "Time out in read socket to " + hostAddr + ":" + port , ire );
			throw new CommunicationException(CommErrorCodeConstants.CCCM0002, "通讯超时", ire);
		} catch (IOException ie) {
			close();
			// "IO Exception in read socket to " + hostAddr + ":" + port, ie
			throw new CommunicationException(CommErrorCodeConstants.CCCM0001, "通讯异常", ie);

		} catch (Exception e) {
			close();
			// "Exception in read socket to " + hostAddr + ":" + port,
			throw new CommunicationException(CommErrorCodeConstants.CCCM0001, "通讯异常", e);
		}
	}

	/**
	 * 连接到服务器
	 * 
	 * @throws Exception
	 */
	private void connect() throws ServiceException {
		try {
			if (socket != null)
				return;

			logger.error("try Connect to host " + hostAddr + ":" + port
					+ " ......");
			socket = new Socket(hostAddr, port);
			out = socket.getOutputStream();
			in = socket.getInputStream();
		} catch (Exception e) {
			logger.error("Exception from connect().");
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常",e);
		}
		logger.error("Connect to host " + hostAddr + ":" + port
				+ " sucessfully!");
	}

	/**
	 * 关闭与服务器的连接
	 */
	private void close() {
		logger.error("Close Connect to host " + hostAddr + ":" + port
				+ " ......");
		try {
			in.close();
		} catch (Exception e) {
			logger.error("输入流关闭异常", e);
		}
		try {
			out.close();
		} catch (Exception e) {
			logger.error("输出流关闭异常", e);
		}
		try {
			socket.close();
		} catch (Exception e) {
			logger.error("连接关闭异常", e);
		}

		in = null;
		out = null;
		socket = null;

		if (this.socketListener != null)
			socketListener.socketClosed(socket);

	}
	
	private void sendMsgMethod(byte[] msg) throws Exception{
		String sendMsg = new String(msg,encode);
		String headStr = sendMsg.substring(0,headLength);
		String bodyStr = sendMsg.substring(headLength);
		ByteBuffer header = ByteBuffer.allocate(headLength);
		header.putInt(Integer.parseInt(headStr));
		out.write(header.array());
		out.write(bodyStr.getBytes(encode));
	}

	public void terminate() {
		close();
	}

	public String getHostAddr() {
		return hostAddr;
	}

	public boolean isConnectionOk() {
		return socket != null;
	}

	public void setInUse(boolean value) {
		isInUse = value;
	}

	public boolean isInUse() {
		return isInUse;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public Socket getSocket() {
		return socket;
	}

	public InputStream getIn() {
		return in;
	}
	
}
