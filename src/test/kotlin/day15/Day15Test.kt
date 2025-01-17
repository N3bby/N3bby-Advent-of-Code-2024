package day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInput

class Day15KtTest {

    @Test
    fun `part 1 - small example input`() {
        val input = """
            ########
            #..O.O.#
            ##@.O..#
            #...O..#
            #.#.O..#
            #...O..#
            #......#
            ########

            <^^>>>vv<v>>v<<
        """.trimIndent()

        val (warehouse, movements) = parseWarehouseAndMovements(input)

        val gpsCoordinatesSum = warehouse
            .executeMovements(movements, debug = true)
            .getGpsCoordinates()
            .sum()

        assertThat(gpsCoordinatesSum).isEqualTo(2028)
    }

    @Test
    fun `part 1 - example input`() {
        val input = """
            ##########
            #..O..O.O#
            #......O.#
            #.OO..O.O#
            #..O@..O.#
            #O#..O...#
            #O..O..O.#
            #.OO.O.OO#
            #....O...#
            ##########
            
            <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
            vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
            ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
            <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
            ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
            ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
            >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
            <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
            ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
            v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
        """.trimIndent()

        val (warehouse, movements) = parseWarehouseAndMovements(input)

        val gpsCoordinatesSum = warehouse
            .executeMovements(movements)
            .getGpsCoordinates()
            .sum()

        assertThat(gpsCoordinatesSum).isEqualTo(10092)
    }

    @Test
    fun `part 1 - gps coordinate calculation`() {
        val warehouse = parseWarehouse(
            """
            #######
            #...O..
            #.....@
            #......
        """.trimIndent(),
            false
        )

        assertThat(warehouse.getGpsCoordinates().sum()).isEqualTo(104)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(15)

        val (warehouse, movements) = parseWarehouseAndMovements(input)

        val gpsCoordinatesSum = warehouse
            .executeMovements(movements)
            .getGpsCoordinates()
            .sum()

        assertThat(gpsCoordinatesSum).isEqualTo(1478649)
    }

    @Test
    @Disabled("This test has no assertion; it is used to manually check the movements for part 2")
    fun `part 2 - small example input`() {
        val input = """
            #######
            #...#.#
            #.....#
            #..OO@#
            #..O..#
            #.....#
            #######
            
            <vv<<^^<<^^
        """.trimIndent()

        val (warehouse, movements) = parseWarehouseAndMovements(input, expandWarehouse = true)

        warehouse.executeMovements(movements, debug = true)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            ##########
            #..O..O.O#
            #......O.#
            #.OO..O.O#
            #..O@..O.#
            #O#..O...#
            #O..O..O.#
            #.OO.O.OO#
            #....O...#
            ##########
            
            <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
            vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
            ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
            <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
            ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
            ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
            >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
            <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
            ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
            v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
        """.trimIndent()

        val (warehouse, movements) = parseWarehouseAndMovements(input, expandWarehouse = true)

        val gpsCoordinatesSum = warehouse
            .executeMovements(movements, debug = true)
            .getGpsCoordinates()
            .sum()

        assertThat(gpsCoordinatesSum).isEqualTo(9021)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(15)

        val (warehouse, movements) = parseWarehouseAndMovements(input, expandWarehouse = true)

        val gpsCoordinatesSum = warehouse
            .executeMovements(movements)
            .getGpsCoordinates()
            .sum()

        assertThat(gpsCoordinatesSum).isEqualTo(1495455)
    }
}

