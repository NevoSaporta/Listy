package com.nevosap.listy.details

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nevosap.listy.R
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.model.GroceryViewModel

class DeleteListDialogFragment(private val model:GroceryViewModel,private val list:GroceryListModel):DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.quantity_dialog_title)
                .setMessage(R.string.delete_dialog_Message)
                .setPositiveButton(R.string.delete_dialog_positive,
                    DialogInterface.OnClickListener{ dialog, id ->
                        model.deleteList(list)
                    })
                .setNegativeButton(R.string.delete_dialog_negative,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}