package com.example.boxowl.ui.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Andrey Morgunov on 22/10/2020.
 */

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}