package cn.huwhy.angel.biz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.huwhy.angel.dao.ItemDao;
import cn.huwhy.angel.po.Item;

@Service
public class ItemManager {

    @Autowired
    private ItemDao itemDao;

    @Transactional
    public void save(Item item) {
        itemDao.save(item);
        itemDao.saveContent(item);
    }

    public Item get(Long id) {
        return itemDao.get(id);
    }

}
