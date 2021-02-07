package logic.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

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
				file = new File("src/res/png/weather/Moon.png").toURI().toString();
				break;
				
			case "Clouds":
				file = new File("src/res/png/weather/CloudMoon.png").toURI().toString();
				break;
				
			case "Rain":
				file = new File("src/res/png/weather/Rain.png").toURI().toString();
				break;
				
			case "Thunderstorm":
				file = new File("src/res/png/weather/Thunderstorm.png").toURI().toString();
				break;
				
			default:
				file = new File("src/res/png/weather/Cloud.png").toURI().toString();
				break;
			
			}
		}
		
		else {
			
			switch (weather) {
			
			case "Clear":
				file = new File("src/res/png/weather/Sun.png").toURI().toString();
				break;
				
			case "Clouds":
				file = new File("src/res/png/weather/CloudSun.png").toURI().toString();
				break;
				
			case "Rain":
				file = new File("src/res/png/weather/Rain.png").toURI().toString();
				break;
				
			case "Thunderstorm":
				file = new File("src/res/png/weather/Thunderstorm.png").toURI().toString();
				break;
				
			default:
				file = new File("src/res/png/weather/Cloud.png").toURI().toString();
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
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		    String jsonText = readAll(rd);
		    JSONObject json = new JSONObject(jsonText);
		    is.close();
		    return json;
	    
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "JSON loading error");
			return null;
		}
	}
	
	private JSONArray getInfo() {

		// TODO STRING IN PROPERTIES
		String API_KEY = "a4f22e032f9d48ee8fd3a2dfe5101878";
		//String LOCATION = "Rome, IT";
		String LAT = "41.89";
		String LON = "12.48";
		//String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + API_KEY + "&units=imperial";
		String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat=" + LAT + "&lon=" + LON +"&exclude=daily,minutely,current,alerts&appid=" + API_KEY;

		JSONObject json;
		
		json = readJsonFromUrl(urlString);
		JSONArray array = null;
		try {
			array = json.getJSONArray("hourly");
			
		} catch (NullPointerException e) {
			return null;
		}
		
		return array;
	}
	
	public List<String> getWeather(int hour) {
		
		String image = null;
		JSONArray info = getInfo();
		
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
		
		String temp = kelvinToCelsius(info.getJSONObject(hour).getDouble("temp")) + "\u00B0" + "C";
		
		List<String> w = new ArrayList<>();
		w.add(temp);
		w.add(image);
		w.add(h);
		
		return w;
	}
}