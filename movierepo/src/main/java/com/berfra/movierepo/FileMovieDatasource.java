package com.berfra.movierepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileMovieDatasource implements IMovieDatasource {

	private final String pathById;
	private final String pathByTitle;
	
	public FileMovieDatasource() {
		super();
		this.pathById = FileMovieSaver.PATH_BY_ID;
		this.pathByTitle = FileMovieSaver.PATH_BY_TITLE;
	}

	public FileMovieDatasource(String pathById, String pathByTitle) {
		super();
		this.pathById = pathById;
		this.pathByTitle = pathByTitle;
	}

	@Override
	public List<String> loadMovieIdsByTitle(String title) throws MovieLoadException {

		List<String> ids = new ArrayList<String>();
		String validTitle = title.replaceAll("[^a-zA-Z0-9\\.\\-]", "_").trim();
		
		// makes a stream of all the files in the informed path
		try (Stream<Path> walk = Files.walk(Paths.get(pathByTitle))) {

			ids = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()) // maps every file to its filename
					.filter(f -> f.toLowerCase().contains(validTitle.toLowerCase())) // considers only the filenames containing the title
					.map(name -> name.split("###")[1].replace(".json", "")) // maps every filename to the id part (i.e.: "Blade Runner###tt0083658.json" --> "tt0083658")
					.collect(Collectors.toList()); // collects the result into a List<String>

		} catch (Exception e) {
			MovieLoadException exc = new MovieLoadException();
			exc.initCause(e);
			throw exc;
		}

		return ids;
	}

	@Override
	public String loadMovieTextById(String id) throws MovieLoadException {
		try {
			return new String(Files.readAllBytes(Paths.get(pathById + id + ".json")));
		} catch (Exception e) {
			MovieLoadException exc = new MovieLoadException();
			exc.initCause(e);
			throw exc;
		}
	}

	@Override
	public List<String> loadAllMovieIds() throws MovieLoadException {
		List<String> ids = new ArrayList<String>();
		
		try (Stream<Path> walk = Files.walk(Paths.get(pathById))) {

			ids = walk.filter(Files::isRegularFile)
					.map(x -> x.getFileName().toString().replace(".json", "")) // maps every file to its filename without the json extension
					.collect(Collectors.toList()); // collects the result into a List<String>

		} catch (Exception e) {
			MovieLoadException exc = new MovieLoadException();
			exc.initCause(e);
			throw exc;
		}

		return ids;
	}

}
