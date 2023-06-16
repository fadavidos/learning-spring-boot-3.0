package com.fadavidos.demoSpringWeb.controllers;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    public List<Video> getVideos() {
        return videoRepository
                .findAll()
                .stream()
                .map(videoEntity -> new Video(videoEntity.getName(), videoEntity.getDescription()))
                .toList();
    }

    public Video create(Video newVideo) {
        videoRepository.save(new VideoEntity(newVideo.name(), newVideo.description()));
        return newVideo;

    }

    public List<VideoEntity> search(VideoSearch videoSearch) {
        if (StringUtils.hasText(videoSearch.name()) //
                && StringUtils.hasText(videoSearch.description())) {
            return videoRepository
                    .findByNameContainsOrDescriptionContainsAllIgnoreCase( //
                            videoSearch.name(), videoSearch.description());
        }

        if (StringUtils.hasText(videoSearch.name())) {
            return videoRepository.findByNameContainsIgnoreCase(videoSearch.name());
        }

        if (StringUtils.hasText(videoSearch.description())) {
            return videoRepository.findByDescriptionContainsIgnoreCase(videoSearch.description());
        }

        return Collections.emptyList();
    }

    public List<VideoEntity> search(UniversalSearch search){
        VideoEntity probe = new VideoEntity();
        if(StringUtils.hasText(search.value())) {
            probe.setName(search.value());
            probe.setDescription(search.value());
        }
        Example<VideoEntity> example = Example.of(probe,
                ExampleMatcher.matchingAny() // we are putting the same values in name and description
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return videoRepository.findAll(example);
    }
}
