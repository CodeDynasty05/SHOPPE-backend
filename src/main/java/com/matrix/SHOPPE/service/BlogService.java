package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.BlogAddRequestDto;
import com.matrix.SHOPPE.model.dto.BlogBriefDto;
import com.matrix.SHOPPE.model.dto.BlogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public interface BlogService {

    Page<BlogBriefDto> getBlogs(Pageable pageable);

    BlogDto createBlog(@RequestBody BlogAddRequestDto blogAddRequestDto);

    BlogDto updateBlog(@RequestBody BlogAddRequestDto blogAddRequestDto,Integer id);

    void delete(@PathVariable Integer id);

    BlogDto getBlogById(@PathVariable Integer id);
}
