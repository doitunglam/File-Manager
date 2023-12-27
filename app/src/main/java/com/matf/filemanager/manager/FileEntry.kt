package com.matf.filemanager.manager

import java.io.File

/**
 * Model for the entity in the file list
 */
data class FileEntry (var file: File, var selected: Boolean = false)