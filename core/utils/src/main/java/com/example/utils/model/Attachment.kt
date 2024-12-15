package com.example.utils.model

import android.net.Uri
import android.os.Parcelable
import com.example.resourse.R
import kotlinx.parcelize.Parcelize
import java.util.UUID

data class Attachment(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val type: AttachType = AttachType.IMAGE,
    val uri: Uri? = null,
    val isUploading: Boolean = false,
    val remoteUrl: String? = null,
    val thumbnail: String? = null,
    val durationVideo: String? = null,
) {

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
        fun getType(contentType: String): AttachType {
            return when (contentType) {
                "application" -> FILE
                "image" -> IMAGE
                "video" -> VIDEO
                else -> throw RuntimeException("unknown type attachment")
            }
        }
    }
}
