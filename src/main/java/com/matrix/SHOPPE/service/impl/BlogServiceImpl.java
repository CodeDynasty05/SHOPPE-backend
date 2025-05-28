package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.BlogRepository;
import com.matrix.SHOPPE.exception.BlogNotFoundException;
import com.matrix.SHOPPE.mapper.BlogMapper;
import com.matrix.SHOPPE.model.dto.BlogAddRequestDto;
import com.matrix.SHOPPE.model.dto.BlogBriefDto;
import com.matrix.SHOPPE.model.dto.BlogDto;
import com.matrix.SHOPPE.model.entity.Blog;
import com.matrix.SHOPPE.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    @Override
    public Page<BlogBriefDto> getBlogs(Pageable pageable) {
        log.info("Fetching blogs page: {}", pageable.getPageNumber());
        return blogRepository.findAll(pageable).map(blogMapper::productToBlogBriefDto);
    }

    @Override
    public BlogDto createBlog(BlogAddRequestDto blogAddRequestDto) {
        log.info("Creating new blog post: {}", blogAddRequestDto.getTitle());
        Blog blog = blogRepository.save(blogMapper.blogAddRequestToBlog(blogAddRequestDto));
        log.info("Created new blog post: {}", blogAddRequestDto.getTitle());
        return blogMapper.blogToBlogDto(blog);
    }

    @Override
    public BlogDto updateBlog(BlogAddRequestDto blogAddRequestDto, Integer id) {
        log.info("Updating blog with ID: {}", id);
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException("Blog with id " + id + " not found"));
        Blog newBlog = blogMapper.updateBlog(blogAddRequestDto, blog);
        log.info("Updated blog with ID: {}", newBlog.getId());
        return blogMapper.blogToBlogDto(blogRepository.save(newBlog));
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting blog with ID: {}", id);
        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException("Blog with id " + id + " not found");
        }
        blogRepository.deleteById(id);
        log.info("Deleted blog with ID: {}", id);
    }

    @Override
    public BlogDto getBlogById(Integer id) {
        log.info("Fetching blog with ID: {}", id);
        return blogMapper.blogToBlogDto(blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException("Blog with id " + id + " not found")));
    }
}
