package com.iqiyi.elvis.znt.custom.service;

import org.apache.thrift.TException;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 15:29
 * Desc TODO
 */
public class RpcServiceImpl implements RpcService.Iface{
    public String rpc(String version) throws TException {
        System.out.println("RpcServiceImpl print : " + version);
        return "[rpc]Rpc return : " + version;
    }
}
