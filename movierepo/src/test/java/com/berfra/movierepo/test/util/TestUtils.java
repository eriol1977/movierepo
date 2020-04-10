package com.berfra.movierepo.test.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.berfra.movierepo.Actor;
import com.berfra.movierepo.Genre;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.Rating;
import com.berfra.movierepo.Source;

public class TestUtils {
	
	public static String getValidMovieTitle(final String title) {
		return title.replaceAll("[^a-zA-Z0-9\\.\\-]", "_").trim();
	}
	
	public static void writeStringToFile(final String content, final String path, final String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename));
		writer.write(content);
		writer.close();
	}

	public static void deleteDirectory(File directoryToBeDeleted, boolean alsoDeleteDir) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file, true);
			}
		}
		if (alsoDeleteDir)
			directoryToBeDeleted.delete();
	}
	
	public static void executeBladeRunnerTests(final Movie bladeRunner) {
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
	
	public static String getBladeRunnerJson() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunnerOneLine.json")));
	}
	
	public static String getBladeRunnerInFileFormatJson() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunnerInFileFormat.json")));
	}

	public static List<String> getPlutoSearchIds() throws IOException {
		final String stringIds = new String(Files.readAllBytes(Paths.get("src/test/resources/plutoIds.txt")));
		return Arrays.asList(stringIds.split(","));
	}
}
