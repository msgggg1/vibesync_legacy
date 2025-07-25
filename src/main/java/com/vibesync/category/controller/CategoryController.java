package com.vibesync.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vibesync.category.domain.CategoryVO;
import com.vibesync.category.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add")
    public String addCategoryForm() {
        // 카테고리 추가 폼 페이지를 보여줌
        return "admin/category-add";
    }

    @PostMapping("/add")
    public String addCategory(CategoryVO category) {
        // 폼에서 받은 데이터로 새 카테고리를 추가하고 목록으로 리다이렉트
        categoryService.addNewCategory(category);
        return "redirect:/admin/category/list";
    }
}