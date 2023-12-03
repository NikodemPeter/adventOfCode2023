package day2

import org.testng.annotations.Test
import java.io.File

@Test
class CubeConundrum {

    fun sumGameIdsOfPossibleGames(){
        val possibles = loadLines().map(::toGame).filter(::isPossible).sumOf{it.gameId}
        println(possibles)
    }

    fun sumPowerOfMinimumRequiredSets(){
        val powerSum = loadLines().map(::toGame).sumOf(::getPower)
        println(powerSum)
    }
    private fun getPower(configuration: Game): Int {
        val maxRed = configuration.hands.maxOf { it.red }
        val maxBlue = configuration.hands.maxOf { it.blue }
        val maxGreen = configuration.hands.maxOf { it.green }
        return maxRed * maxBlue * maxGreen
    }
    private fun isPossible(configuration: Game): Boolean {
        return configuration.hands.all { it.red <= 12 } &&
                configuration.hands.all { it.green <=13 } &&
                configuration.hands.all { it.blue <=14 }
    }
    private fun toGame(line: String): Game{
        val parts = line.split(":")
        val gameId = parts[0].replace("Game ","").toInt()
        val hands = parts[1].split(";").map {
            val bagStrings = it.split(",")
            Hand(
                    red = findCubesOfColor("red",bagStrings),
                    blue = findCubesOfColor("blue", bagStrings),
                    green = findCubesOfColor("green", bagStrings)
            )
        }
        return Game(
                gameId = gameId,
                hands = hands
        )
    }
    private fun findCubesOfColor(color: String, hands: List<String>): Int{
        val cubes = hands.find { it.contains(color) } ?: return 0
        return cubes.replace(color,"").trim().toInt()
    }

    data class Game(
            val gameId: Int,
            val hands: List<Hand>
    )

    data class Hand(
        val red: Int,
        val blue: Int,
        val green: Int
    )

    private fun loadLines(): List<String>{
        val fileName = "src/day2/input.txt"
        val file = File(fileName)
        return file.readLines()
    }

}