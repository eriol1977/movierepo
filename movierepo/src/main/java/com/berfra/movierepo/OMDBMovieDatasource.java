package com.berfra.movierepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OMDBMovieDatasource implements IMovieDatasource {

	private final static String APIKEY = "a073d924";
	
	/**
	 * Returns a list of movie ids after searching for a specific title.
	 * 
	 * This implementation is based on the OMDB API search, which returns paginated json content such as this:
	 * 
	 * {
	* "Search": [
	*   {
	* "Title": "Stranger Than Paradise",
	* "Year": "1984",
	* "imdbID": "tt0088184",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BMDQ1ODM0NzctNDUwZS00NTI3LThjYWQtY2QyN2U5NDAyNDM3XkEyXkFqcGdeQXVyNTc1NTQxODI@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "1492: Conquest of Paradise",
	* "Year": "1992",
	* "imdbID": "tt0103594",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BZDViYzU4ZGUtN2IyMi00YzljLWJiMGUtNDljZTk4MGUxOWI4L2ltYWdlXkEyXkFqcGdeQXVyNjQzNDI3NzY@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "Paradise Now",
	* "Year": "2005",
	* "imdbID": "tt0445620",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BNDI5ODQxNTUxMV5BMl5BanBnXkFtZTcwMTgzMDEzMQ@@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "Escobar: Paradise Lost",
	* "Year": "2014",
	* "imdbID": "tt2515030",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BNTUzMDQ0MzA4OV5BMl5BanBnXkFtZTgwNDY3OTc2MjE@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "Children of Paradise",
	* "Year": "1945",
	* "imdbID": "tt0037674",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BOTgzZWIxNGYtNzE3OC00NjJlLThlMDgtYzgxZTUzOWQ2MGJkL2ltYWdlXkEyXkFqcGdeQXVyNzE3NzY5NTM@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "Phantom of the Paradise",
	* "Year": "1974",
	* "imdbID": "tt0071994",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BZWI5NDk1MDgtYzYwZi00ZmZhLThiN2QtN2U5OGExNTBhOTZhXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "Paradise Lost: The Child Murders at Robin Hood Hills",
	* "Year": "1996",
	* "imdbID": "tt0117293",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BN2EzNWNlZGYtMjQ1YS00YTI4LThkYTctNjRjYzI1NDdmNjEyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "The Color of Paradise",
	* "Year": "1999",
	* "imdbID": "tt0191043",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BMTgyMTcwOTc0Nl5BMl5BanBnXkFtZTcwNjAyNTEzMQ@@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "Return to Paradise",
	* "Year": "1998",
	* "imdbID": "tt0124595",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BNjIyZDZmNDYtNTk0Mi00N2MyLWFhN2ItZjhiYzY4ZGU2NjI4XkEyXkFqcGdeQXVyNjU0NTI0Nw@@._V1_SX300.jpg"
	* },
	*   {
	* "Title": "Revenge of the Nerds II: Nerds in Paradise",
	* "Year": "1987",
	* "imdbID": "tt0093857",
	* "Type": "movie",
	* "Poster": "https://m.media-amazon.com/images/M/MV5BYThmOGViMjQtYjYwMS00ZmI5LWEwNjMtNmQ5YzhmYWY5NTAyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
	* }
	* ],
	* "totalResults": "1132",
	* "Response": "True"
	* }
	 * 
	 * @param title The movie title to search for
	 * @return A list of movie ids resulting from the search of the informed title
	 * @throws MovieLoadException 
	 */
	public List<String> loadMovieIdsByTitle(String title) throws MovieLoadException {
		
		final List<String> ids = new ArrayList<String>();
		
		try {
			String searchResult = callSearchURL(title, 1);
			final int totalSearchPages = getTotalSearchPages(searchResult);
			ids.addAll(getSearchIds(searchResult));
			
			for(int i=2; i <= totalSearchPages; i++) { 
			  searchResult = callSearchURL(title, i);
			  ids.addAll(getSearchIds(searchResult));
			}
			 
		}catch(Exception e) {
			MovieLoadException exc = new MovieLoadException();
			exc.initCause(e);
			throw exc;
		}
		
		return ids;
	}

	private List<String> getSearchIds(final String searchResult) throws ParseException {
		final List<String> ids = new ArrayList<String>();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject parsedObject = (JSONObject) jsonParser.parse(searchResult);
		JSONArray moviesArray = (JSONArray) parsedObject.get("Search");
		for (Object movie : moviesArray) {
			JSONObject oMovie = (JSONObject) movie;
			ids.add((String) oMovie.get("imdbID"));
		}
		
		return ids;
	}
	
	private int getTotalSearchPages(final String searchResult) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject parsedObject = (JSONObject) jsonParser.parse(searchResult);
		final int totalResults = Integer.valueOf((String) parsedObject.get("totalResults")).intValue();
		int tot = (totalResults / 10) + (totalResults % 10 > 0 ? 1 : 0);
		return tot;
	}
	
	private String callSearchURL(final String title, final int page) throws MalformedURLException, ProtocolException, IOException, MovieLoadException {
		String urlText = "http://www.omdbapi.com/?s=" + title + "&type=movie&page=" + page + "&apikey=" + APIKEY;
		return this.callURL(urlText);
	}
	
	/**
	 * Returns the text info corresponding to a specific movie, in the following format:
	 * 
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
	 * @param id The id of the movie
	 * @return Text info corresponding to a specific movie
	 * @throws MovieLoadException 
	 */
	public String loadMovieTextById(String id) throws MovieLoadException {
		try {
			final String url = "http://www.omdbapi.com/?i=" + id + "&apikey=" + APIKEY;
			return callURL(url);
		}catch(Exception e) {
			MovieLoadException exc = new MovieLoadException();
			exc.initCause(e);
			throw exc;
		}
	}

	private String callURL(final String urlText) throws MalformedURLException, IOException, ProtocolException, MovieLoadException {
		URL url = new URL(urlText);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();
		
		con.disconnect();
		
		String result = content.toString();
		if(result.contains("\"Response\":\"False\""))
			throw new MovieLoadException();
		
		return result;
	}

	@Override
	public List<String> loadAllMovieIds() throws MovieLoadException {
		MovieLoadException exc = new MovieLoadException();
		exc.initCause(new Exception("Operation unavailable: too many results would be considered"));
		throw exc;
	}

}
