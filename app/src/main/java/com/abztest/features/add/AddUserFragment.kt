package com.abztest.features.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentAddUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddUserFragment : Fragment(R.layout.fragment_add_user) {


    private val binding: FragmentAddUserBinding by viewBinding()
    private val viewModel: AddUserVM by viewModel()
    private lateinit var positionAdapter: PositionAdapter

    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { url ->
        url?.let {
            val photo = getPathFromUri(it)
            if (photo != null) {
                viewModel.setPhoto(photo)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLauncher()
        initViews()
        initObserver()
    }

    private fun initLauncher() {
    }

    private fun initViews() = with(binding) {
        positionAdapter = PositionAdapter(actionSelectedPosition = { position ->
            viewModel.setPosition(position)
        })
        addUserPositionRV.adapter = positionAdapter
        addUserSingUpBtn.setOnClickListener {
            viewModel.checkFields()
        }
        addUserUploadBtn.setOnClickListener {
            checkPhotoPermission()
        }

        addUserNameEdT.isActivated = true
        addUserPhoneEdT.isActivated = true
        addUserEmailEdT.isActivated = true
        addUserPhotoContainer.isActivated = true

        addUserNameEdT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                addUserNameEdT.isActivated = true
            }

            override fun onTextChanged(name: CharSequence?, start: Int, before: Int, count: Int) {
                if (name?.isNotEmpty() == true) {
                    addUserHintErrorNameTv.visibility = View.GONE
                    viewModel.setName(name = name.toString())
                } else {
                    viewModel.setName(name = "")
                }
            }

            override fun afterTextChanged(name: Editable?) {}

        })
        addUserEmailEdT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                addUserEmailEdT.isActivated = true
            }

            override fun onTextChanged(email: CharSequence?, start: Int, before: Int, count: Int) {
                if (email?.isNotEmpty() == true) {
                    addUserHintErrorEmailTv.visibility = View.GONE
                    viewModel.setEmail(email = email.toString())
                } else {
                    viewModel.setEmail(email = "")
                }
            }

            override fun afterTextChanged(email: Editable?) {}

        })
        addUserPhoneEdT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                addUserPhoneEdT.isActivated = true
            }

            override fun onTextChanged(number: CharSequence?, start: Int, before: Int, count: Int) {
                if (number?.isNotEmpty() == true) {
                    addUserHintPhoneTv.visibility = View.VISIBLE
                    addUserHintErrorPhoneTv.visibility = View.GONE
                    viewModel.setPhone(number = number.toString())
                } else {
                    viewModel.setPhone(number = "")
                }
            }

            override fun afterTextChanged(phone: Editable?) {

            }

        })
    }

    private fun initObserver() = with(viewModel) {
        listUserPosition.observe(viewLifecycleOwner) { positions ->
            positionAdapter.setPositions(positions)
        }
        isNameValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding.addUserNameEdT.apply {
                    this.isActivated = false
                    this.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    binding.addUserHintErrorNameTv.visibility = View.VISIBLE
                }
            } else {
                binding.addUserNameEdT.apply {
                    this.isActivated = true
                    this.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                    binding.addUserHintErrorNameTv.visibility = View.GONE
                }
            }
        }

        isPhoneValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding.addUserPhoneEdT.apply {
                    this.isActivated = false
                    this.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    binding.addUserHintErrorPhoneTv.visibility = View.VISIBLE
                    binding.addUserHintPhoneTv.visibility = View.INVISIBLE
                }
            } else {
                binding.addUserPhoneEdT.apply {
                    this.isActivated = true
                    this.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                    binding.addUserHintErrorPhoneTv.visibility = View.GONE
                    binding.addUserHintPhoneTv.visibility = View.VISIBLE
                }
            }
        }

        isEmailValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding.addUserEmailEdT.apply {
                    this.isActivated = false
                    this.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    binding.addUserHintErrorEmailTv.visibility = View.VISIBLE
                }
            } else {
                binding.addUserEmailEdT.apply {
                    this.isActivated = true
                    this.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                    binding.addUserHintErrorEmailTv.visibility = View.GONE
                }
            }
        }
        isPhotoValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                binding.addUserPhotoContainer.isActivated = false
                binding.addUserLoadPhotoTitleTV.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                binding.addUserHintErrorPhotoTv.visibility = View.VISIBLE
            } else {
                binding.addUserPhotoContainer.isActivated = true
                binding.addUserLoadPhotoTitleTV.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray
                    )
                )
                binding.addUserHintErrorPhotoTv.visibility = View.GONE
            }
        }

        photo.observe(viewLifecycleOwner) { photo ->
            if (photo.length >= 3) {
                binding.addUserLoadPhotoTitleTV.text = photo
            }
        }
        userRegistration.observe(viewLifecycleOwner) { user ->
            if (user.id.isNotEmpty()) {
                when (user.isSuccess) {
                    true -> findNavController().navigate(R.id.actionAddUserFragmentToSuccessFragment)
                    false -> findNavController().navigate(R.id.actionAddUserFragmentToFailedFragment)
                }
            }
        }

    }

    private fun checkPhotoPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Permission cancel
            showMessagePermission()
        } else {
            //Permission ok
            openGallery()
        }
    }

    private fun openGallery() {
        launcher.launch("image/")
    }

    private fun showMessagePermission() {

    }

    private fun getPathFromUri(uri: Uri): String? {
        var filePath: String? = null
        if (DocumentsContract.isDocumentUri(requireContext(), uri)) {
            val documentId: String = DocumentsContract.getDocumentId(uri)
            val split = documentId.split(":").toTypedArray()
            val type = split[0]
            val id = split[1]

            val contentUri: Uri = when (type) {
                "raw" -> Uri.parse(split[1])
                "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                else -> return null
            }

            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val selection = "${MediaStore.Images.Media._ID}=?"
            val selectionArgs = arrayOf(id)

            val cursor = requireActivity().contentResolver.query(
                contentUri,
                projection,
                selection,
                selectionArgs,
                null
            )
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            filePath = uri.path
        }
        return filePath
    }
}