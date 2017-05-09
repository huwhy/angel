package cn.huwhy.angel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MpReplyManager mpReplyManager;

    @Override
    public String handle(Command command) {
        logger.info("command: {}", JSON.toJSONString(command));
        if (command.getCommandKey().equals(TEXT_MSG)) {
            Message message = new TextMessage();
            message.setToUserName(command.getFromUserName());
            message.setFromUserName(command.getToUserName());
            message.setCreateTime(command.getCreateTime());
            MpReply reply = mpReplyManager.getByKeyword(command.getContent());
            if (reply != null) {
                message.setContent(reply.getContent());
                return ReplyMsgBuilder.toXml(message);
            }
        }
        return "";
    }
}
