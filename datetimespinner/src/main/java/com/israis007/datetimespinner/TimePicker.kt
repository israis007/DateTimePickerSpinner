package com.israis007.datetimespinner

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
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
    private var hourArray: ArrayList<String>? = null
    private var minutesArray: ArrayList<String>? = null
    private var colorDots: Int = 0
    private var colorText: Int = 0
    private var textSize: Int = 0
    private var date: Calendar? = null

    constructor(context: Context, @Nullable hourArray: ArrayList<String>?, @Nullable minutesArray: ArrayList<String>?, @NonNull colorDots: Int): this(context){
        this.hourArray = hourArray
        this.minutesArray = minutesArray
        this.colorDots = colorDots
        setUpViews()
    }

    constructor(context: Context, @Nullable hourArray: ArrayList<String>?, @Nullable minutesArray: ArrayList<String>?, @NonNull colorDots: Int, colorText: Int, textSize: Int): this(context){
        this.hourArray = hourArray
        this.minutesArray = minutesArray
        this.colorDots = colorDots
        this.colorText = colorText
        this.textSize = textSize
        setUpViews()
    }

    constructor(context: Context, @Nullable hourArray: ArrayList<String>?, @Nullable minutesArray: ArrayList<String>?, date: Calendar, @NonNull colorDots: Int, colorText: Int, textSize: Int): this(context){
        this.hourArray = hourArray
        this.minutesArray = minutesArray
        this.colorDots = colorDots
        this.colorText = colorText
        this.textSize = textSize
        this.date = date
        setUpViews()
    }

    private fun setUpViews(){
        val dm = DecimalFormat("00")

        orientation = HORIZONTAL

        val view = LayoutInflater.from(context).inflate(R.layout.picker, null, false)
        val layout = view.findViewById<LinearLayout>(R.id.layout_root)
        hpic = view.findViewById(R.id.hour_picker)

        val numberPicker = MaterialNumberPicker(
            context = context,
            minValue = 1,
            maxValue = 50,
            value = 10,
            separatorColor = colorDots,
            textColor = colorText,
            textSize = textSize,
            textStyle = Typeface.BOLD_ITALIC,
            editable = false,
            wrapped = false,
            fontName = "Hand.ttf",
            formatter = NumberPicker.Formatter { value ->
                return@Formatter "Value $value"
            }
        )

        mpic = view.findViewById(R.id.minute_picker)
        mmpic = view.findViewById(R.id.meridian_picker)
        val dots = view.findViewById<ImageView>(R.id.dots_picker)

//        hpic.dividerDrawable.setTint(colorDots)
//        mpic.dividerDrawable.setTint(colorDots)
//        mmpic.dividerDrawable.setTint(colorDots)

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

//        setDividerColor(hpic, colorDots)
//        setDividerColor(mpic, colorDots)
//        setDividerColor(mmpic, colorDots)

        hpic.displayedValues = houra.toTypedArray()
        hpic.minValue = 0
        hpic.maxValue = houra.size - 1

        numberPicker.displayedValues = houra.toTypedArray()
        numberPicker.minValue = 0
        numberPicker.maxValue = houra.size - 1

        mpic.displayedValues = minutesa.toTypedArray()
        mpic.minValue = 0
        mpic.maxValue = minutesa.size - 1

        mmpic.displayedValues = arrayOf("a.m.","p.m.", "a.m.","p.m.")
        mmpic.minValue = 0
        mmpic.maxValue = 3

        if (date != null) {
            hpic.value = if (date!![Calendar.HOUR_OF_DAY] > 12) date!![Calendar.HOUR_OF_DAY] - 13 else date!![Calendar.HOUR_OF_DAY] - 1
            mpic.value = if (minutesa.size == 60) date!![Calendar.MINUTE] else 0
            mmpic.value = if (date!![Calendar.HOUR_OF_DAY] > 12) 1 else 0
        }

        layout.addView(numberPicker, 0)

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
        array[2] = when (mmpic.value) {
            0 -> 0
            1 -> 1
            2 -> 0
            else -> 1
        }

        return array
    }

    private fun setDividerColor(numberPicker: NumberPicker, color: Int) {
//        val dividerField = NumberPicker::class.java.declaredFields.firstOrNull { it.name == "mSelectionDivider" } ?: return
//        try {
//            dividerField.isAccessible = true
//            dividerField.set(this, color)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        try {
            val fDividerDrawable =
                numberPicker::class.java.getDeclaredField("mSelectionDivider")
            fDividerDrawable.isAccessible = true
            val d = fDividerDrawable.get(this) as Drawable
            d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            d.invalidateSelf()
            postInvalidate() // Drawable is dirty
        } catch (e: Exception) {
        }
    }

}