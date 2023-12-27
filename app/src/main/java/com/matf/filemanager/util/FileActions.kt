package com.matf.filemanager.util

/**
 * Enum of action types to perform on selected files
 *
 * Possible values:
 * COPY   - Copy files
 * MOVE   - Move files
 * DELETE - Delete files
 */
enum class FileActions {
    COPY,
    MOVE,
    DELETE
}