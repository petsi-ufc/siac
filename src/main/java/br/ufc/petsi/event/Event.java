package br.ufc.petsi.event;

import java.text.SimpleDateFormat;

import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Reserve;

public class Event {

	
	private String start;
	private String end;
	private String title;
	private String color;
	private String textColor;
	private Long id;	
	private String hour;
	private String state;
	private boolean isRatingNull;
	private long idReserve;

	public Event(Patient patient,Consultation consultation) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm:ss");

		this.hour = simpleHourFormat.format(consultation.getDateInit().getTime());
		this.start = simpleDateFormat.format(consultation.getDateInit());
		this.end = simpleDateFormat.format(consultation.getDateEnd());
		this.title = consultation.getService().getName();
		this.state = consultation.getState().name();
		if(consultation.getRating() != null) this.isRatingNull = false;
		else this.isRatingNull = true;

		boolean isReserved = false;
		
		if(this.state == ConsultationState.SC.name()){
			if(!patient.getCpf().equals(consultation.getPatient().getCpf())){
				for(Reserve reserve:consultation.getReserves()){
					if(patient.getCpf().equals(reserve.getPatient().getCpf()) && reserve.isActive()){
						isReserved = true;
						this.idReserve = reserve.getId();
					}
				}
				if(isReserved){
					this.color = "#D9D919";
					this.textColor = "white";
					this.state = "Reservado";
				}else{
					this.color = "#FF7F00";
					this.textColor = "white";
					this.state = "Ocupado";
				}
				
			}else{
				this.color = "#4682B4";
				this.textColor = "white";
				this.state = "Agendado";
			}
			
		}else if(this.state == ConsultationState.FR.name()){
			this.color = "#32CD32";
			this.textColor = "white";
			this.state = "Disponivel";
		}else if(this.state == ConsultationState.CD.name()){
			this.color = "#FF0000";
			this.textColor = "white";
			this.state = "Cancelado";
		}else if(this.state == ConsultationState.RD.name()){
			this.color = "grey";
			this.textColor = "white";
			this.state = "Realizado";
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

	public boolean isRatingNull() {
		return isRatingNull;
	}

	public void setRatingNull(boolean isRatingNull) {
		this.isRatingNull = isRatingNull;
	}

	public long getIdReserve() {
		return idReserve;
	}

	public void setIdReserve(long idReserve) {
		this.idReserve = idReserve;
	}
	
	
	

}