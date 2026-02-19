package com.bruno_roberto.movie.services;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.bruno_roberto.movie.dtos.IntervalProducersDTO;
import com.bruno_roberto.movie.dtos.IntervalProducersListDTO;
import com.bruno_roberto.movie.dtos.MoviesDTO;
import com.bruno_roberto.movie.entities.Movies;
import com.bruno_roberto.movie.repositories.MoviesRepository;

@Service
public class MoviesService {

	private final MoviesRepository repo;
	
	public MoviesService(MoviesRepository repo) {
		this.repo = repo;
	}
	
	public IntervalProducersListDTO returnMovies() {
		List<Movies> listMovies = repo.findAll();
		
		if(listMovies.isEmpty()) 
			return new IntervalProducersListDTO(List.of(), List.of());
		
		List<MoviesDTO> dtoList = listMovies.stream().map(this::convertDTO).toList();
		
		Map<String, List<Integer>> map = this.prepareMapProducersAndYears(dtoList);
		
		
		List<IntervalProducersDTO> interval = calcListInterval(map);
		
		if(interval.isEmpty())
			return new IntervalProducersListDTO(List.of(), List.of());
		
		IntSummaryStatistics statistic = interval.stream()
				.mapToInt(IntervalProducersDTO::interval)
				.summaryStatistics();
		
		Integer valueMin = statistic.getMin();		
		
		Integer valueMax = statistic.getMax();

		List<IntervalProducersDTO> minList = returnListMinMax(interval, valueMin);
		
		List<IntervalProducersDTO> maxList = returnListMinMax(interval, valueMax);
		
		return new IntervalProducersListDTO(minList, maxList);
		
	}
	
	public Map<String, List<Integer>> prepareMapProducersAndYears(List<MoviesDTO> dtoList) {
		return dtoList.stream()
				.filter(MoviesDTO::winner)
				.flatMap(m -> 
					Arrays.stream(m.producers()
						.split(",\\s*|\\s+and\\s+"))
						.map(String::trim)
						.map(p -> Map.entry(p,  m.year()))
				)
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,
						Collectors.mapping(Map.Entry::getValue, Collectors.toList())
				));
	}
	
	public List<IntervalProducersDTO> calcListInterval(Map<String, List<Integer>> map){
		return map.entrySet().stream().flatMap(entry -> {
			String produtor = entry.getKey();
			List<Integer> years = entry.getValue()
					.stream().sorted().toList();
			
			if(years.size() < 2)
				return Stream.empty();
			
			return IntStream.range(1, years.size())
					.mapToObj(i -> 
					new IntervalProducersDTO(
							produtor,
							years.get(i) - years.get(i - 1),
							years.get(i - 1),
							years.get(i)));
			
		}).toList();
	}
	
	public List<IntervalProducersDTO> returnListMinMax(List<IntervalProducersDTO> interval, Integer value){
		return interval.stream()
				.filter(x -> x.interval() == value).toList();
	}
	
	public MoviesDTO convertDTO(Movies entity) {
		return new MoviesDTO(entity.getYear(),
				entity.getTitle(),
				entity.getStudios(),
				entity.getProducers(),
				entity.getWinner());
	}
	
}
