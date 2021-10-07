package com.clothingstore.anilshatharashi.presentation.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.clothingstore.anilshatharashi.R

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String?) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment, tag)
        .addToBackStack(null)
        .commit()
}

fun FragmentActivity.addFragment(fragment: Fragment, tag: String?) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment, tag)
        .commit()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.applicationContext?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}