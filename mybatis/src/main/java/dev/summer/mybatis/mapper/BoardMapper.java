package dev.summer.mybatis.mapper;

import dev.summer.mybatis.dto.BoardDto;

public interface BoardMapper {
    int createBoard(BoardDto dto);

}
