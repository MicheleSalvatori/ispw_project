package logic.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.utilities.AppProperties;

public class CheckWeatherController {
	
	private double kelvinToCelsius(double kelvin) {
		BigDecimal bd = BigDecimal.valueOf(kelvin-273.15).setScale(1, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	private String weatherImage(int hour, String weather) {

		String file;
		
		if (hour < 6 || hour > 19) {
			
			switch (weather) {
			
			case "Clear":
				file = "Moon.png";
				break;
				
			case "Clouds":
				file = "CloudMoon.png";
				break;
				
			case "Rain":
				file = "Rain.png";
				break;
				
			case "Thunderstorm":
				file = "Thunderstorm.png";
				break;
				
			default:
				file = "Cloud.png";
				break;
			
			}
		}
		
		else {
			
			switch (weather) {
			
			case "Clear":
				file = "Sun.png";
				break;
				
			case "Clouds":
				file = "CloudSun.png";
				break;
				
			case "Rain":
				file = "Rain.png";
				break;
				
			case "Thunderstorm":
				file = "Thunderstorm.png";
				break;
				
			default:
				file = "Cloud.png";
				break;
			
			}
		}
		
		return file;
	}
	
	private String readAll(Reader rd) {
		try {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
	    
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Read error");
			return null;
		}
	}

	private JSONObject readJsonFromUrl(String url) {
		try {
			InputStream is = new URL(url).openStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		    String jsonText = readAll(rd);
		    JSONObject json = new JSONObject(jsonText);
		    is.close();
		    return json;
	    
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "JSON loading error");
			throw new NullPointerException();
		}
	}
	
	private JSONArray getInfo() {
		
		String api = AppProperties.getInstance().getProperty("weatherapi");
		String lat = AppProperties.getInstance().getProperty("weatherlat");
		String lon = AppProperties.getInstance().getProperty("weatherlon");

		String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon +"&exclude=daily,minutely,current,alerts&appid=" + api;

		JSONObject json;
		JSONArray array = null;
		
		json = readJsonFromUrl(urlString);
		try {
			array = json.getJSONArray("hourly");
		
		} catch (Exception e) {
			array = null;
		}

		return array;
	}
	
	public List<String> getWeather(int hour) {
		
		String image = null;
		JSONArray info = getInfo();
		if (info == null) {
			return new ArrayList<>();
		}
		
		JSONArray hourly = info.getJSONObject(hour).getJSONArray("weather");
		JSONObject weather = hourly.getJSONObject(0);
		
		int hourMod = (hour)%24;
		
		image = weatherImage(hourMod, weather.getString("main"));
		
		String h;
		if ((hourMod) < 10) {
			h = "0" + (hourMod);
		}
		else {
			h = Integer.toString(hour);
		}
		h = h + ":00";
		
		String temp = kelvinToCelsius(info.getJSONObject(hour).getDouble("temp")) + " \u00B0" + "C";
		
		List<String> w = new ArrayList<>();
		w.add(temp);
		w.add(image);
		w.add(h);
		
		return w;
	}
}