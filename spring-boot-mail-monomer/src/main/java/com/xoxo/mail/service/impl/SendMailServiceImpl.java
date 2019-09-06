package com.xoxo.mail.service.impl;

import com.xoxo.mail.domain.MessageObj;
import com.xoxo.mail.service.ISendMailService;
import com.xoxo.mail.util.ContentTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Package com.xoxo.mail.service.impl
 * @Description 邮件发送Service实现类
 * @Author xiehua@zhongshuheyi.com
 * @Date 2019-08-28 10:50
 */
@Slf4j
@Service
public class SendMailServiceImpl implements ISendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送文本邮件
     * @return
     */
    @Async
    public void sendTextMail(MessageObj messageObj) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(messageObj.getSender());
        message.setTo(messageObj.getReceiver());
        message.setSubject(messageObj.getSubject());
        message.setText(messageObj.getContent());
        try {
            javaMailSender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
    }

    /**
     * 发送HTML邮件
     * @return
     */
    @Async
    public void sendHtmlMail(MessageObj messageObj) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(messageObj.getSender());
            helper.setTo(messageObj.getReceiver());
            helper.setSubject(messageObj.getSubject());
            helper.setText(messageObj.getContent(),true);
            javaMailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！", e);
        }
    }

    /**
     * 支持多附件发送
     * @param messageObj
     */
    @Async
    public void sendOMFilesMail(MessageObj messageObj) {
        MimeMessage message = javaMailSender.createMimeMessage();
        Map<String, ByteArrayDataSource> stringByteArrayDataSourceMap = convertByteArrayDataSourceMap(messageObj.getFilesMap());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(messageObj.getSender());
            helper.setTo(messageObj.getReceiver());
            helper.setSubject(messageObj.getSubject());
            helper.setText(messageObj.getContent(), true);
            for(Map.Entry<String,ByteArrayDataSource> sourceEntry:stringByteArrayDataSourceMap.entrySet()){
                helper.addAttachment(sourceEntry.getKey(),sourceEntry.getValue());
            }
            javaMailSender.send(message);
            log.info("带附件的邮件已经发送。");
        }catch (IllegalArgumentException e) {
            log.error("附件内容异常！",e);
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
    }

    /**
     * 支持多图片发送
     * @param messageObj
     */
    @Async
    public void sendInlineOMImagesMail(MessageObj messageObj) {
        MimeMessage message = javaMailSender.createMimeMessage();
        Map<String, String> imagesMap = messageObj.getImagesMap();
        Map<String, ByteArrayDataSource> stringByteArrayDataSourceMap = convertByteArrayDataSourceMap(imagesMap);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(messageObj.getSender());
            helper.setTo(messageObj.getReceiver());
            helper.setSubject(messageObj.getSubject());
            helper.setText(messageObj.getContent(), true);
            for (Map.Entry<String, ByteArrayDataSource> sourceEntry : stringByteArrayDataSourceMap.entrySet()) {
                helper.addInline(sourceEntry.getKey(), sourceEntry.getValue());
            }
            javaMailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (IllegalArgumentException e) {
            log.error("图片内容异常！",e);
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
        } catch (Exception e) {
            log.error("获取文件流异常！", e);
        }
    }

    /**
     * base64encode->ByteArrayDataSource
     *
     * @param imagesMap
     * @return
     */
    private Map<String, ByteArrayDataSource> convertByteArrayDataSourceMap(Map<String, String> imagesMap) {
        Map<String, ByteArrayDataSource> stringByteArrayDataSourceMap = imagesMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey
                        , e -> {
                            byte[] decode = Base64.getDecoder().decode(e.getValue());
                            InputStream byteArrayInputStream = new ByteArrayInputStream(decode);
                            ByteArrayDataSource byteArrayDataSource = null;
                            try {
                                //自动根据文件后缀获取contentType
                                byteArrayDataSource = new ByteArrayDataSource(byteArrayInputStream, ContentTypeUtil.getContentTypeByExt(e.getKey()));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            return byteArrayDataSource;
                        }));
        return stringByteArrayDataSourceMap;
    }
}
