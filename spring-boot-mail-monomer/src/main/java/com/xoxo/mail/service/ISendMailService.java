package com.xoxo.mail.service;

import com.xoxo.mail.domain.MessageObj;

/**
 * @Package com.xoxo.mail.service
 * @Description 邮件发送接口定义
 * @Author xiehua@zhongshuheyi.com
 * @Date 2019-08-28 10:50
 */
public interface ISendMailService {

    void sendTextMail(MessageObj messageObj);

    void sendHtmlMail(MessageObj messageObj);

    void sendOMFilesMail(MessageObj messageObj);

    void sendInlineOMImagesMail(MessageObj messageObj);
}
