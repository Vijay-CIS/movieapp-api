package com.Restapi.movieapp.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Restapi.movieapp.model.Movie;
import com.Restapi.movieapp.service.MovieService;

import lombok.extern.log4j.Log4j2;

/**
 * Movie Controller, It redirect the request and response to service layer
 * 
 * @author vijay
 *
 */

@Log4j2
@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	/**
	 * To insert a movie into Database
	 * 
	 * @param movie
	 * @return
	 */

	@PostMapping(value = " ", consumes = "application/json")
	public ResponseEntity<?> insertMovie(@Valid @RequestBody Movie movie) {
		log.info("MovieController - insertMovie()- POST ");
		try {
			movie.getDuration();
			movie.getReleaseDate();
			Movie savedmovie = movieService.insertMovie(movie);
			return new ResponseEntity<>(savedmovie, HttpStatus.CREATED);
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			e.printStackTrace();
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Display the list of mvovie
	 * 
	 * @return
	 */

	@GetMapping(value = "", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Movie> getAllMovies() {
		log.info("MovieController - getAllMovie()- GET");
		return movieService.getAllMovies();
	}

	/**
	 * Update the movie details
	 * 
	 * @param movie
	 * @return
	 */
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie) {
		log.info("MovieController - updateMovie()- PUT");
		try {
			Movie updateMovie = movieService.updateMovie(movie);
			return new ResponseEntity<>(updateMovie, HttpStatus.OK);
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			e.printStackTrace();
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Delete the movie using movie id
	 * 
	 * @param id
	 * @return
	 */

	@DeleteMapping(value = "/{id}", produces = "application/json")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id) {
		log.info("MovieController - deleteMovie()- DELETE");
		try {
			movieService.deleteMovie(id);
			return new ResponseEntity<>("Movie Deleted SuccessFully ", HttpStatus.OK);
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			e.printStackTrace();
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/**
	 * Get the single movie by movie id
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> getMovieById(@PathVariable("id") Long id) {
		log.info("MovieController - getMovieById()- GET ");
		try {
			Movie movieById = movieService.getMovieById(id);
			if (movieById != null) {
				return new ResponseEntity<>(movieById, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			e.printStackTrace();
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * List the movie by name
	 * 
	 * @param movieName
	 * @return
	 */

	@GetMapping(value = "/search1/{movieName}", produces = "application/json")
	public List<Movie> findMovieByMovieName(@PathVariable("movieName") String movieName) {
		log.info("MovieController - getMovieByMovieName()- GET ");
		if (movieName != null) {
			List<Movie> movieList = movieService.findMovieByMovieName(movieName);
			return movieList;
		}
		return null;
	}

	/**
	 * List the movie by actor name
	 * 
	 * @param actorName
	 * @return
	 */

	@GetMapping(value = "/search2/{actorName}", produces = "application/json")
	public List<Movie> findMovieByActorName(@PathVariable("actorName") String actorName) {
		log.info("MovieController - getMovieByActorName()- GET ");
		if (actorName != null) {
			List<Movie> movieList = movieService.findMovieByActorName(actorName);
			return movieList;
		}
		return null;
	}

	/**
	 * List the movie by genre
	 * 
	 * @param genre
	 * @return
	 */

	@GetMapping(value = "/search3/{genre}", produces = "application/json")
	public List<Movie> findMovieByGenre(@PathVariable("genre") String genre) {
		log.info("MovieController - getMovieByGenre()- GET ");
		if (genre != null) {
			List<Movie> movieList = movieService.findMovieByGenre(genre);
			return movieList;
		}
		return null;
	}

	/**
	 * List the movie by movie name. actor name, genre
	 * 
	 * @param movieName
	 * @param actorName
	 * @param genre
	 * @return
	 */

	@GetMapping(value = "/search", produces = "application/json")
	public List<Movie> findByNames(@RequestParam(name = "movieName", required = false) String movieName,
			@RequestParam(name = "actorName", required = false) String actorName,
			@RequestParam(name = "genre", required = false) String genre) {
		log.info("MovieController - findByNames()- GET ");
		if (movieName != null && actorName == null && genre == null) {
			log.info("MovieController - getMovieByMovieName()- GET ");
			List<Movie> movieList = movieService.findMovieByMovieName(movieName);
			return movieList;

		} else if (movieName == null && actorName != null && genre == null) {
			log.info("MovieController - getMovieByActorName()- GET ");
			List<Movie> movieList = movieService.findMovieByActorName(actorName);
			return movieList;
		} else if (movieName == null && actorName == null && genre != null) {
			log.info("MovieController - getMovieByGenre()- GET ");
			List<Movie> movieList = movieService.findMovieByGenre(genre);
			return movieList;
		} else if (movieName != null && actorName != null && genre != null) {
			log.info("MovieController - getMovieByMovieAndActorAndGenreName()- GET ");
			List<Movie> movieList = movieService.findByName(movieName, actorName, genre);
			return movieList;

		}
		return null;

	}

	/**
	 * List the movie based on the date,month,year
	 * 
	 * @param releaseDate
	 * @return
	 */
	@GetMapping(value = "/find/{releaseDate}", produces = "application/json")
	public List<Movie> findMoviebyReleaseDate(
			@PathVariable("releaseDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDate) {
		log.info("MovieController - getMovieByReleaseDate()- GET ");
		List<Movie> movieList = movieService.findMovieByReleaseDate(releaseDate);
		return movieList;

	}

	/**
	 * List the movie based on day
	 * 
	 * @param day
	 * @return
	 */

	@GetMapping(value = "/findDay", produces = "application/json")
	public List<Movie> findMovieByDay(@RequestParam("day") int day) {
		log.info("MovieController - getMovieByDay()- GET ");
		List<Movie> movieList = movieService.findMovieByDay(day);
		return movieList;
	}

	/**
	 * List the movie based on month
	 * 
	 * @param month
	 * @return
	 */

	@GetMapping(value = "/findMonth", produces = "application/json")
	public List<Movie> findMovieByMonth(@RequestParam("month") int month) {
		log.info("MovieController - getMovieByMonth()- GET ");
		List<Movie> movieList = movieService.findMovieByMonth(month);
		return movieList;
	}

	/**
	 * List the movie based on year
	 * 
	 * @param year
	 * @return
	 */

	@GetMapping(value = "/findYear/{year}", produces = "application/json")
	public List<Movie> findMovieByYear(@PathVariable int year) {
		log.info("MovieController - getMovieByYear()- GET ");
		List<Movie> movieList = movieService.findMovieByYear(year);
		return movieList;
	}

	/**
	 * List the movie between reference year and year diference
	 * 
	 * @param refYear
	 * @param yearDiff
	 * @return
	 */

//	@GetMapping(value = "/findYearDiff/{startYear}/{endYear}", produces = "application/json")
//	public List<Movie> findMovieByYearDiff(@PathVariable("startYear") int startYear,
//			@PathVariable("endYear") int endYear) {
//		log.info("MovieController - getMovieByYearDiff()- GET ");
//		List<Movie> movieList = movieService.findMovieByYearDiff(startYear, endYear);
//		return movieList;
//	}
//	
	/**
	 * List the movie by start year and end year using streams concept
	 * 
	 * @param startYear
	 * @param endYear
	 * @return
	 */

	@GetMapping(value = "/streams/{startYear}/{endYear}", produces = "application/json")
	public List<Movie> findMovieByYearDiff(@PathVariable("startYear") int startYear,
			@PathVariable("endYear") int endYear) {
		log.info("MovieController - getMovieByStreamsYear()- GET ");
		List<Movie> movieList = movieService.findMovieByYearDiff(startYear, endYear);
		return movieList;
	}

	/**
	 * List the movie by duration(hours)
	 * 
	 * @param hours
	 * @return
	 */

	@GetMapping(value = "/findHours/{hours}", produces = "application/json")
	public List<Movie> findMovieByHours(@PathVariable("hours") int hours) {
		log.info("MovieController - getMovieByhours()- GET ");
		List<Movie> movieList = movieService.findMovieByHours(hours);
		return movieList;
	}


	
	//Remainding tasks
	//1.distinct name and sorted using stream concept
	//2.hours greater than method using stream concept
	
	/**
	 * List the distinct movie by actorname using stream api
	 * @param actorName
	 * @return
	 */
	
	@GetMapping(value="/streams/{actorName}", produces ="application/json")
    public List<Movie> findMovieByDistinctName(@PathVariable("actorName") String actorName){
    	log.info("MovieController - getMovieByDistinctName()- GET ");
    	List<Movie> movieList = movieService.findMovieByDistinctName(actorName);
    	return movieList;
    }
	
	
	/**
	 * List the movie by sorted in reverse order using stream api
	 * @return
	 */
	@GetMapping(value="/streams",produces ="application/json")
	List<Movie> findMovieBySortedName(){
		log.info("MovieController - getMovieBySortedName()- GET ");
    	List<Movie> movieList = movieService.findMovieBySortedName();
    	return movieList;
	}
	
	/**
	 * List the movie by duration greater than hours using stream api
	 * @param duration
	 * @return
	 */
	
	@GetMapping(value="/streams1/{duration}",produces ="application/json")
	List<Movie> findMovieByGreaterThanHours(@PathVariable("duration") int duration){
		log.info("MovieController - findMovieByGreaterThanHours()- GET ");
    	List<Movie> movieList = movieService.findMovieByGreaterThanHours(duration);
    	return movieList;
	}
	

}
