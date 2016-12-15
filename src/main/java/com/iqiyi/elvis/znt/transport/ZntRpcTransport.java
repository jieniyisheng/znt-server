package com.iqiyi.elvis.znt.transport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author elviswang
 * @date 2016/12/12
 * @time 15:33
 * Desc TODO
 */
public class ZntRpcTransport extends TTransport {
    private Channel channel;

    private ByteBuf in;

    private ByteBuf out;

    public ZntRpcTransport(Channel channel, ByteBuf in) {
        this.channel = channel;
        this.in = in;
        this.out = Unpooled.buffer(1024);
    }

    @Override
    public boolean isOpen() {
        return channel.isOpen();
    }

    @Override
    public void open() throws TTransportException {

    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public int read(byte[] buf, int off, int len) throws TTransportException {
        int _read = Math.min(in.readableBytes(), len);
        in.readBytes(buf, off, _read);
        return _read;
    }

    @Override
    public void write(byte[] buf, int off, int len) throws TTransportException {
        out.writeBytes(buf, off, len);
    }

    @Override
    public void flush()
            throws TTransportException {
        channel.write(out);
    }
}
