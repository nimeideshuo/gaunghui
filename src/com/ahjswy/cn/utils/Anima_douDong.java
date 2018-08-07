package com.ahjswy.cn.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

public class Anima_douDong {
	public void douDong(Context context, EditText editText) {

		Vibrator shakeVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		Animation shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
		shakeAnimation.setDuration(300);
		CycleInterpolator cycleInterpolator = new CycleInterpolator(8);
		shakeAnimation.setInterpolator(cycleInterpolator);
		editText.startAnimation(shakeAnimation);
		shakeVibrator.vibrate(new long[] { 0, 500 }, -1);
	}
}
