package com.berfra.movierepo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {

	private final Source source;
	private final String rating;
	
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES) // these annotations are necessary to deserialize an immutable object with Jackson
	public Rating(@JsonProperty("source") Source source, @JsonProperty("rating") String rating) {
		super();
		this.source = source;
		this.rating = rating;
	}

	public Source getSource() {
		return source;
	}

	public String getRating() {
		return rating;
	}

	public int getPercRating() {
		return this.source.getPercRating(this.rating);
	}

	@Override
	public String toString() {
		return "Rating [source=" + source + ", rating=" + rating + "]";
	}
	
}
