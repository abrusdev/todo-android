package ru.abrus.task.todo.utils

object Constants {

    val alarmTexts = arrayListOf(
        "At time",
        "5 minutes before",
        "10 minutes before",
        "15 minutes before",
        "30 minutes before",
        "45 minutes before"
    )

    const val AT_TIME = 0
    const val BEFORE_5_MIN = 1
    const val BEFORE_10_MIN = 2
    const val BEFORE_15_MIN = 3
    const val BEFORE_30_MIN = 4
    const val BEFORE_45_MIN = 5

    fun getAlarmType(type: Int): String {
        return when (type) {
            BEFORE_5_MIN -> alarmTexts[1]
            BEFORE_10_MIN -> alarmTexts[2]
            BEFORE_15_MIN -> alarmTexts[3]
            BEFORE_30_MIN -> alarmTexts[4]
            BEFORE_45_MIN -> alarmTexts[5]
            else -> alarmTexts[0]
        }
    }

}