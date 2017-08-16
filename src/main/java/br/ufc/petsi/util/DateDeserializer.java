package br.ufc.petsi.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date> {
      public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
    	  String date = json.getAsString();

          Calendar c = Calendar.getInstance();
          c.setTimeInMillis(Long.parseLong(date));
          
          int mYear = c.get(Calendar.YEAR);
    	  int mMonth = c.get(Calendar.MONTH);
    	  int mDay = c.get(Calendar.DAY_OF_MONTH);
    	  int mHour = c.get(Calendar.HOUR_OF_DAY);
    	  int mMinite = c.get(Calendar.MINUTE);
    	  
    	  Date d = new Date((mMonth+1)+"/"+mDay+"/"+mYear+" "+(mHour+3)+":"+mMinite);
    	  
    	  try {
    		  return d;
    	  } catch (Exception e) {
    		  System.err.println("Failed to parse Date due to:"+ e);
    		  return null;
    	  }
      }
}

