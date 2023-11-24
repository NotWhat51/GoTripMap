package com.android.gotripmap.domain.usecases

import com.android.gotripmap.domain.entities.Route
import com.android.gotripmap.domain.repositories.RoutesRepository

class AddRouteUseCase(private val repository: RoutesRepository) {
  suspend operator fun invoke(route: Route) = repository.insertRoute(route)
}
