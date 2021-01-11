package logic.utilities;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.image.Image;

public class Weather {
	
	public static double kelvinToCelsius(double kelvin) {
		BigDecimal bd = new BigDecimal(kelvin-273.15).setScale(1, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static Image weatherImage(int hour, String weather) {
		
		Image image;
		File file;
		
		if (hour < 6 || hour > 19) {
			
			switch (weather) {
			
			case "Clear":
				file = new File("src/res/png/weather/Moon.png");
				image = new Image(file.toURI().toString());
				break;
				
			case "Clouds":
				file = new File("src/res/png/weather/CloudMoon.png");
				image = new Image(file.toURI().toString());
				break;
				
			case "Rain":
				file = new File("src/res/png/weather/Rain.png");
				image = new Image(file.toURI().toString());
				break;
				
			case "Thunderstorm":
				file = new File("src/res/png/weather/Thunderstorm.png");
				image = new Image(file.toURI().toString());
				break;
				
			default:
				file = new File("src/res/png/weather/Cloud.png");
				image = new Image(file.toURI().toString());
				break;
			
			}
		}
		
		else {
			
			switch (weather) {
			
			case "Clear":
				file = new File("src/res/png/weather/Sun.png");
				image = new Image(file.toURI().toString());
				break;
				
			case "Clouds":
				file = new File("src/res/png/weather/CloudSun.png");
				image = new Image(file.toURI().toString());
				break;
				
			case "Rain":
				file = new File("src/res/png/weather/Rain.png");
				image = new Image(file.toURI().toString());
				break;
				
			case "Thunderstorm":
				file = new File("src/res/png/weather/Thunderstorm.png");
				image = new Image(file.toURI().toString());
				break;
				
			default:
				file = new File("src/res/png/weather/Cloud.png");
				image = new Image(file.toURI().toString());
				break;
			
			}
		}
		
		return image;
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	}
	
	public static JSONArray getInfo() {

		String API_KEY = "a4f22e032f9d48ee8fd3a2dfe5101878";
		String LAT = "41.89";
		String LON = "12.48";
		String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat=" + LAT + "&lon=" + LON +"&exclude=daily,minutely,current,alerts&appid=" + API_KEY;

		JSONObject json;
		
		try {
			json = readJsonFromUrl(urlString);
		    JSONArray array = json.getJSONArray("hourly");
		    return array;
		    
		} catch (JSONException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
