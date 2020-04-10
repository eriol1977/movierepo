package com.berfra.movierepo.user;

import java.util.NoSuchElementException;
import java.util.Scanner;

import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieFactory;
import com.berfra.movierepo.Genre;
import com.berfra.movierepo.MovieLibrary;
import com.berfra.movierepo.MovieLoader;

public class SearchFileLibrary {

	public static void main(String[] args) throws Exception {
		System.out.println("Loading movie library...");
		MovieLoader loader = new MovieLoader(new FileMovieDatasource(), new FileMovieFactory());
		MovieLibrary library = loader.loadAllMovies("ALL", "All Movies", "All movies collection");

		Scanner scanner = new Scanner(System.in);
		System.out.println("Library loaded with " + library.size() + " movies.");
		System.out.println("Waiting for commands...");
		System.out.println("----------------------------------------------");
		try {
			while (true) {
				System.out.print(">> ");
				String line = scanner.nextLine();
				if (line.trim().toLowerCase().equals("exit")) {
					System.out.println("Goodbye!");
					System.exit(0);
				} else if (line.trim().toLowerCase().equals("all")) {
					System.out.println(library.print());
				} else if (line.trim().toLowerCase().startsWith("year")) {
					final String year = line.replace("year", "").trim();
					System.out.println(library.print(library.getMoviesFromYear(year)));
				} else if (line.trim().toLowerCase().startsWith("actor")) {
					final String actor = line.replace("actor", "").trim();
					System.out.println(library.print(library.getMoviesWithActor(actor)));
				} else if (line.trim().toLowerCase().startsWith("rating")) {
					final int rating = Integer.valueOf(line.replace("rating", "").trim()).intValue();
					System.out.println(library.print(library.getMoviesWithAvgRatingGreaterThan(rating)));
				} else if (line.trim().toLowerCase().startsWith("id")) {
					final String id = line.replace("id", "").trim();
					System.out.println(library.printDetail(library.getMovieById(id)));
				} else if (line.trim().toLowerCase().startsWith("title")) {
					final String title = line.replace("title", "").trim();
					System.out.println(library.print(library.getMoviesByTitle(title)));
				} else if (line.trim().toLowerCase().startsWith("director")) {
					final String director = line.replace("director", "").trim();
					System.out.println(library.print(library.getMoviesByDirector(director)));
				} else if (line.trim().toLowerCase().startsWith("plot")) {
					final String plot = line.replace("plot", "").trim();
					System.out.println(library.print(library.getMoviesByPlotContent(plot)));
				} else if (line.trim().toLowerCase().startsWith("genre")) {
					final String genre = line.replace("genre", "").trim();
					final Genre g = Genre.fromDescription(genre);
					if(g.equals(Genre.UNDEFINED))
						System.out.println("Invalid genre");
					else
						System.out.println(library.print(library.getMoviesByGenre(g)));
				} else if (line.trim().toLowerCase().startsWith("country")) {
					final String country = line.replace("country", "").trim();
					System.out.println(library.print(library.getMoviesByCountry(country)));
				}
				System.out.println("----------------------------------------------");
			}
		} catch (IllegalStateException | NoSuchElementException e) {
			// System.in has been closed
			System.out.println("System.in was closed; exiting");
		}
	}

}
