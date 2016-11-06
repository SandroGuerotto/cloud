package view;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Time {

    private String text;
    private SimpleDateFormat sdf;

   public Time(){
       sdf = new SimpleDateFormat("HH:mm");
   }

    public String getTime() {
        return sdf.format(Calendar.getInstance().getTime());
    }

    private int getTimeOfDay() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public String getText() {
        if (getTimeOfDay() >= 4 && getTimeOfDay() < 12) {
            setText("Guten Morgen, ");
        } else if (getTimeOfDay() >= 12 && getTimeOfDay() <= 16) {
            setText("Guten Tag, ");
        } else if (getTimeOfDay() >= 17 && getTimeOfDay() < 21) {
            setText("Guten Abend, ");

        } else if (getTimeOfDay() >= 21 && getTimeOfDay() < 24 && getTimeOfDay() >= 0 && getTimeOfDay() > 3) {
            setText("Gute Nacht, ");
        } else {
        	setText("Gute Nacht, ");

        }
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
