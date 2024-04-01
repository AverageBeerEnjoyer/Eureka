package ru.katkov.android.eureka.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.GridView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.katkov.android.eureka.R
import ru.katkov.android.eureka.game.model.Eureka
import ru.katkov.android.eureka.game.model.Eureka.EurekaColor.*
import kotlin.math.min

class GameActivity : AppCompatActivity() {
    private val leftGridAdapter: GridViewAdapter by lazy {
        GridViewAdapter(
            R.drawable.circle,
            applicationContext,
            MutableList(24) { _ -> GREY })
    }
    private val rightGridAdapter: GridViewAdapter by lazy {
        GridViewAdapter(
            R.drawable.circle,
            applicationContext,
            MutableList(24) { _ -> GREY })
    }
    private val colorButtonsAdapter: GridViewAdapter by lazy {
        GridViewAdapter(
            R.drawable.rectangle,
            applicationContext,
            mutableListOf(RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, VIOLET, BROWN)
        )
    }
    private val playerChose = registerForActivityResult(ChooseColorActivityContract()){
        res-> if(res==null) game.newGame()
        else game.newGame(res)
    }
    private val game = Eureka()
    private val currentColors = mutableListOf<Eureka.EurekaColor>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)
        createGameField()
    }

    private fun createGameField() {
        findViewById<GridView>(R.id.leftGrid).adapter = leftGridAdapter
        findViewById<GridView>(R.id.rightGrid).adapter = rightGridAdapter
        findViewById<GridView>(R.id.colorButtons).adapter = colorButtonsAdapter
        findViewById<GridView>(R.id.colorButtons).setOnItemClickListener { _, _, position, _ ->
            if (game.status != Eureka.GameStatus.IN_PROCESS) return@setOnItemClickListener
            leftGridAdapter.setItem(
                game.tries * 4 + min(currentColors.size, 3),
                colorButtonsAdapter.getItem(position) as Eureka.EurekaColor
            )
            if (currentColors.size > 3) currentColors.removeAt(3)
            currentColors.add(colorButtonsAdapter.getItem(position) as Eureka.EurekaColor)
        }
    }

    fun newGame(v: View) {
        game.newGame()
        rightGridAdapter.clear()
        leftGridAdapter.clear()
    }

    fun confirm(v: View) {
        if (game.status != Eureka.GameStatus.IN_PROCESS) return
        if (currentColors.size < 4) return;
        val ans = game.guess(currentColors)
        currentColors.clear()
        for (i in 0..3) {
            rightGridAdapter.setItem((game.tries - 1) * 4 + i, ans[i])
        }
        if (game.status == Eureka.GameStatus.IN_PROCESS) return

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("res", getString(if (game.status == Eureka.GameStatus.WIN) R.string.win else R.string.lose))
        intent.putExtra("colors", game.getAnswer().map { clr->clr.num }.toIntArray())
        this.startActivity(intent)
        newGame(v)
    }

    fun remove(v: View) {
        if (game.status != Eureka.GameStatus.IN_PROCESS) return
        if (currentColors.isEmpty()) return
        currentColors.removeLast()
        leftGridAdapter.setItem(game.tries * 4 + currentColors.size, GREY)
    }

    fun exit(v: View) {
        this.finish()
    }

    fun chooseColors(v: View) {
        playerChose.launch(0)
    }
}
