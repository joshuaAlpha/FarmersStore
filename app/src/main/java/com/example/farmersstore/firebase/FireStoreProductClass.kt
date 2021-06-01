package com.example.farmersstore.firebase

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import com.example.farmersstore.activities.AddProductActivity
import com.example.farmersstore.activities.ProductDetailsActivity
import com.example.farmersstore.fragments.DashboardFragment
import com.example.farmersstore.fragments.ProductsFragment
import com.example.farmersstore.models.Product
import com.example.farmersstore.utils.Constants.PRODUCTS
import com.example.farmersstore.utils.Constants.PRODUCT_IMAGES_FOLDER
import com.example.farmersstore.utils.Constants.getFileExtension
import com.google.android.gms.common.internal.Constants
import com.google.common.io.Files.getFileExtension
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.ArrayList

class FireStoreProductClass {

    private val mFireStoreClass = FirebaseFirestore.getInstance()

    fun fetchProduct(activity: Activity, productId: String) {
        mFireStoreClass.collection(com.example.farmersstore.utils.Constants.PRODUCTS).document(productId).get()
            .addOnSuccessListener { doc ->
                val product = doc.toObject(Product::class.java)
                product?.id = productId
                when (activity) {
                    is ProductDetailsActivity -> {
                        activity.fetchProductSuccess(product!!)
                    }
                }
            }.addOnFailureListener { e ->
                when (activity) {
                    is ProductDetailsActivity -> {
                        activity.fetchProductFailure(e.message.toString())
                    }
                }
            }
    }

    fun fetchProducts(fragment: Fragment) {
        mFireStoreClass.collection(com.example.farmersstore.utils.Constants.PRODUCTS).get()
            .addOnSuccessListener { snapshot ->
                val docs = snapshot.documents
                val products = ArrayList<Product>()
                for (doc in docs) {
                    val product = doc.toObject(Product::class.java)
                    product!!.id = doc.id
                    products.add(product)
                }
                when (fragment) {
                    is DashboardFragment -> {
                        fragment.fetchProductsSuccess(products)
                    }
                }
            }.addOnFailureListener { e ->
                when (fragment) {
                    is DashboardFragment -> {
                        fragment.fetchProductsFailure(e.message.toString())
                    }
                }
            }
    }

    fun fetchUserProducts(fragment: Fragment) {
        mFireStoreClass.collection(com.example.farmersstore.utils.Constants.PRODUCTS)
            .whereEqualTo(com.example.farmersstore.utils.Constants.USER_ID, FireStoreAuthClass().getCurrentUserId()).get()
            .addOnSuccessListener { snapshot ->
                val docs = snapshot.documents
                val userProducts = ArrayList<Product>()
                for (doc in docs) {
                    val product = doc.toObject(Product::class.java)
                    product!!.id = doc.id
                    userProducts.add(product)
                }
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.fetchProductsSuccess(userProducts)
                    }
                }
            }.addOnFailureListener { e ->
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.fetchProductsFailure(e.message.toString())
                    }
                }
            }
    }

    fun addProduct(activity: AddProductActivity, product: Product) {
        product.userId = FireStoreAuthClass().getCurrentUserId()!!
        product.userName = FireStoreAuthClass().getLocalUserName(activity)
        mFireStoreClass.collection(com.example.farmersstore.utils.Constants.PRODUCTS).document()
            .set(product, SetOptions.merge()).addOnSuccessListener {
                activity.addProductSuccess()
            }.addOnFailureListener { e ->
                activity.addProductFailure(e.message.toString())
            }
    }

    fun uploadProductImage(activity: AddProductActivity, imageUri: Uri) {
        val sRef = FirebaseStorage.getInstance().reference.child(
            com.example.farmersstore.utils.Constants.PRODUCT_IMAGES_FOLDER +  "/" + FireStoreAuthClass().getCurrentUserId() + "/" +
                   com.example.farmersstore.utils.Constants.PRODUCT_IMAGE + System.currentTimeMillis() + com.example.farmersstore.utils.Constants.getFileExtension(activity, imageUri))
        sRef.putFile(imageUri).addOnSuccessListener { taskScreenshot ->
            taskScreenshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { imageUri ->
                    activity.imageUploadSuccess(imageUri.toString())
                }
        }.addOnFailureListener { e ->
            activity.addProductFailure(e.message.toString())
        }
    }

    fun deleteProduct(fragment: Fragment, productId: String) {
        mFireStoreClass.collection(com.example.farmersstore.utils.Constants.PRODUCTS).document(productId).delete()
            .addOnSuccessListener {
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.deleteProductSuccess()
                    }
                }
            }.addOnFailureListener {
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.deleteProductFailure()
                    }
                }
            }
    }

}