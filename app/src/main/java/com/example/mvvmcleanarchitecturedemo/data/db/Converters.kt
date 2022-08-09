package com.example.mvvmcleanarchitecturedemo.data.db

import androidx.room.TypeConverter
import com.example.mvvmcleanarchitecturedemo.data.model.Source
import java.util.*

class Converters {
    @TypeConverter
    fun fromSource(source: Source) = source.name

    @TypeConverter
    fun toSource(name: String) = Source(name, name)

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}