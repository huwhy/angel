package cn.huwhy.angel.biz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.huwhy.angel.dao.ShopDao;
import cn.huwhy.angel.po.Shop;

@Service
public class ShopManager {

    @Autowired
    private ShopDao shopDao;

    @Transactional
    public void save(Shop shop) {
        shopDao.save(shop);
    }

    public Shop get(Long id) {
        return shopDao.get(id);
    }

}
