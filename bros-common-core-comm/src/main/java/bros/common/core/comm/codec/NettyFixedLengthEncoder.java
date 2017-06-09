package bros.common.core.comm.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyFixedLengthEncoder extends MessageToByteEncoder<Object > {

	private int HEAD_LENGTH = 8;
	public NettyFixedLengthEncoder(int HEAD_LENGTH) {
		this.HEAD_LENGTH = HEAD_LENGTH;
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		byte[] body = msg.toString().getBytes();
        int dataLength = body.length;  //读取消息的长度
        String head = String.valueOf(dataLength);
        int reduce = HEAD_LENGTH-head.length();
        
        for(int i=0;i<reduce;i++){
        	head = "0"+head;
        }

        out.writeBytes(head.getBytes());  //先将消息长度写入，也就是消息头
        out.writeBytes(body);  //消息体中包含我们要发送的数据
	}
}
