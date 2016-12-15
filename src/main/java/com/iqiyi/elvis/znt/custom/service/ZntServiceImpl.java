package com.iqiyi.elvis.znt.custom.service;

import org.apache.thrift.TException;

/**
 * @author elviswang
 * @date 2016/12/7
 * @time 11:18
 * Desc TODO
 */
public class ZntServiceImpl implements ZntService.Iface {
    @Override
    public String znt(String version) throws TException {
        System.out.println("ZntServiceImpl print : " + version);
        return "[znt]Rpc return : " + version;
    }
}
