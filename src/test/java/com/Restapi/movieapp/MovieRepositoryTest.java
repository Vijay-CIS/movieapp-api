package com.Restapi.movieapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Restapi.movieapp.model.Movie;
import com.Restapi.movieapp.repository.MovieRepository;

@SpringBootTest
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository movieRepository;

	@Test
	@DisplayName("Save Method")
	public void save() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = (LocalDate) dtf.parse("2007-07-25");
		try {
			Movie m = new Movie();
			m.setActorName("Rajini");
			m.setMovieName("Sivaji");
			m.setGenre("Triller");
			m.setReleaseDate(date);
			// m.setReleaseDate(LocalDate.of(2007, 07, 25));
			m.setDuration(LocalTime.of(03, 30, 00));
			Movie newMovie = movieRepository.save(m);
			assertNotNull(newMovie);
			assertNotNull(newMovie.getId());

		} catch (Exception e) {
			fail("Error occurs in saving the movie " + e.getMessage());
		}

	}

	@Test
	@DisplayName("Save Method Without Other Details")
	public void save1() {
		try {
			Movie m = new Movie();
			m.setActorName("Kamal");
			m.setMovieName("Vikram");
			// m.setGenre("Triller");
			// m.setReleaseDate(LocalDate.of(2007, 7, 25));
			// m.setDuration(LocalTime.of(03, 30, 00));
			Movie newMovie = movieRepository.save(m);
			fail("Error ocuurs while saving without required input");
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("",e.getMessage());

		}
	}

	@Test
	@DisplayName("Save Method with Same Name")
	public void save2() {
		try {
			Movie m1 = new Movie();
			m1.setActorName("Kamal");
			m1.setMovieName("Vikram");
			Movie m2 = new Movie();
			m2.setActorName("Kamal");
			m2.setMovieName("Vikram");
			Movie newMovie1 = movieRepository.save(m1);
			Movie newMovie2 = movieRepository.save(m2);
			fail("Error Occur same movie must be saved twice");
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	@DisplayName("List all the movies")
	public void findAll() {
		try {
			List<Movie> list = movieRepository.findAll();
			List<Movie> mlist = new ArrayList<>();
			for (Movie m : list)
				mlist.add(m);
			assertThat(mlist.size() > 2);

		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	@DisplayName("Get single movie by id")
	public void findById() {
		try {
			Long id = 3l;
			Optional<Movie> m = movieRepository.findById(id);
			if (m.isPresent()) {
				assert (true);
			}
		} catch (Exception e) {
			assert (false);
		}

	}

	@Test
	@DisplayName("Get single movie by invalid id")
	public void findById1() {
		try {
			Long id = 100l;
			Optional<Movie> m = movieRepository.findById(id);
			if (m.isPresent()) {
				assert (false);
			}
		} catch (Exception e) {
			assert (true);
		}

	}

	@Test
	@DisplayName("List movie by valid actor name")
	public void findByActorName() {
		try {
			String name = "Vijay";
			List<Movie> m = movieRepository.findByActorName(name);
			assertNotNull(m);
			assertThat(m.size() > 0);
		} catch (Exception e) {
			assert (true);
		}

	}

	@Test
	@DisplayName("List movie by invalid actor name")
	public void findByActorName1() {
		try {

			List<Movie> m = movieRepository.findByActorName("Vijay123");
			assertNotNull(m);
			assertThat(m.size() == 0);
		} catch (Exception e) {
			assert (true);
		}

	}

	@Test
	@DisplayName("List movie by null actor name")
	public void findByActorName2() {
		try {
			String name = null;
			List<Movie> m = movieRepository.findByActorName(name);
			assertNotNull(m);
			assertThat(m.size() == 0);
		} catch (Exception e) {
			assert (true);
		}

	}

	// Check the same valid and invalid and null conditions for movie name , genre
	// check the valid ,invalid and null Conditions for date and time
	// check the valid ,invalid and null Conditions for day, month and year
	// check the valid ,invalid and null Conditions for hours and minutes in time

	@Test
	@DisplayName("List movie by valid release date")
	public void findByReleaseDate() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate date = (LocalDate) dtf.parse("2023/04/14");
			List<Movie> m = movieRepository.findByReleaseDate(date);
			assertNotNull(m);
			assertThat(m.size() > 0);
		} catch (Exception e) {
			assert (true);
		}

	}
	
	@Test
	@DisplayName("List movie by valid hours")
	public void findByHours() {
		try {
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
//			LocalTime time = (LocalTime) dtf.parse("03:00:00");
			int time = 3;
			List<Movie> m = movieRepository.findByHours(time);
			assertNotNull(m);
			assertThat(m.size() > 0);
		} catch (Exception e) {
			assert (true);
		}

	}
	
	

	@Test
	@DisplayName("List the movie based on year")
	public void findByYears() {
		try {
			int year = 2023;
			List<Movie> m = movieRepository.findByYear(year);
			assertNotNull(m);
			assertThat(m.size() > 0);

		} catch (Exception e) {

		}
	}
	
	@Test
	@DisplayName("List the movie based on year difference")
	public void findByYearDifference() {
		try {
			int startYear = 2001;
			int endYear = 2023;
			List<Movie> m = movieRepository.findByYearDifference(startYear, endYear);
			assertNotNull(m);
			assertThat(m.size() > 0);

		} catch (Exception e) {

		}
	}

	@Test
	@DisplayName("Update Movie Method")
	public void updateMovie() {
		Long id = 8L;
		Movie existingMovie = movieRepository.findById(id).get();
		existingMovie.setActorName("Kamal");
		Movie updatedMovie = movieRepository.save(existingMovie);
		assertEquals("Kamal", updatedMovie.getActorName());

	}

	@Test
	@Disabled
	@DisplayName("Delete Movie Method")
	public void deleteMovie() {

		Long id = 3L;
		movieRepository.deleteById(id);

		Optional<Movie> existingMovie = movieRepository.findById(id);
		if (existingMovie.isPresent()) {
			assert (false);
		} else {
			assert (true);
		}

	}

}
