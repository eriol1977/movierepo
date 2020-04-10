package com.berfra.movierepo;

public interface IMovieFactory {
	
	/**
	 * Creates a Movie object from an input text.
	 * 
	 * @param movieString Movie information in text format
	 * @return the created movie
	 * @throws MovieParseException 
	 */
	Movie createMovie(final String movieString) throws MovieParseException;
}
