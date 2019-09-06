package com.xoxo.mail.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xoxo.mail.domain.MessageObj;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.xoxo.mail.util
 * @Description
 * @Author xiehua@zhongshuheyi.com
 * @Date 2019-08-29 15:51
 */
public class FileUtil {
    /**
     * <p>Title: toByteArrayNIO</p>
     * <p>Description: NIO way</p>
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArrayNIO(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException(filename);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while (channel.read(byteBuffer) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        String image1= Base64.getEncoder().encodeToString(toByteArrayNIO("C:\\Users\\a8932\\Desktop\\java基础语法1.pptx"));
        System.out.println(image1);
        Map<String,String> map = new HashMap<>();
        map.put("java基础语法1.pptx",image1);
        String s = JSONArray.toJSONString(map);
        System.out.println(s);
        MessageObj messageObj = new MessageObj();
        messageObj.setFilesMap(map);
        String s1 = JSONObject.toJSONString(messageObj);
        System.out.println(s1);
    }
}
