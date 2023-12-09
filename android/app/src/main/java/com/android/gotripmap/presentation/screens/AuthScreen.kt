package com.android.gotripmap.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import com.android.gotripmap.ui.theme.gradient

@Composable
fun AuthScreen() {
  Column(modifier= Modifier
    .fillMaxSize()
    .background(gradient)) {
    Column(modifier=Modifier.padding(10.dp,0.dp)) {
      Box(modifier= Modifier
        .fillMaxWidth()
        .aspectRatio(1.25f), contentAlignment = Alignment.Center) {
        Column {
          Image(
            painterResource(R.drawable.app_icon),
            contentDescription = "App icon",
            Modifier.size(60.dp)
          )
          Text(text= stringResource(R.string.app_name))
        }
      }
    }
  }
}
