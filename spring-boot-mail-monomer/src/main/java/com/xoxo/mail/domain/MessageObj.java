package com.xoxo.mail.domain;

import lombok.Data;

import java.util.Map;

/**
 * @Package com.xoxo.mail.domain
 * @Description  邮件发送类（可自定义）
 * @Author xiehua@zhongshuheyi.com
 * @Date 2019-08-29 15:57
 */
@Data
public class MessageObj {
    /**
     * 发送人
     */
    private String sender;
    /**
     * 接收人
     */
    private String receiver;
    /**
     * 内容
     */
    private String content;
    /**
     * 标题
     */
    private String subject;

    /**
     * key
     * 图片关系映射<id,BASE64.encode(byte[])>
     */
    private Map<String,String> imagesMap;

    /**
     * 附件关系映射<id,BASE64.encode(byte[])>
     */
    private Map<String,String> filesMap;
}
