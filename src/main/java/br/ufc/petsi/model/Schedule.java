package br.ufc.petsi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "schedule" )
public class Schedule {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dateInit;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dateEnd;
	
	@ManyToOne
	@JoinColumn( name = "id_agenda" )
	private Agenda agenda;

	@NotNull
	private boolean available;
	
	public Schedule() {

	}
	
	public Schedule(Long id, Date dateInit, Date dateEnd) {
		super();
		this.id = id;
		this.dateInit = dateInit;
		this.dateEnd = dateEnd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateInit() {
		return dateInit;
	}

	public void setDateInit(Date dateInit) {
		this.dateInit = dateInit;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
}
