package com.xoxo.mail.service.impl;

import com.xoxo.mail.MailApplicationTests;
import com.xoxo.mail.domain.MessageObj;
import com.xoxo.mail.service.ISendMailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Package com.xoxo.mail.service.impl
 * @Description
 * @Author xiehua@zhongshuheyi.com
 * @Date 2019-08-29 16:59
 */
public class SendMailServiceImplTest extends MailApplicationTests{

    @Autowired
    private ISendMailService sendMailService;

    /**
     * 异步无法发送
     * @throws Exception
     */
    @Test
    public void sendTextMail() throws Exception{
        MessageObj messageObj = new MessageObj();
        messageObj.setSender("893231722@qq.com");
        messageObj.setReceiver("893231722@qq.com");
        messageObj.setSubject("xoxo's Subject");
        messageObj.setContent("xoxo's Content");
        sendMailService.sendTextMail(messageObj);
    }

}