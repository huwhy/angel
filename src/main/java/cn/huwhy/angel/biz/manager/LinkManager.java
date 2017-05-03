package cn.huwhy.angel.biz.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.dao.LinkDao;
import cn.huwhy.angel.enums.LinkType;
import cn.huwhy.angel.po.Link;

@Service
public class LinkManager {

    @Autowired
    private LinkDao linkDao;

    public List<Link> findLinks() {
        return linkDao.findLinksByUid(0, LinkType.navigation);
    }
}
