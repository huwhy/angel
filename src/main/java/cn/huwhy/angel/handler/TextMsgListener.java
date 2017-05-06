package cn.huwhy.angel.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.huwhy.angel.biz.manager.MpReplyManager;
import cn.huwhy.angel.po.MpReply;
import cn.huwhy.wx.sdk.listener.Listener;
import cn.huwhy.wx.sdk.message.Message;
import cn.huwhy.wx.sdk.message.ReplyMsgBuilder;
import cn.huwhy.wx.sdk.message.TextMessage;
import cn.huwhy.wx.sdk.model.Command;

import static cn.huwhy.wx.sdk.model.Command.EVENT_KEYS.TEXT_MSG;

@Component
public class TextMsgListener extends Listener {

    @Autowired
    private MpReplyManager mpReplyManager;

    @Override
    public String handle(Command command) {
        Message message = new TextMessage();
        message.setToUserName(command.getFromUserName());
        message.setFromUserName(command.getToUserName());
        message.setCreateTime(command.getCreateTime());
        if (command.getCommandKey().equals(TEXT_MSG)) {
            MpReply reply = mpReplyManager.getByKeyword(message.getContent());
            if (reply != null) {
                message.setContent(reply.getContent());
            } else {
                message.setContent("success");
            }
        }
        return ReplyMsgBuilder.toXml(message);
    }
}
