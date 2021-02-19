package com.example.boxowl.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.boxowl.R
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog
import retrofit2.HttpException
import java.util.*
import java.util.regex.Pattern.compile

/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

fun showAPIErrors(error: Throwable) : String {
    val httpException = error as? HttpException
    return httpException?.response()?.errorBody()?.string() ?: error.stackTraceToString()
}

fun String.isEmail() : Boolean {
    val emailRegex = compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )
    return emailRegex.matcher(this).matches()
}

fun String.isPhone() : Boolean {
    return this.length == 18
}

fun String.toNormalString() : String {
    return this.filter { it.isDigit() || it == '+' }
}

fun loadingSpotsDialog(context: Context) : AlertDialog {
    return SpotsDialog.Builder()
            .setContext(context)
            .setMessage(R.string.loading_dialog_text)
            .build()
}

fun showLoadingDialog(alertDialog: AlertDialog) {
    alertDialog.show()
}

fun dismissLoadingDialog(alertDialog: AlertDialog) {
    alertDialog.dismiss()
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

@Suppress("DEPRECATION")
fun setAppLocale(localCode: String, activity: Activity) {
    val res = activity.resources
    val conf = res.configuration
    conf.setLocale(Locale(localCode.toLowerCase(Locale.ROOT)))
    res.updateConfiguration(conf, null)
}

