package com.umesh.crawler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umesh.crawler.model.HomePage;

public interface HomePageRepository extends JpaRepository<HomePage, Long> {

	HomePage findByUrl(String url);
}
