package com.treeleaf.blog.serviceImpl;

import com.treeleaf.blog.config.ImageUtils;
import com.treeleaf.blog.entity.Blogs;
import com.treeleaf.blog.entity.User;
import com.treeleaf.blog.exceptions.ResourceNotFoundException;
import com.treeleaf.blog.repository.BlogsRepository;
import com.treeleaf.blog.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BlogsServiceImpl implements BlogsService {
    @Autowired
    private BlogsRepository blogsRepository;

    @Autowired
    private ImageUtils imageUtils;

    @Override
    public Blogs findBlogById(int id) {
        try {
            return blogsRepository.findBlogById(id);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Blogs> findBlogsByUser(User user) {
        try {
            return blogsRepository.findBlogsByUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public Blogs createBlog(Blogs blog, MultipartFile thumbnailImage, User user) {
        try {
            String thumbnailImageUrl = imageUtils.saveThumbnailImage(thumbnailImage);

            blog.setThumbnailImageUrl(thumbnailImageUrl);
            blog.setUser(user);
            blog.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

            blogsRepository.createBlog(blog);
            return blog;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public Blogs updateBlog(Blogs blog, MultipartFile thumbnailImage) {
        try {
            Blogs blogs = blogsRepository.findBlogById(blog.getId());
            if (blogs == null) {
                throw new ResourceNotFoundException("Blogs not found with this id:" + blogs.getId());
            }
            if (thumbnailImage != null && !thumbnailImage.isEmpty()) {
                String thumbnailImageUrl = imageUtils.saveThumbnailImage(thumbnailImage);
                blog.setThumbnailImageUrl(thumbnailImageUrl);
            }
            blogsRepository.updateBlog(blog);
            return blog;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Blogs> findBlogsByTitle(String title) {
        try {
            return blogsRepository.findBlogsByTitle(title);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public int deleteBlogs(int id) {
        try {
            Blogs blogs = blogsRepository.findBlogById(id);
            if (blogs == null) {
                throw new ResourceNotFoundException("Blogs not found with this id:" + id);
            }
            return blogsRepository.deleteBlog(id);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
}
