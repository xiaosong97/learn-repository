package com.ekkosong.nettydemo.client;

import com.ekkosong.nettydemo.codec.NettyKryoDecoder;
import com.ekkosong.nettydemo.codec.NettyKryoEncoder;
import com.ekkosong.nettydemo.dto.RpcRequest;
import com.ekkosong.nettydemo.dto.RpcResponse;
import com.ekkosong.nettydemo.serialize.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {
    private final String host;
    private final int port;
    public static final Bootstrap b;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // 初始化相关资源比如 EventLoopGroup, Bootstrap
    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        KryoSerializer kryoSerializer = new KryoSerializer();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        /**
                         * 自定义序列化解编码器
                         */
                        socketChannel.pipeline().addLast(new NettyKryoDecoder(kryoSerializer, RpcResponse.class));
                        socketChannel.pipeline().addLast(new NettyKryoEncoder(kryoSerializer, RpcRequest.class));
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }

    /**
     * 发送消息到服务端
     *
     * @param request
     * @return
     */
    public RpcResponse sendMessage(RpcRequest request) {
        try {
            ChannelFuture f = b.connect(host, port).sync();
            log.info("client connect {}", host + ":" + port);
            Channel futureChannel = f.channel();
            log.info("send message");
            if (futureChannel != null) {
                futureChannel.writeAndFlush(request).addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("client send message:[{}]", request.toString());
                    } else {
                        log.error("Send failed:", future.cause());
                    }
                });
                // 阻塞等待，直到channel关闭
                futureChannel.closeFuture().sync();
                // 将服务端数据也就是RpcResponse取出
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                return futureChannel.attr(key).get();
            }
        } catch (InterruptedException e) {
            log.error("Exception occurs when connecting server:", e);
        }
        return null;
    }

    public static void main(String[] args) {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("interface")
                .methodName("hello").build();
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8889);
        for (int i = 0; i < 3; i++) {
            nettyClient.sendMessage(rpcRequest);
        }
        RpcResponse rpcResponse = nettyClient.sendMessage(rpcRequest);
        System.out.println(rpcResponse.toString());
    }
}
