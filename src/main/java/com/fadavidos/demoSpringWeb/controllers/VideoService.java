package com.fadavidos.demoSpringWeb.controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    public List<VideoEntity> getVideos() {
        return videoRepository
                .findAll()
                .stream()
                .toList();
    }

    public VideoEntity create(Video newVideo, String username) {
        return videoRepository.saveAndFlush(new VideoEntity(newVideo.name(), newVideo.description(), username));
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

    public void delete(Long videoId) {
        videoRepository.findById(videoId)
                .map(videoEntity -> {
                    videoRepository.delete(videoEntity);
                    return true;
                }).orElseThrow(() -> new RuntimeException(String.format("No video at %s", videoId)));
    }

    @PostConstruct
    void initDatabase(){
        videoRepository.save(
                new VideoEntity(
                        "Need help with your spring boot 3 app?",
                        "Spring boot 3 will only speed things up and make it super simple to serve templates " +
                                "and raw data",
                        "alice"
                ));
        videoRepository.save(
                new VideoEntity(
                        "Don't do THIS to your own CODE!",
                        "As a pro developer, never ever EVER do this to your code. Because you'll ultimately " +
                                "be doing it to YOURSELF!",
                        "alice"
                ));
        videoRepository.save(
                new VideoEntity(
                        "SECRETS to fix BROKEN CODE!",
                        "Discover ways to not only debug your code, but to regain your confidence and get " +
                                "back in the game as a software developer.",
                        "bob"
                ));
    }
}
