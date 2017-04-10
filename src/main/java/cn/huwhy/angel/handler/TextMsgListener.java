package cn.huwhy.angel.handler;

import org.springframework.stereotype.Component;

import cn.huwhy.wx.sdk.listener.Listener;
import cn.huwhy.wx.sdk.model.Command;

@Component
public class TextMsgListener extends Listener {
    @Override
    public String handle(Command command) {
        return "http://angel.huwhy.cn/mp-article/5.html";
    }
}
