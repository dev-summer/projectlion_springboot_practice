package dev.summer.mybatis.dao;

import dev.summer.mybatis.dto.PostDto;
import dev.summer.mybatis.mapper.PostMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDao {
    private final SqlSessionFactory sessionFactory;

    public PostDao(
            @Autowired SqlSessionFactory sessionFactory
    ){
        this.sessionFactory = sessionFactory;
    }

    public int createPost(PostDto dto){
        try (SqlSession session = sessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.createPost(dto);
        }
        // Java 일정 버전 이상에서 지원
        // try with resource 형태로 사용하며, try문 안의 {}안에서만 ()의 변수가 살아있도록 만들어주는 문법
        // 중괄호 끝에 도달 시 session이 자동으로 close됨
    }
    // sessionfactory에서 매번 session을 열고 닫으면서 mapper를 매번 새로 받는 이유 (mapper만 바로 사용하지 않는 이유)
    // mapper instance는 thread-safe 하지 않기 때문
    // not thread-safe : 요청이 연속적으로 빠르게 두 번 들어왔을 때 데이터베이스 통신이 조금 길어졌을 경우 서로 다른 두 함수의 결과가 다른 함수에 영향을 미칠 수 있음
    public PostDto readPost(int id){
        try (SqlSession session = sessionFactory.openSession()){
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.readPost(id);
        }
    }

    public List<PostDto> readAllPost(){
        try (SqlSession session = sessionFactory.openSession()){
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.readAllPost();
        }
    }

    public int updatePost(PostDto dto){
        try (SqlSession session = sessionFactory.openSession()){
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.updatePost(dto);
        }
    }

    public int deletePost(int id){
        try (SqlSession session = sessionFactory.openSession()){
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.deletePost(id);
        }
    }
}

