package com.florm.mymovies.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "subscriptions")
data class SubscriptionEntity (
    @ColumnInfo(name = "serie_id") @NotNull val serieId: String,
    @ColumnInfo(name = "name") @NotNull val name: String,
    @ColumnInfo(name = "genre_id") @NotNull val genreId: String,
    @ColumnInfo(name = "overview") @NotNull val overview: String,
    @ColumnInfo(name = "first_air_date") @NotNull val firstAirDate: String,
    @ColumnInfo(name = "poster_path") @NotNull val posterPath: String
) {
    companion object {
        const val TABLE_NAME = "subscriptions"
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}