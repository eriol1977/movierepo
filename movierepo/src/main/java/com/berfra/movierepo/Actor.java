package com.berfra.movierepo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Actor {

	private final String name;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES) // these annotations are necessary to deserialize an immutable object with Jackson
	public Actor(@JsonProperty("name") String name) {
		super();
		this.name = name.trim();
	}

	public boolean couldBe(final String namePart) {
		return this.name.toLowerCase().contains(namePart.toLowerCase());
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Actor [name=" + name + "]";
	}
	
}
