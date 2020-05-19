package com.batanks.nextplan.eventdetails

import android.view.View

interface OnClickFunImplementation {

    fun addOnClickToDateView(view: View)
    fun addOnClickToDateBackView(view: View)
    fun addOnClickToPlaceView(view: View)
    fun addOnClickToPlaceBackView(view: View)
    fun addOnClickToDescriptionBackView(view: View)
    fun addOnClickToActivityAssociatedBackView(view: View)
    fun addOnClickToEverybodyComeBackView(view: View)
    fun addOnClickCommentsBackView(view: View)
}