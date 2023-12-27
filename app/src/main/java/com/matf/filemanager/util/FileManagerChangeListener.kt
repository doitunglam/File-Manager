package com.matf.filemanager.util

import java.io.File

/**
 * Listener for events fired by FileManager
 * Used for refreshing the file list and performing operations on files
 */
interface FileManagerChangeListener {
    /**
     * Callback function which is called when the file list or clipboard changes
     */
    fun onEntriesChange()

    /**
     * Callback function which is called when the selection mode changes
     *
     * @param mode New selection mode
     */
    fun onSelectionModeChange(mode : MenuMode)

    /**
     * Callback function which is called when the clipboard mode changes
     *
     * @param mode New clipboard mode
     */
    fun onClipboardChange(mode : ClipboardMode)

    /**
     * Callback function which requests to open files with this app
     * If we don't support the type, then open it with a system app
     *
     * @param file File to open
     * @return Was file successfully opened
     */
    fun onRequestFileOpen(file: File): Boolean

    /**
     * Callback function which requests to open files with a system app
     *
     * @param file File to open
     * @return Was file successfully opened
     */
    fun onRequestFileOpenWith(file: File) : Boolean

    /**
     * Callback function which starts file copy
     *
     * @param targets List of files to copy
     * @param dest Destination for copying the targets
     */
    fun copyFile(targets: List<File>, dest: File)

    /**
     * Callback function which starts file move
     *
     * @param targets List of files to move
     * @param dest Destination for moving the targets
     */
    fun moveFile(targets: List<File>, dest: File)

    /**
     * Callback function which starts file deletion
     *
     * @param targets List of files to delete
     */
    fun deleteFile(targets: List<File>)
}