package cn.huwhy.angel.handler;

import org.springframework.stereotype.Component;

import cn.huwhy.wx.sdk.listener.Listener;
import cn.huwhy.wx.sdk.message.Message;
import cn.huwhy.wx.sdk.message.ReplyMsgBuilder;
import cn.huwhy.wx.sdk.message.TextMessage;
import cn.huwhy.wx.sdk.model.Command;

import static cn.huwhy.wx.sdk.model.Command.EVENT_KEYS.TEXT_MSG;

@Component
public class TextMsgListener extends Listener {
    @Override
    public String handle(Command command) {
        Message message = new TextMessage();
        message.setToUserName(command.getFromUserName());
        message.setFromUserName(command.getToUserName());
        message.setCreateTime(command.getCreateTime());
        if (command.getCommandKey().equals(TEXT_MSG) && command.getContent().equals("写")) {
            message.setContent("https://www.huwhy.cn/admin/mp-article/add2.html");
        } else {
            message.setContent("https://www.huwhy.cn/mp-article/5.html");
        }
        return ReplyMsgBuilder.toXml(message);
    }
}
