package com.example.furni.ui.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.furni.R
import com.example.furni.data.product.Product
import com.example.furni.databinding.ActivityProductDetailBinding
import com.example.furni.viewmodel.ProductDetailViewModel
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.skydoves.bindables.BindingActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity :
    BindingActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail),
    OnClickListener {

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            back.setOnClickListener(this@ProductDetailActivity)
            addToCart.setOnClickListener(this@ProductDetailActivity)
        }

        intent.getStringExtra("ProductId")?.let {
            productDetailViewModel.getProductDetail(it)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    productDetailViewModel.productDetail.collect { product ->
                        if (product.isLoading) {
                            // TODO Handle later
                        } else if (product.error.isNotBlank()) {
                            val toast =
                                Toast.makeText(
                                    this@ProductDetailActivity,
                                    product.error,
                                    Toast.LENGTH_SHORT
                                )
                            toast.show()
                        } else {
                            populateUiWithProduct(product)
                        }
                    }
                }

                launch {
                    productDetailViewModel.orderStatus.collect {
                        if (it.isLoading) {
                            // TODO Handle later
                        } else if (it.error.isNotBlank()) {
                            val toast =
                                Toast.makeText(
                                    this@ProductDetailActivity,
                                    it.error,
                                    Toast.LENGTH_SHORT
                                )
                            toast.show()
                        } else {
                            Toast.makeText(
                                this@ProductDetailActivity,
                                "Added to cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun populateUiWithProduct(product: Product) {
        binding {
            Picasso.get().load(product.image).into(imageProduct)
            priceProduct.text = "$  ${product.price}"
            nameProduct.text = product.name
            descriptionProduct.text = product.description
            amount.setRange(1, product.quantity.toInt())
        }

        val arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            placeModel(arFragment, hitResult.createAnchor(), product.sfb)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                finish()
            }

            R.id.add_to_cart -> {
                binding {
                    intent.getStringExtra("ProductId")?.let {
                        placeOrder(
                            productID = it,
                            product = productDetailViewModel.productDetail.value,
                            amount = amount.number.toInt()
                        )
                    }
                }
            }
        }
    }

    private fun placeOrder(productID: String, product: Product, amount: Int) {
        // TODO replace UserID
        productDetailViewModel.addToCart("", productID, product, amount)
    }

    private fun placeModel(arFragment: ArFragment, anchor: Anchor, model_sfb: String) {
        ModelRenderable.builder()
            .setSource(
                this, RenderableSource.builder()
                    .setSource(this, Uri.parse(model_sfb), RenderableSource.SourceType.GLB)
                    .setScale(0.005f)
                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                    .build()
            )
            .setRegistryId(model_sfb)
            .build()
            .thenAccept { modelRenderable: ModelRenderable ->
                addNodeToScreen(
                    arFragment,
                    anchor,
                    modelRenderable
                )
            }
            .exceptionally { throwable: Throwable? ->
                null
            }
    }

    private fun addNodeToScreen(
        arFragment: ArFragment,
        anchor: Anchor,
        modelRenderable: ModelRenderable
    ) {
        val anchorNode = AnchorNode(anchor)
        val transformableNode = TransformableNode(arFragment.transformationSystem)
        transformableNode.renderable = modelRenderable
        transformableNode.setParent(anchorNode)
        arFragment.arSceneView.scene.addChild(anchorNode)
        transformableNode.select()
    }

    companion object {
        fun startActivity(view: View, productId: Int) {
            Intent(view.context, ProductDetailActivity::class.java).also {
                it.putExtra("ProductId", productId.toString())
                view.context.startActivity(it)
            }
        }
    }
}