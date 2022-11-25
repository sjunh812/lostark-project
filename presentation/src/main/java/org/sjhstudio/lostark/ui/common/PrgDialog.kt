package org.sjhstudio.lostark.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import org.sjhstudio.lostark.databinding.DialogPrgBinding

class PrgDialog : DialogFragment() {

    private var _binding: DialogPrgBinding? = null
    val binding: DialogPrgBinding
        get() = _binding ?: error("ViewBinding error")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPrgBinding.inflate(inflater, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().apply {
            add(this@PrgDialog, tag)
            commitAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance(): PrgDialog = PrgDialog()
    }
}

