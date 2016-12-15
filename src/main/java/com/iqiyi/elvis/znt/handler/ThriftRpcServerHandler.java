package com.iqiyi.elvis.znt.handler;

import com.iqiyi.elvis.znt.processor.ZntProcessor;
import com.iqiyi.elvis.znt.transport.ZntRpcTransport;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

/**
 * @author elviswang
 * @date 2016/12/12
 * @time 15:13
 * Desc TODO
 */
public class ThriftRpcServerHandler extends ChannelInboundHandlerAdapter {
    private String serviceName;

    public ThriftRpcServerHandler(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        TTransport transport = new ZntRpcTransport(ctx.channel(), byteBuf);
        new ZntProcessor(serviceName, getTProtocol(transport), getTProtocol(transport)).process();
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private TProtocol getTProtocol(TTransport transport) {
        return new TBinaryProtocol.Factory(true, true).getProtocol(transport);
    }
}
