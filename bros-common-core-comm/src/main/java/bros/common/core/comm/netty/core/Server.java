package bros.common.core.comm.netty.core;
/**
 * 
 * @ClassName: Server 
 * @Description: 服务启动停止接口
 * @author 何鹏
 * @date 2016年6月30日 上午10:58:34 
 * @version 1.0
 */
public interface Server {

    /**
     * 启动服务器
     */
    public void start();

    /**
     * 停止服务器
     */
    public void stop();
}
