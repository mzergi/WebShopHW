package hu.bme.aut.android.webshophw.fragments

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.webshophw.R

class CheckoutDialogFragment : DialogFragment() {
    interface CheckoutDialogListener {
        fun onCheckout()
        fun onCheckoutFail()
    }

    private lateinit var listener : CheckoutDialogListener
    private lateinit var nameEditText : EditText
    private lateinit var emailEditText : EditText
    private lateinit var bankCardEditText : EditText
    private lateinit var bankCardValidEditText : EditText
    private lateinit var bankCardCodeEditText : EditText
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? CheckoutDialogListener
                ?: throw RuntimeException("Activity must implement the CheckoutDialogListener interface")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.checkout_header)
                .setView(getContentView())
                .setPositiveButton(R.string.OK) { dialogInterface, i->
                    if(TextUtils.isEmpty(nameEditText.text) || TextUtils.isEmpty(emailEditText.text) || TextUtils.isEmpty(bankCardEditText.text) || TextUtils.isEmpty(bankCardValidEditText.text) || TextUtils.isEmpty(bankCardCodeEditText.text)) {
                        listener.onCheckoutFail()
                    }
                    else {
                        listener.onCheckout()
                    }

                }
                .setNegativeButton(R.string.cancel, null)
                .create()

    }

    private fun getContentView() : View {
        val contentView =
                LayoutInflater.from(context).inflate(R.layout.checkout, null)
        nameEditText = contentView.findViewById(R.id.CheckoutNameEditText)
        emailEditText = contentView.findViewById(R.id.CheckoutEmailEditText)
        bankCardEditText = contentView.findViewById(R.id.CheckoutBankCardEditText)
        bankCardValidEditText = contentView.findViewById(R.id.CheckoutBankCardValidEditText)
        bankCardCodeEditText = contentView.findViewById(R.id.CheckoutBankCardCodeEditText)

        return contentView
    }

    companion object {
        const val TAG = "CheckoutDialogFragment"
    }
}