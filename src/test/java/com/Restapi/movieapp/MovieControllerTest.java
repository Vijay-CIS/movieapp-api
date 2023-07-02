package com.Restapi.movieapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.Restapi.movieapp.controller.MovieController;
import com.Restapi.movieapp.model.Movie;
import com.Restapi.movieapp.service.MovieServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class)
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MovieServiceImpl movieService;

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void testInsertMovie() throws Exception {

		Movie movie = new Movie();
		movie.setId((long) 5);
		movie.setActorName("Rajini");
		movie.setMovieName("Sivaji");
		movie.setGenre("Triller");
		// movie.setReleaseDate(date);
		movie.setReleaseDate(LocalDate.parse("2023-06-26"));
		movie.setDuration(LocalTime.parse("02:30:23"));
		//System.out.println(movie);
		String inputInJson = this.mapToJson(movie);
		String URI = "/movies";

		Mockito.when(movieService.insertMovie(Mockito.any(Movie.class))).thenReturn(movie);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void testGetMovieById() throws Exception {
		Movie movie = new Movie();
		movie.setId((long) 1);
		movie.setActorName("Karthi");
		movie.setMovieName("Paiya");
		movie.setDuration(LocalTime.parse("02:45"));
		movie.setReleaseDate(LocalDate.now());

		Mockito.when(movieService.getMovieById((long) Mockito.anyInt())).thenReturn(movie);
		String URI = "/movies/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(movie);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	public void testGetAllMovies() throws Exception {

		Movie movie1 = new Movie();
		movie1.setId((long) 1);
		movie1.setActorName("Karthi");
		movie1.setMovieName("paiya");
		movie1.setDuration(LocalTime.now());
		movie1.setReleaseDate(LocalDate.now());

		Movie movie2 = new Movie();
		movie2.setId((long) 2);
		movie2.setActorName("Vikram");
		movie2.setMovieName("Aniyan");
		movie2.setDuration(LocalTime.now());
		movie2.setReleaseDate(LocalDate.now());

		List<Movie> movieList = new ArrayList<>();

		movieList.add(movie1);
		movieList.add(movie2);

		Mockito.when(movieService.getAllMovies()).thenReturn(movieList);
		String URI = "/movies/";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(movieList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	@Test
	public void testGetMovieByActorName() throws Exception {
		Movie movie1 = new Movie();
		movie1.setId((long) 1);
		movie1.setActorName("Karthi");
		movie1.setMovieName("paiya");
		movie1.setDuration(LocalTime.now());
		movie1.setReleaseDate(LocalDate.now());

		Movie movie2 = new Movie();
		movie2.setId((long) 2);
		movie2.setActorName("Vikram");
		movie2.setMovieName("Aniyan");
		movie2.setDuration(LocalTime.now());
		movie2.setReleaseDate(LocalDate.now());

		List<Movie> movieList = new ArrayList<>();

		movieList.add(movie1);
		movieList.add(movie2);

		Mockito.when(movieService.findMovieByActorName(Mockito.anyString())).thenReturn(movieList);
		String URI = "/movies/search2/karthi";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(movieList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	@Test
	public void testUpdateMovie() throws Exception {

		Movie movie = new Movie();
		movie.setId((long) 1);
		movie.setActorName("Karthi");
		movie.setMovieName("paiya");
		movie.setDuration(LocalTime.now());
		movie.setReleaseDate(LocalDate.now());

		Mockito.when(movieService.getMovieById((long) Mockito.anyInt())).thenReturn(movie);

		movie.setMovieName("Siruthai");

		String inputInJson = this.mapToJson(movie);

		String URI = "/movies/1";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(

				URI).accept(MediaType.APPLICATION_JSON).content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(movie);
		System.out.println(expectedJson);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println("output" + outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);

	}
	
	 @Test
	    public void testDeleteMovieActor() throws Exception {
		 Movie movie = new Movie();
			movie.setId((long) 1);
			movie.setActorName("Karthi");
			movie.setMovieName("paiya");
			movie.setDuration(LocalTime.now());
			movie.setReleaseDate(LocalDate.now());

			Mockito.when(movieService.getMovieById((long) Mockito.anyInt())).thenReturn(movie);


			String inputInJson = this.mapToJson(movie);

			String URI = "/movies/1";

			RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(

					URI).accept(MediaType.APPLICATION_JSON).content(inputInJson).contentType(MediaType.APPLICATION_JSON);
			;

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			String expectedJson = this.mapToJson(movie);
			System.out.println(expectedJson);
			String outputInJson = result.getResponse().getContentAsString();
			System.out.println("output" + outputInJson);
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			verify(movieService).deleteMovie(1L);

}
}
