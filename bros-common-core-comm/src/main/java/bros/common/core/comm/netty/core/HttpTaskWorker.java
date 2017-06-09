package bros.common.core.comm.netty.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;

/**
 * 
 * @ClassName: TaskWorker
 * @Description: 业务处理线程类
 * @author 何鹏
 * @date 2016年6月30日 下午1:43:28
 * @version 1.0
 */
public class HttpTaskWorker implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(HttpTaskWorker.class);
	private ChannelHandlerContext ctx;
	private Object object;
	private final ApplicationContext context;//spring容器
	private IBusinessProcessService businessProcessService;//处理器
	private ISeverFormatService formatService;//报文编码解码器
	private AppConfig config;//配置信息
	
	public HttpTaskWorker(final ApplicationContext context,IBusinessProcessService businessProcessService,ChannelHandlerContext ctx, ISeverFormatService formatService,Object object,AppConfig config) {
		this.context = context;
		this.businessProcessService = businessProcessService;
		this.ctx = ctx;
		this.formatService = formatService;
		this.object = object;
		this.config = config;
	}

	@Override
	public void run() {
		logger.info("http recieve format:{}",object);
		String returnMessage = MessageProcess.process(context, businessProcessService, formatService, object,config);
		logger.info("http send format:{}",returnMessage);
		writeJSON(ctx, HttpResponseStatus.OK,Unpooled.copiedBuffer(returnMessage, CharsetUtil.UTF_8));
	}
	
	
	private void writeJSON(ChannelHandlerContext ctx,
			HttpResponseStatus status, ByteBuf content/* , boolean isKeepAlive */) {
		if (ctx.channel().isWritable()) {
			FullHttpResponse msg = null;
			if (content != null) {
				msg = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
						content);
				msg.headers().set(HttpHeaders.Names.CONTENT_TYPE,
						"application/json; charset=utf-8");
			} else {
				msg = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
			}
			if (msg.content() != null) {
				msg.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
						msg.content().readableBytes());
			}

			// not keep-alive
			ctx.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
		}
	}

}
