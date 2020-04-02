package com.batanks.newplan.common

import android.content.Context
import androidx.annotation.CheckResult
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.batanks.newplan.R

@CheckResult
fun Context.dialogBuilder(
        @StringRes title: Int = 0,
        @StringRes message: Int = 0,
        @StringRes positive: Int = android.R.string.ok,
        @StringRes negative: Int = 0,
        @StringRes neutral: Int = 0,
        onPositive: (() -> Unit)? = null,
        onNegative: (() -> Unit)? = null,
        onNeutral: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        cancelable: Boolean = true,
        @StyleRes theme: Int = 0
): AlertDialog.Builder {
    return AlertDialog.Builder(this, theme).apply {
        if (title != 0) setTitle(title)
        if (message != 0) setMessage(message)

        if (positive != 0) setPositiveButton(positive) { _, _ -> onPositive?.invoke() }
        if (negative != 0) setNegativeButton(negative) { _, _ -> onNegative?.invoke() }
        if (neutral != 0) setNeutralButton(neutral) { _, _ -> onNeutral?.invoke() }

        onCancel?.let { listener -> setOnCancelListener { listener() } }
        onDismiss?.let { listener -> setOnDismissListener { listener() } }

        setCancelable(cancelable)
    }
}

@CheckResult
fun Context.dialogBuilder(
        title: CharSequence? = null,
        message: CharSequence? = null,
        positive: CharSequence? = null,
        negative: CharSequence? = null,
        neutral: CharSequence? = null,
        onPositive: (() -> Unit)? = null,
        onNegative: (() -> Unit)? = null,
        onNeutral: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        cancelable: Boolean = true,
        @StyleRes theme: Int = 0
): AlertDialog.Builder {
    return AlertDialog.Builder(this, theme).apply {
        title?.let { setTitle(it) }
        message?.let { setMessage(it) }

        if (positive != null) setPositiveButton(positive) { _, _ -> onPositive?.invoke() }
        if (negative != null) setNegativeButton(negative) { _, _ -> onNegative?.invoke() }
        if (neutral != null) setNeutralButton(neutral) { _, _ -> onNeutral?.invoke() }

        onCancel?.let { setOnCancelListener { _ -> it() } }
        onDismiss?.let { setOnDismissListener { _ -> it() } }

        setCancelable(cancelable)
    }
}

@CheckResult
fun Context.getLoadingDialog(
        @StringRes title: Int = 0,
        @StringRes message: Int = 0,
        @StringRes positive: Int = 0,
        @StringRes negative: Int = 0,
        cancelable: Boolean = true,
        touchOutside: Boolean = false,
        onPositive: (() -> Unit)? = null,
        onNegative: (() -> Unit)? = null
): AlertDialog {
    val builder = this.dialogBuilder(
            title = title,
            message = message,
            positive = positive,
            negative = negative,
            cancelable = cancelable,
            onPositive = onPositive,
            onNegative = onNegative
    )
    builder.setView(R.layout.progress_layout)
    val dialog = builder.create()
    dialog.setCanceledOnTouchOutside(touchOutside)
    return dialog
}