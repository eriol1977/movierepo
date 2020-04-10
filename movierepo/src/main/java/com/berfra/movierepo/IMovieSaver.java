package com.berfra.movierepo;

public interface IMovieSaver {
	
	/**
	 * Saves a movie into some permanent memory device
	 * 
	 * @param movie
	 * @throws MovieSaveException 
	 */
	void saveMovie(final Movie movie) throws MovieSaveException;
}
