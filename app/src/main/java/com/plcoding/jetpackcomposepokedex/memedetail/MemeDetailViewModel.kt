package com.plcoding.jetpackcomposepokedex.memedetail

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.plcoding.jetpackcomposepokedex.repository.MemeRepository
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class MemeDetailViewModel @Inject constructor(
    private val repository: MemeRepository
) : ViewModel() {

    private val _textList: MutableState<MutableList<String>> = mutableStateOf(
        mutableListOf("", "", "", "", "", "")
    )
    val textList: MutableState<MutableList<String>> = _textList

    private val _memeTextList: MutableState<MutableList<String>> = mutableStateOf(
        mutableListOf("", "", "", "", "", "")
    )
    val memeTextList: MutableState<MutableList<String>> = _memeTextList

    private val _memeInfoState: MutableState<MemeInfoState> = mutableStateOf(
        MemeInfoState()
    )
    val memeInfoState: MutableState<MemeInfoState> = _memeInfoState

    private val _saveState: MutableState<SaveState> = mutableStateOf(SaveState())
    val saveState: MutableState<SaveState> = _saveState

    val isTyping: MutableState<Boolean> = mutableStateOf(false)
    val alertDialogVisible: MutableState<Boolean> = mutableStateOf(false)

    private var isPermissionsGranted = false

    fun getMemeInfo(textList: MutableList<String>, id: String) {
        val newList = mutableListOf<String>()
        newList.addAll(textList)
        for (i in 0 until newList.size) {
            if (newList[i] == "") newList[i] = " "
        }
        repository.getMemeInfo(newList, id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _memeInfoState.value = MemeInfoState(data = result.data)
                }
                is Resource.Error -> {
                    _memeInfoState.value =
                        MemeInfoState(error = result.message ?: "Unexpected Error")
                }
                is Resource.Loading -> {
                    _memeInfoState.value = MemeInfoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setText(text: String, textNumber: Int) {
        _textList.value[textNumber] = text
    }

    fun setTextList(boxCount: Int) {
        var list = mutableStateListOf<String>()
        for (i in 0 until boxCount) {
            list.add(i, "")
        }
        _textList.value = list
    }

    fun setMemeTextList(boxCount: Int) {
        var list = mutableStateListOf<String>()
        for (i in 0 until boxCount) {
            list.add(i, "Text ${i + 1}")
        }
        _memeTextList.value = list
    }

    fun onSaveDialogOpen() {
        alertDialogVisible.value = true
    }

    fun onSaveDialogDismiss() {
        alertDialogVisible.value = false
    }

    @ExperimentalPermissionsApi
    suspend fun onSave(permissionsState: MultiplePermissionsState, context: Context, url: String?) {
        updateOrCheckPermissions(permissionsState)
        if(isPermissionsGranted) {
            val bitmap = getBitmap(url, context)
            saveBitmapAsImageToDevice(bitmap, context)
        }
    }

    @ExperimentalPermissionsApi
    fun updateOrCheckPermissions(permissionsState: MultiplePermissionsState) {
        if (permissionsState.permissions.all {
                it.hasPermission
            }) {
            isPermissionsGranted = true
        }
        permissionsState.launchMultiplePermissionRequest()
    }

    private fun saveBitmapAsImageToDevice(bitmap: Bitmap?, context: Context) {
        // Add a specific media item.
        val resolver = context.contentResolver

        val imageStorageAddress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "my_app_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis())
        }

        try {
            // Save the image.
            val contentUri: Uri? = resolver.insert(imageStorageAddress, imageDetails)
            contentUri?.let { uri ->
                // Don't leave an orphan entry in the MediaStore
                if (bitmap == null) resolver.delete(contentUri, null, null)
                val outputStream: OutputStream? = resolver.openOutputStream(uri)
                outputStream?.let { outStream ->
                    val isBitmapCompressed =
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 95, outStream)
                    if (isBitmapCompressed == true) {
                        outStream.flush()
                        outStream.close()
                    }
                } ?: throw IOException("Failed to get output stream.")
            } ?: throw IOException("Failed to create new MediaStore record.")
        } catch (e: IOException) {
            throw e
        }
    }

    private suspend fun getBitmap(url: String?, context: Context): Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}

