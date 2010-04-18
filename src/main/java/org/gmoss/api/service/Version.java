package org.gmoss.api.service;

public class Version {
	private int major;

	private int minor;

	private int phase;

	private int increment;

	private String version;

	protected void calculateVersion() {
		version = major + "." + minor + "." + phase + "." + increment;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		version = null;
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		version = null;
		this.minor = minor;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		version = null;
		this.phase = phase;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		version = null;
		this.increment = increment;
	}

	@Override
	public String toString() {
		if (version == null) {
			calculateVersion();
		}

		return version;
	}
}
