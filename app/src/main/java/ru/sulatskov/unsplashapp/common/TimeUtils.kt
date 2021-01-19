package ru.sulatskov.unsplashapp.common

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun getDate(context: Context, date: String): String {
            var resultDate = date
            try {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                val tempDate: Date = simpleDateFormat.parse(date)
                resultDate = getDateWithTimeFirst(context, tempDate.time)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return resultDate
        }

        private fun getDateWithTimeFirst(context: Context, seconds: Long): String {
            val date = Date(seconds * 1000L)
            val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            return DateUtils.formatDateTime(
                context, seconds,
                DateUtils.FORMAT_SHOW_YEAR
            ) + ", " + simpleTimeFormat.format(date)
        }
    }

}
