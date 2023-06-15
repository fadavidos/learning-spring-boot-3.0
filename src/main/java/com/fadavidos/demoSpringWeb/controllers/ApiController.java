package com.fadavidos.demoSpringWeb.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    private final VideoService videoService;

    public ApiController(VideoService videoService){
        this.videoService = videoService;
    }

    @GetMapping("/api/videos")
    public List<Video> all() {
        return videoService.getVideos();
    }

    @PostMapping("/api/videos")
    public Video newVideo(@RequestBody Video newVideo){
        return videoService.create(newVideo);
    }

    @PostMapping("/api/videos/multi-field-search")
    public List<VideoEntity> multiFieldSearch(@RequestBody VideoSearch search) {
        return videoService.search(search);
    }


}
