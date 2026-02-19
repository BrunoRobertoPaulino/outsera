package com.bruno_roberto.movie.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bruno_roberto.movie.dtos.IntervalProducersListDTO;
import com.bruno_roberto.movie.services.MoviesService;

@RestController
@RequestMapping("/movies")
public class MoviesController {

	private final MoviesService service;
	
	public MoviesController(MoviesService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<IntervalProducersListDTO> FindTeste() {
		return ResponseEntity.ok(this.service.returnMovies());
	}
	
}
