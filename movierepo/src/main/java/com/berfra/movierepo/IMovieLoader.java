package com.berfra.movierepo;

import java.util.List;

public interface IMovieLoader {
	
	/**
	 * Returns a list of movies after searching for a specific title.
	 * 
	 * @param title The movie title to search for
	 * @return A list of movies resulting from the search of the informed title
	 * @throws MovieParseException 
	 * @throws MovieLoadException 
	 */
	List<Movie> loadMoviesByTitle(final String title) throws MovieParseException, MovieLoadException;
	
	/**
	 * Returns the movie corresponding to a specific id
	 * 
	 * @param id The id of the movie
	 * @return Movie
	 * @throws MovieParseException 
	 * @throws MovieLoadException 
	 */
	Movie loadMovieById(final String id) throws MovieParseException, MovieLoadException;
	
	/**
	 * Returns a Movie Library based on the searched title.
	 * 
	 * @param title
	 * @param code
	 * @param description
	 * @param notes
	 * @return
	 * @throws MovieParseException 
	 * @throws MovieLoadException 
	 */
	MovieLibrary loadMovieLibrary(final String title, final String code, final String description, final String notes) throws MovieParseException, MovieLoadException;

	/**
	 * Returns a Movie Library based on all the available movies.
	 * 
	 * @param code
	 * @param description
	 * @param notes
	 * @return
	 * @throws MovieParseException 
	 * @throws MovieLoadException 
	 */
	MovieLibrary loadAllMovies(final String code, final String description, final String notes) throws MovieParseException, MovieLoadException;
}
