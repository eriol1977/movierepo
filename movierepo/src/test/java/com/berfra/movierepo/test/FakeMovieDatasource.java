package com.berfra.movierepo.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.berfra.movierepo.IMovieDatasource;
import com.berfra.movierepo.MovieLoadException;

public class FakeMovieDatasource implements IMovieDatasource {

	@Override
	public List<String> loadMovieIdsByTitle(String title) {
		return Arrays.asList("tt0083658","tt0084787","tt0097576");
	}

	@Override
	public String loadMovieTextById(String id) {
		try {
			if (id.equals("tt0083658")) {
				return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunner.json")));
			}else if (id.equals("tt0084787")) {
				return new String(Files.readAllBytes(Paths.get("src/test/resources/thing.json")));
			}else if (id.equals("tt0097576")) {
				return new String(Files.readAllBytes(Paths.get("src/test/resources/indiana.json")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	@Override
	public List<String> loadAllMovieIds() throws MovieLoadException {
		return Arrays.asList("tt0083658","tt0084787","tt0097576");
	}

}
