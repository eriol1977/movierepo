package com.berfra.movierepo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.berfra.movierepo.FileMovieDatasource;
import com.berfra.movierepo.FileMovieSaver;
import com.berfra.movierepo.OMDBMovieDatasource;
import com.berfra.movierepo.test.util.TestUtils;

class TestMovieDatasource {

	private static final OMDBMovieDatasource ds = new OMDBMovieDatasource();
	private static final FileMovieDatasource fileDs = new FileMovieDatasource(FileMovieSaver.TEST_PATH_BY_ID,
			FileMovieSaver.TEST_PATH_BY_TITLE);

	@BeforeEach
	public void before() {
		TestUtils.deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_ID), false);
		TestUtils.deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_TITLE), false);
	}

	@AfterEach
	public void after() {
		TestUtils.deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_ID), false);
		TestUtils.deleteDirectory(new File(FileMovieSaver.TEST_PATH_BY_TITLE), false);
	}

	@Test
	void testLoadMovieTextById() throws Exception {
		final String movieText = ds.loadMovieTextById("tt0083658");
		assertEquals(TestUtils.getBladeRunnerJson(), movieText);
	}

	@Test
	void testLoadMovieIdsByTitle() throws Exception {
		List<String> ids = ds.loadMovieIdsByTitle("pluto");
		List<String> expectedIds = TestUtils.getPlutoSearchIds();
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
		TestUtils.writeStringToFile(expectedText, FileMovieSaver.TEST_PATH_BY_ID, movieId + ".json");

		assertEquals(expectedText, fileDs.loadMovieTextById(movieId));
	}

	@Test
	void testLoadFileMovieIdsByTitle() throws Exception {
		final String title = "Blade Runner";
		TestUtils.writeStringToFile("AAA", FileMovieSaver.TEST_PATH_BY_TITLE, "Blade_Runner###001.json");
		TestUtils.writeStringToFile("BBB", FileMovieSaver.TEST_PATH_BY_TITLE, "Pippo_Franco###002.json");
		TestUtils.writeStringToFile("CCC", FileMovieSaver.TEST_PATH_BY_TITLE, "I_Love_Blade_Runner###003.json");
		TestUtils.writeStringToFile("DDD", FileMovieSaver.TEST_PATH_BY_TITLE, "blade_runner_bel_film###004.json");
		TestUtils.writeStringToFile("EEE", FileMovieSaver.TEST_PATH_BY_TITLE, "Blade_Qualcosa_Runner###005.json");
		TestUtils.writeStringToFile("FFF", FileMovieSaver.TEST_PATH_BY_TITLE,
				"Adoriamo_tutti_blade_runner_e_il_seguito###006.json");

		List<String> ids = fileDs.loadMovieIdsByTitle(title);
		assertNotNull(ids);
		assertEquals(4, ids.size());
		assertTrue(ids.contains("001"));
		assertTrue(ids.contains("003"));
		assertTrue(ids.contains("004"));
		assertTrue(ids.contains("006"));
	}

}
