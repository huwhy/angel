package cn.huwhy.angel.handler;

import org.springframework.stereotype.Component;

import cn.huwhy.wx.sdk.listener.Listener;
import cn.huwhy.wx.sdk.message.Message;
import cn.huwhy.wx.sdk.message.ReplyMsgBuilder;
import cn.huwhy.wx.sdk.message.TextMessage;
import cn.huwhy.wx.sdk.model.Command;

@Component
public class TextMsgListener extends Listener {
    @Override
    public String handle(Command command) {
        Message message = new TextMessage();
        message.setToUserName(command.getFromUserName());
        message.setFromUserName(command.getToUserName());
        message.setContent("http://angel.huwhy.cn/mp-article/5.html");
        return ReplyMsgBuilder.toXml(message);
    }
}
