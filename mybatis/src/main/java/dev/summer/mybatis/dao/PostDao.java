package dev.summer.mybatis.dao;

import dev.summer.mybatis.dto.PostDto;
import dev.summer.mybatis.mapper.PostMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostDao {
    private final SqlSessionFactory sessionFactory;

    public PostDao(
            @Autowired SqlSessionFactory sessionFactory
    ){
        this.sessionFactory = sessionFactory;
    }

    public int createPost(PostDto dto){
        SqlSession session = sessionFactory.openSession();
        // 세션 오픈
        // default: true. select를 제외한 테이블에 데이터에 영향을 주는 것들에 대한 행위를 자동으로 저장할지
        PostMapper mapper = session.getMapper(PostMapper.class);
        // session에서 PostMapper(선언한 인터페이스 타입)와 동일한 구현체를 달라(getMapper)고 하면 PostMapper 인터페이스의 구현체가 mapper에 주입됨
        int rowAffected = mapper.createPost(dto);
        // 이 시점에서 데이터베이스와 통신 완료
        session.close();
        // 세션을 남기지 않는다(close)
        return rowAffected;
    }
}
