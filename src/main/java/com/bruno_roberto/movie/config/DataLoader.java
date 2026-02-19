package com.bruno_roberto.movie.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bruno_roberto.movie.services.CsvReaderService;

@Component
public class DataLoader implements CommandLineRunner {

	
	private final CsvReaderService csvReader;
	
	public DataLoader(CsvReaderService csvReader) {
		this.csvReader = csvReader;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.csvReader.process("Movielist.csv");		
	}

}
