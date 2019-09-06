package com.xoxo.mail.contants;

import lombok.Getter;

/**
 * @Package com.xoxo.mail.contants
 * @Description
 * @Author xiehua@zhongshuheyi.com
 * @Date 2019-09-03 14:39
 */
@Getter
public enum MessageAPType {

    PICTURE("picture"),
    ATTACHMENT("attachment"),

    ;

    private String type;

    MessageAPType(String type){
        this.type = type;
    }
}
