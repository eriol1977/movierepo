package com.berfra.movierepo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.berfra.movierepo.Source;

class TestSources {

	@ParameterizedTest
	@MethodSource("sourcesProvider")
	void testGetSourceFromName(final Source expected, final String name) throws Exception {
		assertEquals(expected, Source.fromName(name));
	}

	private static Stream<Arguments> sourcesProvider() {
		return Stream.of(Arguments.of(Source.IMDB, "Internet Movie Database"),
				Arguments.of(Source.ROTTEN, "Rotten Tomatoes"), Arguments.of(Source.META, "Metacritic"));
	}
}
