package com.hnl.ytplayer.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.hnl.ytplayer.data.entity.Favorite;
import java.util.List;

/**
 * Data Access Object for Favorite videos
 */
@Dao
public interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("DELETE FROM favorites")
    void deleteAll();

    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE videoId = :videoId LIMIT 1")
    Favorite getByVideoId(String videoId);

    @Query("SELECT * FROM favorites WHERE title LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    LiveData<List<Favorite>> searchFavorites(String query);

    @Query("SELECT COUNT(*) FROM favorites WHERE videoId = :videoId")
    int isFavorite(String videoId);
}
