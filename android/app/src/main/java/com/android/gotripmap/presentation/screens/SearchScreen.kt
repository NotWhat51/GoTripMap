package com.android.gotripmap.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.gotripmap.R
import com.android.gotripmap.domain.entities.Transport
import com.android.gotripmap.presentation.elements.ImageByTransport
import com.android.gotripmap.presentation.elements.RouteElement
import com.android.gotripmap.presentation.viewmodels.SearchScreenVM
import com.android.gotripmap.ui.theme.gradient
import org.koin.androidx.compose.getViewModel

/**
 * Экран для отображения результатов текущего запроса
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(navHostController: NavHostController) {

  val viewModel = getViewModel<SearchScreenVM>()
  val currentEntry by viewModel.currentEntry.collectAsState()
  val routes by viewModel.routes.collectAsState()

  Column(
    Modifier
      .fillMaxWidth()
      .background(gradient)
  ) {
    Column(Modifier.padding(10.dp,17.dp,10.dp,0.dp)) {
      val searchState = remember {
        mutableStateOf(TextFieldValue(currentEntry?.entry?:""))
      }
      SearchView(searchState, stringResource(id = R.string.search_placeholder))
      Row(Modifier.padding(0.dp, 0.dp, 0.dp, 13.dp)) {
        Image(
          painterResource(R.drawable.mdi_map_marker_outline),
          contentDescription = "geotag",
          contentScale = ContentScale.FillBounds,
          modifier = Modifier
            .size(Dp(20f), Dp(20f))
        )
        Text(
          text = "Whereabouts",
          color = Color.Black,
          style = MaterialTheme.typography.bodyMedium,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        ) //Есть баг с длинным текстом, исправить
        Spacer(modifier = Modifier.weight(1f))
        ImageByTransport(currentEntry?.transport?:Transport.WALKING)
      }
      LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(
          items = routes?: listOf(),
          key = { _, item -> item.id },
        ) { _, item ->
          RouteElement(route = item, modifier = Modifier.animateItemPlacement()) { id ->
            viewModel.changeLiked(id)
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
  state: MutableState<TextFieldValue>, placeholder: String
) {
  TextField(
    value = state.value,
    onValueChange = { value: TextFieldValue ->
      state.value = value
    },
    Modifier
      .fillMaxWidth()
      .padding(0.dp,0.dp,0.dp,18.dp)
      .height(30.dp)
      .clip(RoundedCornerShape(30.dp))
      .border(2.dp, Color.DarkGray, RoundedCornerShape(30.dp)),
    placeholder =
    { Text(text = placeholder) },
    trailingIcon = {
      Icon(
        painter = painterResource(id = R.drawable.material_symbols_search),
        contentDescription = "search icon"
      )
    },
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.White
    ),
    maxLines = 1,
    singleLine = true,
    textStyle = MaterialTheme.typography.bodyMedium,
  )
}
