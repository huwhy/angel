package cn.huwhy.angel.dao;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.Item;

public interface ItemDao extends BaseDao<Item, Long> {

    void saveContent(Item item);

    String getContent(@Param("id") Long id);
}
