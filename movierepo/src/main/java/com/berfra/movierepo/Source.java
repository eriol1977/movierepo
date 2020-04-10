package com.berfra.movierepo;

import java.util.function.ToIntFunction;

public enum Source {
	IMDB("Internet Movie Database", rating -> (int) (Float.valueOf(rating.split("/")[0]) * 10)), // "8.1/10"
	ROTTEN("Rotten Tomatoes", rating -> Integer.parseInt(rating.replace("%",""))), // "81%"
	META("Metacritic", rating -> Integer.parseInt(rating.split("/")[0])); // "81/100"

	private final String name;
	private final ToIntFunction<String> percentageConverter;
	
	Source(final String name, final ToIntFunction<String> percentageConverter) {
		this.name = name;
		this.percentageConverter = percentageConverter;
	}
	
	public static Source fromName(final String name) {
		for(Source source: values()) {
			if(source.getName().equals(name.trim()))
				return source;
		}
		return null;
	}
	
	public int getPercRating(final String rating) {
		return this.percentageConverter.applyAsInt(rating);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
