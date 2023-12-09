package com.android.gotripmap.presentation.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.gotripmap.R
import com.android.gotripmap.presentation.elements.FAQ
import com.android.gotripmap.presentation.elements.ProfilePhoto
import com.android.gotripmap.presentation.elements.ProfileSettingElement
import com.android.gotripmap.presentation.elements.ReceiveEmails
import com.android.gotripmap.presentation.viewmodels.EditProfileVM
import com.android.gotripmap.ui.theme.AppTheme
import com.android.gotripmap.ui.theme.gradient
import org.koin.androidx.compose.getViewModel

/**
 * Экран редактирования профиля пользователя
 */
@Composable
fun ProfileScreen(navHostController: NavHostController) {
  val viewModel = getViewModel<EditProfileVM>()
  val profile = viewModel.profile.collectAsState()
  val editing = rememberSaveable {
    mutableStateOf(false)
  }


  val context = LocalContext.current
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent(),
    onResult = { uri: Uri? ->
      uri?.let { saveImageToInternalStorage(context, it) }
      viewModel.updatePhoto("${context.filesDir.path}/$IMAGE_PATH") //Хардкод
    }
  )

  Box(Modifier.background(gradient)) {
    AnimatedVisibility(visible = !editing.value, modifier = Modifier.align(Alignment.TopEnd)) {
      Image(
        modifier = Modifier
          .padding(0.dp, 13.dp, 8.dp, 0.dp)
          .clickable { editing.value = true },
        painter = painterResource(R.drawable.tabler_settings),
        contentDescription = "Setup profile"
      )
    }
    Column(
      Modifier
        .padding(9.dp, 49.dp, 9.dp, 0.dp)
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      ProfilePhoto(context, profile.value.photo, editing.value) {
        launcher.launch("image/*")
      }
      Text(
        text =profile.value.username.text,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 17.dp),
        style = MaterialTheme.typography.titleMedium
      )
      ProfileSettingElement(editing.value, stringResource(R.string.nickname), profile.value.username) {
        viewModel.updateUserName(it)
      }
      ProfileSettingElement(editing.value, stringResource(R.string.phone), profile.value.phone) {
        viewModel.updatePhone(it)

      }
      ProfileSettingElement(editing.value, stringResource(R.string.email), profile.value.email) {
        viewModel.updateEmail(it)
      }
      ReceiveEmails(profile.value.receiveEmails,editing.value) {
        viewModel.changeEmailReceive(it)
      }
      AnimatedVisibility(visible = editing.value, Modifier.align(Alignment.End)) {

        ElevatedButton(
          onClick = {
            Log.w("profile",profile.value.toString())
            viewModel.update(profile.value)
            editing.value=false
          },
          colors = ButtonDefaults.buttonColors(containerColor = AppTheme, contentColor = Color.Black)
        ) {
          Text(stringResource(R.string.save), style = MaterialTheme.typography.labelLarge)
        }
      }
      AnimatedVisibility(visible = !editing.value,Modifier.align(Alignment.Start)) {
        Box(
          Modifier
            .fillMaxWidth()
            .weight(1f)
          , contentAlignment = Alignment.CenterStart
        ) {
          FAQ()
        }
      }
    }
  }
}

fun saveImageToInternalStorage(context: Context, uri: Uri) {
  val inputStream = context.contentResolver.openInputStream(uri)
  val outputStream = context.openFileOutput(IMAGE_PATH, Context.MODE_PRIVATE)
  inputStream?.use { input ->
    outputStream.use { output ->
      input.copyTo(output)
    }
  }
}


fun getDataDir(context: Context): String? {
  return context.packageManager
    .getPackageInfo(context.packageName, 0).applicationInfo.dataDir
}

const val IMAGE_PATH = "image.jpg"
