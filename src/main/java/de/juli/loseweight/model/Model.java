package de.juli.loseweight.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Abstrakte Elternklasse für alle de.nrw.lanuv.idvs.model.persistence.model
 * Modell-Objekte die die ID, Version, das Erstell- und ein Modifications-Datum
 * erhält.
 * 
 * @author ulrichkloodt
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonIgnoreProperties(value = { "serialVersionUID", "id", "version", "createDate", "updateDate" })
public abstract class Model<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Long version;
	private Timestamp createDate;
	private Timestamp updateDate;

	/**
	 * Vor dem Speichern in der Datenbank Uhrzeit und Datum der Speicher-Aktion
	 * erzeugen und festhalten.
	 */
	@PrePersist
	@PreUpdate
	public void prePersist() {
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		if (createDate == null) {
			createDate = timestamp;
		}
		updateDate = timestamp;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	@Override
	@Transient
	public String toString() {
		return "Model [id=" + id + ", version=" + version + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}
}
