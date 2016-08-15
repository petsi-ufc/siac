package br.ufc.petsi.model;

import java.util.Date;
import java.util.List;

public class GeneralReport {
	
	private Date dateBegin;
	private Date dateEnd;
	private List<DetailByMonth> byMonth;
	
	public GeneralReport() {
		super();
	}

	public GeneralReport(Date dateBegin, Date dateEnd, List<DetailByMonth> byMonth) {
		super();
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
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

	public List<DetailByMonth> getByMonth() {
		return byMonth;
	}

	public void setByMonth(List<DetailByMonth> byMonth) {
		this.byMonth = byMonth;
	}

	@Override
	public String toString() {
		return "GeneralReport [dateBegin=" + dateBegin + ", dateEnd=" + dateEnd
				+ ", byMonth=" + byMonth.toString() + "]";
	}
	
	
	
}
