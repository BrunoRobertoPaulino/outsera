package com.bruno_roberto.movie.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bruno_roberto.movie.dtos.MoviesDTO;
import com.bruno_roberto.movie.entities.Movies;
import com.bruno_roberto.movie.repositories.MoviesRepository;

@Service
public class CsvReaderService {

	private final MoviesRepository repo;
	
	public CsvReaderService(MoviesRepository repo) {
		this.repo = repo;
	}
	
	/***
	 * METODO DESTINADO PARA LEITURA DO CSV
	 */
	public void process(String fileName) throws Exception {
		
		try(InputStream input = getClass().getClassLoader().getResourceAsStream(fileName);
				BufferedReader reader = new BufferedReader(new InputStreamReader(input))){
		
			String line;
			Boolean firstLine = true;
			
			List<MoviesDTO> dtos = new ArrayList<>();
			
			while((line = reader.readLine()) != null) {
				
				if(firstLine) {
					firstLine = false;
					continue;
				}
				
				String[] arr = line.split(";");
				
				if(arr.length < 4)
					continue;
				
				Boolean isWinner = false;
				if(arr.length == 5)
					isWinner = "yes".equals(arr[4])? true : false;
				
				MoviesDTO dto = new MoviesDTO(Integer.parseInt(arr[0]), arr[1], arr[2], arr[3], isWinner);				
				
				dtos.add(dto);
			}
			saveMovies(dtos);
		}		
	}
	
	public void saveMovies(List<MoviesDTO> dtos) {
		List<Movies> list = dtos.stream().map(this::convertEntity).toList();
		repo.saveAll(list);
	}
	
	public Movies convertEntity(MoviesDTO dtos){
		return new Movies(dtos);
	}
	

}
