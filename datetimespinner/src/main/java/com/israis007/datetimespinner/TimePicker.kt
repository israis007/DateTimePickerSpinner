package com.israis007.datetimespinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class TimePicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, @Nullable hourArray: ArrayList<String>?, @Nullable minutesArray: ArrayList<String>?, @NonNull colorDots: Int): this(context){
        val dm = DecimalFormat("00")

        orientation = HORIZONTAL

        val view = LayoutInflater.from(context).inflate(R.layout.picker, null, false)
        val hpic = view.findViewById<NumberPicker>(R.id.hour_picker)
        val mpic = view.findViewById<NumberPicker>(R.id.minute_picker)
        val mmpic = view.findViewById<NumberPicker>(R.id.meridian_picker)
        val dots = view.findViewById<ImageView>(R.id.dots_picker)

        val houra = hourArray ?: ArrayList()

        if (hourArray == null)
            repeat(12){
                houra.add(dm.format(it + 1))
            }

        val minutesa = minutesArray ?: ArrayList()

        if (minutesArray == null)
            repeat(60) {
                minutesa.add(dm.format(it))
            }

        dots.setColorFilter(colorDots)
        hpic.displayedValues = houra.toTypedArray()
        hpic.minValue = 0
        hpic.maxValue = houra.size - 1
        mpic.displayedValues = minutesa.toTypedArray()
        mpic.minValue = 0
        mpic.maxValue = minutesa.size - 1
        mmpic.displayedValues = arrayOf("a.m.","p.m.", "a.m.","p.m.")
        mmpic.minValue = 0
        mmpic.maxValue = 3

        this.addView(getViewWithoutParent(view))
    }

    private fun getViewWithoutParent(view: View): View {
        if (view.parent != null)
            (view as ViewGroup).removeView(view)
        return view
    }

}