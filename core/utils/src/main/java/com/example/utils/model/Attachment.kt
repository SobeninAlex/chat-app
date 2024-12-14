package com.example.utils.model

import android.net.Uri
import android.os.Parcelable
import com.example.resourse.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attachment(
    val id: Long = (0..10_000_000_000).random(),
    val name: String,
    val uri: Uri? = null,
    val type: AttachType,
    val isUploading: Boolean = false,
    val remoteUrl: String? = null,
    val thumbnail: String? = null,
    val durationVideo: String? = null,
) : Parcelable {

    val icon: Int get() = when (type) {
        AttachType.IMAGE -> {
            R.drawable.ic_attach
        }
        AttachType.VIDEO -> {
            R.drawable.preview_video
        }
        AttachType.FILE -> {
            when {
                name.endsWith(".pdf") -> R.drawable.ic_pdf_square
                name.endsWith(".xls") || name.endsWith(".xlsx") -> R.drawable.ic_xls_square
                name.endsWith(".doc") || name.endsWith(".docx") -> R.drawable.ic_doc_square
                name.endsWith(".zip") || name.endsWith(".rar") -> R.drawable.ic_zip_square
                else -> R.drawable.ic_file_square
            }
        }
    }

    companion object {
        val DEFAULT = Attachment(
            name = "",
            uri = Uri.EMPTY,
            type = AttachType.IMAGE
        )
    }
}

enum class AttachType {
    IMAGE,
    VIDEO,
    FILE;

    companion object {
        fun getType(str: String): AttachType {
            return when (str) {
                "application" -> FILE
                "image" -> IMAGE
                "video" -> VIDEO
                else -> throw RuntimeException("unknown type attachment")
            }
        }
    }
}
