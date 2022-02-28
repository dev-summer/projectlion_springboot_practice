package dev.summer.mybatis;

import dev.summer.mybatis.dao.BoardDao;
import dev.summer.mybatis.dao.PostDao;
import dev.summer.mybatis.dto.BoardDto;
import dev.summer.mybatis.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestComponent {
    private final PostDao postDao;
//    private final BoardDao boardDao;

    public TestComponent(
            @Autowired PostDao postDao
//            @Autowired BoardDao boardDao
    ){
        this.postDao = postDao;
//        this.boardDao = boardDao;

        PostDto newPost = new PostDto();
        newPost.setTitle("From MyBatis");
        newPost.setContent("Hello Database!");
        newPost.setWriter("summer");
        newPost.setBoard(1);
        this.postDao.createPost(newPost);

        List<PostDto> postDtoList = this.postDao.readAllPost();
        System.out.println(postDtoList.get(postDtoList.size()-1));

        PostDto firstPost = postDtoList.get(0);
        firstPost.setContent("Update From MyBatis!");
        postDao.updatePost(firstPost);


//        BoardDto boardDto = new BoardDto();
//        boardDto.setName("new board");
//        this.boardDao.createBoard(boardDto);
//        System.out.println(boardDto.getId());
    }
}
