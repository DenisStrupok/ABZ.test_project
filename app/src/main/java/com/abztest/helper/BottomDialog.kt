package com.abztest.helper

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.BottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomDialog(
    private val actionOpenGallery: (() -> Unit)? = null,
    private val actionOpenCamera: (() -> Unit)? = null
) : BottomSheetDialogFragment(R.layout.bottom_dialog) {

    private val binding: BottomDialogBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        bottomDialogCameraImgV.setOnClickListener {
            actionOpenCamera?.invoke()
        }
        bottomDialogGalleryImgV.setOnClickListener {
            actionOpenGallery?.invoke()
        }
    }
}