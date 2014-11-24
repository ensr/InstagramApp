package com.ensr.instagram.customcompanent;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Custom {

	public static void hideKeyboard(Activity mcontext) {

		InputMethodManager inputManager = (InputMethodManager) mcontext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = mcontext.getCurrentFocus();
		if (view != null) {
			inputManager.hideSoftInputFromWindow(view.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
