package bros.common.core.comm.tcpip.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.exception.CommunicationException;
import bros.common.core.comm.tcpip.CommProcessor;
import bros.common.core.comm.tcpip.HostConnection;
import bros.common.core.comm.tcpip.service.IConnectToHostService;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ConnectToHostServiceImpl 
 * @Description: 连接到某个TCP/IP服务器的处理类 
 * @author huangcanhui 
 * @date 2011-9-20 上午09:20:37 
 * @version 1.0
 */
public class ConnectToHostServiceImpl implements IConnectToHostService
{
	private static final Logger logger = LoggerFactory.getLogger(ConnectToHostServiceImpl.class);
	
	protected String hostAddr;
	protected int port = 0;
	protected boolean keepAlive = false;
	
	//最近一次交易时间
	private int lastCommTime = 0;
	
	//是否需要按照最近的交易时间控制最大连接数
	private boolean connectionCtrl = false;
	
	//按照交易时间控制最大连接数时的斜率
	private float rate;
	
	public CommProcessor commProcessor;
	
	//最大连接数
	private int maxConnection = 10;
	
	private List<HostConnection> connections;
	
	private int inUsedConnection = 0;
	
	public ConnectToHostServiceImpl() 
	{
		super();
		connections = new ArrayList<HostConnection>();
	}

	public ConnectToHostServiceImpl(String hostAddr, int port ) 
	{
		super();
		connections = new ArrayList<HostConnection>();
		this.hostAddr = hostAddr;
		this.port = port;
	}
	
	public void setHostAddr(String hostAddr) 
	{
		this.hostAddr = hostAddr;
	}
	
	public void setKeepAlive(boolean keepAlive) 
	{
		this.keepAlive = keepAlive;
	}
	
	public void setPort(int port) 
	{
		this.port = port;
	}
	
	public void send(byte[] msg) throws ServiceException
	{
		HostConnection connection = null;
		try{
			connection = this.getConnection();
			connection.send( msg );
		}
		catch(Exception e)
		{
			logger.error("Exception in send message to " + hostAddr, e );
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常",e);
		}
		finally
		{
			if( connection != null )
				this.releaseConnection( connection );
		}
	}

	public byte[] receive(Object identity, int timeOut) throws ServiceException
	{		
		HostConnection connection = null;
		try{
			connection = this.getConnection();
			InputStream in = (InputStream)connection.getIn();
			byte[] retMsg = this.commProcessor.readPackage( in );
			
			return retMsg;
		}
		catch(Exception e)
		{
			logger.error("Exception in send message to " + hostAddr, e );
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常",e);
		}
		finally
		{
			if( connection != null )
				this.releaseConnection( connection );
		}		
	}
	
	public byte[] sendAndWait(byte[] msg, int timeOut ) throws ServiceException
	{
		HostConnection connection = null;
		try{
			long beg = System.currentTimeMillis();
			connection = this.getConnection();
			byte[] retMsg = connection.sendAndWait( msg, timeOut );
			this.lastCommTime = (int)(System.currentTimeMillis() - beg);
			return retMsg;
		}catch(CommunicationException ire)	//time Out
		{

			//"Time out in read socket to " + hostAddr + ":" + port , ire );
			throw ire;
		}
		catch(Exception e)
		{
			logger.error("Exception in send message to " + hostAddr, e );
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常",e);
		}
		finally
		{
			if( connection != null ){
				this.releaseConnection( connection );
			}
		}
	}
	
	
	/**
	 * 得到当前可以建立的最大连接数
	 * @return
	 */
	private int getAllowedConnections()
	{
		if( ! connectionCtrl )
			return maxConnection;
		else
		{
			int value =(int)( maxConnection - this.rate * this.lastCommTime/1000);
			if( value <= 0 )
				value = 0;
			return value;
		}
	}
	
	private synchronized HostConnection getConnection() throws ServiceException
	{
		int max = getAllowedConnections();
		if( inUsedConnection >= max )
		{
			logger.error("Connection over the allowed connection! maxConnection=" + this.maxConnection + " Allowed connection=" + max);
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常");
		}

		for( int i=0; i<this.connections.size(); i++)
		{
			HostConnection connection = (HostConnection)connections.get( i );
			if( ! connection.isInUse() )
			{
				connection.setInUse( true );
				inUsedConnection++;
				return connection;
			}
		}
		
		if( connections.size() < maxConnection || maxConnection <= 0)
		{
			HostConnection connection = new HostConnection(this.hostAddr, this.port, this.commProcessor, keepAlive,commProcessor.getEncodingMethod(),commProcessor.getHeadLengthMethod());
			connection.setInUse( true );
			connections.add( connection );
			inUsedConnection++;
			return connection;
		}
		
		logger.error("Connection over the allowed max connection! maxConnection=" + this.maxConnection + " Allowed connection=" + max);
		throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常");
	}
	
	
	private synchronized  void  releaseConnection( HostConnection connection )
	{
		connection.setInUse( false );
		inUsedConnection--;
		notify();
	}
	
	public void terminate()
	{
		for( int i=0; i<this.connections.size(); i++)
		{
			HostConnection connection = (HostConnection)connections.get( i );
			connection.terminate();
		}
	}
	
	public Object clone()
	{
		ConnectToHostServiceImpl host = new ConnectToHostServiceImpl();
		host.hostAddr = hostAddr;
		host.port = port;
		host.maxConnection = maxConnection;
		host.keepAlive = keepAlive;
		host.commProcessor = commProcessor;
		host.connectionCtrl = connectionCtrl;
		host.rate = rate;
		return host;
	}

	public CommProcessor getCommProcessor() {
		return commProcessor;
	}

	public void setCommProcessor(CommProcessor commProcessor) {
		this.commProcessor = commProcessor;
	}
	
	public void setMaxConnection(int maxConnection){
		this.maxConnection = maxConnection;
	}
	
}
