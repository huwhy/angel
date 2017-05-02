package cn.huwhy.angel.biz.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.dao.CategoryDao;
import cn.huwhy.angel.po.Category;
import cn.huwhy.angel.term.CategoryTerm;

@Component
public class CategoryManager {

    @Autowired
    private CategoryDao categoryDao;

    public Integer save(Category category) {
        categoryDao.save(category);
        return category.getId();
    }

    public Category get(Integer id) {
        return categoryDao.get(id);
    }

    public List<Category> findFirsts(Integer level, int size) {
        return categoryDao.findFirsts(level, size);
    }

    public List<Category> findChildren(Integer pid) {
        return categoryDao.findChildren(pid);
    }

    public Paging<Category> findPaging(CategoryTerm term) {
        if (!Strings.isNullOrEmpty(term.getName())) {
            term.setName(term.getName() + "%");
        }
        List<Category> data = categoryDao.findPaging(term);
        return new Paging<>(term, data);
    }

}
