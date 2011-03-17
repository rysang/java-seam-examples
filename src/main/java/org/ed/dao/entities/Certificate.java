package org.ed.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Certificate extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "c_version", nullable = false)
	private Integer cVersion;

	@Column(name = "c_serial", nullable = false)
	private Integer cSerial;

	@Column(name = "c_issuer", length = 255, nullable = false)
	private String cIssuer;

	@Column(name = "c_subj_key", length = 2048, nullable = false)
	private String cSubjKey;

	@Lob
	@Column(name = "c_binary_content", nullable = false)
	private byte[] cBinaryContent;

	public Certificate() {

	}

	public void setcVersion(Integer cVersion) {
		this.cVersion = cVersion;
	}

	public Integer getcVersion() {
		return cVersion;
	}

	public void setcSerial(Integer cSerial) {
		this.cSerial = cSerial;
	}

	public Integer getcSerial() {
		return cSerial;
	}

	public void setcIssuer(String cIssuer) {
		this.cIssuer = cIssuer;
	}

	public String getcIssuer() {
		return cIssuer;
	}

	public void setcSubjKey(String cSubjKey) {
		this.cSubjKey = cSubjKey;
	}

	public String getcSubjKey() {
		return cSubjKey;
	}

	public void setcBinaryContent(byte[] cBinaryContent) {
		this.cBinaryContent = cBinaryContent;
	}

	public byte[] getcBinaryContent() {
		return cBinaryContent;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder('[');
		sb.append(getUid()).append(" ,");
		sb.append(getcSerial()).append(" ,");
		sb.append(getcIssuer()).append(" ,");
		sb.append(getcSubjKey()).append(" ,");
		sb.append(
				getcBinaryContent() != null ? "hasBinaryContent"
						: "hasNoBinaryContent").append(" ]");

		return sb.toString();
	}

}
