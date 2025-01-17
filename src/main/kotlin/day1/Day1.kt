package day1

import kotlin.math.abs

typealias LocationId = Int

fun parseLocationIds(input: String): Pair<List<LocationId>, List<LocationId>> {
    return input.lines()
        .map { it.split(Regex("""\s+""")) }
        .map { it[0].toInt() to it[1].toInt() }
        .unzip()
}

fun getTotalDistanceBetweenLists(list1: List<LocationId>, list2: List<LocationId>): Int {
    return (list1.sorted() zip list2.sorted())
        .sumOf { abs(it.first - it.second) }
}

fun getSimilarityScore(list1: List<LocationId>, list2: List<LocationId>): Int {
    return list1.sumOf { locationId -> locationId * list2.count { it == locationId } }
}

