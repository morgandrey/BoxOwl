package com.example.boxowl.ui.extension

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.boxowl.R
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */
 
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

