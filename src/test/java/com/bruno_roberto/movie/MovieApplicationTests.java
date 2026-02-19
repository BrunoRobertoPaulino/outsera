package com.bruno_roberto.movie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MovieApplicationTests {

	@Autowired
	private MockMvc mock;
	
	@Test
	void validaIntervalosCorreto() throws Exception {
		mock.perform(get("/movies"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.min[0].producer").value("Joel Silver"))
        .andExpect(jsonPath("$.min[0].interval").value(1))
        .andExpect(jsonPath("$.min[0].previousWin").value(1990))
        .andExpect(jsonPath("$.min[0].followingWin").value(1991))
        .andExpect(jsonPath("$.max[0].producer").value("Matthew Vaughn"))
        .andExpect(jsonPath("$.max[0].interval").value(13))
        .andExpect(jsonPath("$.max[0].previousWin").value(2002))
        .andExpect(jsonPath("$.max[0].followingWin").value(2015));
	}

}
