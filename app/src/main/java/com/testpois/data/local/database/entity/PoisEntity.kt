package com.testpois.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity
data class PoisEntity(
    @PrimaryKey
    @NotNull @ColumnInfo(name = "id") val id: Int,
    @NotNull @ColumnInfo(name = "title") val title: String,
    @NotNull @ColumnInfo(name = "geocoordinates") val geocoordinates: String,
    @NotNull @ColumnInfo(name = "image") val image: String
)
