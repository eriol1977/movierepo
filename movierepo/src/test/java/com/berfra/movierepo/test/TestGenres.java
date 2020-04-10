package com.berfra.movierepo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.berfra.movierepo.Genre;

class TestGenres {

	@ParameterizedTest
	@MethodSource("genresProvider")
	void testGetGenreFromDescription(final Genre expected, final String description) throws Exception {
		assertEquals(expected, Genre.fromDescription(description));
	}

	private static Stream<Arguments> genresProvider() {
		return Stream.of(Arguments.of(Genre.ACTION, "Action"), Arguments.of(Genre.ADVENTURE, "Adventure"), Arguments.of(Genre.HORROR, "Horror"),
				Arguments.of(Genre.MISTERY, "Mistery"), Arguments.of(Genre.SCIFI, "Sci-Fi"), Arguments.of(Genre.THRILLER, "Thriller"));
	}
}
