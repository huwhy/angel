package cn.huwhy.angel.biz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.dao.MpReplyDao;
import cn.huwhy.angel.po.MpReply;

@Service
public class MpReplyManager {

    @Autowired
    private MpReplyDao mpReplyDao;

    public MpReply getByKeyword(String keyword) {
        return mpReplyDao.getByKeyword(keyword);
    }

}
