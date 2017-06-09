package bros.common.core.comm.tcpip.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.exception.TimeOutException;
import bros.common.core.comm.tcpip.CommProcessor;
import bros.common.core.comm.tcpip.ListenPort;
import bros.common.core.comm.tcpip.PackageIdentity;
import bros.common.core.comm.tcpip.PackageProcessThread;
import bros.common.core.comm.tcpip.PackageProcessor;
import bros.common.core.comm.tcpip.PackageReceiver;
import bros.common.core.comm.tcpip.service.IConnectToHostService;
import bros.common.core.comm.tcpip.service.ITCPIPService;

/**
 * 
 * @ClassName: TCPIPServiceImpl 
 * @Description: TCPIP服务，允许监听端口，连接到服务器端口，定义是否长连接
 * @author huangcanhui 
 * @date 2011-9-19 下午10:10:46 
 * @version 1.0
 */
public class TCPIPServiceImpl implements ITCPIPService, PackageReceiver
{

	private static final Logger logger = LoggerFactory.getLogger(TCPIPServiceImpl.class);
	/**
	 * 监听处理器集合，支持配置多个监听器
	 */
	protected List<ListenPort> listenPorts;
	/**
	 * 连接服务处理器集合，支持配置多个连接处理器
	 */
	protected List<IConnectToHostService> connectToHosts;
	/**
	 * 连接处理器
	 */
	public IConnectToHostService connectToHost;
	/**
	 * 监听处理器
	 */
	public ListenPort listenPort;
	/**
	 * 接收消息列表
	 */
	protected List<byte[]> receivedMsg;
	
	/**
	 * 通信协议处理器，用于处理通信协议
	 */
	public CommProcessor commProcessor;

	/**
	 * 报文处理器
	 */
	protected PackageProcessor packageProcessor;
	
	/**
	 * 报文识别器
	 */
	protected PackageIdentity packageIdentity;
	
	//private Socket socket;
	
	/**
	 * 是否为双工工作，在同时有连接出去和进来的时候，是否需要在同一个连接上处理请求/响应
	 */
	protected boolean dual;
	
	/**
	 * 是否长连接
	 */
	protected boolean keepAlive = false;
	
	/**
	 * 当前连接序号
	 */
	int curConnectToIdx = 0;
	
	/**
	 * 是否使用线程池处理新报文请求
	 */
	private boolean poolThread = false;
	
	/**
	 * 线程池大小
	 */
	private int poolSize = 10;
	
	/**
	 * 包处理线程池
	 */
	private List<PackageProcessThread> threadPool = new ArrayList<PackageProcessThread>();
	
	private boolean isMBean = false;
	
	private String MBeanName;

	public TCPIPServiceImpl() 
	{
		super();
		listenPorts = new ArrayList<ListenPort>();
		connectToHosts = new ArrayList<IConnectToHostService>();
		threadPool = new ArrayList<PackageProcessThread>();
		receivedMsg = new ArrayList<byte[]>();
	}
	
	public void addConnectToHost(IConnectToHostService aHost )
	{
		if( connectToHosts == null ){
			connectToHosts = new ArrayList<IConnectToHostService>();
		}
		connectToHosts.add( aHost );
	}

	public void addListenPort(ListenPort aPort )
	{
		if( listenPorts == null ){
			listenPorts = new ArrayList<ListenPort>();
		}
		listenPorts.add( aPort );
	}

	public void setCommProcessor( CommProcessor processor )
	{
		this.commProcessor = processor;
	}
	
	/**
	 * 发送消息
	 */
	public byte[] sendAndWait(Object identity, byte[] msg, int timeOut )throws IOException, Exception
	{
		if( this.listenPorts.size() > 0 && !dual ) //单工
		{
			IConnectToHostService connection = (IConnectToHostService)connectToHosts.get( curConnectToIdx );
			curConnectToIdx++;
			if( curConnectToIdx >= connectToHosts.size() ){
				curConnectToIdx = 0;
			}
			connection.send( msg );
			byte[] retMsg = connection.receive(identity, timeOut);
			return retMsg;			
		}
		else  //双工
		{
			IConnectToHostService connection = (IConnectToHostService)connectToHosts.get( curConnectToIdx );
			curConnectToIdx++;
			if( curConnectToIdx >= connectToHosts.size() ){
				curConnectToIdx = 0;
			}
			return connection.sendAndWait( msg, timeOut);
		}
	}
	
	/**
	 * 发送消息
	 */
	public void send(byte[] msg )throws IOException, Exception
	{
		IConnectToHostService connection = (IConnectToHostService)connectToHosts.get( curConnectToIdx );
		curConnectToIdx++;
		if( curConnectToIdx >= connectToHosts.size() ){
			curConnectToIdx = 0;
		}
		connection.send( msg );		
	}

	/**
	 * 发送消息
	 */
	public void send(byte[] msg, Socket socket )throws IOException, Exception
	{
		OutputStream out = socket.getOutputStream();
		byte[] sendMsg = this.commProcessor.wrapMessagePackage( msg );
		if( this.dual )
		{
			out.write( sendMsg );
		}
		else
		{
			IConnectToHostService connection = (IConnectToHostService)connectToHosts.get( curConnectToIdx );
			connection.send( sendMsg );
			curConnectToIdx++;
			if( curConnectToIdx >= connectToHosts.size() ){
				curConnectToIdx = 0;
			}
		}
	}
	
	/**
	 * 接收消息
	 */
	public byte[] receive(Object identity, long timeOut ) throws TimeOutException
	{
		synchronized( receivedMsg )
		{
			long beg = System.currentTimeMillis();
			while( true )
			{
				Object[] msgs = receivedMsg.toArray();
				for( int i=0; i< msgs.length; i++)
				{
					byte[] msg = (byte[])msgs[i];
					if( packageIdentity == null )
					{
						receivedMsg.remove( msg );
						return msg;
					}
					if( this.packageIdentity.isTargetPackage(identity, msg ))
					{
						receivedMsg.remove( msg );
						return msg;
					}
				}
				
				if( System.currentTimeMillis() - beg > timeOut )
					throw new TimeOutException(CommErrorCodeConstants.CCCM0002, "通讯超时");
				try{
					receivedMsg.wait( timeOut - (System.currentTimeMillis() - beg ));
				}catch(Exception e)
				{
					logger.error("等待响应异常",e);
				}
			}
		}
	}

	public void newPackageReceived(byte[] aPackage, Socket socket) throws Exception
	{
		if( packageProcessor != null )
		{
			if( packageProcessor.isRequestPackage( aPackage ) )	//如果是请求报文，则处理请求报文
			{
				processNewPackage(aPackage, socket);
			}
			else
			{
				addNewMessage(aPackage );
			}
		}
		
		else	//save the message
		{
			addNewMessage(aPackage );
		}			
	}

	
	public void addNewMessage(byte[] msg )
	{
		synchronized( receivedMsg )
		{
			this.receivedMsg.add( msg );
			receivedMsg.notify();
		}
	}
	
	/**
	 * 接收到新数据包处理方法
	 */
	public void processNewPackage(byte[] msg, Socket socket ) throws Exception
	{
		if( this.poolThread )
		{
			synchronized ( threadPool )
			{
				while( true )
				{					
					for( int i=0; i<this.threadPool.size(); i++ )
					{
						PackageProcessThread packageThread = (PackageProcessThread)threadPool.get( i );
						if( packageThread.isBeFree()){
							packageThread.setBeFree(false);
							packageThread.processPackage(msg, socket);
							return;  //return后，包处理线程才启动处理，但sockect会关闭，无法将报文返回到客户端，所以包处理暂时不使用线程处理方式
						}
					}
					
					if( threadPool.size() < this.poolSize )
					{
						PackageProcessThread packageThread = new PackageProcessThread(packageProcessor, commProcessor, this, msg, socket);						
						packageThread.setBeFree(false);
						packageThread.startUp();
						threadPool.add( packageThread );
						return;  //return后，包处理线程才启动处理，但sockect会关闭，无法将报文返回到客户端，所以包处理暂时不使用线程处理方式
					}
						
					logger.error("TCP/IP Service package process thread touch the max thread size:" + this.poolSize);
					
					//waiting for free thread to process
					try{
						threadPool.wait();
					}catch(Exception e)
					{
						logger.error("等待响应异常",e);
					}
				}
			}			
		}
		else
		{
			try{
				packageProcessor.processNewPackage(msg, this, socket );
			}catch(Exception e)
			{
				logger.error("Failed to process received package!" + new String(msg), e);
			}
		}	
	}
	
	public void packageProcessorThreadFree(PackageProcessThread thread)
	{
		synchronized( threadPool )
		{
			threadPool.notify();
		}
	}
	
	/**
	 * 初始化
	 */
	public void initialize()
	{
		for(int i=0; i<this.listenPorts.size(); i++ )
		{
			ListenPort listenPort = (ListenPort)listenPorts.get( i );
			listenPort.commProcessor = this.commProcessor;
			listenPort.packageReceiver = this;
			listenPort.setKeepAlive( this.keepAlive );
			listenPort.startUp();
		}
		for(int i=0; i<this.connectToHosts.size(); i++ )
		{
			IConnectToHostService connection = (IConnectToHostService)connectToHosts.get( i );
			connection.setCommProcessor(this.commProcessor);
		}
	}
	
	public void terminate()
	{
		for(int i=0; i<this.listenPorts.size(); i++ )
		{
			ListenPort listenPort = (ListenPort)listenPorts.get( i );
			listenPort.terminate();
		}
		for(int i=0; i<this.connectToHosts.size(); i++ )
		{
			IConnectToHostService connection = (IConnectToHostService)connectToHosts.get( i );
			connection.terminate();
		}		
	}

	public PackageProcessor getPackageProcessor() {
		return packageProcessor;
	}

	public void setPackageProcessor(PackageProcessor packageProcessor) {
		this.packageProcessor = packageProcessor;
	}

	public boolean isDual() {
		return dual;
	}

	public void setDual(boolean dual) {
		this.dual = dual;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public boolean isMBean() {
		return isMBean;
	}

	public void setMBean(boolean isMBean) {
		this.isMBean = isMBean;
	}

	public String getMBeanName() {
		return MBeanName;
	}

	public void setMBeanName(String beanName) {
		MBeanName = beanName;
	}

	public void setConnectToHost(IConnectToHostService connectToHost) {
		this.connectToHost = connectToHost;
		this.addConnectToHost(this.connectToHost);
	}

	public void setListenPort(ListenPort listenPort) {
		this.listenPort = listenPort;
		this.addListenPort(this.listenPort);
	}

	public void setListenPorts(List<ListenPort> listenPorts) {
		this.listenPorts = listenPorts;
	}

}
