package com.Restapi.movieapp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Restapi.movieapp.model.Movie;
import com.Restapi.movieapp.repository.MovieRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public Movie insertMovie(Movie movie) throws Exception {
		log.info("MovieServiceImpl - insertMovie() ");
		List<Movie> movies = movieRepository.findByMovieName(movie.getMovieName());
		if (movies.size() > 0) {
			throw new Exception("Movie Name is Already Exist");
		}
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> getAllMovies() {
		log.info("MovieServiceImpl - getAllMovies() ");
		return movieRepository.findAll();
	}

	@Override
	public Movie updateMovie(Movie movie) throws Exception {
		log.info("MovieServiceImpl - updateMovie() ");
		Movie movie1 = movieRepository.findById(movie.getId()).orElse(null);
		if (movie1 != null) {
			movie1.setActorName(movie1.getActorName());
			movie1.setMovieName(movie1.getMovieName());
			movie1.setGenre(movie1.getGenre());
			movie1.setDuration(movie1.getDuration());
			movie1.setReleaseDate(movie1.getReleaseDate());
			return movieRepository.save(movie);
		} else {
			throw new Exception("Invalid Movie Id: " + movie.getId());

		}

	}

	@Override
	public void deleteMovie(Long id) throws Exception {
		log.info("MovieServiceImpl - deleteMovie() ");
		Movie movie = movieRepository.findById(id).orElse(null);
		if (movie != null) {
			movieRepository.delete(movie);
		} else {
			throw new Exception("Movie Not Found :" + id);
		}

	}

	@Override
	public Movie getMovieById(Long id) throws Exception {
		log.info("MovieServiceImpl - getMovieById() ");
		Movie movie = movieRepository.findById(id).orElse(null);
		if (movie == null) {
			throw new Exception("Invalid Movie Id: " + id);
		}
		return movie;

	}

	@Override
	public List<Movie> findMovieByMovieName(String movieName) {
		log.info("MovieServiceImpl - findMovieByMovieName() ");
		return movieRepository.findByMovieName(movieName);
	}

	@Override
	public List<Movie> findMovieByActorName(String actorName) {
		log.info("MovieServiceImpl - findMovieByActorName() ");
		return movieRepository.findByActorName(actorName);
	}

	@Override
	public List<Movie> findMovieByGenre(String genre) {
		log.info("MovieServiceImpl - findMovieByGenre() ");
		return movieRepository.findByGenre(genre);
	}

	@Override
	public List<Movie> findByName(String movieName, String actorName, String genre) {
		log.info("MovieServiceImpl - findByName() ");
		return movieRepository.findByMovieNameAndActorNameAndGenre(movieName, actorName, genre);
	}

	@Override
	public List<Movie> findMovieByReleaseDate(LocalDate releaseDate) {
		log.info("MovieServiceImpl - findByReleaseDate() ");
		List<Movie> movieList = movieRepository.findByReleaseDate(releaseDate);
		return movieList;

	}

	@Override
	public List<Movie> findMovieByDay(int day) {
		log.info("MovieServiceImpl - findByDay() ");
		List<Movie> movieList = movieRepository.findByDay(day);
		return movieList;
	}

	@Override
	public List<Movie> findMovieByMonth(int month) {
		log.info("MovieServiceImpl - findByMonth() ");
		List<Movie> movieList = movieRepository.findByMonth(month);
		return movieList;
	}

	@Override
	public List<Movie> findMovieByYear(int year) {
		log.info("MovieServiceImpl - findByYear) ");
		List<Movie> movieList = movieRepository.findByYear(year);
		return movieList;
	}

//	@Override
//	public List<Movie> findMovieByYearDiff(int startYear, int endYear) {
//		log.info("MovieServiceImpl - findByYearDifference ");
//		List<Movie> movieList = movieRepository.findByYearDifference(startYear, endYear);
//		return movieList;
//	}

	@Override
	public List<Movie> findMovieByYearDiff(int startYear, int endYear) {
		log.info("MovieServiceImpl - findByYearBetween ");
		List<Movie> movieList = movieRepository.findAll();
		LocalDate startDate = LocalDate.of(startYear, 1, 1);
		LocalDate endDate = LocalDate.of(endYear, 12, 31);

		return movieList.stream()
				.filter(movie -> movie.getReleaseDate().isAfter(startDate) && movie.getReleaseDate().isBefore(endDate))
				.collect(Collectors.toList());
	}

	@Override
	public List<Movie> findMovieByHours(int hours) {
		log.info("MovieServiceImpl - findByHours ");
		List<Movie> movieList = movieRepository.findByHours(hours);
		return movieList;
	}

	@Override
	public List<Movie> findMovieByDistinctName(String actorName) {
		log.info("MovieServiceImpl - findMovieByDistinctName ");
		List<Movie> movieList = movieRepository.findByActorName(actorName);
		return movieList.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<Movie> findMovieBySortedName() {
		log.info("MovieServiceImpl - findMovieBySortedName ");
		List<Movie> movieList = movieRepository.findAll();
		return movieList.stream().sorted(Comparator.comparing(Movie::getActorName).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public List<Movie> findMovieByGreaterThanHours(int duration) {
		log.info("MovieServiceImpl - findMovieByGreaterThanHours");
		  List<Movie> movieList = movieRepository.findAll();
		  LocalTime startHours = LocalTime.of(duration, 0);
		//  LocalTime endHours = LocalTime.of(duration, 59);
		  return movieList.stream().filter((movie)->movie.getDuration().isAfter(startHours)).collect(Collectors.toList());
				  
	    }

}
