package com.Restapi.movieapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie1")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private Long id;
	@NotEmpty(message = "Please provide movie name")
	@Column(name = "movie_name")
	private String movieName;
	@NotEmpty(message = "Please provide actor name")
	@Column(name = "actor_name")
	private String actorName;
	@NotEmpty(message = "Please provide genre")
	@Column(name = "genre")
	private String genre;
	@NotNull(message = "Please provide time")
	@JsonFormat(pattern="HH:mm:ss")
	@Column(name = "duration")
	private LocalTime duration;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	@NotNull(message = "Please provide ReleaseDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "release_date")
	private LocalDate releaseDate;
	

}
