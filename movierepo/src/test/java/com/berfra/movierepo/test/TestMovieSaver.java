package com.berfra.movierepo.test;

import org.junit.jupiter.api.Test;

import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieFactory;
import com.berfra.movierepo.FileMovieSaver;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.MovieLoader;
import com.berfra.movierepo.OMDBMovieFactory;
import com.berfra.movierepo.test.util.TestUtils;

class TestMovieSaver {

	@Test
	void testSaveMovieToFile() throws Exception {
		final Movie bladeRunner = new OMDBMovieFactory().createMovie(TestUtils.getBladeRunnerJson());
		final FileMovieSaver saver = new FileMovieSaver();
		saver.saveMovie(bladeRunner);

		final MovieLoader loader = new MovieLoader(new FileMovieDatasource(), new FileMovieFactory());
		final Movie loaded = loader.loadMovieById(bladeRunner.getId());
		TestUtils.executeBladeRunnerTests(loaded);
	}

}
