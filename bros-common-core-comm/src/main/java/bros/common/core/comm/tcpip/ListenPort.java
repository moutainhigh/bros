package bros.common.core.comm.tcpip;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ListenPort
 * @Description: 监听线程
 * @author huangcanhui
 * @date 2016年7月8日 下午1:18:39
 * @version 1.0
 */
public class ListenPort implements PackageReceiver, Runnable, SocketListener {

	private static final Logger logger = LoggerFactory.getLogger(ListenPort.class);

	/**
	 * 通信协议处理器，用于处理通信协议
	 */
	public CommProcessor commProcessor;

	/**
	 * 报文处理器
	 */
	public PackageReceiver packageReceiver;

	/**
	 * 端口
	 */
	protected int port = 0;

	/**
	 * 长短连接标识
	 */
	protected boolean keepAlive = false;

	private ServerSocket socket;

	private boolean isStop = false;

	private List<SocketListener> socketAcceptListeners;

	private Thread theThread;

	/**
	 * socket连接处理线程池同步对象
	 */
	private Object socketProcessSync = new Object();

	/**
	 * 最大连接数
	 */
	protected int maxConnection = 10;

	/**
	 * 是否使用线程池
	 */
	private boolean poolThread = true;

	private List<SocketProcessThread> threadPool = new ArrayList<SocketProcessThread>();

	public ListenPort() {
		super();
		threadPool = new ArrayList<SocketProcessThread>();
	}

	public ListenPort(int port) {
		super();
		this.port = port;
		threadPool = new ArrayList<SocketProcessThread>();
	}

	public void socketClosed(Socket socket) {
		fireSocketClosedEvent(socket);
	}

	/**
	 * 接收新连接
	 */
	public void newSocketAccepted(Socket aSocket) {
		logger.info("New socket accept from "
				+ aSocket.getRemoteSocketAddress());

		fireSocketAcceptEvent(aSocket);

		if (this.poolThread) {
			synchronized (socketProcessSync) {
				while (true) {
					for (int i = 0; i < this.threadPool.size(); i++) {
						SocketProcessThread socketThread = (SocketProcessThread) threadPool
								.get(i);
						if (socketThread.isBeFree()) {
							socketThread.setBeFree(false);
							UUID uuid = UUID.randomUUID();
							String aThreadNameString = "TCP/IP SocketProcessThread "
									+ uuid
									+ "#"
									+ Thread.currentThread().getId();
							socketThread.setThreadName(aThreadNameString);
							socketThread.processSocket(aSocket);
							return;
						}
					}

					if (threadPool.size() < this.maxConnection) {
						SocketProcessThread socketThread = new SocketProcessThread(
								aSocket, commProcessor, this, keepAlive);
						socketThread.setSocketListener(this);
						socketThread.setCommProcessor(this.commProcessor);
						socketThread.setPooledThread(true);
						socketThread.setBeFree(false);

						socketThread.parent = this;
						socketThread.startUp();
						threadPool.add(socketThread);
						return;
					}

					logger.info("TCP/IP Listen on port [" + port
							+ "] touch the max connection:"
							+ this.maxConnection);
					// waiting for free thread to process
					try {
						socketProcessSync.wait();
					} catch (Exception e) {
						logger.error(
								"Exception from ListenPort newSocketAccepted.",
								e);
					}
				}
			}
		} else {
			SocketProcessThread socketThread = new SocketProcessThread(aSocket,
					commProcessor, this, keepAlive);
			socketThread.startUp();
		}
	}

	public void socketProcessThreadFree(SocketProcessThread socketThread) {
		synchronized (socketProcessSync) {
			socketProcessSync.notify();
		}
	}

	public void socketProcessThreadEnd(SocketProcessThread socketThread) {
		synchronized (socketProcessSync) {
			threadPool.remove(socketThread);
		}
	}

	/**
	 * 启动监听服务
	 */
	public void startUp() {
		theThread = new Thread(this);
		theThread.setName("Listen Thread [" + port + "]");
		theThread.start();
	}

	public void run() {
		try {
			logger.info("Listen thread listen at port: " + port);
			socket = new ServerSocket(port, 0);
		} catch (Exception e) {
			logger.error("Failed to listen on: " + port, e);
			return;
		}
		while (!isStop) {
			try {
				Socket aSocket = socket.accept();
				if (aSocket != null) {
					this.newSocketAccepted(aSocket);
				}
			} catch (java.io.IOException e) {
				logger.error("Exception from ListenThread on port[" + port
						+ "]", e);
				break;
			}
		}
	}

	/**
	 * 停止线程，关闭套接字
	 */
	public void terminate() {
		isStop = true;
		try {
			socket.close();
			synchronized (this) {
				notifyAll();
			}
		} catch (IllegalMonitorStateException me) {
			logger.error("Exception from ListenPort terminate.", me);
		} catch (Exception e) {
			logger.error("Exception from ListenPort terminate...", e);
		}

		try {

			Object[] sts = threadPool.toArray();
			for (int i = 0; i < sts.length; i++) {
				SocketProcessThread thread = (SocketProcessThread) sts[i];
				thread.terminate();
			}

		} catch (Exception e) {
			logger.error("Exception from SocketProcessThread terminate......",
					e);
		}
	}

	/**
	 * 添加Socket连接监听者
	 * 
	 * @param listener
	 */
	public void addSocketListener(SocketListener listener) {
		if (this.socketAcceptListeners == null) {
			socketAcceptListeners = new ArrayList<SocketListener>();
		}
		socketAcceptListeners.add(listener);
	}

	private void fireSocketAcceptEvent(Socket socket) {
		if (socketAcceptListeners == null) {
			return;
		}
		for (int i = 0; i < socketAcceptListeners.size(); i++) {
			SocketListener listener = (SocketListener) socketAcceptListeners
					.get(i);
			listener.newSocketAccepted(socket);
		}
	}

	private void fireSocketClosedEvent(Socket socket) {
		if (socketAcceptListeners == null) {
			return;
		}
		for (int i = 0; i < socketAcceptListeners.size(); i++) {
			SocketListener listener = (SocketListener) socketAcceptListeners
					.get(i);
			listener.socketClosed(socket);
		}
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void newPackageReceived(byte[] aPackage, Socket socket)
			throws Exception {
		this.packageReceiver.newPackageReceived(aPackage, socket);
	}

	public boolean isPoolThread() {
		return poolThread;
	}

	public void setPoolThread(boolean poolThread) {
		this.poolThread = poolThread;
	}

	public int getMaxConnection() {
		return maxConnection;
	}

	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	public void setCommProcessor(CommProcessor commProcessor) {
		this.commProcessor = commProcessor;
	}

	public void setPackageReceiver(PackageReceiver packageReceiver) {
		this.packageReceiver = packageReceiver;
	}

}
