package com.ahjswy.cn.app;

import android.app.Activity;
import java.util.Stack;

public class ActivityManager {
	private static Stack<Activity> activityStack;
	private static ActivityManager instance;

	public static ActivityManager getScreenManager() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	public Activity currentActivity() {
		return activityStack.lastElement();
	}

	public void popActivity(Activity activity) {
		if (activity != null && !activity.isFinishing()) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	public void popAllActivityExceptOne(Class<?> cls) {

		for (int i = activityStack.size() - 1; i >= 0; i--) {
			Activity activity = activityStack.get(i);
			if (activity == null) {
				continue;
			}
			if (activity.getClass().equals(cls)) {
				continue;
			}
			popActivity(activity);

		}

		// for (int i = 0; i < activityStack.size(); i++) {
		// Activity activity = activityStack.get(i);
		// if (activity == null) {
		// continue;
		// }
		// if (activity.getClass().equals(cls)) {
		// continue;
		// }
		// popActivity(activity);
		// }
	}

	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
}