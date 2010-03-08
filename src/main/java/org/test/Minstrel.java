package org.test;

import org.apache.log4j.Logger;

public class Minstrel {
	private static final Logger SONG = Logger.getLogger(Minstrel.class);

	public void singBefore(Knight knight) {
		SONG.info("Fa la la; Sir " + knight.getName() + " is so brave!");
	}

	public void singAfter(Knight knight) {
		SONG.info("Tee-hee-he; Sir " + knight.getName()
				+ " did embark on a quest!");
	}
}