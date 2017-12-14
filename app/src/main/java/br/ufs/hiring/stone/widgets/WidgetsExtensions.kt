package br.ufs.hiring.stone.widgets

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

/**
 *
 * Created by @ubiratanfsoares
 *
 */

fun Snackbar.colorForActionText(colorResource: Int): Snackbar {
    setActionTextColor(ContextCompat.getColor(this.context, colorResource))
    return this
}

fun Snackbar.action(actionText: Int, block: (Any) -> Unit): Snackbar {
    setAction(actionText, View.OnClickListener(block))
    return this
}

fun TextView.compoundDrawableLeft(drawableResource: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(drawableResource, 0, 0, 0)
}

inline fun <reified T : View> LayoutInflater.typedInflation(layoutResource: Int): T {
    return inflate(layoutResource, null) as T
}