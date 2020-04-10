package com.berfra.movierepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MovieLoader implements IMovieLoader {

	private IMovieDatasource ds;
	private IMovieFactory fac;

	public MovieLoader(final IMovieDatasource datasource, final IMovieFactory factory) {
		super();
		this.ds = datasource;
		this.fac = factory;
	}

	public void setDatasource(final IMovieDatasource datasource) {
		this.ds = datasource;
	}

	@Override
	public List<Movie> loadMoviesByTitle(String title) throws MovieParseException, MovieLoadException {
		final List<String> ids = this.ds.loadMovieIdsByTitle(title);
		return loadMoviesFromIds(ids);
	}

	private List<Movie> loadMoviesFromIds(final List<String> ids) throws MovieParseException, MovieLoadException {
		final List<Movie> movies = new ArrayList<Movie>();
		// the use of stream with lambda wouldn't allow to throw the MovieParseException
		for (String id : ids) {
			movies.add(loadMovieById(id));
		}
		return movies;
	}

	@Override
	public Movie loadMovieById(String id) throws MovieParseException, MovieLoadException {
		final String jsonInfo = this.ds.loadMovieTextById(id);
		return this.fac.createMovie(jsonInfo);
	}

	@Override
	public MovieLibrary loadMovieLibrary(String title, String code, String description, String notes)
			throws MovieParseException, MovieLoadException {
		return new MovieLibrary(code, description, notes, new HashSet<Movie>(loadMoviesByTitle(title)));
	}

	@Override
	public MovieLibrary loadAllMovies(String code, String description, String notes)
			throws MovieParseException, MovieLoadException {
		final List<String> ids = this.ds.loadAllMovieIds();
		return new MovieLibrary(code, description, notes, new HashSet<Movie>(loadMoviesFromIds(ids)));
	}

}
