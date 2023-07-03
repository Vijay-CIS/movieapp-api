package com.Restapi.movieapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Restapi.movieapp.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    //Custom JPA
	List<Movie> findByMovieName(String movieName);
    //Custom JPA
	List<Movie> findByActorName(String actorName);
    //Custom JPA
	List<Movie> findByGenre(String genre);

	List<Movie> findByMovieNameAndActorNameAndGenre(String movieName, String actorName, String genre);

	List<Movie> findByReleaseDate(LocalDate releaseDate);
    //named parameter
	@Query("select m from Movie m WHERE DAY(m.releaseDate)=:day")
	List<Movie> findByDay(@Param("day") int day);

	//named parameter
	@Query("select m from Movie m WHERE MONTH(m.releaseDate)= :month")
	List<Movie> findByMonth(@Param("month") int month);
   
	//positional parameter
	@Query("select m from Movie m WHERE YEAR(m.releaseDate)= ?1")
	List<Movie> findByYear(int year);

	//positional paramter
	@Query("select m from Movie m WHERE YEAR(m.releaseDate) BETWEEN ?1 AND ?2")
	List<Movie> findByYearDifference(int startYear, int endYear);
   
	//positional parameter
	@Query("select m from Movie m WHERE HOUR (m.duration) =?1")
	List<Movie> findByHours(int hours);


}
