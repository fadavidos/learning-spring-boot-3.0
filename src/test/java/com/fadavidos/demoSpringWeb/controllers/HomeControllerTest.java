package com.fadavidos.demoSpringWeb.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    VideoService videoService;

    @Test
    @WithMockUser
    void indexPageHasSeveralHtmlForms() throws Exception {
        String html = mvc.perform(
                get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("Username: user")))
                .andExpect(content().string(
                        containsString("Authorities: [ROLE_USER]")
                ))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(html).contains( //
                "<form action=\"/logout\"", //
                "<form action=\"/universal-search\"", //
                "<form action=\"/new-video\"");
    }

    @Test
    @WithMockUser
    void postNewVideoShouldWork() throws Exception {
        mvc.perform(
                        post("/new-video")
                                .param("name", "new video")
                                .param("description", "new desc")
                                .with(csrf()))
                .andExpect(redirectedUrl("/"));

        verify(videoService).create(
                new Video(
                        "new video",
                        "new desc"),
                "user");
    }

    @Test
    @WithMockUser
    void postCallUniversalSearch() throws Exception {
        mvc.perform(
                post("/universal-search")
                .param("value", "a")
                .with(csrf())
        );
        verify(videoService).search(new UniversalSearch("a"));
    }

    @Test
    @WithMockUser
    void postMultiFieldSearch() throws Exception {
        mvc.perform(
                post("/multi-field-search")
                        .param("name", "title")
                        .param("description", "desc")
                        .with(csrf())
        );
        verify(videoService).search(new VideoSearch("title", "desc"));
    }


}
