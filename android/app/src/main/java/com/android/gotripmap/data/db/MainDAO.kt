package com.android.gotripmap.data.db

import android.app.appsearch.SearchResult
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

//Что мы должны хранить в базе маршрутов? Все маршруты, которые когда-либо были получены, или только избранные(+текущие)?
@Dao
interface MainDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRoute(route: RouteDbModel)
  //Получение списка избранных маршрутов
  @Query("SELECT * FROM routedbmodel WHERE liked=1")
  suspend fun getLikedRoutes(): Flow<List<RouteDbModel>>

  //Есть ли функция удаления маршрута? А очистка истории?
  @Query("UPDATE routedbmodel SET liked=not liked WHERE id=:id")
  suspend fun changeLiked(id: Int)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertEntry(route: SearchResult)

  @Query("SELECT * FROM searchentrydbmodel")
  suspend fun getAllSearchEntries(): Flow<List<SearchEntryDbModel>>

  @Transaction
  @Query("SELECT * FROM searchentrydbmodel WHERE id=:id")
  suspend fun getSearchEntryData(id: Int): RoutesAndEntryRelation

}
