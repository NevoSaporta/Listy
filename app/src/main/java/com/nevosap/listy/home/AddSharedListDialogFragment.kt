package com.nevosap.listy.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nevosap.listy.ListDialogListener
import com.nevosap.listy.R

class AddSharedListDialogFragment(private val listenerList: ListDialogListener): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.shared_dialog_title)
                .setMessage(R.string.shared_dialog_Message)
                .setPositiveButton(
                    R.string.shared_dialog_positive,
                    DialogInterface.OnClickListener{ dialog, id ->
                        listenerList.onPositiveClicked()
                    })
                .setNegativeButton(
                    R.string.shared_dialog_negative,
                    DialogInterface.OnClickListener { dialog, id ->
                        listenerList.onNegativeClicked()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}