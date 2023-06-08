package com.fadavidos.demoSpringWeb.controllers;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private List<Video> videos = List.of(
            new Video("Need HELP with your SPRING BOOT 3App?"),
            new Video("Don't do THIS to your own CODE!"),
            new Video("SECRETS to fix BROKEN CODE!")
    );

    public List<Video> getVideos() {
        return videos;
    }

    public Video create(Video newVideo) {
        List<Video> copyOfVideos = new ArrayList<>(videos);
        copyOfVideos.add(newVideo);
        this.videos = List.copyOf(copyOfVideos);
        return newVideo;

    }

    public List<VideoEntity> search(UniversalSearch search){
        VideoEntity probe = new VideoEntity();
        if(StringUtils.hasText(search.value())) {
            probe.setName(search.value());
            probe.setDescription(search.value());
        }
        Example<VideoEntity> example = Example.of(probe,
                ExampleMatcher.matchingAny()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example)
    }
}
