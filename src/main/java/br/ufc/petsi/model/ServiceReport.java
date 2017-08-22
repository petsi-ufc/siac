package br.ufc.petsi.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class ServiceReport {
	
	@JsonProperty("dateBegin")
	private Date dateBegin;
	@JsonProperty("dateEnd")
	private Date dateEnd;
	@JsonProperty("service")
	private String service;
	@JsonProperty("professional")
	private String professional;
	@JsonProperty("total")
	private int total;
	@JsonProperty("scheduled")
	private int scheduled;
	@JsonProperty("unscheduled")
	private int unscheduled;
	@JsonProperty("canceled")
	private int canceled;
	@JsonProperty("byMonth")
	private List<DetailByMonth> byMonth;
	@JsonProperty("rescheduled")
	private int rescheduled;
	
	public ServiceReport() {
		super();
	}
	
	public ServiceReport(Date dateBegin, Date dateEnd, String service,
			String professional, int total, int scheduled, int unscheduled,
			int canceled, List<DetailByMonth> byMonth) {
		super();
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.service = service;
		this.professional = professional;
		this.total = total;
		this.scheduled = scheduled;
		this.unscheduled = unscheduled;
		this.canceled = canceled;
		this.byMonth = byMonth;
	}

	@JsonProperty("dateBegin")
	public Date getDateBegin() {
		return dateBegin;
	}
	
	@JsonProperty("dateBegin")
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}
	
	@JsonProperty("dateEnd")
	public Date getDateEnd() {
		return dateEnd;
	}
	
	@JsonProperty("dateEnd")
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	@JsonProperty("service")
	public String getService() {
		return service;
	}
	
	@JsonProperty("service")
	public void setService(String service) {
		this.service = service;
	}
	
	@JsonProperty("professional")
	public String getProfessional() {
		return professional;
	}
	
	@JsonProperty("professional")
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	
	@JsonProperty("total")
	public int getTotal() {
		return total;
	}
	
	@JsonProperty("total")
	public void setTotal(int total) {
		this.total = total;
	}
	
	@JsonProperty("scheduled")
	public int getScheduled() {
		return scheduled;
	}
	
	@JsonProperty("scheduled")
	public void setScheduled(int scheduled) {
		this.scheduled = scheduled;
	}
	
	@JsonProperty("unscheduled")
	public int getUnscheduled() {
		return unscheduled;
	}
	
	@JsonProperty("unscheduled")
	public void setUnscheduled(int unscheduled) {
		this.unscheduled = unscheduled;
	}
	
	@JsonProperty("canceled")
	public int getCanceled() {
		return canceled;
	}
	
	@JsonProperty("canceled")
	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}
	
	@JsonProperty("byMonth")
	public List<DetailByMonth> getByMonth() {
		return byMonth;
	}
	
	@JsonProperty("byMonth")
	public void setByMonth(List<DetailByMonth> byMonth) {
		this.byMonth = byMonth;
	}

	@JsonProperty("rescheduled")
	public int getRescheduled() {
		return rescheduled;
	}

	@JsonProperty("rescheduled")
	public void setRescheduled(int rescheduled) {
		this.rescheduled = rescheduled;
	}
	
	
	
}
