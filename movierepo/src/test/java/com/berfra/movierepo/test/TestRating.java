package com.berfra.movierepo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.internal.util.reflection.FieldSetter;

import com.berfra.movierepo.Movie;
import com.berfra.movierepo.Rating;
import com.berfra.movierepo.Source;

class TestRating {

	@ParameterizedTest(name = "\"{1}\" should return {0}")
	@MethodSource("imdbRatingProvider")
	void testIMDBRating(final int expectedValue, final String stringValue) {
		final Rating rating = new Rating(Source.IMDB, stringValue);
		assertEquals(expectedValue, rating.getPercRating());
	}

	@ParameterizedTest(name = "\"{1}\" should return {0}")
	@MethodSource("rottenRatingProvider")
	void testRottenRating(final int expectedValue, final String stringValue) {
		final Rating rating = new Rating(Source.ROTTEN, stringValue);
		assertEquals(expectedValue, rating.getPercRating());
	}

	@ParameterizedTest(name = "\"{1}\" should return {0}")
	@MethodSource("metaRatingProvider")
	void testMetaRating(final int expectedValue, final String stringValue) {
		final Rating rating = new Rating(Source.META, stringValue);
		assertEquals(expectedValue, rating.getPercRating());
	}

	@ParameterizedTest(name = "The average rating should be {0}")
	@MethodSource("avgRatingProvider")
	void testAverageRating(final double expected, final List<Rating> ratings) throws NoSuchFieldException, SecurityException {
		Movie movie = mock(Movie.class); // mocks Movie to avoid building an object with lots of parameters
		doCallRealMethod().when(movie).addRating(any(Rating.class)); // the real addRating method should be called on the mocked object
		when(movie.getAverageRating()).thenCallRealMethod(); // the real getAverageRating method should be called on the mocked object
		FieldSetter.setField(movie, Movie.class.getDeclaredField("ratings"), new ArrayList<Rating>()); // sets the private final field "ratings", which would normally set by the Movie constructor
		ratings.stream().forEach(movie::addRating);
		assertEquals(expected, movie.getAverageRating());
	}

	// ---------------------------------------------------------------------------------------------------------

	private static Stream<Arguments> imdbRatingProvider() {
		return Stream.of(Arguments.of(81, "8.1/10"), Arguments.of(45, "4.5/10"), Arguments.of(50, "5/10"),
				Arguments.of(60, "6.0/10"), Arguments.of(0, "0/10"), Arguments.of(100, "10/10"));
	}

	private static Stream<Arguments> rottenRatingProvider() {
		return Stream.of(Arguments.of(81, "81%"), Arguments.of(45, "45%"), Arguments.of(50, "50%"),
				Arguments.of(60, "60%"), Arguments.of(0, "0%"), Arguments.of(100, "100%"));
	}

	private static Stream<Arguments> metaRatingProvider() {
		return Stream.of(Arguments.of(81, "81/100"), Arguments.of(45, "45/100"), Arguments.of(50, "50/100"),
				Arguments.of(60, "60/100"), Arguments.of(0, "0/100"), Arguments.of(100, "100/100"));
	}

	private static Stream<Arguments> avgRatingProvider() {
		return Stream.of(
				Arguments.of(80,
						Stream.of(new Rating(Source.IMDB, "8.0/10"), new Rating(Source.ROTTEN, "70%"),
								new Rating(Source.META, "90/100")).collect(Collectors.toList())),
				Arguments.of(100,
						Stream.of(new Rating(Source.IMDB, "10.0/10"), new Rating(Source.ROTTEN, "100%"),
								new Rating(Source.META, "100/100")).collect(Collectors.toList())),
				Arguments.of(50, Stream.of(new Rating(Source.IMDB, "0/10"), new Rating(Source.ROTTEN, "100%"),
						new Rating(Source.META, "50/100")).collect(Collectors.toList())),
				Arguments.of(50, Stream.of(new Rating(Source.IMDB, "0/10"), new Rating(Source.ROTTEN, "100%")).collect(Collectors.toList())),
				Arguments.of(50, Stream.of(new Rating(Source.IMDB, "5/10")).collect(Collectors.toList())),
				Arguments.of(0, new ArrayList<Rating>())
				);
			
	}
}
