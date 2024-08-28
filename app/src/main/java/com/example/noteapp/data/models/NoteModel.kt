package com.example.noteapp.data.models

import android.icu.text.CaseMap.Title
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "noteModel")

data class NoteModel(
    val title: String,
    val description: String,
    @ColumnInfo(name = "color")
    val color: String,
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
