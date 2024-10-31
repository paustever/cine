// src/main/java/com/proyecto/proyectostic/model/Movie.java
package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;


    @Column(name = "language")
    private String language;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "director")
    private String director;

    @Column(name = "review")
    private String review;

    @Column(name = "movie_cast")
    private String movieCast;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "cover_image_base64")
    private String coverImagebase64;


    @Column(name = "age_rating")
    private Integer ageRating;



//    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ShowTime> showTimes = new HashSet<>();

    public Movie() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(String movieCast) {
        this.movieCast = movieCast;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


//    public Set<ShowTime> getShowTimes() {
//        return showTimes;
//    }
//
//    public void setShowTimes(Set<ShowTime> showTimes) {
//        this.showTimes = showTimes;
//    }


    public Integer getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Integer ageRating) {
        this.ageRating = ageRating;
    }
}

