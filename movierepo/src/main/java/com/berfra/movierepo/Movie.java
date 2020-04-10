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

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES) // these annotations are necessary to deserialize an immutable
														// object with Jackson
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

	public boolean isFromCountry(final String country) {
		return this.country.toLowerCase().contains(country.toLowerCase());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(title).append(" (").append(year).append(") {").append(id).append("}\n");
		sb.append("Directed by: ").append(director).append("\n");
		sb.append("Starring: ").append(actors).append("\n");
		sb.append("Genre: ").append(genres).append("\n");
		sb.append("Summary: ").append(plot).append("\n");
		sb.append("Runtime: ").append(runtime).append("m\n");
		sb.append("Country: ").append(country).append("\n");
		sb.append("Ratings: ").append(ratings).append("\n");
		sb.append("Average rating: ").append(getAverageRating()).append("/100\n");
		return sb.toString().replace("[", "").replace("]", "");
	}

	public int getAverageRating() {
		if (this.ratings == null || this.ratings.size() == 0)
			return 0;
		return Double.valueOf(this.ratings.stream().mapToInt(rating -> rating.getPercRating()).average().getAsDouble())
				.intValue();
	}

}
