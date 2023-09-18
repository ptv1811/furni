package com.example.furni.ui.fragments

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.furni.R
import com.example.furni.data.cart.Cart
import com.example.furni.databinding.CartLayoutBinding
import com.example.furni.databinding.FragmentCartBinding
import com.example.furni.viewmodel.HomeScreenViewModel
import com.skydoves.bindables.BindingFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BindingFragment<FragmentCartBinding>(R.layout.cart_layout), OnClickListener {

    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartAdapter = CartAdapter()
        binding {
            confirm.setOnClickListener(this@CartFragment)
            cartRecycler.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 1)
                adapter = cartAdapter
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeScreenViewModel.userCartList.collect {
                    if (it.isLoading) {
                        binding.progressBar.visibility = VISIBLE
                    } else if (it.error.isNotBlank()) {
                        binding.progressBar.visibility = GONE
                        val toast =
                            Toast.makeText(activity, it.error, Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        binding.progressBar.visibility = GONE
                        cartAdapter.setItems(it.cartList)

                        updateTotalPrice(it.cartList)
                    }
                }
            }
        }
    }

    private fun updateTotalPrice(cartList: List<Cart>) {
        var totalPrice = 0;
        for (cart in cartList) {
            totalPrice += cart.productAmount.toInt() * cart.productPrice.toInt()
        }

        binding {
            this.totalPrice.text = totalPrice.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        // TODO Replace UID with Global User
        homeScreenViewModel.getUserCart("");
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.confirm -> {
                displayNotification()

                binding {
                    cartRecycler.visibility = GONE
                    totalPrice.text = "$ 0"
                }

                // TODO Replace UID with Global User
                homeScreenViewModel.removeCartValue("")
            }
        }
    }

    private fun displayNotification() {
        val channel1 = NotificationChannel(
            CHANNEL_ID,
            "Channel1",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Hello"
        }


        val manager = requireContext().getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(channel1)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            requireContext(), CHANNEL_ID
        )
        val myIntent = Intent(context, CartFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            myIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setChannelId(CHANNEL_ID)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Thank you for shopping with us")
            .setContentIntent(pendingIntent)
            .setContentText("Your order has been confirmed")
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setContentInfo("Info")

        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }

    class CartAdapter : BaseRecyclerViewAdapter<Cart, CartLayoutBinding>(
        R.layout.cart_layout,
        onBindViewHolderCallback = { cart, binding ->
            binding.apply {
                Picasso.get().load(cart.productImage).into(binding.imageView3)
                nameOfProduct.text = cart.productName
                amountOfProduct.text = cart.productAmount
                priceOfProduct.text = cart.productPrice
            }
        }
    )

    companion object {
        var CHANNEL_ID = "Channel 1"
    }
}