package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.dto.BlogAddRequestDto;
import com.matrix.SHOPPE.model.dto.BlogBriefDto;
import com.matrix.SHOPPE.model.dto.BlogDto;
import com.matrix.SHOPPE.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogsController {

    private final BlogService blogService;

    @GetMapping
    public Page<BlogBriefDto> getBlogs(Pageable pageable) {
        return blogService.getBlogs(pageable);
    }

    @GetMapping("/{id}")
    public BlogDto getBlogById(@PathVariable Integer id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public BlogDto create(@RequestBody BlogAddRequestDto blogAddRequestDto) {
        return blogService.createBlog(blogAddRequestDto);
    }

    @PutMapping("/{id}")
    public BlogDto update(@RequestBody BlogAddRequestDto blogAddRequestDto,@PathVariable Integer id) {
        return blogService.updateBlog(blogAddRequestDto,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        blogService.delete(id);
    }
}
