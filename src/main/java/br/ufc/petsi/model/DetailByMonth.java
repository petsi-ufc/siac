package br.ufc.petsi.model;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class DetailByMonth {
	@JsonProperty("numberOfMonth")
	private String numberOfMonth;
	@JsonProperty("totalByMonth")
	private int totalByMonth;
	@JsonProperty("scheduledByMonth")
	private int scheduledByMonth;
	@JsonProperty("unscheduledByMonth")
	private int unscheduledByMonth;
	@JsonProperty("canceledByMonth")
	private int canceledByMonth;
	
	public DetailByMonth() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DetailByMonth(String numberOfMonth, int totalByMonth,
			int scheduledByMonth, int unscheduledByMonth, int canceledByMonth) {

		this.numberOfMonth = numberOfMonth;
		this.totalByMonth = totalByMonth;
		this.scheduledByMonth = scheduledByMonth;
		this.unscheduledByMonth = unscheduledByMonth;
		this.canceledByMonth = canceledByMonth;
	}
	
	@JsonProperty("numberOfMonth")
	public String getNumberOfMonth() {
		return numberOfMonth;
	}
	
	@JsonProperty("numberOfMonth")
	public void setNumberOfMonth(String numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}
	
	@JsonProperty("totalByMonth")
	public int getTotalByMonth() {
		return totalByMonth;
	}
	
	@JsonProperty("totalByMonth")
	public void setTotalByMonth(int totalByMonth) {
		this.totalByMonth = totalByMonth;
	}
	
	@JsonProperty("scheduledByMonth")
	public int getScheduledByMonth() {
		return scheduledByMonth;
	}
	
	@JsonProperty("scheduledByMonth")
	public void setScheduledByMonth(int scheduledByMonth) {
		this.scheduledByMonth = scheduledByMonth;
	}
	
	@JsonProperty("unscheduledByMonth")
	public int getUnscheduledByMonth() {
		return unscheduledByMonth;
	}
	
	@JsonProperty("unscheduledByMonth")
	public void setUnscheduledByMonth(int unscheduledByMonth) {
		this.unscheduledByMonth = unscheduledByMonth;
	}
	
	@JsonProperty("canceledByMonth")
	public int getCanceledByMonth() {
		return canceledByMonth;
	}
	
	@JsonProperty("canceledByMonth")
	public void setCanceledByMonth(int canceledByMonth) {
		this.canceledByMonth = canceledByMonth;
	}

	
	@Override
	public String toString() {
		return "DetailByMonth [numberOfMonth=" + numberOfMonth
				+ ", totalByMonth=" + totalByMonth + ", scheduledByMonth="
				+ scheduledByMonth + ", unscheduledByMonth="
				+ unscheduledByMonth + ", canceledByMonth=" + canceledByMonth
				+ "]";
	}
}
