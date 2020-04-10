package com.berfra.movierepo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieFactory;
import com.berfra.movierepo.FileMovieSaver;
import com.berfra.movierepo.IMovieLoader;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.MovieLibrary;
import com.berfra.movierepo.MovieLoadException;
import com.berfra.movierepo.MovieLoader;
import com.berfra.movierepo.OMDBMovieDatasource;
import com.berfra.movierepo.OMDBMovieFactory;
import com.berfra.movierepo.test.util.TestUtils;

class TestMovieLoader {

	private static final IMovieLoader fakeLoader = new MovieLoader(new FakeMovieDatasource(), new OMDBMovieFactory());
	private static final IMovieLoader omdbLoader = new MovieLoader(new OMDBMovieDatasource(), new OMDBMovieFactory());
	private static final IMovieLoader fileLoader = new MovieLoader(
			new FileMovieDatasource(FileMovieSaver.TEST_PATH_BY_ID, FileMovieSaver.TEST_PATH_BY_TITLE),
			new FileMovieFactory());

	@Test
	void testFakeLoadMovieById() throws Exception {
		final Movie bladeRunner = fakeLoader.loadMovieById("tt0083658");
		TestUtils.executeBladeRunnerTests(bladeRunner);
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
		TestUtils.executeBladeRunnerTests(bladeRunner);
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
		TestUtils.writeStringToFile(TestUtils.getBladeRunnerInFileFormatJson(), FileMovieSaver.TEST_PATH_BY_ID,
				id + ".json");

		final Movie bladeRunner = fileLoader.loadMovieById(id);
		TestUtils.executeBladeRunnerTests(bladeRunner);
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

}
