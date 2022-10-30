package com.skdlsco.donelib.domain.done.controller;

import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.test.CustomWebMvcTest;
import com.skdlsco.donelib.domain.test.EntityUtil;
import com.skdlsco.donelib.domain.test.WithMockMemberOAuth2User;
import com.skdlsco.donelib.domain.test.WithMockMemberOAuth2UserSecurityContextFactory;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@CustomWebMvcTest
class DoneControllerTest {
    @Autowired
    MockMvc mockMvc;

    @PersistenceContext
    EntityManager em;

    Member loginMember;

    @BeforeEach
    void beforeEach() {
        loginMember = WithMockMemberOAuth2UserSecurityContextFactory.loginMember;
    }

    @AfterEach
    void afterEach() {
        em.remove(loginMember);
        WithMockMemberOAuth2UserSecurityContextFactory.loginMember = null;
        loginMember = null;
    }

    @Test
    @WithMockMemberOAuth2User
    void addDone() throws Exception {
        //given
        String url = "/done";
        String body = "{\"name\": \"testDone\", \"doneAt\":\"2022-02-22T12:00:00Z\", \"tagList\":[]}";

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("\"name\":\"testDone\"")));
        assertThat(loginMember.getDoneList().size()).isEqualTo(1);
    }

    @Test
    @WithMockMemberOAuth2User
    void addDoneNameEmpty() throws Exception {
        //given
        String url = "/done";
        String body = "{\"name\": \"\", \"doneAt\":\"2022-02-22T12:00:00Z\", \"tagList\":[]}";

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockMemberOAuth2User
    void addDoneInvalidDoneAt() throws Exception {
        //given
        String url = "/done";
        String doneAtNullBody = "{\"name\": \"\", \"doneAt\":null, \"tagList\":[]}";
        String doneAtEmptyBody = "{\"name\": \"\", \"doneAt\":\"\", \"tagList\":[]}";
        String doneAtInvalidFormat = "{\"name\": \"\", \"doneAt\":\"1232-12:00\", \"tagList\":[]}";

        //when
        ResultActions doneAtNullResultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(doneAtNullBody)
                .contentType(MediaType.APPLICATION_JSON));
        ResultActions doneAtEmptyResultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(doneAtEmptyBody)
                .contentType(MediaType.APPLICATION_JSON));
        ResultActions doneAtInvalidResultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(doneAtInvalidFormat)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        doneAtNullResultActions
                .andExpect(status().isBadRequest());
        doneAtEmptyResultActions
                .andExpect(status().isBadRequest());
        doneAtInvalidResultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockMemberOAuth2User
    void deleteDoneExistDone() throws Exception {
        //given
        Done done = EntityUtil.generate(em).done(loginMember, "test", List.of());
        String url = "/done/" + done.getId();

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(url));

        //then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockMemberOAuth2User
    void deleteDoneNotExistDone() throws Exception {
        //given
        String url = "/done/" + 123;

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(url));

        //then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockMemberOAuth2User
    void getDoneList() throws Exception {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2022, 10, 29, 12, 1);
        EntityUtil.generate(em).done(loginMember, "done1", List.of(), localDateTime);
        EntityUtil.generate(em).done(loginMember, "done2", List.of(), localDateTime.plusDays(3));
        EntityUtil.generate(em).done(loginMember, "done3", List.of(), localDateTime.plusDays(10));
        String url = "/done?doneAtFrom=" + localDateTime + "&doneAtTo=" + localDateTime.plusDays(4);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));

        //then
        resultActions
                .andExpect(status().isOk())
                // [{"name":"done1"}, {"name":"done2"}] -> ["done1", "done2"]
                .andExpect(jsonPath("$..name").value(Matchers.hasItems("done1", "done2")))
                .andExpect(jsonPath("$..name").value(Matchers.not(Matchers.hasItem("done3"))));
    }
}