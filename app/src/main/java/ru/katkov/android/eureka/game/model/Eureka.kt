package ru.katkov.android.eureka.game.model

import android.graphics.Color
import ru.katkov.android.eureka.R
import kotlin.random.Random

class Eureka {
    var status = GameStatus.IN_PROCESS
        private set
    var tries: Int = 0
        private set

    private var colors: MutableList<EurekaColor> = mutableListOf()


    enum class EurekaColor(clr: Color, num: Int) {
        RED(Color.valueOf(Color.RED), 0),
        ORANGE(Color.valueOf(Color.rgb(255, 165, 0)), 1),
        YELLOW(Color.valueOf(Color.YELLOW), 2),
        GREEN(Color.valueOf(Color.GREEN), 3),
        CYAN(Color.valueOf(Color.CYAN), 4),
        BLUE(Color.valueOf(Color.BLUE),5),
        VIOLET(Color.valueOf(Color.rgb(139,0,255)),6),
        BROWN(Color.valueOf(Color.rgb(150,75,0)), 7),
        BLACK(Color.valueOf(Color.BLACK), 8),
        WHITE(Color.valueOf(Color.WHITE), 9),
        GREY(Color.valueOf(Color.rgb(100,100,100)), 10);

        val color: Color;
        val num: Int
        init {
            color = clr
            this.num = num
        }

        companion object {
            fun byNumber(i: Int): EurekaColor{
                return EurekaColor.values().find { clr->clr.num == i }?: GREY
            }
            fun getFourRandom(): List<EurekaColor> {
                val res = mutableListOf<EurekaColor>()
                val clrs = EurekaColor.values().filter { color -> color.num<8 }.toMutableList();
                for (i in 1..4) {
                    res.add(clrs.removeAt(Random.nextInt(clrs.size)))
                }
                return res
            }
        }
    }
    enum class GameStatus{
        WIN, LOSE, IN_PROCESS
    }
    init {
        tries = 0
        status = GameStatus.IN_PROCESS
        newGame()
    }

    fun getAnswer(): List<EurekaColor>{
        return colors
    }

    private fun reset(){
        tries = 0
        status = GameStatus.IN_PROCESS
    }

    fun newGame(colors: List<EurekaColor>) {
        if(colors.size!=4) throw IllegalArgumentException("There must be 4 colors")
        this.colors.clear()
        this.colors.addAll(colors)
        reset()
    }

    fun newGame() {
        newGame(EurekaColor.getFourRandom())
    }

    fun guess(colors: List<EurekaColor>):List<EurekaColor>{
        if(status!= GameStatus.IN_PROCESS) throw IllegalStateException("Game over. You $status")
        if(colors.size!=4) throw IllegalArgumentException("There must be 4 colors")
        val ans = mutableListOf<EurekaColor>()
        for((index, item) in colors.withIndex()){
            when (item) {
                this.colors[index] -> ans.add(EurekaColor.WHITE)
                in this.colors -> ans.add(EurekaColor.BLACK)
                else -> ans.add(EurekaColor.GREY)
            }
        }
        ++tries
        if(ans.all { it== EurekaColor.WHITE }){
            status = GameStatus.WIN
        }else if(tries>=6) {
            status = GameStatus.LOSE
        }
        return ans
    }
}