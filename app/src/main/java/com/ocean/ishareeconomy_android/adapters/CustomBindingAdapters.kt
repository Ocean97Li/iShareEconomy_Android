package com.ocean.ishareeconomy_android.adapters

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: MutableLiveData<String>) {
    errorMessage.value.let { message ->
        if (message != view.error?.toString()) {
            if (message != null) {
                view.isErrorEnabled = true
                view.error = errorMessage.value
            } else {
                view.isErrorEnabled = false
                view.error = null
            }
        }
    }
}