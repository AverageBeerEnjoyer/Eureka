package ru.katkov.android.eureka

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    enum class Q {
        A, B, C
    }

    @Test
    fun addition_isCorrect() {
        val q = 1
        println(0)
        when {
            q < 2 -> println(2);
            q == 1 -> println(1)
        }
    }
}