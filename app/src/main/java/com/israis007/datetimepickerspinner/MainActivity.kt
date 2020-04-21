package com.israis007.datetimepickerspinner

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.israis007.datetimespinner.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val tp =  TimePicker(this@MainActivity, null, arrayListOf("0", "15", "30", "45"), Color.CYAN)
//        val tp =  TimePicker(this@MainActivity, null, arrayListOf("0", "15", "30", "45"), Calendar.getInstance(), Color.CYAN, Color.BLUE, 30)
        val tp =  TimePicker(this@MainActivity, null, null, Calendar.getInstance(), Color.CYAN, Color.BLUE, 30)

        layout_main.addView(tp)
    }
}
