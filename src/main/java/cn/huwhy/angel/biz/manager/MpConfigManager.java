package cn.huwhy.angel.biz.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.dao.MpConfigDao;
import cn.huwhy.angel.po.MpConfig;
import cn.huwhy.angel.term.MpConfigTerm;

@Component
public class MpConfigManager {

    @Autowired
    private MpConfigDao mpConfigDao;

    public void save(MpConfig po) {
        mpConfigDao.save(po);
    }

    public MpConfig get(Integer id) {
        return mpConfigDao.get(id);
    }

    public Paging<MpConfig> findPaging(MpConfigTerm term) {
        if (!Strings.isNullOrEmpty(term.getName())) {
            term.setName(term.getName() + "%");
        }
        List<MpConfig> list = mpConfigDao.findPaging(term);
        return new Paging<>(term, list);
    }

}