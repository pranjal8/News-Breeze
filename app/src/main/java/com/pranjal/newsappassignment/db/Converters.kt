package com.pranjal.newsappassignment.db

import androidx.room.TypeConverter
import com.pranjal.newsappassignment.models.Source

// This class is used to convert Source type to String and vice versa
class Converters {
    @TypeConverter
    fun fromSource(source : Source) : String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name : String) : Source{
        return Source(name, name )
    }

}