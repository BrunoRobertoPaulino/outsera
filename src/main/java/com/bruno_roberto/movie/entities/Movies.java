package com.bruno_roberto.movie.entities;

import com.bruno_roberto.movie.dtos.MoviesDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Movies")
public class Movies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "\"year\"")
	private Integer year;
	private String title;
	private String studios;
	private String producers;
	private Boolean winner;
	
	public Movies() {		
	}
	
	public Movies(MoviesDTO dto) {
		this.year = dto.year();
		this.title = dto.title();
		this.studios = dto.studios();
		this.producers = dto.producers();
		this.winner = dto.winner();
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStudios() {
		return studios;
	}
	public void setStudios(String studios) {
		this.studios = studios;
	}
	public String getProducers() {
		return producers;
	}
	public void setProducers(String producers) {
		this.producers = producers;
	}
	public Boolean getWinner() {
		return winner;
	}
	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	
	
}
