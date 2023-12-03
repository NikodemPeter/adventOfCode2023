package day3

import org.testng.annotations.Test
import java.io.File

@Test
class GearRatios {


    fun retrieveNumbers() {
        val lines = loadLines()
        val sumOfParts = lines.mapIndexed { index, line ->
            numbersWithPositions(index, line)
        }.flatten()
                .filter { isPartNumber(it, lines) }
                .sumOf { it.number }



        println(sumOfParts)
    }

    fun sumGearRatios(){
        val lines = loadLines()
        val parts = lines.mapIndexed { index, line ->
            numbersWithPositions(index, line)
        }.flatten()
                .filter { isPartNumber(it, lines) }
        val gearRatiosSum = lines.mapIndexed{ row, line ->
            line.mapIndexed { column, symbol ->
                if (symbol == '*'){
                    val adjacentParts = findAdjacentParts(Position(row, column ),parts)
                    if (adjacentParts.size == 2) {
                        adjacentParts[0].number * adjacentParts[1].number
                    } else {
                        0
                    }
                } else {
                    0
                }
            }
        }.flatten().sum()
        println(gearRatiosSum)
    }


    fun findAdjacentParts(position: Position, parts : List<Number>):List<Number> {
        return parts.filter { it.getNumberedPositions().any{ it in position.getAdjacentPositions()}}
    }

    fun isPartNumber(number: Number, lines: List<String>): Boolean {
        return number.getNumberedPositions().any { isAdjacentToSymbol(it, lines) }
    }

    fun isAdjacentToSymbol(position: Position, lines: List<String>): Boolean {
        return position.getAdjacentPositions().any { isSymbol(it,lines) }
    }

    fun isSymbol(position: Position, lines: List<String>): Boolean {
        if (position.row >=0 && position.row < lines.size){
            val line =lines.get(position.row)
            if (position.column >=0 && position.column < line.length){
                val character = line.get(position.column)
                return if (character in '0'..'9' || character == '.'){
                    false
                } else {
                    true
                }
            }
        }
        return false
    }
    fun numbersWithPositions(rowIndex: Int, line: String): List<Number> {
        val lineNumbers = line.split("\\D+".toRegex()).filter { it.isNotEmpty() }
        var updatedLine = line
        val numbersWithPositions: List<Number> = lineNumbers.map {
            val columnIndex = updatedLine.indexOf(it)
            updatedLine = updatedLine.replaceFirst(it, "x".repeat(it.length))
            Number(
                    number = it.toInt(),
                    position = Position(
                            row = rowIndex,
                            column = columnIndex
                    )

            )
        }
        return numbersWithPositions
    }

}

data class Number(
        val number: Int,
        val position: Position
){
    fun getNumberedPositions(): List<Position>{
        return (0..<number.toString().length).map { Position(row = position.row, column = position.column + it) }
    }
}


data class Position(
        val row: Int,
        val column: Int
) {
    fun getAdjacentPositions(): List<Position>{
        return listOf(Position(row-1,column-1),Position(row-1, column), Position(row-1,column+1),
                Position(row, column- 1),Position(row, column+1),
                Position(row+1, column -1), Position(row+1, column), Position(row+1, column+1)
        )
    }
}



private fun loadLines(): List<String> {
    val fileName = "src/day3/input.txt"
    val file = File(fileName)
    return file.readLines()
}
