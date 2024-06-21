package com.example.demo.controllers;

import com.example.demo.TestDataUtil;
import com.example.demo.domain.dto.AuthorDto;
import com.example.demo.domain.entities.AuthorEntity;
import com.example.demo.services.impl.AuthorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthorServiceImpl authorService;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorServiceImpl authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void testCreateAuthorReturn201() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testCreateAuthorSaved() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testListAllAuthorsReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testListAllAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorService.saveAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testFindOneAuthorReturns200() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.saveAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testFindOneAuthorReturns404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testFindOneAuthorReturnsAuthorBody() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.saveAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(savedAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedAuthor.getAge())
        );
    }

    @Test
    public void testFullUpdateAuthorReturns200() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        AuthorDto testAuthor = TestDataUtil.createTestAuthorDto();
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testFullUpdateAuthorReturns404() throws Exception {
        AuthorDto testAuthor = TestDataUtil.createTestAuthorDto();
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testFullUpdateAuthorUpdatesBody() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        AuthorDto testAuthor = TestDataUtil.createTestAuthorDto();
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testPartialUpdateAuthorReturns200() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        AuthorDto testAuthor = TestDataUtil.createTestAuthorDto();
        testAuthor.setName("NEW AUTHOR");
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testPartialUpdateAuthorUpdatesBody() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        AuthorDto testAuthor = TestDataUtil.createTestAuthorDto();
        testAuthor.setName("NEW AUTHOR");
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testDeleteAuthorReturns204() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
