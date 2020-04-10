package com.berfra.movierepo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.berfra.movierepo.Actor;
import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieFactory;
import com.berfra.movierepo.FileMovieSaver;
import com.berfra.movierepo.Genre;
import com.berfra.movierepo.IMovieLoader;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.MovieLibrary;
import com.berfra.movierepo.MovieLoadException;
import com.berfra.movierepo.MovieLoader;
import com.berfra.movierepo.OMDBMovieDatasource;
import com.berfra.movierepo.OMDBMovieFactory;
import com.berfra.movierepo.Rating;
import com.berfra.movierepo.Source;

class TestMovieLoader {

	private static final IMovieLoader fakeLoader = new MovieLoader(new FakeMovieDatasource(), new OMDBMovieFactory());
	private static final IMovieLoader omdbLoader = new MovieLoader(new OMDBMovieDatasource(), new OMDBMovieFactory());
	private static final IMovieLoader fileLoader = new MovieLoader(
			new FileMovieDatasource(FileMovieSaver.TEST_PATH_BY_ID, FileMovieSaver.TEST_PATH_BY_TITLE),
			new FileMovieFactory());

	@Test
	void testFakeLoadMovieById() throws Exception {
		final Movie bladeRunner = fakeLoader.loadMovieById("tt0083658");
		executeBladeRunnerTests(bladeRunner);
	}

	private void executeBladeRunnerTests(final Movie bladeRunner) {
		assertNotNull(bladeRunner);
		assertEquals("tt0083658", bladeRunner.getId());
		assertEquals("Blade Runner", bladeRunner.getTitle());
		assertEquals("1982", bladeRunner.getYear());
		assertEquals(117, bladeRunner.getRuntime());
		assertEquals("Ridley Scott", bladeRunner.getDirector());
		assertEquals(
				"A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.",
				bladeRunner.getPlot());
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
	void testFakeLoadMoviesByTitle() throws Exception {
		List<Movie> movies = fakeLoader.loadMoviesByTitle("any");

		assertNotNull(movies);
		assertEquals(3, movies.size());
		assertEquals("Blade Runner", movies.get(0).getTitle());
		assertEquals("John Carpenter", movies.get(1).getDirector());
		assertEquals(78, Double.valueOf((movies.get(2).getAverageRating())).intValue());
	}

	@Test
	void testFakeLoadMovieLibrary() throws Exception {
		final MovieLibrary library = fakeLoader.loadMovieLibrary("any", "TEST", "Test Library", "A wonderful test");

		assertNotNull(library);
		assertEquals(3, library.getMovies().size());
		assertEquals(2, library.getMoviesFromYear("1982").size());
		assertEquals(2, library.getMoviesWithActor("Harrison Ford").size());
	}

	@Test
	void testOMDBLoadMovieById() throws Exception {
		final Movie bladeRunner = omdbLoader.loadMovieById("tt0083658");
		executeBladeRunnerTests(bladeRunner);
	}

	@Test
	@Disabled // very slow and connection-consuming
	void testOMDBLoadMoviesByTitle() throws Exception {
		List<Movie> movies = omdbLoader.loadMoviesByTitle("shark");

		assertNotNull(movies);
		assertEquals(319, movies.size());
		assertEquals("Shark Tale", movies.get(0).getTitle());
		assertEquals("Shark Suit: The Musical", movies.get(318).getTitle());
	}

	@Test
	@Disabled // very slow and connection-consuming
	void testOMDBLoadMovieLibrary() throws Exception {
		final MovieLibrary library = omdbLoader.loadMovieLibrary("tank", "TANK", "Tank Library",
				"Everybody loves tanks");

		assertNotNull(library);
		assertEquals(98, library.getMovies().size());
	}

	@Test
	void testFileLoadMovieById() throws Exception {
		final String id = "tt0083658";
		writeStringToFile(getBladeRunnerInFileFormatJson(), FileMovieSaver.TEST_PATH_BY_ID, id + ".json");

		final Movie bladeRunner = fileLoader.loadMovieById(id);
		executeBladeRunnerTests(bladeRunner);
	}

	@Test
	void testFileLoadMoviesByTitle() throws Exception {
		// TODO
	}
	
	@Test
	void testFileLoadMovieLibrary() throws Exception {
		// TODO
	}
	
	@Test
	void testMovieLoadException() throws Exception {
		assertThrows(MovieLoadException.class, () -> omdbLoader.loadMovieById("pippo"));
		assertThrows(MovieLoadException.class, () -> omdbLoader.loadMoviesByTitle("supercalifragilisti"));
		assertThrows(MovieLoadException.class,
				() -> omdbLoader.loadMovieLibrary("supercalifragilisti", "BOH", "Boh", "Wrong!"));
	}

	private String getBladeRunnerInFileFormatJson() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunnerInFileFormat.json")));
	}

	private void writeStringToFile(final String content, final String path, final String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename));
		writer.write(content);
		writer.close();
	}
}
