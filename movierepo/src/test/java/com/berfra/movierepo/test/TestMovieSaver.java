package com.berfra.movierepo.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieSaver;
import com.berfra.movierepo.Movie;
import com.berfra.movierepo.OMDBMovieFactory;

class TestMovieSaver {

	@Test
	void testSaveMovieToFile() throws Exception {
		final Movie bladeRunner = new OMDBMovieFactory().createMovie(getBladeRunnerJson());
		final FileMovieSaver saver = new FileMovieSaver();
		saver.saveMovie(bladeRunner);
		
		final FileMovieDatasource ds = new FileMovieDatasource();
		System.out.println(ds.loadMovieTextById(bladeRunner.getId()));
		System.out.println(ds.loadMovieIdsByTitle(bladeRunner.getTitle()));
	}

	private String getBladeRunnerJson() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunner.json")));
	}
}
