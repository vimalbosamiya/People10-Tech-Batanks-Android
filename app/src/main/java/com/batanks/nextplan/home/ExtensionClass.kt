package com.batanks.nextplan.home

import android.graphics.Color
import android.util.Patterns
import android.widget.AutoCompleteTextView
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.batanks.nextplan.home.ExtensionClass.Companion.PhoneNumberPattern
import com.batanks.nextplan.home.ExtensionClass.Companion.textPattern
import com.batanks.nextplan.home.ExtensionClass.Companion.userNamePattern
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

 internal fun TextInputLayout.markRequiredInRed() {

    hint = buildSpannedString {
        append(hint)
        color(Color.RED) { append(" *") }
    }
}

 internal fun AutoCompleteTextView.markRequiredRed() {

    setText( buildSpannedString {
        append(text)
        color(Color.RED) { append(" *") }
    })
}

 internal fun MaterialButton.markInRed() {

    setText( buildSpannedString {
        append(text)
        color(Color.RED) { append(" *") }
    })
}

 internal fun isEmailValid(email: CharSequence?): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
 internal fun isValidPassword(textToCheck: String?): Boolean = textPattern.matcher(textToCheck).matches()
 internal fun isValidUsername(textToCheck: String?) : Boolean = userNamePattern.matcher(textToCheck).matches()
 internal fun isValidPhoneNumber(textToCheck: String?) : Boolean = PhoneNumberPattern.matcher(textToCheck).matches()


class ExtensionClass {

    companion object {
        //val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])$")
        //val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!/*-_])(?=\\S+$)(?=.*\\d).+$")
        //val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#?!@$%^&()\\[\\\\\\]<>*~:_/|`-])(?=\\S+$)(?=.*\\d).+$")
        //val textPattern: Pattern = Pattern.compile("^(?=.?[A-Z])(?=.?[a-z])(?=.?[0-9])(?=.?[#?!@$%^&()\\[\\\\\\]<>*~:_/|`-]).{6,}$")
        //val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$@!%?&.#^+=-])(?=\\S+$)(?=.*\\d).+$")
        //@#$%^&+=!/*-_
        //val textPattern: Pattern = Pattern.compile("^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[$@$!%?&.])[A-Za-z\\d$@$!%?&.]")
        val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#?!@$%^&()+=\\[\\\\\\]<>*~:_/|`-])(?=\\S+$)(?=.*\\d).+$")
        val userNamePattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")
        //val firstNamePattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")
        val PhoneNumberPattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")

        //(?=.*\d).+  (?=.*[@#\$%^&+=])
    }
}