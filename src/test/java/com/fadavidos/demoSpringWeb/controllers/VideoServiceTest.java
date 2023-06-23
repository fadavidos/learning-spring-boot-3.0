package com.fadavidos.demoSpringWeb.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    VideoService service;
    @Mock
    VideoRepository repository;

    @BeforeEach
    void setUp(){
        this.service = new VideoService(repository);
    }

    @Test
    void getVideosShouldReturnAll(){
        //BDD
        // The idea is that given a set of inputs, when you do an action X, then you can expect Y.

        // given
        VideoEntity video1 = new VideoEntity("spring boot 3 intro", "learn the basics!", "alice");
        VideoEntity video2 = new VideoEntity("spring boot 3 deep dive", "go deep!", "alice");
        when(repository.findAll()).thenReturn(List.of(video1, video2));

        // when
        List<VideoEntity> videos = service.getVideos();

        // then
        assertThat(videos).containsExactly(video1, video2);
    }

    @Test
    void creatingANewVideoShouldReturnTheSameData() {
        // given
        given(repository.saveAndFlush(any(VideoEntity.class)))
                .willReturn(new VideoEntity("name", "desc", "alice"));
        // when
        VideoEntity newVideo = service.create(new Video("name", "desc"), "alice");

        // then
        assertThat(newVideo.getName()).isEqualTo("name");
        assertThat(newVideo.getDescription()).isEqualTo("desc");
        assertThat(newVideo.getUsername()).isEqualTo("alice");
    }

    @Test
    void deletingAVideoShouldWork(){
        // given
        VideoEntity entity = new VideoEntity("name", "desc", "alice");
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        // when
        service.delete(1L);

        // then
        verify(repository).findById(1L);
        verify(repository).delete(entity);
    }

    @Test
    void universalSearchShouldWork(){
        // given
        Predicate<VideoEntity> byNameAndDescription = (entity) ->
                entity.getName().contains("b") || entity.getDescription().contains("b");
        VideoEntity entity1 = new VideoEntity("name a", "desc c", "alice");
        VideoEntity entity2 = new VideoEntity("name b", "desc a", "alice");
        VideoEntity entity3 = new VideoEntity("name c", "desc b", "alice");
        List<VideoEntity> videoEntities = List.of(entity1, entity2, entity3);
        List<VideoEntity> filteredVideos = videoEntities.stream().filter(byNameAndDescription).toList();
        when(repository.findAll(any(Example.class))).thenReturn(filteredVideos);
        // when
        List<VideoEntity> response = service.search(new UniversalSearch("b"));

        // then
        assertThat(response).containsExactly(filteredVideos.get(0), filteredVideos.get(1));
    }
}
