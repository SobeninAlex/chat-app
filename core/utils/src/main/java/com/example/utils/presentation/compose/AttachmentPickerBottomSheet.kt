package com.example.utils.presentation.compose

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.resourse.MainColor
import com.example.resourse.R
import com.example.resourse.body1_Reg16
import com.example.utils.model.AttachType
import com.example.utils.model.Attachment
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentPickerBottomSheet(
    onPick: ((attachments: List<Attachment>) -> Unit),
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    var cameraUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    var type = PickType.PICK_IMAGE_AND_VIDEO
    val authority = "com.example.chat_app.fileprovider"

    //лаунчер фотокамеры
    val cameraPictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                val name = getFileName(cameraUri, context)
                val attachment = Attachment(
                    name = name,
                    uri = cameraUri,
                    type = AttachType.IMAGE
                )
                onPick(listOf(attachment))
                onDismissRequest()
            }
        }
    )

    //лаунчер видео камеры
    val cameraVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if (success) {
                val name = getFileName(cameraUri, context)
                val attachment = Attachment(
                    name = name,
                    uri = cameraUri,
                    type = AttachType.VIDEO
                )
                onPick(listOf(attachment))
                onDismissRequest()
            }
        }
    )

    //лаунчер прикрепления документов
    val pickDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { listUri ->
            val documents = mutableListOf<Attachment>()
            listUri.forEach {
                val name = getFileName(it, context)
                val attachment = Attachment(
                    name = name,
                    uri = it,
                    type = AttachType.FILE
                )
                documents.add(attachment)
            }
            onPick(documents)
            onDismissRequest()
        }
    )

    //лаунчер прикрепления фото / видео
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { listUri ->
            val newList = mutableListOf<Attachment>()
            listUri.forEach {
                val name = getFileName(it, context)
                if (name.isVideo()) {
                    newList.add(
                        Attachment(
                            name = name,
                            uri = it,
                            type = AttachType.VIDEO
                        )
                    )
                } else {
                    newList.add(
                        Attachment(
                            name = name,
                            uri = it,
                            type = AttachType.IMAGE
                        )
                    )
                }
            }
            onPick(newList)
            onDismissRequest()
        }
    )

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val notGranted = permissions.filterNot { it.value }.map { it.key }
            if (notGranted.isEmpty()) {
                when (type) {
                    PickType.CAMERA_IMAGE -> {
                        val file = File.createTempFile(
                            "picture_${System.currentTimeMillis()}",
                            ".jpg",
                            context.externalCacheDir
                        )
                        cameraUri = FileProvider.getUriForFile(context, authority, file)
                        cameraPictureLauncher.launch(cameraUri)
                    }

                    PickType.CAMERA_VIDEO -> {
                        val file = File.createTempFile(
                            "movie_${System.currentTimeMillis()}",
                            ".mp4",
                            context.externalCacheDir
                        )
                        cameraUri = FileProvider.getUriForFile(context, authority, file)
                        cameraVideoLauncher.launch(cameraUri)
                    }

                    PickType.PICK_FILES -> {
                        pickDocumentLauncher.launch(
                            arrayOf(
                                "text/html",
                                "application/msword",
                                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                                "application/vnd.ms-excel",
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                                "application/vnd.oasis.opendocument.text",
                                "application/vnd.oasis.opendocument.graphics",
                                "text/plain",
                                "application/xml",
                                "application/pdf",
                                "audio/mpeg",
                                "text/csv",
                                "application/dbase",
                                "application/dbf",
                                "image/vnd.microsoft.icon",
                                "application/vnd.rar",
                                "application/zip",
                                "application/x-tar"
                            )
                        )
                    }

                    PickType.PICK_IMAGE_AND_VIDEO -> {
                        pickMediaLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                        )
                    }
                }
            }
        }
    )

    SimpleBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {
        Column {
            PickerButton(
                painter = painterResource(id = R.drawable.ic_attach_cam),
                text = stringResource(id = R.string.take_photo),
                onClick = {
                    type = PickType.CAMERA_IMAGE
                    activityResultLauncher.launch(cameraPermissions)
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            PickerButton(
                painter = painterResource(id = R.drawable.ic_attach_video_camera),
                text = stringResource(id = R.string.take_video),
                onClick = {
                    type = PickType.CAMERA_VIDEO
                    activityResultLauncher.launch(videoCameraPermissions)
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            PickerButton(
                painter = painterResource(id = R.drawable.ic_attach_gal),
                text = stringResource(id = R.string.pick_image),
                onClick = {
                    type = PickType.PICK_IMAGE_AND_VIDEO
                    activityResultLauncher.launch(galleryPermissions)
                }
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            PickerButton(
                painter = painterResource(id = R.drawable.ic_attach_storage),
                text = stringResource(id = R.string.local_storage),
                onClick = {
                    type = PickType.PICK_FILES
                    activityResultLauncher.launch(documentsPermissions)
                }
            )
        }
    }
}

@Composable
private fun PickerButton(
    painter: Painter,
    text: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = MainColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = body1_Reg16,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
    }
}

// Разрешения необходимые для работы камеры
private val cameraPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.CAMERA,
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

// Разрешения необходимые для работы видео камеры
private val videoCameraPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

// Разрешения необходимые для выбора изображений и видео в галерее
private val galleryPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

// Разрешения необходимые для выбора файлов
private val documentsPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

private fun String.isVideo(): Boolean {
    return endsWith(".mp4")
}

private enum class PickType {
    CAMERA_IMAGE, CAMERA_VIDEO, PICK_FILES, PICK_IMAGE_AND_VIDEO
}

@SuppressLint("Range")
private fun getFileName(uri: Uri, context: Context): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor: Cursor? =
            context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result =
                    cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    )
            }
        } finally {
            cursor?.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}