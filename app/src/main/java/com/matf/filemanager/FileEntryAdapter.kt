package com.matf.filemanager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matf.filemanager.manager.FileEntry
import com.matf.filemanager.manager.FileManager
import com.matf.filemanager.util.*

/**
 * Adapter that maps a FileEntry model to a View for the file list
 */
class FileEntryAdapter(private val entries: ArrayList<FileEntry>) : RecyclerView.Adapter<FileEntryAdapter.EntryHolder>() {

    class EntryHolder(val entryView : View) : RecyclerView.ViewHolder(entryView) {
        val imgIcon: ImageView = entryView.findViewById(R.id.imgIcon)
        val tvTitle: TextView = entryView.findViewById(R.id.fileTitletv)
        val tvSize: TextView = entryView.findViewById(R.id.fileSizetv)
        val bProperties: ImageView = entryView.findViewById(R.id.bProperties)
        val cbSelected: CheckBox = entryView.findViewById(R.id.cbSelected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_entry, parent, false)
        return EntryHolder(view)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        val entry = FileManager.entries[position]

        holder.tvTitle.text = entry.file.name

        // Set the file icon
        holder.imgIcon.setImageResource(getIconForFile(entry.file))

        // Set the file size
        if (!entry.file.isDirectory)
            holder.tvSize.text = holder.entryView.context.getString(R.string.text_size, getSizeString(FileManager.entries[position].file))
        else
            holder.tvSize.text = ""

        // Initialization
        when(FileManager.menuMode) {
            MenuMode.SELECT -> {
                holder.bProperties.visibility = ImageButton.GONE
                holder.cbSelected.visibility = CheckBox.VISIBLE
            }
            MenuMode.OPEN -> {
                holder.bProperties.visibility = ImageButton.VISIBLE
                holder.bProperties.setOnClickListener {
                    val popup = PopupMenu(it.context, it)
                    popup.menuInflater.inflate(R.menu.file_entry_menu, popup.menu)
                    if(!FileManager.canOpenWith(entry.file))
                        popup.menu.findItem(R.id.menu_open_with).isVisible = false
                    popup.setOnMenuItemClickListener { item ->
                        when(item.itemId) {
                            R.id.menu_open_with -> FileManager.requestFileOpenWith(entry.file)
                            R.id.menu_copy -> FileManager.moveToClipboard(entry.file, ClipboardMode.COPY)
                            R.id.menu_cut -> FileManager.moveToClipboard(entry.file, ClipboardMode.CUT)
                            R.id.menu_delete -> FileManager.delete(entry.file)
                        }
                        true
                    }
                    popup.show()
                }
                holder.cbSelected.visibility = CheckBox.GONE
            }
        }

        // Highlight selected entries
        if (entry.selected) {
            holder.cbSelected.isChecked = true
            holder.entryView.setBackgroundColor(holder.entryView.resources.getColor(R.color.colorHighlight))
        } else {
            holder.cbSelected.isChecked = false
            holder.entryView.setBackgroundColor(Color.TRANSPARENT)
        }

        // Event handlers
        holder.entryView.setOnClickListener {
            holder.entryView.setBackgroundColor(holder.entryView.resources.getColor(R.color.colorHighlight))
            holder.entryView.animate().setDuration(20).withEndAction {
                holder.entryView.setBackgroundColor(Color.TRANSPARENT)
                if(FileManager.menuMode == MenuMode.OPEN){
                    if (!FileManager.goTo(entry.file)) {
                        FileManager.requestFileOpenWith(entry.file)
                    }
                }else{
                    FileManager.toggleSelectionAt(position)
                }
            }.start()
        }

        holder.entryView.setOnLongClickListener {
            if(FileManager.menuMode == MenuMode.OPEN) {
                FileManager.toggleSelectionMode()
                FileManager.toggleSelectionAt(position)
            }
            true
        }
    }

}