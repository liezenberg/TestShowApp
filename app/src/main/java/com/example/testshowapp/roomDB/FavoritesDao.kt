package com.example.testshowapp.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.testshowapp.models.Show

@Dao
public interface FavoritesDao {
    //Insert item to table
    @Insert(onConflict = REPLACE)
    fun insert(favorites: Show)

    //Delete item from table
    @Delete
    fun delete(favorite: Show)

    //Delete all items
    @Query("DELETE FROM FavoritesEntity")
    fun reset()

    //Get all items from table
    @Query("SELECT * FROM FavoritesEntity")
    fun getAll(): List<Show>

    //Update selected item
    @Query("UPDATE FavoritesEntity SET isFavorite=:isFavorite WHERE ID = :sID")
    fun update(sID: Int?, isFavorite: Boolean)

    //Get only favorites from table
    @Query("SELECT * FROM FavoritesEntity WHERE isFavorite = 1")
    fun getFavorites(): List<Show>
}