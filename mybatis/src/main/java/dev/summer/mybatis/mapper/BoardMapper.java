package dev.summer.mybatis.mapper;

import dev.summer.mybatis.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    int createBoard(BoardDto dto);

}
