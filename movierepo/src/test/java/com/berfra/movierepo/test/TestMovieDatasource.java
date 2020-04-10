package com.berfra.movierepo.test;

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
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieSaver;
import com.berfra.movierepo.OMDBMovieDatasource;

class TestMovieDatasource {

	private static final OMDBMovieDatasource ds = new OMDBMovieDatasource();
	private static final FileMovieDatasource fileDs = new FileMovieDatasource(FileMovieSaver.TEST_PATH_BY_ID,
			FileMovieSaver.TEST_PATH_BY_TITLE);

	@BeforeEach
	public void before(){
		deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_ID),false);
		deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_TITLE),false);
	}
	
	@AfterEach
	public void after(){
		deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_ID),false);
		deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_TITLE),false);
	}
	
	@Test
	void testLoadMovieTextById() throws Exception {
		final String movieText = ds.loadMovieTextById("tt0083658");
		assertEquals(getBladeRunnerJson(), movieText);
	}

	@Test
	void testLoadMovieIdsByTitle() throws Exception {
		List<String> ids = ds.loadMovieIdsByTitle("pluto");
		List<String> expectedIds = getPlutoSearchIds();
		assertNotNull(ids);
		assertEquals(expectedIds.size(), ids.size());
		Set<String> intersection = ids.stream().distinct().filter(expectedIds::contains).collect(Collectors.toSet()); // intersection
																														// between
																														// lists
		assertEquals(expectedIds.size(), intersection.size());
	}

	@Test
	void testLoadFileMovieTextById() throws Exception {
		final String expectedText = "HELLO, IT'S A ME, MARIO!";
		final String movieId = "tt123456";
		writeStringToFile(expectedText, FileMovieSaver.TEST_PATH_BY_ID, movieId + ".json");

		assertEquals(expectedText, fileDs.loadMovieTextById(movieId));
	}

	@Test
	void testLoadFileMovieIdsByTitle() throws Exception {
		final String title = "Blade Runner";
		writeStringToFile("AAA", FileMovieSaver.TEST_PATH_BY_TITLE, "Blade Runner###001.json");
		writeStringToFile("BBB", FileMovieSaver.TEST_PATH_BY_TITLE, "Pippo Franco###002.json");
		writeStringToFile("CCC", FileMovieSaver.TEST_PATH_BY_TITLE, "I Love Blade Runner###003.json");
		writeStringToFile("DDD", FileMovieSaver.TEST_PATH_BY_TITLE, "blade runner bel film###004.json");
		writeStringToFile("EEE", FileMovieSaver.TEST_PATH_BY_TITLE, "Blade Qualcosa Runner###005.json");
		writeStringToFile("FFF", FileMovieSaver.TEST_PATH_BY_TITLE,
				"Adoriamo tutti blade runner e il seguito###006.json");

		List<String> ids = fileDs.loadMovieIdsByTitle(title);
		assertNotNull(ids);
		assertEquals(4, ids.size());
		assertTrue(ids.contains("001"));
		assertTrue(ids.contains("003"));
		assertTrue(ids.contains("004"));
		assertTrue(ids.contains("006"));
	}

	private String getBladeRunnerJson() throws IOException {
		return new String(Files.readAllBytes(Paths.get("src/test/resources/bladeRunnerOneLine.json")));
	}

	private List<String> getPlutoSearchIds() throws IOException {
		final String stringIds = new String(Files.readAllBytes(Paths.get("src/test/resources/plutoIds.txt")));
		return Arrays.asList(stringIds.split(","));
	}

	private void writeStringToFile(final String content, final String path, final String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename));
		writer.write(content);
		writer.close();
	}

	private void deleteDirectory(File directoryToBeDeleted, boolean alsoDeleteDir) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file, true);
			}
		}
		if (alsoDeleteDir)
			directoryToBeDeleted.delete();
	}
}
