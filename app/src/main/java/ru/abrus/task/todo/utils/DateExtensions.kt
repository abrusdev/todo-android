package ru.abrus.task.todo.utils

fun getWeekName(weekOfDay: Int): String{
    return when (weekOfDay){
        1 -> "Sunday"
        2 -> "Monday"
        3 -> "Tuesday"
        4 -> "Wednesday"
        5 -> "Thursday"
        6 -> "Friday"
        7 -> "Saturday"
        else -> "Sunday"
    }
}