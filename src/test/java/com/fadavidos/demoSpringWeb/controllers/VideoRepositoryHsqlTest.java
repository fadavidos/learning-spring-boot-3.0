package com.fadavidos.demoSpringWeb.controllers;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class VideoRepositoryHsqlTest {

    @Autowired
    VideoRepository repository;

    @BeforeEach
    void setUp() {
        repository.saveAll(
                List.of(
                     new VideoEntity(
                             "name 1",
                             "desc 1",
                             "alice"
                     ),
                     new VideoEntity(
                             "name 12",
                             "desc 12",
                             "alice"
                     )   ,
                     new VideoEntity(
                             "name 3",
                             "desc 3",
                             "alice"
                     )
                )
        );
    }

    @Test
    void findAllShouldProduceAllVideos(){
        List<VideoEntity> videos = repository.findAll();
        assertThat(videos).hasSize(3);
    }

    @Test
    void findByNameShouldRetrieveOneEntry(){
        List<VideoEntity> videos = repository.findByNameContainsIgnoreCase("AmE 3");
        assertThat(videos).hasSize(1);
        assertThat(videos).extracting(VideoEntity::getName)
                .containsExactlyInAnyOrder("name 3");
    }

    @Test
    void findByNameOrDescriptionShouldFindTwo(){
        List<VideoEntity> videos = repository.findByNameContainsOrDescriptionContainsAllIgnoreCase("2", "1");
        assertThat(videos).hasSize(2);
        assertThat(videos).extracting(VideoEntity::getDescription)
                .contains("desc 1", "desc 12");
    }

}
