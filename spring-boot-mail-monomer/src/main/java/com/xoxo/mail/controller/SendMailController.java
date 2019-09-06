package com.xoxo.mail.controller;

import com.xoxo.mail.domain.MessageObj;
import com.xoxo.mail.service.ISendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.xoxo.mail.controller
 * @Description sendMailController
 * @Author xiehua@zhongshuheyi.com
 * @Date 2019-08-27 20:00
 */
@Slf4j
@RestController
public class SendMailController {

    @Autowired
    private ISendMailService sendMailService;

    /**
     * 发送文本邮件
     * @return
     */
    @PostMapping("/sendTextMail")
    public String sendTextMail(@RequestBody MessageObj messageObj) {
        log.info("发送文本邮件");
        sendMailService.sendTextMail(messageObj);
        return "success";
    }

    /**
     * 发送HTML邮件
     * @return
     */
    @PostMapping("/sendHtmlMail")
    public String testHtmlMail(@RequestBody MessageObj messageObj) {
        log.info("发送HTML邮件");
        sendMailService.sendHtmlMail(messageObj);
        return "success";
    }

    /**
     * 发送附件邮件
     * ps:支持多图片发送，图片内容需要经过base64加密
     * @return
     */
    @PostMapping("/sendOMFilesMail")
    public String sendOMFilesMail(@RequestBody MessageObj messageObj) {
        log.info("发送附件邮件");
        sendMailService.sendOMFilesMail(messageObj);
        return "success";
    }

    /**
     * 发送图片邮件
     * ps:支持多图片发送，图片内容需要经过base64加密
     * @return
     */
    @PostMapping("/sendInlineOMImagesMail")
    public String sendInlineOMImagesMail(@RequestBody  MessageObj messageObj){
        log.info("发送图片邮件");
        sendMailService.sendInlineOMImagesMail(messageObj);
        return "success";
    }
}
