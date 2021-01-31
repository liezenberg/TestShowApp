package com.example.testshowapp.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.testshowapp.models.Show

@Dao
public interface FavoritesDao {
    //Insert item
    @Insert(onConflict = REPLACE)
    fun insert(favorites: Show)
    //Delete item
    @Delete
    fun delete(favorite: Show)
    //Delete all items
    @Query("DELETE FROM FavoritesEntity")
    fun reset()
    //Get all items
    @Query("SELECT * FROM FavoritesEntity")
    fun getAll(): List<Show>
    //Update item
    @Query("UPDATE FavoritesEntity SET isFavorite=:isFavorite WHERE ID = :sID")
    fun update(sID:Int?, isFavorite:Boolean)
    //Get only favorites
    //TODO: Make better query
    @Query("SELECT * FROM FavoritesEntity WHERE isFavorite = 1")
    fun getFavorites(): List<Show>
}