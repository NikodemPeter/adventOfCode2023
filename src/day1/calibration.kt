package day1

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import java.io.File

@Test
class Calibration {

    @Test
    fun `replacing works sequentially, not from 1 to 9`(){
        assertEquals(replaceSpelledOutNumbersWithDigits("three4two4rnnslsvxmsbcpvnbpfseveneightwokcn") ,"3424rnnslsvxmsbcpvnbpf78wokcn")
    }
   fun getCalibrationSum(){
       println(loadLines().map (::replaceSpelledOutNumbersWithDigits ).sumOf (::getCalibrationValue ))
   }

    val spelledOutNumbers = listOf("one","two","three","four","five","six","seven","eight","nine")
    private fun replaceSpelledOutNumbersWithDigits(line: String):String {
        val firstRemainingSpelledOutNumber = line.findAnyOf(spelledOutNumbers)
        return if (firstRemainingSpelledOutNumber!= null){
            val word = firstRemainingSpelledOutNumber.second
            replaceSpelledOutNumbersWithDigits(line.replaceFirst(word,toDigit(word)))
        } else {
            line
        }
    }

    private fun getCalibrationValue(line: String): Int {
        val calibration=  line.find { it.isDigit()}!!.toString() + line.last { it.isDigit() }.toString()
        return calibration.toInt()
    }

    private fun toDigit(word: String): String {
        return when (word){
            "one" -> "1"
            "two" -> "2"
            "three" -> "3"
            "four" -> "4"
            "five" -> "5"
            "six" -> "6"
            "seven" -> "7"
            "eight" -> "8"
            "nine" -> "9"
            else -> throw Error("ups")
        }
    }
    private fun loadLines(): List<String>{
        val fileName = "src/day1/calibrationInput.txt"
        val file = File(fileName)
        return file.readLines()
    }
}

