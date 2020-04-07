package com.israis007.datetimespinner

import java.util.*

interface EventPicker {

    fun getHourSelected(calendar: Calendar)

    fun getHourSelected(hour: Int, minute: Int, meridian: Int)

}