package dev.summer.mybatis.mapper;

import dev.summer.mybatis.dto.PostDto;

import java.util.List;

public interface PostMapper {
    int createPost(PostDto dto);
    int createAllPost(List<PostDto> dtoList);
    PostDto readPost(int id);
    List<PostDto> readAllPost();
    PostDto readPostQuery(PostDto dto);
    int updatePost(PostDto dto);
    int deletePost(int id);
}
