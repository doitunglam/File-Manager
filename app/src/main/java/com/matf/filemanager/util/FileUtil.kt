package com.matf.filemanager.util

import com.matf.filemanager.R
import java.io.File

private val textExtensionRegex = Regex("^(txt|css|js|c|h|cpp|hpp|py|java|pl)$", RegexOption.IGNORE_CASE)
private val imageExtensionRegex = Regex("^(jpg|jpeg|png|bmp)$", RegexOption.IGNORE_CASE)
private val videoExtensionRegex = Regex("^(mp4|mkv|webm|flv|ogg|avi|gif|mov)$", RegexOption.IGNORE_CASE)
private val audioExtensionRegex = Regex("^(mp3|wav|3gp|aac|flac)$", RegexOption.IGNORE_CASE)
private val zipExtensionRegex = Regex("^(zip|rar|7z|iso)$", RegexOption.IGNORE_CASE)
private val pdfExtensionRegex = Regex("^pdf$", RegexOption.IGNORE_CASE)
private val htmlExtensionRegex = Regex("^(html|htm)$", RegexOption.IGNORE_CASE)

/**
 * Returns the file type for the given extension
 *
 * @param extension File extension
 * @return File type for given extension
 */
fun getTypeFromExtension(extension: String): FileTypes {
    return when {
        extension.matches(textExtensionRegex) -> FileTypes.TEXT
        extension.matches(imageExtensionRegex) -> FileTypes.IMAGE
        extension.matches(videoExtensionRegex) -> FileTypes.VIDEO
        extension.matches(audioExtensionRegex) -> FileTypes.AUDIO
        extension.matches(zipExtensionRegex) -> FileTypes.ZIP
        extension.matches(pdfExtensionRegex) -> FileTypes.PDF
        extension.matches(htmlExtensionRegex) -> FileTypes.HTML
        else -> FileTypes.UNKNOWN
    }
}

/**
 * Returns the icon for the given file
 *
 * @param file File to find the icon for
 * @return Resource ID for the file's icon
 */
fun getIconForFile(file: File): Int {
    return if(file.isDirectory) {
        R.drawable.folder_empty
    } else {
        when(getTypeFromExtension(file.extension)) {
            FileTypes.IMAGE -> R.drawable.file_image
            FileTypes.AUDIO -> R.drawable.file_audio
            FileTypes.VIDEO -> R.drawable.file_video
            FileTypes.HTML -> R.drawable.file_html
            FileTypes.PDF -> R.drawable.file_pdf
            FileTypes.ZIP -> R.drawable.file_zip
            else -> R.drawable.file_text
        }
    }
}

private val sizeUnits: Array<String> = arrayOf("B", "KB", "MB", "GB")

/**
 * Returns the file's size as a string
 *
 * @param file File to find the size of
 * @return Size string of the given file
 */
fun getSizeString(file: File): String {
    var size = file.length().toFloat()
    var unit = 0
    for(i in 1 until sizeUnits.size) {
        if(size<1024)
            break
        size /= 1024
        unit++
    }
    if(unit==0)
        return "${size.toInt()}${sizeUnits[unit]}"
    return "%.2f${sizeUnits[unit]}".format(size)
}