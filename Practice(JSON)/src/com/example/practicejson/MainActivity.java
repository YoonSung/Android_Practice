package com.example.practicejson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;

public class MainActivity extends Activity {

	public String SERVER_URL = "http://54.250.171.142";
	public String REG_ID = "APA91bF77zEj0j4SzMYGiiItZA0atAn4z9JfvUJvdZCCCXCwU6CUjWKlDlJoVOrQqEYn_ho1T1ZqpKVL23wRVNv_vSN6wa6ENdkGEHMLVjZdECKLD7p3ilAm3Cvx0PPgM15eKGt5BkC2J3NfxU7-uCQcc6ly3OdIzg";
	
	public String LOCATION_PUSH_URL = "/location/push";
	public String LOCATION_PULL_URL = "/location/pull";
	public String SEND_MESSAGE_URL 	= "/message/create";
	public String JOIN_URL 			= "/register";
	String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Gson gson = new Gson();

		
//		test location push
//		JsonController jsonController = new JsonController();
//		jsonController.add("regId", REG_ID);
//		jsonController.add("latitude", "37.401421806078154");
//		jsonController.add("longitude", "127.10793762466238");
//		result = jsonController.getResult();
		
		
		//test message send
//		JsonController jsonController = new JsonController();
//		jsonController.add("regId", REG_ID);
//		jsonController.add("message", "메세지 테스트");
//		result = "regId=" + REG_ID +"&"+"message="+ "동국안녕? 메세지 테스트입니다.";
		result = "regId="+REG_ID+"&latitude=37.401421806078154&longitude=127.10793762466238";
				
		
		System.out.println(result);
		new Thread(new Runnable() {
			public void run() {
				sendHttpServer(LOCATION_PUSH_URL, result);
				//sendHttpServer(SEND_MESSAGE_URL, result); 
			}
		}).start();
	}
	
	class JsonCreator {
		String token = "&";
		String equality = "=";
		StringBuilder sb;
		
		public JsonCreator() {
			sb = new StringBuilder();
		}
		
		public void add(String key, String value){
			if(sb.length() !=0 )
				sb.append(token);
			sb.append(key);
			sb.append(equality);
			sb.append(value);
		}
		
		public String getJSONString(){
			return sb.toString();
		}
	}
	
	
	class JsonController {
		
		Map map;
		Gson gson;
		
		public JsonController() {
			map = new LinkedHashMap();
			gson = new Gson();
		}
		
		public JsonController(String json) {
			
		}
		
		public void add(String key, String value) {
			map.put(key, value);
		}
		
		public String getResult() {
			return gson.toJson(map);
		}
	}
	

	public void sendHttpServer(String endpoint, String message) {
		URL url;
		
        try {
            url = new URL(SERVER_URL+endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        
        byte[] bytes = message.getBytes();
        HttpURLConnection conn = null;
        
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");

            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);

            // handle the response
            int status = conn.getResponseCode();
            System.out.println(" int status  :" +status);
            
            
            InputStream in = conn.getInputStream();
            if (in != null) {
	            InputStreamReader isr = new InputStreamReader(in);
	            BufferedReader br = new BufferedReader(isr);
	            
	            String data = br.readLine();
	            
	            while(data !=null) {
	            	System.out.println(data);
	            	data = br.readLine();
	            }
            }
            
            if (in != null) {
            	in.close();
            }
            
            if (out != null) {
            	out.close();
            }
            
            if (status != 200) {
            	//handle error (todo)
            	throw new IOException("Post failed with error code " + status);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
	}

}
