package com.matf.filemanager.util

/**
 * Enum of possible clipboard states
 *
 * Possible values:
 * NONE - Nothing is being stored
 * COPY - Stored files are to be copied
 * CUT  - Stored files are to be moved
 */
enum class ClipboardMode {
    NONE,
    COPY,
    CUT
}