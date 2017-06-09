package bros.common.core.comm.tcpip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: SocketProcessThread
 * @Description: Socket处理线程
 * @author huangcanhui
 * @date 2015年6月14日 上午11:21:22
 * @version3.0
 */
public class SocketProcessThread implements Runnable {
	private static final Logger logger = LoggerFactory
			.getLogger(SocketProcessThread.class);

	/**
	 * 长短连接标识
	 */
	private boolean keepAlive = false;

	/**
	 * 监听器
	 */
	protected ListenPort parent;

	/**
	 * 通信协议处理器，用于处理通信协议
	 */
	private CommProcessor commProcessor;

	/**
	 * 当通信服务接收到数据包时，新接收的数据包处理器
	 */
	private PackageReceiver packageReceiver;

	/**
	 * 线程停止标识
	 */
	private boolean isStop = false;

	/**
	 * Socket对象
	 */
	private Socket socket = null;

	/**
	 * 输入输入流
	 */
	private InputStream in = null;

	/**
	 * 输出流
	 */
	private OutputStream out = null;

	// private int id;

	private Thread thread = null;

	/**
	 * 是否为线程池的线程
	 */
	private boolean pooledThread = false;

	private boolean beFree = true;

	private SocketListener socketListener = null;

	public SocketProcessThread() {
		super();
	}

	public SocketProcessThread(Socket socket, CommProcessor commProcessor,
			PackageReceiver packageReceiver, boolean keepAlive) {
		super();
		this.socket = socket;
		this.commProcessor = commProcessor;
		this.packageReceiver = packageReceiver;
		this.keepAlive = keepAlive;
	}

	public void startUp() {
		Thread aThread = new Thread(this);
		UUID uuid = UUID.randomUUID();
		String aThreadNameString = "TCP/IP SocketProcessThread " + uuid + "#"
				+ aThread.getId();
		aThread.setName(aThreadNameString);
		aThread.start();
		thread = aThread;
	}

	public void setSocketListener(SocketListener listener) {
		socketListener = listener;
	}

	public void run() {
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (java.io.IOException e) {
			logger.error("Failed to get in/out from socket.", e);
			return;
		}
		while (!isStop) {
			try {
				byte[] readMsg = commProcessor.readPackage(in);
				if (readMsg != null) {
					// 对接收到的数据包进行解包，并调用业务逻辑服务
					if (packageReceiver != null) {
						packageReceiver.newPackageReceived(readMsg, socket);
					}
				} else { // encounter fatal error!! should close the connection
					if (socketListener != null) {
						socketListener.socketClosed(socket);
					}
					logger.error("SocketProcess Thread: readMsg was null! ");
					break;
				}
			} catch (IOException ie) {
				if (socketListener != null) {
					socketListener.socketClosed(socket);
				}
				logger.error("SocketProcess Thread: ", ie);
				break;
			} catch (Exception e) {
				if (socketListener != null) {
					socketListener.socketClosed(socket);
				}
				logger.error("SocketProcess Thread: ", e);
				break;
			} finally {
				if (!keepAlive) { // close the socket with none keep alive
									// connection
					if (socketListener != null) {
						socketListener.socketClosed(socket);
					}

					logger.error("Not keep alive, socket closed. ");

					if (!pooledThread) {
						this.beFree = true;
						break;
					}

					closeAll();
					if (this.parent != null) {
						parent.socketProcessThreadFree(this);
					}

					this.beFree = true;
					synchronized (this) {
						try {
							wait();
						} catch (Exception e) {
							logger.error(
									"Exception from SocketProcessThread run method.",
									e);
						}
					}
				} else {
					this.beFree = true;
				}
			}
		}

		closeAll();
		if (parent != null) {
			parent.socketProcessThreadEnd(this);
		}
	}

	private void closeAll() {
		try {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
			if (null != socket) {
				socket.close();
			}
		} catch (Exception e) {
			logger.error("ReadThread method run() ERROR.", e);
		}
		in = null;
		out = null;
		socket = null;
	}

	/**
	 * 对Socket 请求进行处理
	 * 
	 * @param socket
	 */
	public void processSocket(Socket socket) {
		try {
			this.socket = socket;
			in = socket.getInputStream();
			out = socket.getOutputStream();
			synchronized (this) {
				notify();
			}
		} catch (java.io.IOException e) {
			logger.error("ReadThread method run() ERROR.", e);
			return;
		}
	}

	/**
	 * 设置线程名
	 */
	public void setThreadName(String name) {
		if (thread != null) {
			thread.setName(name);
		}
	}

	/**
	 * 停止线程，关闭套接字
	 */
	public void terminate() {
		try {
			isStop = true;
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			logger.error("ReadThread method run() ERROR.", e);
		}
		synchronized (this) {
			notify();
		}
	}

	public boolean isBeFree() {
		return beFree;
	}

	public void setBeFree(boolean beFree) {
		this.beFree = beFree;
	}

	public CommProcessor getCommProcessor() {
		return commProcessor;
	}

	public void setCommProcessor(CommProcessor commProcessor) {
		this.commProcessor = commProcessor;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public PackageReceiver getPackageReceiver() {
		return packageReceiver;
	}

	public void setPackageReceiver(PackageReceiver packageReceiver) {
		this.packageReceiver = packageReceiver;
	}

	public boolean isPooledThread() {
		return pooledThread;
	}

	public void setPooledThread(boolean pooledThread) {
		this.pooledThread = pooledThread;
	}

}
