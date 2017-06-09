package bros.common.core.comm.tcpip.service;

import java.io.IOException;
import java.net.Socket;

import bros.common.core.comm.exception.TimeOutException;
import bros.common.core.comm.tcpip.PackageProcessThread;

public interface ITCPIPService {

	public void initialize();
	
	public byte[] sendAndWait(Object identity, byte[] msg, int timeOut )throws IOException, Exception;
	
	public void send(byte[] msg )throws IOException, Exception;
	
	public void send(byte[] msg, Socket socket )throws IOException, Exception;
	
	public byte[] receive(Object identity, long timeOut )throws TimeOutException;
	
	public void addNewMessage(byte[] msg );
	
	public void processNewPackage(byte[] msg, Socket socket ) throws Exception;
	
	public void packageProcessorThreadFree(PackageProcessThread thread );
	
	public void terminate();
}
