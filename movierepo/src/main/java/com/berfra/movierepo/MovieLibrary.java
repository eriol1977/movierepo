package com.berfra.movierepo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieLibrary {

	private final String code;
	private final String description;
	private final String notes;
	private final Set<Movie> movies;

	public MovieLibrary(String code, String description, String notes, Set<Movie> movies) {
		super();
		this.code = code;
		this.description = description;
		this.notes = notes;
		this.movies = movies;
	}

	public MovieLibrary(String code, String description, String notes) {
		this(code, description, notes, new HashSet<Movie>());
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getNotes() {
		return notes;
	}

	public Set<Movie> getMovies() {
		return new HashSet<Movie>(this.movies);
	}

	public void addMovie(final Movie movie) {
		this.movies.add(movie);
	}

	public Set<Movie> getMoviesFromYear(final String year) {
		return this.movies.stream().filter(m -> m.getYear().equals(year)).collect(Collectors.toSet());
	}

	public Set<Movie> getMoviesWithActor(final String actor) {
		return this.movies.stream().filter(m -> m.getActors().stream().anyMatch(a -> a.couldBe(actor))).collect(Collectors.toSet());
	}

	public Set<Movie> getMoviesWithAvgRatingGreaterThan(final int rating) {
		return this.movies.stream().filter(m -> m.getAverageRating() >= rating).collect(Collectors.toSet());
	}

	public Movie getMovieById(final String id) {
		return this.movies.stream().filter(m -> m.getId().equals(id)).findAny().orElse(Movie.EMPTY);
	}
	
	public Set<Movie> getMoviesByTitle(final String title) {
		return this.movies.stream().filter(m -> m.couldBe(title)).collect(Collectors.toSet());
	}
	
	public Set<Movie> getMoviesByDirector(final String director) {
		return this.movies.stream().filter(m -> m.directorCouldBe(director)).collect(Collectors.toSet());
	}
	
	public Set<Movie> getMoviesByPlotContent(final String phrase) {
		return this.movies.stream().filter(m -> m.plotContainsPhrase(phrase)).collect(Collectors.toSet());
	}
	
	public Set<Movie> getMoviesByGenre(final Genre genre) {
		return this.movies.stream().filter(m -> m.isOfGenre(genre)).collect(Collectors.toSet());
	}
	
	public void saveMoviesToFile() throws MovieSaveException {
		final FileMovieSaver saver = new FileMovieSaver();
		for (final Movie movie : this.movies)
			saver.saveMovie(movie);
	}

	public int size() {
		return this.movies.size();
	}

	public String print() {
		return print(this.movies);
	}

	public String print(final Set<Movie> movs) {
		if(movs == null || movs.size() == 0)
			return "Nothing to print";
		StringBuilder sb = new StringBuilder();
		movs.stream().sorted(Comparator.comparing(Movie::getTitle))
				.forEach(m -> sb.append(print(m)).append("\n"));
		return sb.toString();
	}
	
	public String print(final Movie movie) {
		StringBuilder sb = new StringBuilder();
		sb.append(movie.getTitle()).append(" (").append(movie.getYear()).append(") [").append(movie.getId()).append("]");
		return sb.toString();
	}
	
	public String printDetail(final Movie movie) {
		return movie.toString();
	}
	
	@Override
	public String toString() {
		return "MovieLibrary [code=" + code + ", description=" + description + ", notes=" + notes + ", movies=" + movies
				+ "]";
	}

}
