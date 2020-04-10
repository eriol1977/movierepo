package com.berfra.movierepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

	public final static Movie EMPTY = new Movie("-1", "Movie not found", "", 0, "", "", "");
	
	private final String id;
	private final String title;
	private final String year;
	private final int runtime;
	private final String director;
	private final String plot;
	private final String country;
	private final List<Genre> genres;
	private final List<Actor> actors;
	private final List<Rating> ratings;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES) // these annotations are necessary to deserialize an immutable object with Jackson
	public Movie(@JsonProperty("id") String id, @JsonProperty("title") String title, @JsonProperty("year") String year,
			@JsonProperty("runtime") int runtime, @JsonProperty("director") String director,
			@JsonProperty("plot") String plot, @JsonProperty("country") String country) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.runtime = runtime;
		this.director = director;
		this.plot = plot;
		this.country = country;
		this.genres = new ArrayList<Genre>();
		this.actors = new ArrayList<Actor>();
		this.ratings = new ArrayList<Rating>();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public int getRuntime() {
		return runtime;
	}

	public String getDirector() {
		return director;
	}

	public String getPlot() {
		return plot;
	}

	public String getCountry() {
		return country;
	}

	public List<Genre> getGenres() {
		return new ArrayList<Genre>(this.genres);
	}

	public List<Actor> getActors() {
		return new ArrayList<Actor>(this.actors);
	}

	public List<Rating> getRatings() {
		return new ArrayList<Rating>(this.ratings);
	}

	public void addRating(final Rating rating) {
		this.ratings.add(rating);
	}

	public void addGenre(final Genre genre) {
		this.genres.add(genre);
	}

	public void addActor(final Actor actor) {
		this.actors.add(actor);
	}

	public List<String> getActorNames() {
		return this.actors.stream().map(a -> a.getName()).collect(Collectors.toList());
	}

	public boolean couldBe(final String title) {
		return this.title.toLowerCase().contains(title.toLowerCase());
	}
	
	public boolean directorCouldBe(final String dir) {
		return this.director.toLowerCase().contains(dir.toLowerCase());
	}
	
	public boolean plotContainsPhrase(final String phrase) {
		return this.plot.toLowerCase().contains(phrase.toLowerCase());
	}
	
	public boolean isOfGenre(final Genre genre) {
		return this.genres.contains(genre);
	}
	
	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", year=" + year + ", runtime=" + runtime + ", director="
				+ director + ", plot=" + plot + ", country=" + country + ", genres=" + genres + ", actors=" + actors
				+ ", ratings=" + ratings + "]";
	}

	public double getAverageRating() {
		if (this.ratings == null || this.ratings.size() == 0)
			return 0;
		return this.ratings.stream().mapToInt(rating -> rating.getPercRating()).average().getAsDouble();
	}

}
