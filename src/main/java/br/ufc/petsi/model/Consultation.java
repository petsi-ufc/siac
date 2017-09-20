package br.ufc.petsi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.enums.TypeConsultation;

//Adicionar id ou cpf do profissional e o cpf ou id do pacientes

@Entity
@Table( name = "consultation" )
//, indexes={@Index(name="idx_consultation_professional", columnList = "professional")}
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Consultation implements Serializable{

	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;
	
	@OneToOne(targetEntity = SocialService.class, 
			  cascade = { CascadeType.MERGE }, 
			  fetch = FetchType.EAGER )
	@JoinColumn( name = "id_service" )
	@JsonProperty("socialService")
	private SocialService socialService;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonProperty("professional")
	@JsonBackReference(value="professional1")
	private Professional professional;
	
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="id_patient")
	@Fetch(FetchMode.JOIN)
	@JsonProperty("patient")
	@JsonBackReference(value="patient1")
	private Patient patient;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_group")
	@Fetch(FetchMode.JOIN)
	@JsonProperty("group")
	@JsonBackReference(value="group1")
	private Group group;
	
	@Column(name="date_init")
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonProperty("dateInit")
	private Date dateInit;
	
	@Column(name="date_end")
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonProperty("dateEnd")
	private Date dateEnd;
	
	//Comentário após a realização da consulta
	@Column(name="comment")
	@JsonProperty("comment")
	private String comment;
	
	//Motivo pelo qual o paciente solicitou a consulta
	@Column(name="reason")
	@JsonProperty("reason")
	private String reason;
	
	//Motivo pelo qual a consulta foi cancelada / reagendada
	@Column(name="reasonCancel")
	@JsonProperty("reasonCancel")
	private String reasonCancel;
	
	@Enumerated( EnumType.STRING )
	@JsonProperty("state")
	private ConsultationState state;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="consultation", cascade=CascadeType.MERGE)
	@JsonProperty("ratings")
	@JsonBackReference("ratings")
	private List<Rating> ratings;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="consultation")
	@JsonProperty("reserves")
	@JsonBackReference
	private List<Reserve> reserves;
	
	@Enumerated( EnumType.STRING )
	@Column(name="type_consultation")
	@JsonProperty("typeConsultation")
	private TypeConsultation typeConsultation;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="consultation")
	@JsonProperty("frequencyList")
	@JsonBackReference(value="frequencyList1")
	private List<Frequency> frequencyList;
	
	public Consultation(Long id, SocialService socialService, Professional profesisonal,
			ConsultationState state, Date dateInit, Date dateEnd) {
		this.id = id;
		this.socialService = socialService;
		this.state = state;
		this.professional = profesisonal;
		this.dateEnd = dateEnd;
		this.dateInit = dateInit;
	}

	public Consultation() {}
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("professional")
	public Professional getProfessional() {
		return professional;
	}

	@JsonProperty("dateInit")
	public Date getDateInit() {
		return dateInit;
	}

	@JsonProperty("dateInit")
	public void setDateInit(Date dateInit) {
		this.dateInit = dateInit;
	}

	@JsonProperty("dateEnd")
	public Date getDateEnd() {
		return dateEnd;
	}

	@JsonProperty("dateEnd")
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@JsonProperty("professional")
	public void setProfessional(Professional professional) {
		this.professional = professional;
	}

	@JsonProperty("socialService")
	public SocialService getService() {
		return socialService;
	}

	@JsonProperty("socialService")
	public void setService(SocialService service) {
		this.socialService = service;
	}

	@JsonProperty("state")
	public ConsultationState getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(ConsultationState state) {
		this.state = state;
	}

	@JsonProperty("ratings")
	public List<Rating> getRatings() {
		return ratings;
	}

	@JsonProperty("ratings")
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	@JsonProperty("reserves")
	public List<Reserve> getReserves() {
		return reserves;
	}

	@JsonProperty("reserves")
	public void setReserves(List<Reserve> reserves) {
		this.reserves = reserves;
	}
	
	@JsonProperty("typeConsultation")
	public TypeConsultation getTypeConsultation() {
		return typeConsultation;
	}

	@JsonProperty("typeConsultation")
	public void setTypeConsultation(TypeConsultation typeConsultation) {
		this.typeConsultation = typeConsultation;
	}

	@JsonProperty("comment")
	public String getComment() {
		return comment;
	}

	@JsonProperty("comment")
	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonProperty("reason")
	public String getReason() {
		return reason;
	}

	@JsonProperty("reason")
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@JsonProperty("reasonCancel")
	public String getReasonCancel() {
		return reasonCancel;
	}

	@JsonProperty("reasonCancel")
	public void setReasonCancel(String reasonCancel) {
		this.reasonCancel = reasonCancel;
	}
	
	@JsonProperty("patient")
	public Patient getPatient() {
		return patient;
	}

	@JsonProperty("patient")
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@JsonProperty("group")
	public Group getGroup() {
		return group;
	}

	@JsonProperty("group")
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@JsonProperty("frequencyList")
	public List<Frequency> getFrequencyList() {
		return frequencyList;
	}

	@JsonProperty("frequencyList")
	public void setFrequencyList(List<Frequency> frequencyList) {
		this.frequencyList = frequencyList;
	}

	@Override
	public String toString() {
		return "Consultation: [ id:"+id+"; socialService: "+socialService.getName()+"; dataInit: "+dateInit.toString()+"; dataEnd: "+dateEnd.toString()+"]";
	}

}
