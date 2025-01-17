package day6

import util.Bounds
import util.Direction
import util.Grid
import util.Position

data class Guard(
    var position: Position,
    var direction: Direction,
    val visitedPositionsSet: HashSet<Pair<Position, Direction>>
) {
    private val nextPosition: Position get() = position + direction.offset

    private fun canMoveForward(map: Map): Boolean = !map.isObstructed(nextPosition)

    private fun moveForward() {
        val positionToAdd = position to direction
        this.visitedPositionsSet.add(positionToAdd)

        this.position = nextPosition
    }

    private fun turnRight() {
        this.direction = this.direction.turnRight()
    }

    fun move(map: Map) {
        if (this.canMoveForward(map)) {
            this.moveForward()
        } else {
            this.turnRight()
        }
    }

    fun makeCopy() = Guard(
        position,
        direction,
        HashSet<Pair<Position, Direction>>(10_000)
            .also { it.addAll(visitedPositionsSet) }
    )
}

data class Map(
    private val obstructions: Set<Position>,
    val bounds: Bounds
) {
    fun isInBounds(position: Position): Boolean {
        return position.x in 0 until bounds.width && position.y in 0 until bounds.height
    }

    fun isObstructed(position: Position): Boolean {
        return obstructions.contains(position)
    }

    fun withObstruction(position: Position): Map {
        return Map(obstructions + position, bounds)
    }

    fun toString(guard: Guard, manualObstructions: List<Position> = emptyList()): String {
        val stringBuilder = StringBuilder()

        for (y in bounds.height - 1 downTo 0) {
            for (x in 0 until bounds.width) {
                val position = Position(x, y)
                when {
                    position == guard.position -> stringBuilder.append(guard.direction.toChar())
                    manualObstructions.contains(position) -> stringBuilder.append('O')
                    obstructions.contains(position) -> stringBuilder.append('#')
                    else -> stringBuilder.append('.')
                }
            }
            stringBuilder.append('\n')
        }

        return stringBuilder.toString()
    }
}

fun Guard.moveUntilOutOfBounds(map: Map): Guard {
    while (map.isInBounds(this.position)) {
        this.move(map)
    }
    return this
}

fun Guard.isRunningInLoop(map: Map): Boolean {
    val isInLoop: () -> Boolean = {
        this.visitedPositionsSet.contains(this.position to this.direction)
    }

    while (!isInLoop()) {
        this.move(map)

        if (!map.isInBounds(this.position)) {
            return false
        }
    }
    return true
}

fun findDistinctVisitedPositions(map: Map, guard: Guard): Int {
    return guard.moveUntilOutOfBounds(map).visitedPositionsSet.map { it.first }.toSet().size
}

fun findManualObstructionsThatCauseLoop(map: Map, guard: Guard): Int {
    val positionsToCheck = guard.makeCopy().moveUntilOutOfBounds(map).visitedPositionsSet.map { it.first }.toSet()

    return positionsToCheck.parallelStream()
        .filter { position ->
            when {
                map.isObstructed(position) || position == guard.position -> false
                else -> guard.makeCopy().isRunningInLoop(map.withObstruction(position))
            }
        }.count().toInt()
}


fun parseMap(input: String): Pair<Map, Guard> {
    val obstructions = mutableSetOf<Position>()
    var guardPosition: Position? = null
    var guardDirection: Direction? = null

    val grid = Grid.fromString(input.lines().reversed().joinToString("\n"))
    grid.positions.forEach { position ->
        when (grid.getAtPosition(position)) {
            '#' -> obstructions.add(position)
            '^' -> {
                guardPosition = position
                guardDirection = Direction.UP
            }

            '<' -> {
                guardPosition = position
                guardDirection = Direction.LEFT
            }

            '>' -> {
                guardPosition = position
                guardDirection = Direction.RIGHT
            }

            'v' -> {
                guardPosition = position
                guardDirection = Direction.DOWN
            }
        }
    }

    val map = Map(obstructions, Bounds(grid.width, grid.height))
    val guard = Guard(guardPosition!!, guardDirection!!, HashSet(10_000))

    return map to guard
}
