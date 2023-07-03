package com.Restapi.movieapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;


import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;



import com.Restapi.movieapp.model.Movie;
import com.Restapi.movieapp.repository.MovieRepository;
import com.Restapi.movieapp.service.MovieServiceImpl;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MovieServiceTest {
	
	

	@Autowired
	private MovieServiceImpl movieService;

	@MockBean
	private MovieRepository movieRepository;

	@Test
	@DisplayName("Test case for insert movie")
	public void insertMovie() {

		try {
			Movie movie = new Movie();
			movie.setActorName("Rajini");
			movie.setMovieName("Sivaji");
			movie.setGenre("Triller");
			movie.setReleaseDate(LocalDate.of(2007, 07, 25));
			movie.setDuration(LocalTime.of(03, 30, 00));
			Mockito.when(movieRepository.save(movie)).thenReturn(movie);
			assertThat(movieService.insertMovie(movie)).isEqualTo(movie);

		} catch (Exception e) {
			fail("Error occurs in saving the movie " + e.getMessage());
			System.out.println("error");
		}

	}

	@Test
	@DisplayName("Test Case for Get all movies")
	public void getAllMovies() {
		try {
			List<Movie> movie = null;
			Mockito.when(movieRepository.findAll()).thenReturn(movie);
			assertThat(movieService.getAllMovies()).isEqualTo(movie);
		} catch (Exception e) {
			assert (true);
			System.out.println("error");
		}

	}

	@Test
	@DisplayName("Test Case for Get Single movie by id")
	public void getMovieById() throws Exception {
		try {
        LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
	  
        Long movieId = 2L;
        Movie movie = new Movie((long) 2, "Kuruvi", "vijay", "triller", time, date);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(movieId);

     
        Assertions.assertEquals(movieId, result.getId());
        Assertions.assertEquals("vijay", result.getActorName());
	}catch(Exception e) {
		assert(true);
		System.out.println("error");
	}
	}

	@Test
	@DisplayName("Test Case for update movie")
	public void updateMovie() throws Exception {
	

		
//       		Long id = 3l;
			
//			Mockito.when(movieRepository.findById(id).get()).thenReturn(movie);
//			movie.setActorName("Vishal");
//			Mockito.when(movieRepository.save(movie)).thenReturn(movie);
//			Movie updatedMovie = movieService.updateMovie(movie);
//			assertThat(updatedMovie.getActorName()).isEqualTo("Vishal");
//           }catch(Exception e) {
//        	   assert(true);
//        	   System.out.println("error");
//           }
		
		 Long movieId = 3L;
		 LocalDate date = LocalDate.of(2007, 8, 23);
   		 LocalTime time = LocalTime.of(02,45);
		 Movie movie = new Movie((long) 3, "Singam", "Surya", "triller", time, date);
	        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
	        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

	        Movie updatedMovie = movieService.updateMovie(movie);

	   
	        Assertions.assertEquals(movieId, updatedMovie.getId());
	        Assertions.assertEquals("Singam", updatedMovie.getMovieName());
	        Assertions.assertEquals("Surya", updatedMovie.getActorName());
	        
	
//		catch(Exception e) {
//			assert(true);
//		}
	}

	@Test
	@DisplayName("Test case for delete movie ")
	public void deleteMovie() throws Exception {
		try {
			 Long movieId = 5L; //expected value
			 LocalDate date = LocalDate.of(2007, 8, 23);
	   		 LocalTime time = LocalTime.of(02,45);
			 Movie movie = new Movie((long) 5, "Singam", "Surya", "triller", time, date); //Actual value
				Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
		        movieService.deleteMovie(movieId);
			    Mockito.verify(movieRepository, Mockito.times(1)).findById(movieId);
		        Mockito.verify(movieRepository, Mockito.times(1)).delete(movie);
				Mockito.when(movieRepository.existsById(movieId)).thenReturn(false);
				assertFalse(movieRepository.existsById(movieId));
			
		} catch (Exception e) {
			assert (true);
			System.out.println("Error");
			
		}

	}
	
	
	@Test
	@DisplayName("Test Case for Get Single movie by invalid id")
	public void getMovieById1() throws Exception {
		try {
        LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
	  
        Long movieId = 200L;
       // Movie movie = new Movie((long) 2, "Kuruvi", "vijay", "triller", time, date);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        Movie result = movieService.getMovieById(movieId);
        Assertions.assertNull(result);
       
      
	}catch(Exception e) {
		 assert(true);
	}
	}
}

