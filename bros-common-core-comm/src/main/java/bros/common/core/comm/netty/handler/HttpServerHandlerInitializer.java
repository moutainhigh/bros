package bros.common.core.comm.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import org.springframework.context.ApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;
/**
 * 
 * @ClassName: HttpServerHandlerInitializer 
 * @Description: http报文解析器
 * @author 何鹏
 * @date 2016年6月30日 下午3:52:56 
 * @version 1.0
 */
public class HttpServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
	private final ApplicationContext context;//spring容器
	private IBusinessProcessService businessProcessService;//处理器
	private ISeverFormatService formatService;//报文编码解码器
	private AppConfig config;
	public HttpServerHandlerInitializer(final ApplicationContext context,IBusinessProcessService businessProcessService,ISeverFormatService formatService,AppConfig config) {
		this.context = context;
		this.businessProcessService = businessProcessService;
		this.formatService = formatService;
		this.config = config;
	}
	@Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 服务端，对请求解码  
        pipeline.addLast("http-decoder",new HttpRequestDecoder());  
        // 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse  
        pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));  
        // 服务端，对响应编码  
        pipeline.addLast("http-encoder",new HttpResponseEncoder());  
        // 块写入处理器  
        //pipeline.addLast("http-chunked",new ChunkedWriteHandler());  
        // 自定义服务端处理器  
        pipeline.addLast("handler",new HttpServerHandler(context,businessProcessService,formatService,config)); 
    }
}
