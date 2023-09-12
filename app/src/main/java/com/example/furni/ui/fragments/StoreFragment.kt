package com.example.furni.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.furni.R
import com.example.furni.data.store.Store
import com.example.furni.databinding.FragmentStoreBinding
import com.example.furni.viewmodel.HomeScreenViewModel
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoreFragment : BindingFragment<FragmentStoreBinding>(R.layout.fragment_store),
    OnClickListener {
    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()

    private lateinit var longitude: String
    private lateinit var latitude: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.let { it ->
            it.lifecycleScope.launch {
                it.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeScreenViewModel.store.collect { store ->
                        updateStoreInformationUI(store)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.phone -> {
                makePhoneCall()
            }

            R.id.address -> {
                openGoogleMap()
            }
        }
    }

    private fun makePhoneCall() {
        binding {
            val number: String = phone.text.toString()
            if (number.trim { it <= ' ' }.isNotEmpty()) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_CALL
                    )
                } else {
                    val dial = "tel: $number"
                    startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
                }
            } else {
                Toast.makeText(context, "No phone number found!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGoogleMap() {
        Uri.parse("geo:3$latitude,$longitude?q=$latitude,$longitude(Label+Name)").let { uri ->
            Intent(Intent.ACTION_VIEW, uri).also {
                it.setPackage("com.google.android.apps.maps")
                startActivity(it)
            }
        }
    }

    private fun updateStoreInformationUI(store: Store) {
        binding {
            address.text = store.address
            phone.text = store.phone
            longitude = store.longitude!!
            latitude = store.latitude!!
        }
    }

    companion object {
        private const val REQUEST_CALL = 1
    }
}