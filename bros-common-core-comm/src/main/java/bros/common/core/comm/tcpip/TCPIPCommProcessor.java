package bros.common.core.comm.tcpip;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.exception.FormatException;

/**
 * 
 * @ClassName: TCPIPCommProcessor
 * @Description: TCP/IP通信报文处理类
 * @author huangcanhui
 * @date 2015年6月14日 下午3:33:52
 * @version 3.0
 */
public class TCPIPCommProcessor implements CommProcessor {

	private static final Logger logger = LoggerFactory
			.getLogger(TCPIPCommProcessor.class);

	/**
	 * 报文内容长度的字节数
	 */
	private int lengthHeadLen = 8;

	private int lengthHeadType = 0; // 0: ascii, 1: bin

	/**
	 * 字符集 默认UTF-8
	 */
	private String encoding = "UTF-8";

	/**
	 * 从输入流中读取数据包，并进行通讯协议处理。
	 */
	public byte[] readPackage(InputStream in) throws IOException,
			FormatException {
		byte[] lenHeadBuf = new byte[lengthHeadLen];
		int off = 0;
		// 获得包头
		while (off < lengthHeadLen) {
			off = off + in.read(lenHeadBuf, off, lengthHeadLen - off);
			if (off < 0) {
				logger.error("Socket was closed! while reading!");
				throw new FormatException(CommErrorCodeConstants.CCCM0003,"正在读取的时候连接关闭了");
			}
		}

		logger.info("TCPIPCommProcessor read in head:"
				+ new String(lenHeadBuf, encoding));

		int contentLength = 0;
		if (lengthHeadType == 0) {
			contentLength = Integer.parseInt(new String(lenHeadBuf, encoding));
		} else // bin
		{
			for (int i = lengthHeadLen - 1; i >= 0; i--) {
				int value = (int) (lenHeadBuf[i] & 0xff);
				contentLength = contentLength * 256 + value;
			}
		}

		if (contentLength == 0) {
			logger.error("Invalid TCPIP package protocol!");
			throw new FormatException(CommErrorCodeConstants.CCCM0004,"无效的TCPIP包协议");
		}

		// 获得包体内容开始
		off = 0;
		byte[] contentBuf = new byte[contentLength];
		while (off < contentLength) {

			int len = in.read(contentBuf, off, contentLength - off);
			if (len <= 0) {
				break;
			}
			off = off + len;
		}

		logger.info("TCPIPCommProcessor Read in package As: "
				+ new String(contentBuf, encoding));

		return contentBuf;
	}

	/**
	 * 根据通信协议对数据包进行打包，如加入通信报文头
	 */
	public byte[] wrapMessagePackage(byte[] msg) {
		if (msg == null)
			return msg;

		int length = msg.length;
		byte[] buf = new byte[length + this.lengthHeadLen];

		System.arraycopy(msg, 0, buf, lengthHeadLen, length);

		if (this.lengthHeadType == 0) {
			String lenValue = String.valueOf(length);
			for (int i = 0; i < lengthHeadLen; i++)
				buf[i] = '0';
			int idx = 0;
			for (int i = lengthHeadLen - lenValue.length(); i < lengthHeadLen; i++) {
				buf[i] = (byte) lenValue.charAt(idx++);
			}

			try {
				logger.info("TCPIPCommProcessor Write in package As: "
						+ new String(buf, this.encoding));
			} catch (Exception e) {
				logger.error(
						"Exception from TCPIPCommProcessor wrapMessagePackage method.",
						e);
			}

			return buf;
		} else // bin
		{
			int len = length;
			for (int i = 0; i < lengthHeadLen; i++) {
				buf[i] = (byte) (len % 256);
				len = len / 256;
			}

			return buf;
		}
	}

	public int getLengthHeadLen() {
		return lengthHeadLen;
	}

	public void setLengthHeadLen(int lengthHeadLen) {
		this.lengthHeadLen = lengthHeadLen;
	}

	public int getLengthHeadType() {
		return lengthHeadType;
	}

	public void setLengthHeadType(int lengthHeadType) {
		this.lengthHeadType = lengthHeadType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public int getHeadLengthMethod() {
		return this.lengthHeadLen;
	}

	@Override
	public String getEncodingMethod() {
		return this.encoding;
	}

}
