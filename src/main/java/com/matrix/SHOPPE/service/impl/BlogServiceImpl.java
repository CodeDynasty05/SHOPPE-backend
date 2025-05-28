package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.exception.BlogNotFoundException;
import com.matrix.SHOPPE.mapper.BlogMapper;
import com.matrix.SHOPPE.model.dto.BlogAddRequestDto;
import com.matrix.SHOPPE.model.dto.BlogBriefDto;
import com.matrix.SHOPPE.model.dto.BlogDto;
import com.matrix.SHOPPE.model.entity.Blog;
import com.matrix.SHOPPE.repository.BlogRepository;
import com.matrix.SHOPPE.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    @Override
    public Page<BlogBriefDto> getBlogs(Pageable pageable) {
        log.info("Fetching blogs page: {}", pageable);
        Page<Blog> blogs = blogRepository.findAll(pageable);
        log.debug("Found {} blogs in page {}", blogs.getContent().size(), pageable.getPageNumber());
        return blogs.map(blogMapper::productToBlogBriefDto);
    }

    @Override
    public BlogDto createBlog(BlogAddRequestDto blogAddRequestDto) {
        log.info("Creating new blog with title: {}", blogAddRequestDto.getTitle());

        Blog blog = blogMapper.blogAddRequestToBlog(blogAddRequestDto);
        log.debug("Mapped blog entity: {}", blog);

        Blog savedBlog = blogRepository.save(blog);
        log.info("Successfully created blog with ID: {}", savedBlog.getId());

        return blogMapper.blogToBlogDto(savedBlog);
    }

    @Override
    public BlogDto updateBlog(BlogAddRequestDto blogAddRequestDto, Integer id) {
        log.info("Updating blog with ID: {}", id);

        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + id));

        Blog updatedBlog = blogMapper.updateBlog(blogAddRequestDto, existingBlog);
        log.debug("Updated blog entity: {}", updatedBlog);

        Blog savedBlog = blogRepository.save(updatedBlog);
        log.info("Successfully updated blog with ID: {}", savedBlog.getId());

        return blogMapper.blogToBlogDto(savedBlog);
    }

    @Override
    public void delete(Integer id) {
        log.info("Attempting to delete blog with ID: {}", id);

        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException("Blog not found with id: " + id);
        }

        blogRepository.deleteById(id);
        log.info("Successfully deleted blog with ID: {}", id);
    }

    @Override
    public BlogDto getBlogById(Integer id) {
        log.info("Fetching blog with ID: {}", id);

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + id));

        log.debug("Found blog: {}", blog);
        return blogMapper.blogToBlogDto(blog);
    }
}