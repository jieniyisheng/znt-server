package com.iqiyi.elvis.znt.init;

import com.iqiyi.elvis.znt.register.Register;
import com.iqiyi.elvis.znt.register.ZkRegister;
import com.iqiyi.elvis.znt.server.ZntRpcServer;
import com.iqiyi.elvis.znt.utils.NetworkUtils;
import com.iqiyi.elvis.znt.zk.ServerNodeConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 10:35
 * Desc TODO
 */
public class RpcServer {
    private List<ZntRpcServer> zntRpcServerList;

    public void setZntRpcServerList(List<ZntRpcServer> zntRpcServerList) {
        this.zntRpcServerList = zntRpcServerList;
    }

    public void init() {
        try {
            for (ZntRpcServer zntRpcServer : zntRpcServerList) {
                zntRpcServer.start();

                registerService(zntRpcServer);
            }
        } catch (Exception e) {
            throw new RuntimeException("rpc server init error ", e);
        }
    }

    private void registerService(ZntRpcServer zntRpcServer) throws ClassNotFoundException {
        Register register = new ZkRegister();
        String innerClassName = Thread.currentThread().getContextClassLoader().loadClass(zntRpcServer.getServiceClassName()).getInterfaces()[0].getName();
        String thriftClassName = Thread.currentThread().getContextClassLoader().loadClass(StringUtils.split(innerClassName, '$')[0]).getSimpleName();
        register.register(new ServerNodeConfig("ZNT", NetworkUtils.getEth0Address(), zntRpcServer.getPort(), "ZNT_" + thriftClassName, "/znt/rpc/"));
    }
}
