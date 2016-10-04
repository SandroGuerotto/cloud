package view;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
	String text;
	
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	Calendar cal = Calendar.getInstance();
    String time = sdf.format(cal.getTime());
    int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getTimeOfDay() {
		return timeOfDay;
	}
	public void setTimeOfDay(int timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	public String getText() {
		if (getTimeOfDay() >= 4 && getTimeOfDay()  < 12) {
			setText("Guten Morgen, ");
        } else if (getTimeOfDay() >= 12 && getTimeOfDay() < 16) {
        	setText("Guten Tag, ");
        } else if (getTimeOfDay() >= 17 && getTimeOfDay() < 21) {
            setText("Guten Abend, ");
        } else if (getTimeOfDay() >= 21 && getTimeOfDay() < 24  && getTimeOfDay() >= 0  && getTimeOfDay() > 2) {
        	setText("Guten Nacht, ");
        }
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
    
    
}
