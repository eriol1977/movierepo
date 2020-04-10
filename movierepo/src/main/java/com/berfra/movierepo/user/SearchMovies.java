package com.berfra.movierepo.user;

import java.util.List;

import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieFactory;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.MovieLoader;

public class SearchMovies {

	public static void main(String[] args) throws Exception {
//		String word = "star wars";
//		MovieLoader loader = new MovieLoader(new OMDBMovieDatasource(), new OMDBMovieFactory());
//		MovieLibrary library = loader.loadMovieLibrary(word, "USER_SEARCH", "Manual user search", "All movies containing the word " + word + " in the title.");
//		System.out.println(library);

//		MovieLoader loader = new MovieLoader(new OMDBMovieDatasource(), new OMDBMovieFactory());
//		Movie movie = loader.loadMovieById("tt0458339");
//		new FileMovieSaver().saveMovie(movie);
//		System.out.println(movie);
		
		MovieLoader loader = new MovieLoader(new FileMovieDatasource(), new FileMovieFactory());
		List<Movie> moviesByTitle = loader.loadMoviesByTitle("Captain America: The First Avenger");
		System.out.println(moviesByTitle);
	}

}
