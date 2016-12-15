package com.iqiyi.elvis.znt.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.*;
import java.util.Enumeration;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 12:11
 * Desc TODO
 */
public class NetworkUtils {
    public static String getLocalHost() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }

    }

    public static String getLocalAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }

    }

    /**
     * 获取内网的ip地址，取第一块网卡的地址
     * @return
     */
    public static String getEth0Address() {
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) netInterfaces.nextElement();
                if (StringUtils.equals(netInterface.getName(), "eth0")) {
                    Enumeration addresses = netInterface.getInetAddresses();
                    InetAddress address = null;
                    while (addresses.hasMoreElements()) {
                        address = (InetAddress) addresses.nextElement();
                        if (address != null && address instanceof Inet4Address) {
                            return address.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            new RuntimeException("getEth0Address fail." + e.getMessage(), e);
        }
        //找不到的话取本机的地址
        return getLocalAddress();
    }
}
