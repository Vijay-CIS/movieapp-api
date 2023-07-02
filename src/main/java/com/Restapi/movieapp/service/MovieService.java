package com.Restapi.movieapp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.Restapi.movieapp.model.Movie;

public interface MovieService {

	Movie insertMovie(Movie movie) throws Exception;

	List<Movie> getAllMovies();

	Movie updateMovie(Movie movie) throws Exception;

	void deleteMovie(Long id) throws Exception;

	Movie getMovieById(Long id) throws Exception;

	List<Movie> findMovieByMovieName(String movieName);

	List<Movie> findMovieByActorName(String actorName);

	List<Movie> findMovieByGenre(String genre);

	List<Movie> findByName(String movieName, String actorName, String genre);

	List<Movie> findMovieByReleaseDate(LocalDate releaseDate);

	List<Movie> findMovieByDay(int day);

	List<Movie> findMovieByMonth(int month);

	List<Movie> findMovieByYear(int year);

	List<Movie> findMovieByYearDiff(int startYear, int endYear);

	List<Movie> findMovieByHours(int hours);

	List<Movie> findMovieByDistinctName(String actorName);

	List<Movie> findMovieBySortedName();

	List<Movie> findMovieByGreaterThanHours(int duration);


}
