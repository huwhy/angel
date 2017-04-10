package cn.huwhy.angel.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.huwhy.wx.sdk.listener.EventHandler;
import cn.huwhy.wx.sdk.message.Message;

@Configuration
public class HandlerConfig {

    @Bean
    @Autowired
    public EventHandler eventHandler(TextMsgListener textListener) {
        EventHandler handler = new EventHandler();
        handler.register(Message.EVENT_KEYS.TEXT_MSG, textListener);
        return handler;
    }

}
