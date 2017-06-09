package bros.common.core.comm.tcpip;

import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.comm.tcpip.service.ITCPIPService;

/**
 * 
 * @ClassName: PackageProcessThread
 * @Description: 包处理线程
 * @author huangcanhui
 * @date 2015年6月14日 上午11:00:37
 * @version 3.0
 */
public class PackageProcessThread implements Runnable {
	private static final Logger logger = LoggerFactory
			.getLogger(PackageProcessThread.class);

	/**
	 * 通信协议处理器，用于处理通信协议
	 */
	private CommProcessor commProcessor;

	/**
	 * 当通信服务接收到数据包时，新接收的数据包处理器
	 */
	private PackageProcessor packageProcessor;

	private ITCPIPService service;

	private byte[] msg;

	private boolean isStop = false;

	@SuppressWarnings("unused")
	private OutputStream out = null;

	@SuppressWarnings("unused")
	private int id;

	private Thread thread = null;

	private boolean beFree = true;

	private Socket socket;

	public PackageProcessThread() {
		super();
	}

	public PackageProcessThread(PackageProcessor packageProcessor,
			CommProcessor commProcessor, ITCPIPService service, byte[] msg,
			Socket socket) {
		this.packageProcessor = packageProcessor;
		this.commProcessor = commProcessor;
		this.service = service;
		this.socket = socket;
	}

	public void startUp() {
		Thread aThread = new Thread(this);
		UUID uuid = UUID.randomUUID();
		String aThreadNameString = "TCPIP PackageProcess Thread " + uuid + "#"
				+ aThread.getId();
		aThread.setName(aThreadNameString);
		aThread.start();
		thread = aThread;
	}

	public void run() {
		while (!isStop) {
			try {
				packageProcessor.processNewPackage(msg, service, socket);
			} catch (Exception e) {
				logger.info("Fatal error in process TCPIP req package: "
						+ new String(msg), e);
			}
			this.beFree = true;
			// service.packageProcessorThreadEnd(this);
			service.packageProcessorThreadFree(this);

			synchronized (this) {
				try {
					wait();
				} catch (Exception e) {
					logger.info("Exception from wait method.", e);
				}
			}
		}
	}

	/**
	 * 对Socket 请求进行处理
	 * 
	 * @param socket
	 */
	public void processPackage(byte[] msg, Socket socket) throws Exception {
		this.msg = msg;
		// this.out = out;
		this.socket = socket;

		synchronized (this) {
			notify();
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
			synchronized (this) {
				notify();
			}
		} catch (Exception e) {
			logger.info("ReadThread method stop() ERROR.", e);
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

}
