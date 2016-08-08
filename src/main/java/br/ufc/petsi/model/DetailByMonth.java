package br.ufc.petsi.model;

public class DetailByMonth {
	private String numberOfMonth;
	private int totalByMonth;
	private int scheduledByMonth;
	private int unscheduledByMonth;
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

	public String getNumberOfMonth() {
		return numberOfMonth;
	}
	public void setNumberOfMonth(String numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}
	public int getTotalByMonth() {
		return totalByMonth;
	}
	public void setTotalByMonth(int totalByMonth) {
		this.totalByMonth = totalByMonth;
	}
	public int getScheduledByMonth() {
		return scheduledByMonth;
	}
	public void setScheduledByMonth(int scheduledByMonth) {
		this.scheduledByMonth = scheduledByMonth;
	}
	public int getUnscheduledByMonth() {
		return unscheduledByMonth;
	}
	public void setUnscheduledByMonth(int unscheduledByMonth) {
		this.unscheduledByMonth = unscheduledByMonth;
	}
	public int getCanceledByMonth() {
		return canceledByMonth;
	}
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
