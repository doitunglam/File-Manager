package com.matf.filemanager.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import androidx.core.app.JobIntentService
import com.matf.filemanager.util.FileActions
import com.matf.filemanager.util.References
import java.io.File

/**
 * Service for performing file actions
 *
 * Actions include copying, cutting and deleting files
 * Because those operations can last a lot of time we use a service so they are executed
 * in the background even if the user closes the application
 */
class FileActionService : JobIntentService() {

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, FileActionService::class.java, 0, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        val rec: ResultReceiver = intent.getParcelableExtra(References.intentReceiver)
        val action = intent.getSerializableExtra(References.intentAction)

        when(action) {
            FileActions.COPY -> {
                val targets = intent.getStringArrayExtra(References.intentTargets).map { t -> File(t) }
                val dest = File(intent.getStringExtra(References.intentDest))

                targets.forEach { target ->
                    var newName = target.nameWithoutExtension

                    var hasTarget = false
                    for(i in 0 until 5) {
                        if(dest.resolve(newName+"."+target.extension).exists()) {
                            newName += "-copy"
                        } else {
                            hasTarget = true
                            break
                        }
                    }

                    if(hasTarget) {
                        if(target.isDirectory) {
                            target.copyRecursively(dest.resolve(newName), false)
                        } else {
                            newName += "." + target.extension
                            target.copyTo(dest.resolve(newName), false)
                        }
                    }
                }
            }
            FileActions.MOVE -> {
                val targets = intent.getStringArrayExtra(References.intentTargets).map { t -> File(t) }
                val dest = File(intent.getStringExtra(References.intentDest))

                targets.forEach { target ->
                    if(!dest.resolve(target.name).exists())
                        target.renameTo(dest.resolve(target.name))
                }
            }
            FileActions.DELETE -> {
                val targets = intent.getStringArrayExtra(References.intentTargets).map { t -> File(t) }
                targets.forEach { target ->
                    if (target.exists()) {
                        if (target.isDirectory) {
                            target.deleteRecursively()
                        } else {
                            target.delete()
                        }
                    }
                }
            }
        }

        val bundle = Bundle()
        bundle.putSerializable(References.intentAction, action)
        rec.send(Activity.RESULT_OK, bundle)
    }

}