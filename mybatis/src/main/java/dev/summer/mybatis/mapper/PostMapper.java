package dev.summer.mybatis.mapper;

import dev.summer.mybatis.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    int createPost(PostDto dto);
    int createAllPost(List<PostDto> dtoList);
    PostDto readPost(int id);
    List<PostDto> readAllPost();
    PostDto readPostQuery(PostDto dto);
    int updatePost(PostDto dto);
    int deletePost(int id);
}
