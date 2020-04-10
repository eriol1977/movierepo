package com.berfra.movierepo;

public enum Genre {
	ACTION("Action"),
	SCIFI("Sci-Fi"),
	THRILLER("Thriller"),
	ADVENTURE("Adventure"),
	HORROR("Horror"),
	MISTERY("Mistery"),
	ANIMATION("Animation"),
	SHORT("Short"),
	TALK("Talk-Show"),
	DOC("Documentary"),
	DRAMA("Drama"),
	COMEDY("Comedy"),
	WAR("War"),
	CRIME("Crime"),
	ROMANCE("Romance"),
	MUSIC("Music"),
	BIO("Biography"),
	SPORT("Sport"),
	HISTORY("History"),
	NEWS("News"),
	FANTASY("Fantasy"),
	FAMILY("Family"),
	UNDEFINED("Undefined");

	private final String description;
	
	Genre(String description) {
		this.description = description;
	}
	
	public static Genre fromDescription(final String description) {
		for(Genre genre: values()) {
			if(genre.getDescription().toLowerCase().equals(description.toLowerCase().trim()))
				return genre;
		}
		return UNDEFINED;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return this.description;
	}
}
