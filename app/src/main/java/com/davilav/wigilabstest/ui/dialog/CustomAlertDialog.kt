package com.davilav.wigilabstest.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.davilav.wigilabstest.R

class CustomAlertDialog : DialogFragment() {

    private var titleDialog: String? = null
    private var messageDialog: String? = null
    private var positiveButtonDialog: String? = null
    private var negativeButtonDialog: String? = null
    private var mPositiveListener: (() -> Unit)? = null
    private var mNegativeListener: (() -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleDialog = it.getString(TITLE_DIALOG)
            messageDialog = it.getString(MESSAGE_DIALOG)
            positiveButtonDialog = it.getString(POSITIVE_BUTTON_DIALOG)
            negativeButtonDialog = it.getString(NEGATIVE_BUTTON_DIALOG)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.film_layout_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity()).apply {
            titleDialog?.let { title -> setTitle(title) }
            messageDialog?.let { messageDialog -> setMessage(messageDialog) }
            if (mPositiveListener != null && !positiveButtonDialog.isNullOrBlank()) {
                setPositiveButton(positiveButtonDialog) { _, _ ->
                    dismissAllowingStateLoss()
                    mPositiveListener?.invoke()
                }
            }
            if (mNegativeListener != null && !negativeButtonDialog.isNullOrBlank()) {
                setNegativeButton(negativeButtonDialog) { _, _ ->
                    dismissAllowingStateLoss()
                    mNegativeListener?.invoke()
                }
            }
        }
        return builder.create()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun onDetach() {
        super.onDetach()
        mPositiveListener = null
        mNegativeListener = null
    }

    companion object {

        private const val TITLE_DIALOG = "title_dialog"
        private const val MESSAGE_DIALOG = "message_dialog"
        private const val POSITIVE_BUTTON_DIALOG = "positive_button_dialog"
        private const val NEGATIVE_BUTTON_DIALOG = "negative_button_dialog"

        fun instance(
            title: String,
            message: String,
            positiveButtonTitle: String? = null,
            negativeButtonTitle: String? = null,
            positiveListener: () -> Unit,
            negativeListener: () -> Unit
        ): CustomAlertDialog {
            return CustomAlertDialog().apply {
                mPositiveListener = positiveListener
                mNegativeListener = negativeListener
                arguments = Bundle().apply {
                    isCancelable = false
                    putString(TITLE_DIALOG, title)
                    putString(MESSAGE_DIALOG, message)
                    putString(POSITIVE_BUTTON_DIALOG, positiveButtonTitle)
                    putString(NEGATIVE_BUTTON_DIALOG, negativeButtonTitle)
                }
            }
        }
    }
}