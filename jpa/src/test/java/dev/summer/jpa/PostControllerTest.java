package dev.summer.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class) // PostController만 unit test하기 위해 사용하는 어노테이션
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    // http client인척 하는 것

    @MockBean // 실제로는 만들어지지 않을 빈을 만들어진 것처럼 하는 어노테이션
    private PostService postService;

    @Test
    public void readPost() throws Exception{
        // perform을 통해 어떤 요청을 보낼 지 정해주면
        // http 요청을 보낸 것처럼 작동

        // 아래의 3가지 단계로 나누어 테스트코드를 작성하면 어떤 목적을 위해 작성된 테스트모드인지 알기 쉬움
        // given: 어떤 데이터가 준비가 되어 있다 를 보여주기 위한 조건 코드를 작성하는 부분
        // PostEntity가 존재할 때 (PostService가 PostEntity를 잘 돌려줄 때)
        final int id = 10;
        PostDto testDto = new PostDto();
        testDto.setId(id);
        testDto.setTitle("Unit Title");
        testDto.setContent("Unit Content");
        testDto.setWriter("Unit");

        given(postService.readPost(id)).willReturn(testDto);

        // when: 어떤 행위(함수 호출 등)가 일어났을 때
        // 경로에 GET 요청이 오면
        final ResultActions actions = mockMvc.perform(get("/post/{id}", 10))
                .andDo(print());

        // then: 어떤 결과가 올 것인지
        // PostDto가 반환된다
        actions.andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.title", is("Unit Title")), // $: json문서 자체. 자기 자신을 나타냄 .: 현재 위치의 title: key값 title
                jsonPath("$.content", is("Unit Content")),
                jsonPath("$.writer", is("Unit"))
        );
    }

    @Test
    public void readPostAll() throws Exception{
        // given
        PostDto post1 = new PostDto();
        post1.setTitle("title 1");
        post1.setContent("test");
        post1.setWriter("test");
        PostDto post2 = new PostDto();
        post2.setTitle("title 2");
        post2.setContent("test2");
        post2.setWriter("test2");

        List<PostDto> readAllPost = Arrays.asList(post1, post2);
        given(postService.readPostAll()).willReturn(readAllPost);

        // when
        final ResultActions actions = mockMvc.perform(get("/post"))
                .andDo(print());

        // then
        actions.andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$", hasSize(readAllPost.size())) // 돌려받은 json 객체가 같은 크기를 가지고 있다 (list와)
                );
    }
}