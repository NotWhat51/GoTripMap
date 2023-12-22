package com.android.gotripmap.presentation.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.gotripmap.R

@Composable
fun FAQ() {
  Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
    Image(
      painter = painterResource(R.drawable.ph_question),
      contentDescription = "Question mark",
      Modifier.size(40.dp)
    )
    Text(
      text= stringResource(R.string.frequently_asked_questions),
      Modifier.padding(15.dp,0.dp)
    )
  }
} //доделать экран
