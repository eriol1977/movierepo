package com.berfra.movierepo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.berfra.movierepo.Actor;
import com.berfra.movierepo.Genre;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.MovieLibrary;
import com.berfra.movierepo.Rating;
import com.berfra.movierepo.Source;

class TestMovieLibrary {

	private static final MovieLibrary library = new MovieLibrary("TEST", "Test Library", "Some test movie library");

	@BeforeAll
	public static void init() {
		moviesProvider().forEach(library::addMovie);
	}

	@Test
	void testMoviesFrom1982() throws Exception {
		Set<Movie> movies = library.getMoviesFromYear("1982");
		assertEquals(2, movies.size());
		List<String> ids = movies.stream().map(m -> m.getId()).collect(Collectors.toList());
		assertTrue(ids.contains("001"));
		assertFalse(ids.contains("002"));
		assertTrue(ids.contains("003"));
	}

	@Test
	void testMoviesFrom1989() throws Exception {
		Set<Movie> movies = library.getMoviesFromYear("1989");
		assertEquals(1, movies.size());
		List<String> ids = movies.stream().map(m -> m.getId()).collect(Collectors.toList());
		assertFalse(ids.contains("001"));
		assertTrue(ids.contains("002"));
		assertFalse(ids.contains("003"));
	}

	@Test
	void testMoviesFrom1990() throws Exception {
		Set<Movie> movies = library.getMoviesFromYear("1990");
		assertEquals(0, movies.size());
	}

	@Test
	void testMoviesWithHarrisonFord() throws Exception {
		Set<Movie> movies = library.getMoviesWithActor("Harrison Ford");
		assertEquals(2, movies.size());
		List<String> ids = movies.stream().map(m -> m.getId()).collect(Collectors.toList());
		assertTrue(ids.contains("001"));
		assertTrue(ids.contains("002"));
		assertFalse(ids.contains("003"));
	}

	@Test
	void testMoviesWithKurtRussel() throws Exception {
		Set<Movie> movies = library.getMoviesWithActor("Kurt Russel");
		assertEquals(1, movies.size());
		List<String> ids = movies.stream().map(m -> m.getId()).collect(Collectors.toList());
		assertFalse(ids.contains("001"));
		assertFalse(ids.contains("002"));
		assertTrue(ids.contains("003"));
	}

	@Test
	void testMoviesWithAndyGarcia() throws Exception {
		Set<Movie> movies = library.getMoviesWithActor("Andy Garcia");
		assertEquals(0, movies.size());
	}

	@Test
	void testMoviesWithAvgRatingGreaterThan80() throws Exception {
		Set<Movie> movies = library.getMoviesWithAvgRatingGreaterThan(80);
		assertEquals(1, movies.size());
		List<String> ids = movies.stream().map(m -> m.getId()).collect(Collectors.toList());
		assertTrue(ids.contains("001"));
		assertFalse(ids.contains("002"));
		assertFalse(ids.contains("003"));
	}
	
	@Test
	void testMoviesWithAvgRatingGreaterThan70() throws Exception {
		Set<Movie> movies = library.getMoviesWithAvgRatingGreaterThan(70);
		assertEquals(3, movies.size());
		List<String> ids = movies.stream().map(m -> m.getId()).collect(Collectors.toList());
		assertTrue(ids.contains("001"));
		assertTrue(ids.contains("002"));
		assertTrue(ids.contains("003"));
	}
	
	@Test
	void testMoviesWithAvgRatingGreaterThan90() throws Exception {
		Set<Movie> movies = library.getMoviesWithAvgRatingGreaterThan(90);
		assertEquals(0, movies.size());
	}
	
	private static Stream<Movie> moviesProvider() {
		Movie bladeRunner = new Movie("001", "Blade Runner", "1982", 117, "Ridley Scott",
				"A blade runner must pursue and terminate four replicants...", "USA");
		bladeRunner.addGenre(Genre.ACTION);
		bladeRunner.addGenre(Genre.SCIFI);
		bladeRunner.addGenre(Genre.THRILLER);
		bladeRunner.addActor(new Actor("Harrison Ford"));
		bladeRunner.addActor(new Actor("Rutger Hauer"));
		bladeRunner.addRating(new Rating(Source.IMDB, "8.1/10"));
		bladeRunner.addRating(new Rating(Source.ROTTEN, "90%"));
		bladeRunner.addRating(new Rating(Source.META, "84/100"));

		Movie indy = new Movie("002", "Indiana Jones and the Last Crusade", "1989", 127, "Steven Spielberg",
				"In 1938, after his father Professor Henry Jones, Sr. goes missing...", "USA");
		indy.addGenre(Genre.ACTION);
		indy.addGenre(Genre.ADVENTURE);
		indy.addActor(new Actor("Harrison Ford"));
		indy.addActor(new Actor("Sean Connery"));
		indy.addRating(new Rating(Source.IMDB, "8.2/10"));
		indy.addRating(new Rating(Source.ROTTEN, "88%"));
		indy.addRating(new Rating(Source.META, "65/100"));

		Movie thing = new Movie("003", "The Thing", "1982", 109, "John Carpenter",
				"\"A research team in Antarctica is hunted by a shape-shifting alien...", "USA");
		thing.addGenre(Genre.HORROR);
		thing.addGenre(Genre.MISTERY);
		thing.addActor(new Actor("Kurt Russel"));
		thing.addRating(new Rating(Source.IMDB, "8.1/10"));
		thing.addRating(new Rating(Source.ROTTEN, "84%"));
		thing.addRating(new Rating(Source.META, "57/100"));

		return Stream.of(bladeRunner, indy, thing);
	}

}
