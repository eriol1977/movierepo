package com.berfra.movierepo.user;

import com.berfra.movierepo.MovieLibrary;
import com.berfra.movierepo.MovieLoader;
import com.berfra.movierepo.OMDBMovieDatasource;
import com.berfra.movierepo.OMDBMovieFactory;

public class SearchOMDBMoviesAndSaveThemAsFiles {

	public static void main(String[] args) throws Exception {
		String title = "panther";
		MovieLoader omdbLoader = new MovieLoader(new OMDBMovieDatasource(), new OMDBMovieFactory());
		MovieLibrary library = omdbLoader.loadMovieLibrary(title, title.toUpperCase(), title, "");
		library.saveMoviesToFile();
		System.out.printf("FINISHED saving %d movies:\n",library.size());
		System.out.println(library.print());
	}

}
