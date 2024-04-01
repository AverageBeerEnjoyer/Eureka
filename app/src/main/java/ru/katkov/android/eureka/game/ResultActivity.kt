package ru.katkov.android.eureka.game

import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.katkov.android.eureka.R
import ru.katkov.android.eureka.game.model.Eureka
import ru.katkov.android.eureka.game.model.Eureka.EurekaColor.Companion.byNumber

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_view)

        findViewById<TextView>(R.id.gameRes).text = intent.getStringExtra("res")
        findViewById<GridView>(R.id.resGrid).adapter = GridViewAdapter(
            R.drawable.rectangle,
            this,
            intent.getIntArrayExtra("colors")!!.map { num -> byNumber(num) }.toMutableList())
    }

    fun exit(v: View) {
        this.finish()
    }
}