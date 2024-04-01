package ru.katkov.android.eureka.game

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.katkov.android.eureka.game.model.Eureka

class ChooseColorActivityContract: ActivityResultContract<Int, List<Eureka.EurekaColor>?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, ChooseColorActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Eureka.EurekaColor>? {
        if(resultCode == android.app.Activity.RESULT_CANCELED) return null
        intent?:return null
        return intent.getIntArrayExtra("colors")?.map { i-> Eureka.EurekaColor.byNumber(i) }
    }
}