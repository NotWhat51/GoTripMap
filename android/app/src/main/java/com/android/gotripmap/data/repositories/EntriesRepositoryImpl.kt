package com.android.gotripmap.data.repositories

import com.android.gotripmap.data.db.MainDAO
import com.android.gotripmap.data.db.SearchEntryDbModel
import com.android.gotripmap.data.mappers.RoutesAndEntriesMapper
import com.android.gotripmap.data.mappers.SearchEntryMapper
import com.android.gotripmap.domain.entities.CurrentEntryRoutes
import com.android.gotripmap.domain.entities.Route
import com.android.gotripmap.domain.entities.SearchEntry
import com.android.gotripmap.domain.entities.Transport
import com.android.gotripmap.domain.repositories.EntriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.coroutines.coroutineContext

/**
 * Реализация репозитория запросов
 */
class EntriesRepositoryImpl(
  private val mainDAO: MainDAO,
  private val entriesMapper: SearchEntryMapper,
  private val routesAndEntriesMapper: RoutesAndEntriesMapper
) : EntriesRepository {

  init {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
      mainDAO.insertEntry(SearchEntryDbModel(entry = "blablaggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggf", dateTime = 11111111, transport = Transport.WALKING))
      mainDAO.insertEntry(SearchEntryDbModel(entry = "blabla2", dateTime = 1111123323, transport = Transport.WALKING))
    }
  }
  /**
   * История запросов
   */
  override val entries: Flow<List<SearchEntry>>
    get() = mainDAO.getAllSearchEntries().map { entriesMapper.mapDbListToDtList(it) }

  /**
   * Данные для отображения на главном экране
   */
  override val currentSearchData: Flow<CurrentEntryRoutes?>
    get() = mainDAO.getCurrentRoutes().map { if (it.size>0) routesAndEntriesMapper.mapDbToDtModel(it[0]) else null }

  override suspend fun insertEntry(entry: String, dateTime: LocalDateTime, current: Boolean, transport: Transport) {
    mainDAO.insertEntry(
      SearchEntryDbModel(
        entry = entry,
        dateTime = dateTime.toEpochSecond(ZoneOffset.of(ZoneId.systemDefault().id)),
        transport = transport
      )
    )
  }

  /**
   * Функция для отображения данного запроса на главном экране
   */

  override suspend fun makeEntryCurrent(entryId: Int) {
    mainDAO.makeEntryCurrent(entryId)
  }

  override suspend fun clearHistory() {
    mainDAO.clearHistory()
  }
}
