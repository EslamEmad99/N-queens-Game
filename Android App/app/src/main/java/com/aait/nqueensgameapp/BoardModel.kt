package com.aait.nqueensgameapp

data class BoardModel(
    var hasQueen: Boolean = false,
    var isError: Boolean = false,
    var position: Point =  Point(0, 0),
    var zonePoints: ArrayList<Point> = ArrayList()
)
