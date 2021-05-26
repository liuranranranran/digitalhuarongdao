package digitalhuarongdao;

import java.util.Date;

public class Records {
	private String timeCost;
	private String date;
	private String order;
	private String step;
	
	public Records(String Cost,String now,String chorder,String step) {
		timeCost = Cost;
		date = now;
		order = chorder;
		this.step = step;
	}
	public String getCost() {
		return timeCost;
	}
	public String getDate() {
		return date;
	}
	public String getOrder() {
		return order;
	}
	public String getStep() {
		return step;
	}
	public String toString() {
		return this.getCost() + "_" + this.getDate() + "_" +this.getOrder() + "_" + this.getStep();
	}
}
