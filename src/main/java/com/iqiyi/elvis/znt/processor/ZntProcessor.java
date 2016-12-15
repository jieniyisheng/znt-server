package com.iqiyi.elvis.znt.processor;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;

import java.lang.reflect.Constructor;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 10:53
 * Desc TODO
 */
public class ZntProcessor {
    private String serviceName;
    private TProtocol inProtocol;
    private TProtocol outProtocol;

    public ZntProcessor(String serviceName, TProtocol inProtocol, TProtocol outProtocol) {
        this.serviceName = serviceName;
        this.inProtocol = inProtocol;
        this.outProtocol = outProtocol;
    }

    public void process() {
        try {
            Object serviceBean = Thread.currentThread().getContextClassLoader().loadClass(serviceName).newInstance();
            getTProcessor(serviceBean).process(inProtocol, outProtocol);
        } catch (Exception e) {
            throw new RuntimeException("processor process error ", e);
        }
    }

    public TProcessor getTProcessor(Object serviceBean) {
        TProcessor processor = null;
        try {
            Class<?>[] interfaces = serviceBean.getClass().getInterfaces();
            Class<?> innerClass = interfaces[0];
            Class processClass = Thread.currentThread().getContextClassLoader().loadClass(getProcessorName(innerClass));
            Constructor cons = processClass.getConstructor(new Class<?>[]{innerClass});
            processor = (TProcessor) cons.newInstance(new Object[]{serviceBean});
        } catch (Exception e) {
            throw new RuntimeException("processor instance error ", e);
        }
        return processor;
    }

    private String getProcessorName(Class<?> innerClass) {
        String thriftClassName = StringUtils.substringBeforeLast(innerClass.getName(), "$");
        return thriftClassName + "$Processor";
    }
}
