package com.sum.tea

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    var junker:String? = null
    val numbers = listOf(1, 2, 3)
    val doubledNumbers = numbers.let {
        it.map { number -> number * 2 }
    }

    @Test
    fun printNumber(){
        val value = junker.let {  }
        println("$value")
//        doubledNumbers.forEach {
//            println("value =  $it")
//        }
    }
}