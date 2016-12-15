package com.iqiyi.elvis.znt.register;

import com.alibaba.fastjson.JSON;
import com.iqiyi.elvis.znt.zk.ServerNodeConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 11:37
 * Desc TODO
 */
public class ZkRegister implements Register {
    private static final Logger LOG = LoggerFactory.getLogger(ZkRegister.class);

    private final String CONNECT_STRING = "10.1.2.125:2181,10.1.2.125:2182,10.1.2.125:2183";

    public void register(ServerNodeConfig serverNodeConfig) {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(CONNECT_STRING)
                .sessionTimeoutMs(3000)
                .connectionTimeoutMs(3000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .defaultData(null)
                .build();
        client.start();
        try {
            if (null != client.checkExists().forPath(serverNodeConfig.buildPath())) {
                client.delete().deletingChildrenIfNeeded().forPath(serverNodeConfig.buildPath());
            }
            client.create().creatingParentsIfNeeded().forPath(serverNodeConfig.buildPath(), JSON.toJSONString(serverNodeConfig).getBytes());
            LOG.info("zk register finish, path = {}", serverNodeConfig.buildPath());
            System.out.println("zk register finish, path = " + serverNodeConfig.buildPath());
        } catch (Exception e) {
            throw new RuntimeException("rpc server register error ", e);
        }
    }
}
