package com.gcu.chinook.album;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// Runs against the real chinook database. @Transactional rolls each test back.
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AlbumApiIntegrationTest {

    // Artist 1 exists in the Chinook data
    private static final int ARTIST_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlbumRepository repository;

    @Test
    void getById() throws Exception {
        Album saved = repository.saveAndFlush(new Album("Test Album", ARTIST_ID));

        mockMvc.perform(get("/api/albums/{id}", saved.getAlbumId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.albumId").value(saved.getAlbumId()))
                .andExpect(jsonPath("$.title").value("Test Album"))
                .andExpect(jsonPath("$.artistId").value(ARTIST_ID));
    }

    @Test
    void getAll() throws Exception {
        repository.saveAndFlush(new Album("Test Album", ARTIST_ID));

        mockMvc.perform(get("/api/albums"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Album\",\"artistId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.albumId").isNumber())
                .andExpect(jsonPath("$.title").value("New Album"));
    }

    @Test
    void update() throws Exception {
        Album saved = repository.saveAndFlush(new Album("Old Title", ARTIST_ID));

        mockMvc.perform(put("/api/albums/{id}", saved.getAlbumId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\",\"artistId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void deleteAlbum() throws Exception {
        Album saved = repository.saveAndFlush(new Album("Delete Me", ARTIST_ID));

        mockMvc.perform(delete("/api/albums/{id}", saved.getAlbumId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/albums/{id}", saved.getAlbumId()))
                .andExpect(status().isNotFound());
    }
}
