package com.batanks.nextplan.home.fragment.tabfragment

import com.batanks.nextplan.swagger.model.Event
import com.batanks.nextplan.swagger.model.PostActivities
import com.batanks.nextplan.swagger.model.PostPlaces
import com.batanks.nextplan.swagger.model.PostTasks

interface ButtonContract {
    fun addPeriodClicked()
    //fun fromAddPeriodClicked()
    //fun toAddPeriodClicked()
    fun addPlaceClicked(placePosition : Int, placeList : ArrayList<PostPlaces>)
    fun addActionClicked(taskPosition : Int, taskList : ArrayList<PostTasks>, editButtonClicked : Boolean)
    fun addActivityClicked(activityPosition : Int, activityList : ArrayList<PostActivities>, editButtonClicked : Boolean)
    fun addPeopleClicked()
}