package ru.katkov.android.eureka.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.katkov.android.eureka.R
import ru.katkov.android.eureka.game.model.Eureka
import ru.katkov.android.eureka.game.model.Eureka.EurekaColor.*

class ChooseColorActivity : AppCompatActivity() {
    private val clrButtonsAdapter: GridViewAdapter by lazy {
        GridViewAdapter(
            R.drawable.rectangle,
            applicationContext,
            mutableListOf(RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, VIOLET, BROWN)
        )
    }

    private val chosenColorsAdapter: GridViewAdapter by lazy {
        GridViewAdapter(
            R.drawable.rectangle,
            applicationContext,
            MutableList(4) { GREY }
        )
    }

    private val clrs = mutableListOf<Eureka.EurekaColor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_color_view)
        findViewById<GridView>(R.id.colorButtons2).adapter = clrButtonsAdapter
        findViewById<GridView>(R.id.chozenColorsGrid).adapter = chosenColorsAdapter
        findViewById<GridView>(R.id.colorButtons2).setOnItemClickListener { _, _, position, _ ->
            if (clrs.size > 3) clrs.removeLast()
            clrs.add(clrButtonsAdapter.getItem(position) as Eureka.EurekaColor)
            chosenColorsAdapter.setItem(
                clrs.size - 1,
                clrButtonsAdapter.getItem(position) as Eureka.EurekaColor
            )
        }

    }

    fun remove(v: View) {
        if (clrs.isEmpty()) return
        clrs.removeLast()
        chosenColorsAdapter.setItem(clrs.size, GREY)
    }

    fun confirm(v: View) {
        if (clrs.size < 4) return
        val map: MutableMap<Eureka.EurekaColor, Int> = Eureka.EurekaColor.values().associateBy({it}, {0}).toMutableMap()
        for(item in clrs){
            map[item] = map[item] as Int + 1
        }
        if(map.filter { e -> e.value > 1 }.isNotEmpty())return
        val intent = Intent()
        intent.putExtra("colors", clrs.map { clr -> clr.num }.toIntArray())
        setResult(RESULT_OK, intent)
        this.finish()
    }

    fun cancel(v: View) {
        setResult(RESULT_CANCELED)
        this.finish()
    }

}