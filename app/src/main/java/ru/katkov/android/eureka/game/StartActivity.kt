package ru.katkov.android.eureka.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.katkov.android.eureka.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_view)
    }

    fun exit(v: View){
        this.finishAffinity()
    }

    fun play(v: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

}