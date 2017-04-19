package cn.huwhy.angel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.Category;

public interface CategoryDao extends BaseDao {

    List<Category> findFirsts(@Param("level") int level, @Param("size") int size);

    List<Category> findChildren(@Param("pid") int pid);
}
