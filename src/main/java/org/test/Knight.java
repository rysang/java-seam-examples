package org.test;

public interface Knight {
	Object embarkOnQuest() throws QuestFailedException;

	String getName();

	void setName(String name);
}