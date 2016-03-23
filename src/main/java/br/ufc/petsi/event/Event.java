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
	
		public Event(Consultation consultation) {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
	
			this.start = simpleDateFormat.format(consultation.getDateInit());
			System.out.println(simpleDateFormat.format(consultation.getDateInit()));
			this.end = simpleDateFormat.format(consultation.getDateEnd());
			this.title = consultation.getService().getName();
			System.out.println(consultation.getService().getName());
	
			if(consultation.getState() == ConsultationState.RD){
				this.color = "blue";
				this.textColor = "white";
	
			}
			if(consultation.getState() == ConsultationState.SC){
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

	






}