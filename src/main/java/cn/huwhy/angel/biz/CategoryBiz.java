package cn.huwhy.angel.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.huwhy.angel.biz.manager.CategoryManager;
import cn.huwhy.angel.common.Paging;
import cn.huwhy.angel.po.Category;
import cn.huwhy.angel.term.CategoryTerm;

@Service
public class CategoryBiz {

    @Autowired
    private CategoryManager categoryManager;

    public Integer save(Category category) {
        return categoryManager.save(category);
    }

    public Paging<Category> findPaging(CategoryTerm term) {
        return categoryManager.findPaging(term);
    }

}
