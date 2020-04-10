package com.berfra.movierepo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.berfra.movierepo.Actor;
import com.berfra.movierepo.FileMovieFactory;
import com.berfra.movierepo.Genre;
import com.berfra.movierepo.IMovieFactory;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.OMDBMovieFactory;
import com.berfra.movierepo.MovieParseException;
import com.berfra.movierepo.Rating;
import com.berfra.movierepo.Source;

class TestMovieFactory {

	private final OMDBMovieFactory omdbFactory = new OMDBMovieFactory();
	private final FileMovieFactory fileFactory = new FileMovieFactory();
	
	@Test
	void testCreateBladeRunnerMovie() throws Exception {
		final Movie bladeRunner = omdbFactory.createMovie(getBladeRunnerJson());
		executeBladeRunnerTests(bladeRunner);
	}

	private void executeBladeRunnerTests(final Movie bladeRunner) {
		assertEquals("tt0083658", bladeRunner.getId());
		assertEquals("Blade Runner", bladeRunner.getTitle());
		assertEquals("1982", bladeRunner.getYear());
		assertEquals(117, bladeRunner.getRuntime());
		assertEquals("Ridley Scott", bladeRunner.getDirector());
		assertEquals("A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.", bladeRunner.getPlot());
		assertEquals("USA, Hong Kong, UK", bladeRunner.getCountry());
		
		List<Genre> genres = bladeRunner.getGenres();
		assertNotNull(genres);
		assertEquals(3, genres.size());
		assertTrue(genres.contains(Genre.ACTION));
		assertTrue(genres.contains(Genre.SCIFI));
		assertTrue(genres.contains(Genre.THRILLER));
		
		List<Actor> actors = bladeRunner.getActors();
		assertNotNull(actors);
		assertEquals(4, actors.size());
		List<String> actorNames = bladeRunner.getActorNames();
		assertTrue(actorNames.contains("Harrison Ford"));
		assertTrue(actorNames.contains("Rutger Hauer"));
		assertTrue(actorNames.contains("Sean Young"));
		assertTrue(actorNames.contains("Edward James Olmos"));
		
		List<Rating> ratings = bladeRunner.getRatings();
		assertNotNull(ratings);
		assertEquals(3, ratings.size());
		final Rating imdb = ratings.get(0);
		assertEquals(Source.IMDB, imdb.getSource());
		assertEquals(81, imdb.getPercRating());
		final Rating rotten = ratings.get(1);
		assertEquals(Source.ROTTEN, rotten.getSource());
		assertEquals(90, rotten.getPercRating());
		final Rating meta = ratings.get(2);
		assertEquals(Source.META, meta.getSource());
		assertEquals(84, meta.getPercRating());
	}
	
	@Test
	void testMovieParseException() throws Exception {
		String json = getBladeRunnerJson();
		final String wrongJson = "[" + json + "]";
		assertThrows(MovieParseException.class, () -> omdbFactory.createMovie(wrongJson));
	}
	
	@Test
	void testCreateBladeRunnerMovieFromFile() throws Exception {
		final Movie bladeRunner = fileFactory.createMovie(getBladeRunnerInFileFormatJson());
		executeBladeRunnerTests(bladeRunner);
	}
	
	private String getBladeRunnerJson() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunner.json")));
	}
	
	private String getBladeRunnerInFileFormatJson() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunnerInFileFormat.json")));
	}
}
