package com.fadavidos.demoSpringWeb.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

   List<VideoEntity> findByName (String name);
}
