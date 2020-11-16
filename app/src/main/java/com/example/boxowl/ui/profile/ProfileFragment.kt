package com.example.boxowl.ui.profile

import android.app.Activity.MODE_PRIVATE
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.boxowl.AuthActivity
import com.example.boxowl.NavigationManager
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.bases.HasNavigationManager
import com.example.boxowl.databinding.FragmentProfileBinding
import com.example.boxowl.models.Courier
import com.example.boxowl.models.CurrentCourier
import com.example.boxowl.presentation.profile.ProfileContract
import com.example.boxowl.presentation.profile.ProfilePresenter
import com.example.boxowl.ui.extension.onClick
import com.example.boxowl.utils.*
import com.wada811.viewbinding.viewBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.io.ByteArrayOutputStream


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

class ProfileFragment : Fragment(R.layout.fragment_profile), ProfileContract.View,
    HasNavigationManager {

    interface OnProfileFragmentInteractionListener : FragmentInteractionListener

    private lateinit var listener: OnProfileFragmentInteractionListener
    private lateinit var mNavigationManager: NavigationManager

    private val binding: FragmentProfileBinding by viewBinding()
    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var loadingDialog: AlertDialog

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profilePresenter = ProfilePresenter(this)
        loadingDialog = loadingSpotsDialog(requireContext())

        loadUserData(CurrentCourier.courier)

        with(binding) {
            changeUserDataBtn.onClick {
                showLoadingDialog(loadingDialog)
                profilePresenter.updateUserData(changedUser())
            }
            changeUserImageBtn.onClick { getImageFromGallery() }
            logOutBtn.onClick { logOut() }
        }
        loadChangeProfileTextWatcher()

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(
                true
            ) {
                override fun handleOnBackPressed() {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            })
    }

    private fun logOut() {
        AlertDialog.Builder(activity)
            .setMessage(R.string.dialog_logout_message)
            .setTitle(R.string.dialog_confirmation_title)
            .setPositiveButton(R.string.dialog_ok) { _, _ ->
                val sharedPreferencesUser =
                    requireActivity().getSharedPreferences("COURIER", MODE_PRIVATE)
                sharedPreferencesUser.edit().remove("CourierObject").apply()
                sharedPreferencesUser.edit().remove("CourierPinCode").apply()
                startActivity(Intent(activity, AuthActivity::class.java))
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .create()
            .show()
    }

    private fun getImageFromGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            binding.userImageView.setImageURI(imageUri)
            val inputStream = requireActivity().contentResolver.openInputStream(imageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val byteArray = outputStream.toByteArray()
            val encodedString: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            CurrentCourier.courier.CourierImage = encodedString
        }
    }

    private fun loadChangeProfileTextWatcher() {
        val changeProfileTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.changeUserDataBtn.isEnabled =
                    !binding.firstNameEditText.text.toString().isBlank()
                            && !binding.secondNameEditText.text.toString().isBlank()
                            && binding.phoneEditText.text.toString().isPhone()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        with(binding) {
            firstNameEditText.addTextChangedListener(changeProfileTextWatcher)
            secondNameEditText.addTextChangedListener(changeProfileTextWatcher)
            phoneEditText.addTextChangedListener(changeProfileTextWatcher)
        }
    }

    private fun changedUser(): Courier {
        val user = CurrentCourier.courier
        with(user) {
            CourierName = binding.firstNameEditText.text.toString()
            CourierSurname = binding.secondNameEditText.text.toString()
            CourierPhone = binding.phoneEditText.text.toString().toNormalString()
        }
        return user
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProfileFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnProfileFragmentInteractionListener")
        }
        mNavigationManager = NavigationManager(childFragmentManager, R.id.container)
    }

    override fun onStart() {
        super.onStart()
        listener.setBottomNavigation(true, R.id.navigation_profile)
        listener.setToolbarTitle(resources.getString(R.string.title_profile))
    }

    override fun provideNavigationManager(): NavigationManager = mNavigationManager

    override fun loadUserData(courier: Courier) {
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.phoneEditText)

        if (courier.CourierImage == null) {
            binding.userImageView.setImageResource(R.drawable.default_user_image)
        } else {
            val imageBytes = Base64.decode(courier.CourierImage, 0)
            binding.userImageView.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    imageBytes,
                    0,
                    imageBytes.size
                )
            )
        }
        binding.firstNameEditText.setText(courier.CourierName)
        binding.secondNameEditText.setText(courier.CourierSurname)
        binding.phoneEditText.setText(courier.CourierPhone)
    }

    override fun onError(error: String) {
        dismissLoadingDialog(loadingDialog)
        showToast(requireContext(), error)
    }

    override fun onSuccess() {
        dismissLoadingDialog(loadingDialog)
        showToast(requireContext(), "Данные изменены")
    }

    /*override fun onResume() {
        Objects.requireNonNull(requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING))
        super.onResume()
    }*/

    override fun onDestroy() {
        profilePresenter.onDestroy()
        super.onDestroy()
    }
}