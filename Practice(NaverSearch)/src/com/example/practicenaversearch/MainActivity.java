package com.example.practicenaversearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener{

	Button btnSearch;
	ImageView btnTarget;
	String testKey = "c1b406b32dbbbbeee5f2a36ddc14067f";
	
	String searchInfo = null;
	String link = null;
	String thumbNail = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnTarget = (ImageView)findViewById(R.id.btnTarget);
		btnTarget.setOnClickListener(this);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnSearch) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					searchImage();
				}
			}).start();
		} else {
			
		}
	}
	private void searchImage() {
		
		
		/////////////////////////////////////////////////
		try {
			searchInfo = URLEncoder.encode("단어", "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////////////
		try {
			URL requestUrl = new URL(
					"http://openapi.naver.com/search"
						+"?key="	+testKey
						+"&query="	+searchInfo					
						+"&target=image"
						+"&start=1"
						+"&display=1");
			
			XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserCreator.newPullParser();
			
			parser.setInput(requestUrl.openStream(), null);
			
			int parseEvent = parser.getEventType();
			
			while (parseEvent != XmlPullParser.END_DOCUMENT) {
				switch (parseEvent) {

				case XmlPullParser.START_TAG:
					String tag = parser.getName();
					if (tag.compareTo("link") == 0)
						link = parser.nextText();
					if (tag.compareTo("thumbnail") == 0)
						thumbNail = parser.nextText();
					break;
				}
				parseEvent = parser.next();
				//다음 데이터로 넘어간다. end_document일때까지
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////////////

		System.out.println("link : "+link);
		System.out.println("thumbNail : "+thumbNail);
		
		
		
		
		
		//btnTarget.setBackgroundDrawable(LoadImageFromWebOperations(link));
		//http://itgury.tistory.com/30
		
	}
	
	private Drawable LoadImageFromWebOperations(String strPhotoUrl) 
    {
        try {
        	InputStream is = (InputStream) new URL(strPhotoUrl).getContent();
        	Drawable drawable = Drawable.createFromStream(is, "src name");
        	return drawable;
        }catch (Exception e) {
        	System.out.println("       error occur      ");
        	e.printStackTrace();
        }
		return null;
    }
}
