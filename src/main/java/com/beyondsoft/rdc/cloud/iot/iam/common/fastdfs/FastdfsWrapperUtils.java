package com.beyondsoft.rdc.cloud.iot.iam.common.fastdfs;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Jsy
 * @Date ${Date}
 * @ /**
 * .
 * @date: 2019-07-12
 * @version: 1.0
 * @author: jiangshuyi@beyondsoft.com
 */
public class FastdfsWrapperUtils {
    private final static String charsetName ="UTF-8";
    private final static String secret_key ="FastDFS178239349";

    public static String getToken(String remote_filename, int ts) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String substring = remote_filename.substring(remote_filename.indexOf("/")+1);
        System.out.println(substring);
        byte[] bsFilename = substring.getBytes(charsetName);
        byte[] bsKey = secret_key.getBytes(charsetName);
        byte[] bsTimestamp = (new Integer(ts)).toString().getBytes(charsetName);

        byte[] buff = new byte[bsFilename.length + bsKey.length + bsTimestamp.length];
        System.arraycopy(bsFilename, 0, buff, 0, bsFilename.length);
        System.arraycopy(bsKey, 0, buff, bsFilename.length, bsKey.length);
        System.arraycopy(bsTimestamp, 0, buff, bsFilename.length + bsKey.length, bsTimestamp.length);

        return md5(buff);
    }

    public static String md5(byte[] source) throws NoSuchAlgorithmException {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        md.update(source);
        byte tmp[] = md.digest();
        char str[] = new char[32];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            str[k++] = hexDigits[tmp[i] >>> 4 & 0xf];
            str[k++] = hexDigits[tmp[i] & 0xf];
        }

        return new String(str);
    }
}
