package bros.common.core.comm.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import org.springframework.context.ApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.codec.NettyFixedLengthDecoder;
import bros.common.core.comm.codec.NettyFixedLengthEncoder;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;

/**
 * 
 * @ClassName: FixedLengthServerHandlerInitializer 
 * @Description: 定长报文解析器
 * @author 何鹏
 * @date 2016年6月30日 下午1:30:43 
 * @version 1.0
 */
public class FixedLengthServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
	private final ApplicationContext context;//spring容器
	private IBusinessProcessService businessProcessService;//处理器
	private AppConfig config;
	private ISeverFormatService formatService;//报文编码解码器
	
	public FixedLengthServerHandlerInitializer(final ApplicationContext context,IBusinessProcessService businessProcessService,AppConfig config,ISeverFormatService formatService) {
		this.context = context;
		this.businessProcessService = businessProcessService;
		this.config = config;
		this.formatService = formatService;
	}
	@Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new NettyFixedLengthDecoder(config.getHeadLength()));
        pipeline.addLast(new NettyFixedLengthEncoder(config.getHeadLength()));
        pipeline.addLast(new FixedLengthServerHandler(context,businessProcessService,formatService,config));
    }
}
