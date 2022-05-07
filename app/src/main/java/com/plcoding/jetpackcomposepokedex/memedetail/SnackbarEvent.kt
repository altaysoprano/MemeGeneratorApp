package com.plcoding.jetpackcomposepokedex.memedetail

sealed class SnackbarEvent {
    data class ShowPhotoSavedGallerySnackbar(val message: String): SnackbarEvent()
    data class ShowPermanentlyDeniedSnackbar(val message: String) : SnackbarEvent()
    data class SomeErrorWhenSavingSnackbar(val message: String) : SnackbarEvent()
}
