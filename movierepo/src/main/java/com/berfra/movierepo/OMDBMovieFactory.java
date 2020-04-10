package com.berfra.movierepo;

import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class OMDBMovieFactory implements IMovieFactory {

	@Override
	/**
	 * Creates a Movie object from an input json like:
	 * {
	 * "Title": "Blade Runner",
	 * "Year": "1982",
	 * "Rated": "R",
	 * "Released": "25 Jun 1982",
	 * "Runtime": "117 min",
	 * "Genre": "Action, Sci-Fi, Thriller",
	 * "Director": "Ridley Scott",
	 * "Writer": "Hampton Fancher (screenplay), David Webb Peoples (screenplay), Philip K. Dick (novel)",
	 * "Actors": "Harrison Ford, Rutger Hauer, Sean Young, Edward James Olmos",
	 * "Plot": "A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.",
	 * "Language": "English, German, Cantonese, Japanese, Hungarian, Arabic",
	 * "Country": "USA, Hong Kong, UK",
	 * "Awards": "Nominated for 2 Oscars. Another 12 wins & 16 nominations.",
	 * "Poster": "https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
	 * "Ratings": [
	 *   {
	 * "Source": "Internet Movie Database",
	 * "Value": "8.1/10"
	 * },
	 *   {
	 * "Source": "Rotten Tomatoes",
	 * "Value": "90%"
	 * },
	 *   {
	 * "Source": "Metacritic",
	 * "Value": "84/100"
	 * }
	 * ],
	 * "Metascore": "84",
	 * "imdbRating": "8.1",
	 * "imdbVotes": "658,507",
	 * "imdbID": "tt0083658",
	 * "Type": "movie",
	 * "DVD": "27 Aug 1997",
	 * "BoxOffice": "N/A",
	 * "Production": "Warner Bros. Pictures",
	 * "Website": "N/A",
	 * "Response": "True"
	 * }
	 * 
	 * @param movieString Movie information in text format
	 * @return the created movie
	 * @throws MovieParseException 
	 */
	public Movie createMovie(String movieString) throws MovieParseException {
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject parsedObject = (JSONObject) jsonParser.parse(movieString);

			// basic info
			String id = (String) parsedObject.get("imdbID");
			String title = (String) parsedObject.get("Title");
			String year = (String) parsedObject.get("Year");
			String rt = (String) parsedObject.get("Runtime");
			int runtime = 0;
			try {
				runtime = Integer.valueOf(rt.replace("min", "").trim()).intValue();
			}catch(NumberFormatException e) {
				// do nothing but set the runtime to 0, if it isn't in standard format (es: "117 min")
			}
			String director = (String) parsedObject.get("Director");
			String plot = (String) parsedObject.get("Plot");
			String country = (String) parsedObject.get("Country");
			Movie movie = new Movie(id, title, year, runtime, director, plot, country);

			// genres (es: from "Action, Sci-Fi, Thriller" to Genre list)
			String gs = (String) parsedObject.get("Genre");
			Arrays.stream(gs.split(",")).map(desc -> Genre.fromDescription(desc)).forEach(movie::addGenre);

			// actors
			String as = (String) parsedObject.get("Actors");
			Arrays.stream(as.split(",")).map(act -> new Actor(act)).forEach(movie::addActor);

			// ratings
			JSONArray rsArray = (JSONArray) parsedObject.get("Ratings");
			for (Object rs : rsArray) {
				JSONObject oRs = (JSONObject) rs;
				String sourceName = (String) oRs.get("Source");
				String rtg = (String) oRs.get("Value");
				movie.addRating(new Rating(Source.fromName(sourceName), rtg));
			}

			return movie;
		} catch (Exception e) {
			MovieParseException exc = new MovieParseException();
			exc.initCause(e);
			throw exc;
		}
	}

}
