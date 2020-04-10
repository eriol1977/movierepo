package com.berfra.movierepo;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileMovieSaver implements IMovieSaver {

	public final static String BASE_PATH = "C:\\tmp\\MovieRepo\\";
	public final static String PATH_BY_ID = BASE_PATH + "byId\\";
	public final static String PATH_BY_TITLE = BASE_PATH + "byTitle\\";
	
	public final static String BASE_TEST_PATH = "C:\\tmp\\MovieRepoTest\\";
	public final static String TEST_PATH_BY_ID = BASE_TEST_PATH + "byId\\";
	public final static String TEST_PATH_BY_TITLE = BASE_TEST_PATH + "byTitle\\";

	@Override
	/**
	 * Saves a movie into two different json files, one with filename id.json,
	 * the other title###id.json, for loading purposes.
	 */
	public void saveMovie(final Movie movie) throws MovieSaveException {
		try {

			ObjectMapper mapper = new ObjectMapper();
			// the following configuration saves only the fields, ignoring other getXXX
			// methods
			mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

			mapper.writeValue(new File(PATH_BY_ID + movie.getId() + ".json"), movie);
			// replaces invalid filename chars with "_"
			String validTitle = movie.getTitle().replaceAll("[^a-zA-Z0-9\\.\\-]", "_").trim();
			mapper.writeValue(new File(PATH_BY_TITLE + validTitle + "###" + movie.getId() + ".json"), movie);
		} catch (IOException e) {
			final MovieSaveException exc = new MovieSaveException();
			exc.initCause(e);
			throw exc;
		}
	}

}
