package br.ufc.petsi.model;

import java.util.Date;
import java.util.List;

public class ServiceReport {
	
	private Date dateBegin;
	private Date dateEnd;
	private String service;
	private String professional;
	private int total;
	private int scheduled;
	private int unscheduled;
	private int canceled;
	private List<DetailByMonth> byMonth;

	
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

	public Date getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getScheduled() {
		return scheduled;
	}
	public void setScheduled(int scheduled) {
		this.scheduled = scheduled;
	}
	public int getUnscheduled() {
		return unscheduled;
	}
	public void setUnscheduled(int unscheduled) {
		this.unscheduled = unscheduled;
	}
	public int getCanceled() {
		return canceled;
	}
	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}
	public List<DetailByMonth> getByMonth() {
		return byMonth;
	}
	public void setByMonth(List<DetailByMonth> byMonth) {
		this.byMonth = byMonth;
	}
	
}
