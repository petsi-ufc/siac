package br.ufc.petsi.event;

import java.text.SimpleDateFormat;

import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;

public class Event {

	private String start;
	private String end;
	private String title;
	private String color;
	private String textColor;
	private Long id;	
	private String hour;
	private String state;

	public Event(Consultation consultation) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm:ss");

		this.hour = simpleHourFormat.format(consultation.getDateInit().getTime());
		this.start = simpleDateFormat.format(consultation.getDateInit());
		this.end = simpleDateFormat.format(consultation.getDateEnd());
		this.title = consultation.getService().getName();
		this.state = consultation.getState().name();
		

		if(this.state == "SC"){
			this.state = "Agendado";
		}
		if(this.state == "FR"){
			this.state = "Disponível";
		}
		if(this.state == "CD"){
			this.state = "Cancelado";
		}
		if(this.state == "RD"){
			this.state = "Realizado";
		}
		if(this.state == "RV"){
			this.state = "Reservado";
		}
		

		if(consultation.getState() == ConsultationState.RD){
			this.color = "grey";
			this.textColor = "white";

		}
		if(consultation.getState() == ConsultationState.SC){
			this.color = "#4682B4";
			this.textColor = "white";
		}
		if(consultation.getState() == ConsultationState.CD){
			this.color = "red";
			this.textColor = "white";
		}
		if(consultation.getState() == ConsultationState.RV){
			this.color = "yellow";
			this.textColor = "black";
		}

		this.id = consultation.getId();
	}

	public Event(){

	}


	public String getStart() {
		return start;
	}

	public void setStart(String start) {

		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}