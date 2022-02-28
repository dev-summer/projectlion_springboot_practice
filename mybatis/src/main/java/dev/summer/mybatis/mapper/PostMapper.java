package dev.summer.mybatis.mapper;

import dev.summer.mybatis.dto.PostDto;

import java.util.List;

public interface PostMapper {
    int createPost(PostDto dto);
    PostDto readPost(int id);
    List<PostDto> readAllPost();
    int updatePost(PostDto dto);
    int deletePost(int id);
}
