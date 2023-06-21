package com.fadavidos.demoSpringWeb.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

   List<VideoEntity> findByName(String name);

   List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase(String name, String description);

   List<VideoEntity> findByNameContainsIgnoreCase(String name);

   List<VideoEntity> findByDescriptionContainsIgnoreCase(String description);

   @Query("select v from VideoEntity v where v.name = ?1")
   List<VideoEntity> findCustomerReport(String name);

   @PreAuthorize("#entity.username == authentication.name")
   @Override
   void delete(VideoEntity entity);

}
