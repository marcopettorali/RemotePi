package com.marcopettorali.remotepi

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomnavigation.BottomNavigationView

fun hideKeyboard(v: View) {
    val inputManager: InputMethodManager = v.getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0)
}

fun hideBottomNavigationBar(activity: Activity){
    val navBar: BottomNavigationView = activity.findViewById(R.id.nav_view)
    navBar.visibility = View.GONE
}

fun showBottomNavigationBar(activity: Activity){
    val navBar: BottomNavigationView = activity.findViewById(R.id.nav_view)
    navBar.visibility = View.VISIBLE
}