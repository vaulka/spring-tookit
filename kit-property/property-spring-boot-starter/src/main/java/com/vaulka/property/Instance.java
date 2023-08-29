package com.vaulka.property;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 实例 ID 生成器
 *
 * @author Vaulka
 */
public class Instance {

    /**
     * 获取实例 ID
     * <p>
     * 根据 hostName、hostAddress 生成，保证重复获取时，都是同一个实例 ID
     *
     * @return 实例 ID
     */
    public String getId() throws UnknownHostException, NoSuchAlgorithmException {
        InetAddress address = InetAddress.getLocalHost();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(address.getHostName().getBytes());
        messageDigest.update(address.getHostAddress().getBytes());
        byte[] bytes = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte value : bytes) {
            String haxHex = Integer.toHexString(value & 0xFF);
            if (haxHex.length() < 2) {
                stringBuilder.append("0");
            }
            stringBuilder.append(haxHex);
        }
        return Long.valueOf(stringBuilder.substring(20), 16).toString();
    }

}
