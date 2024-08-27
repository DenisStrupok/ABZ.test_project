package com.abztest.features.add

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentAddUserBinding
import com.abztest.helper.BottomDialog
import com.abztest.helper.CAMERA_PERMISSION_REQUEST_CODE
import com.abztest.helper.GALLERY_PERMISSION_REQUEST_CODE
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddUserFragment : Fragment(R.layout.fragment_add_user) {

    private val binding: FragmentAddUserBinding by viewBinding()
    private val viewModel: AddUserVM by viewModel()
    private lateinit var positionAdapter: PositionAdapter
    private lateinit var bottomDialog: BottomDialog
    private lateinit var photoUri: Uri
    private var absolutePath: String? = null

    private var launcherGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { url ->
            url?.let {
                val photo = getPathFromUri(it)
                if (photo != null) {
                    viewModel.setPhoto(photo)
                }
            }
        }

    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                absolutePath?.let { viewModel.setPhoto(it) }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserver()
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
            showBottomDialog()
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

            override fun afterTextChanged(phone: Editable?) {}

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
                bottomDialog.dismiss()
                binding.addUserLoadPhotoTitleTV.text = photo
            }
        }
        userRegistration.observe(viewLifecycleOwner) { user ->
            if (user.id.isNotEmpty()) {
                when (user.isSuccess) {
                    true -> {
                        findNavController().navigate(
                            AddUserFragmentDirections.actionAddUserFragmentToSuccessFragment(
                                user.id
                            )
                        )
                    }

                    false -> findNavController().navigate(R.id.actionAddUserFragmentToFailedFragment)
                }
            }
        }

    }

    private fun checkGalleryPermission() {
        if (isGalleryPermissionGranted()) {
            openGallery()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            openCamera()
        }
    }

    private fun openGallery() {
        launcherGallery.launch("image/")
    }

    private fun showBottomDialog() {
        bottomDialog = BottomDialog(
            actionOpenCamera = {
                checkCameraPermission()
            },
            actionOpenGallery = {
                checkGalleryPermission()
            }
        )
        bottomDialog.show(childFragmentManager, bottomDialog.tag)
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

    private fun openCamera() {
        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "com.abztest.features.main.fileProvider",
            createPhotoFile().also {
                absolutePath = it.path
            }
        )
        cameraLauncher.launch(photoUri)
    }

    private fun createPhotoFile(): File {
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("camera_image", ".jpg", storageDir)
    }

    private fun isGalleryPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}