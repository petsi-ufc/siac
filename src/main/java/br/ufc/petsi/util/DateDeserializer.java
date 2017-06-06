package br.ufc.petsi.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		
    	  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		
    	  try {
    		  return formatter.parse(date);
    	  } catch (ParseException e) {
    		  System.err.println("Failed to parse Date due to:"+ e);
    		  return null;
    	  }
      }
}

