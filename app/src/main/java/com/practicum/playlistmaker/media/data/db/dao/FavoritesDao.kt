package com.practicum.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.media.data.db.entity.FavoritesEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(track: FavoritesEntity)

    @Delete
    fun deleteTracK(track: FavoritesEntity)

    @Query("SELECT * FROM favorites")
    fun getFavoriteTracks(): Flow<List<FavoritesEntity>>

    @Query("SELECT trackId FROM favorites")
    fun getFavoriteTracksIds(): Flow<List<String>>
}