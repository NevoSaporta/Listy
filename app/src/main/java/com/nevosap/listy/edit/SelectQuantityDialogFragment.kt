package com.nevosap.listy.edit

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.nevosap.listy.R
import com.nevosap.listy.databinding.ListItemGroceryEditBinding
import com.nevosap.listy.model.GroceryItemOrderModel
import kotlinx.android.synthetic.main.dialog_quantity_edit.view.*

class SelectQuantityDialogFragment(
    var orderModel: GroceryItemOrderModel,
    var orders: MutableList<GroceryItemOrderModel>,
    val binding: ListItemGroceryEditBinding
) :DialogFragment() {
    companion object{
        private const val MINSIZE =1
        private const val MAXSIZE =10000

    }
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_quantity_edit,null)
            view.quantity_picker.minValue= MINSIZE
            view.quantity_picker.maxValue= MAXSIZE
            builder.setTitle(R.string.quantity_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.quantity_dialog_positive,
                    DialogInterface.OnClickListener{ dialog, id ->
                        orderModel =GroceryItemOrderModel((orderModel.id),orderModel.item,view.quantity_picker.value)
                    })
                .setNegativeButton(R.string.quantity_dialog_negative,
                    DialogInterface.OnClickListener { dialog, id ->
                    orderModel =GroceryItemOrderModel((orderModel.id),orderModel.item, MINSIZE)
                })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDismiss(dialog: DialogInterface) {
        //updating the order's quantity
        orders.add(orderModel)
        binding.itemQuantity.text = orderModel.quantity.toString()
        binding.itemQuantity.visibility = View.VISIBLE
    }
}