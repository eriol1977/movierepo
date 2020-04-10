package com.berfra.movierepo;

import java.util.List;

public interface IMovieDatasource {

	/**
	 * Returns a list of movie ids after searching for a specific title.
	 * 
	 * @param title The movie title to search for
	 * @return A list of movie ids resulting from the search of the informed title
	 * @throws MovieLoadException 
	 */
	List<String> loadMovieIdsByTitle(String title) throws MovieLoadException;

	/**
	 * Returns the text info corresponding to a specific movie.
	 * 
	 * @param id The id of the movie
	 * @return Text info corresponding to a specific movie
	 * @throws MovieLoadException 
	 */
	String loadMovieTextById(String id) throws MovieLoadException;
	
	/**
	 * Returns a list of all the availbale movie ids.
	 * 
	 * @return A list of movie ids
	 * @throws MovieLoadException 
	 */
	List<String> loadAllMovieIds() throws MovieLoadException;
}
