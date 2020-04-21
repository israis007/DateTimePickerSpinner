package com.israis007.datetimespinner

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.NumberPicker

class CustomNumberPicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NumberPicker(context, attrs, defStyleAttr) {

    private var color = 0

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, color: Int): this (context, attrs, defStyleAttr){
        this.color = color
    }

    constructor(context: Context, color: Int): this (context){
        this.color = color
    }

    init {
        val numberPickerClass = try {
            Class.forName("android.widget.NumberPicker")
        } catch (e: ClassNotFoundException){
            null
        }

        val selectionDivider = try {
            numberPickerClass?.getDeclaredField("mSelectionDivider")
        } catch (e: NoSuchFieldException) {
            null
        }

        try {
            selectionDivider?.isAccessible = true
            selectionDivider?.set(this, null)
        } catch (i: IllegalArgumentException){
            i.printStackTrace()
        } catch (r: Resources.NotFoundException){
            r.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}