package ru.devsokovix.investor

import android.media.Image
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField

class Stock(var name: String, _price: Int = 0, @DrawableRes val iconId: Int) {
    val priceObservable = ObservableField<Int>(_price)
    var price = _price
        set(value){
            field = value
            priceObservable.set(value)
        }

    companion object{
        @BindingAdapter("imageId")
        @JvmStatic
        fun loadImage(view: ImageView, imageId: Int){
            view.setImageResource(imageId)
        }
    }
}