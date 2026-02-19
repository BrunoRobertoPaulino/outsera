package com.bruno_roberto.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno_roberto.movie.entities.Movies;

public interface MoviesRepository extends JpaRepository<Movies, Long> {

}
