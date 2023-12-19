package com.session202312.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget

object ViewAdapter {
    @JvmStatic
    @BindingAdapter("viewAdapter:glide")
    fun glide(view: ImageView, imageUrl: String?) {
        Glide.with(view.context).load(imageUrl).apply(glideOptions).into(view)
    }

    @BindingAdapter("viewAdapter:glideFitCenter")
    fun glideFitCenter(view: ImageView, imageUrl: String?) {
        Glide.with(view.context).load(imageUrl).apply(glideNormalOptions).into(view)
    }

    @BindingAdapter("viewAdapter:setDrawableImage")
    fun setDrawableImage(view: ImageView, resource: String?) {
        view.setBackgroundResource(getDrawableResource(view.context, resource))
    }

    @BindingAdapter("viewAdapter:setActiveTextViewStyle")
    fun setActiveTextViewStyle(view: TextView, isBold: Boolean) {
        if (isBold) view.setTypeface(null, Typeface.BOLD) else view.setTypeface(
            null,
            Typeface.NORMAL
        )
    }

    @get:SuppressLint("CheckResult")
    val glideOptions: RequestOptions
        get() {
            var requestOptions = RequestOptions()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
            requestOptions = requestOptions.transforms(CenterCrop())
            return requestOptions
        }

    @get:SuppressLint("CheckResult")
    val glideNormalOptions: RequestOptions
        get() {
            var requestOptions = RequestOptions()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
            requestOptions = requestOptions.transforms(FitCenter())
            return requestOptions
        }

    @get:SuppressLint("CheckResult")
    val glideCenterInSideOptions: RequestOptions
        get() {
            var requestOptions = RequestOptions()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
            requestOptions = requestOptions.transforms(CenterInside())
            return requestOptions
        }

    fun getDrawableResource(context: Context, icon: String?): Int {
        return context.resources.getIdentifier(icon, "drawable", context.packageName)
    }


    @JvmStatic
    @BindingAdapter("paletteImage")
    fun TextView.bindLoadImagePalette(paletteImage: String?) {
        Glide.with(this.context)
            .asBitmap()
            .load(paletteImage)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    Palette.from(resource).generate {
                        var color: Int? = null
                        if (it?.lightMutedSwatch?.rgb != null)
                            color = it.lightMutedSwatch?.rgb
                        if (it?.lightVibrantSwatch?.rgb != null)
                            color = it.lightVibrantSwatch?.rgb
                        if (color != null) {
                            backgroundTintList =
                                ColorStateList.valueOf(color)
                        }
                    }
                }
            })
    }
}