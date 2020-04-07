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

    private var event: EventPicker? = null
    private lateinit var hpic: NumberPicker
    private lateinit var mpic: NumberPicker
    private lateinit var mmpic: NumberPicker

    constructor(context: Context, @Nullable hourArray: ArrayList<String>?, @Nullable minutesArray: ArrayList<String>?, @NonNull colorDots: Int): this(context){
        val dm = DecimalFormat("00")

        orientation = HORIZONTAL

        val view = LayoutInflater.from(context).inflate(R.layout.picker, null, false)
        hpic = view.findViewById(R.id.hour_picker)
        mpic = view.findViewById(R.id.minute_picker)
        mmpic = view.findViewById(R.id.meridian_picker)
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

    fun addEventPicker(eventPicker: EventPicker){
        this.event = eventPicker
    }

//    fun getSelectedHour(): Calendar {
//        val cal = Calendar.getInstance(Locale.getDefault())
//        cal[Calendar.HOUR_OF_DAY] = hpic.displayedValues[hpic.value]
//    }

    fun getSelectedHour(): Array<Int>{
        val array = Array(3){0}

        array[0] = hpic.value
        array[1] = mpic.value
        array[2] = mmpic.value

        return array
    }

}