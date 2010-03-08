package org.test;

public class KnightOfTheRoundTable implements Knight {
	private String name;
	private Quest quest;

	public KnightOfTheRoundTable(String name) {
		this.name = name;
		quest = new HolyGrailQuest();
	}

	public Object embarkOnQuest() throws QuestFailedException {
		return quest.embark();
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
