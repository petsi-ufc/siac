package br.ufc.petsi.event;

import java.text.SimpleDateFormat;

import br.ufc.petsi.model.Consultation;

public class EventsDay {
	
	private String hour;
	private String status;
	private Long id;
	public EventsDay() {
		// TODO Auto-generated constructor stub
	}
	
	
	public EventsDay(Consultation c) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		this.hour = simpleDateFormat.format(c.getDateInit().getTime());
		this.status = c.getState().name();
		if(this.status == "SC"){
			this.status = "Agendado";
		}
		if(this.status == "FR"){
			this.status = "Disponível";
		}
		if(this.status == "CD"){
			this.status = "Cancelado";
		}
		if(this.status == "RD"){
			this.status = "Realizado";
		}
		if(this.status == "RV"){
			this.status = "Reservado";
		}
		this.id = c.getId();
	}


	public String getHour() {
		return hour;
	}


	public void setHour(String hour) {
		this.hour = hour;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "EventsDay [hour=" + hour + ", status=" + status + "]";
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}