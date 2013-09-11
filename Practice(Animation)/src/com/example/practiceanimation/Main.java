package com.example.practiceanimation;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class Main extends Activity implements OnClickListener, AnimationListener {

	private Animation animation1;
	private Animation animation2;
	private boolean isBackOfCardShowing = true;
	private LinearLayout word1Content;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
		animation1.setAnimationListener(this);
		animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
		animation2.setAnimationListener(this);
		//findViewById(R.id.button1).setOnClickListener(this);
		
		word1Content = (LinearLayout) findViewById(R.id.word1);
		word1Content.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		v.setEnabled(false);
		((LinearLayout)findViewById(R.id.word1)).clearAnimation();
		((LinearLayout)findViewById(R.id.word1)).setAnimation(animation1);
		((LinearLayout)findViewById(R.id.word1)).startAnimation(animation1);
	}
//http://blog.naver.com/ggaddr?Redirect=Log&logNo=20123287802
//http://dmh11.tistory.com/75
	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation==animation1) {
			if (isBackOfCardShowing) {
				((LinearLayout)findViewById(R.id.word1)).setBackgroundResource(R.drawable.empty256);
				findViewById(R.id.word1_content).setVisibility(View.VISIBLE);
				findViewById(R.id.word1_btnSearch).setClickable(true);
//				findViewById(R.id.word1_txt).setVisibility(View.VISIBLE);
//				findViewById(R.id.word1_btnSearch).setVisibility(View.VISIBLE);
			} else {
				((LinearLayout)findViewById(R.id.word1)).setBackgroundResource(R.drawable.diamonds256);
			}
			((LinearLayout)findViewById(R.id.word1)).clearAnimation();
			((LinearLayout)findViewById(R.id.word1)).setAnimation(animation2);
			((LinearLayout)findViewById(R.id.word1)).startAnimation(animation2);
		} else {
			isBackOfCardShowing=!isBackOfCardShowing;
			findViewById(R.id.button1).setEnabled(true);
		}
	} 

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

}

