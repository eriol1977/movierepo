package com.berfra.movierepo;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileMovieFactory implements IMovieFactory {

	/**
	 * Creates a Movie object from an input json like:
	 * 
	 * {"id":"tt0083658","title":"Blade
	 * Runner","year":"1982","runtime":117,"director":"Ridley Scott","plot":"A blade
	 * runner must pursue and terminate four replicants who stole a ship in space,
	 * and have returned to Earth to find their creator.","country":"USA, Hong Kong,
	 * UK","genres":["ACTION","SCIFI","THRILLER"],"actors":[{"name":"Harrison
	 * Ford"},{"name":"Rutger Hauer"},{"name":"Sean Young"},{"name":"Edward James
	 * Olmos"}],"ratings":[{"source":"IMDB","rating":"8.1/10"},{"source":"ROTTEN","rating":"90%"},{"source":"META","rating":"84/100"}]}
	 * 
	 * @param movieString Movie information in text format
	 * @return the created movie
	 * @throws MovieParseException
	 */
	public Movie createMovie(String movieString) throws MovieParseException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(movieString, Movie.class);
		} catch (Exception e) {
			MovieParseException exc = new MovieParseException();
			exc.initCause(e);
			throw exc;
		}
	}

}
