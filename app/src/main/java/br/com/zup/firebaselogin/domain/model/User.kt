package br.com.zup.firebaselogin.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var email: String = "",
    var password: String = ""
): Parcelable