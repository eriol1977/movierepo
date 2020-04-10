package com.berfra.movierepo.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.berfra.movierepo.FileMovieFactory;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.MovieParseException;
import com.berfra.movierepo.OMDBMovieFactory;
import com.berfra.movierepo.test.util.TestUtils;

class TestMovieFactory {

	private final OMDBMovieFactory omdbFactory = new OMDBMovieFactory();
	private final FileMovieFactory fileFactory = new FileMovieFactory();

	@Test
	void testCreateBladeRunnerMovie() throws Exception {
		final Movie bladeRunner = omdbFactory.createMovie(TestUtils.getBladeRunnerJson());
		TestUtils.executeBladeRunnerTests(bladeRunner);
	}

	@Test
	void testMovieParseException() throws Exception {
		String json = TestUtils.getBladeRunnerJson();
		final String wrongJson = "[" + json + "]";
		assertThrows(MovieParseException.class, () -> omdbFactory.createMovie(wrongJson));
	}

	@Test
	void testCreateBladeRunnerMovieFromFile() throws Exception {
		final Movie bladeRunner = fileFactory.createMovie(TestUtils.getBladeRunnerInFileFormatJson());
		TestUtils.executeBladeRunnerTests(bladeRunner);
	}

}
