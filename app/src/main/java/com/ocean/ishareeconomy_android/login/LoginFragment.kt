package com.ocean.ishareeconomy_android.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ocean.ishareeconomy_android.R
import com.ocean.ishareeconomy_android.databinding.FragmentLoginBinding
import com.ocean.ishareeconomy_android.lending.LendingActivity
import com.ocean.ishareeconomy_android.viewmodels.LoginViewModel

/**
 * Part of *login*.
 *
 * Fragment that supports inputs for username and password and login button
 *
 */
class LoginFragment : Fragment(), LoginInterface {

    // storage
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    private lateinit var viewModel: LoginViewModel

    // Login Interface
    override fun getUsername(): String {
        return sharedPreferences.getString(getString(R.string.sp_login_username), "")!!
    }

    override fun getPassword(): String {
        return sharedPreferences.getString(getString(R.string.sp_login_password), "")!!
    }

    override fun getToken(): String {
        return sharedPreferences.getString(getString(R.string.sp_user_token), "")!!
    }

    override fun setUsername(username: String) {
        spEditor.putString(getString(R.string.sp_login_username), username).apply()
    }

    override fun setPassword(password: String) {
        spEditor.putString(getString(R.string.sp_login_password), password).apply()
    }

    override fun setToken(token: String) {
        spEditor.putString(getString(R.string.sp_user_token), token).apply()
    }

    override fun navigateToMain() {
        // goto the HomeActivity
        val intent = Intent(activity, LendingActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun showToastWith(text: String, lengthId: Int) {
        val toast = Toast.makeText(context, text, lengthId)
        val v = toast.view.findViewById(android.R.id.message) as TextView
        v.gravity = Gravity.CENTER
        toast.show()
    }

    override fun showToastWith(textId: Int, lengthId: Int) {
        showToastWith(getString(textId), lengthId)
    }

    // Fragment methods

    /**
     * Method called when the fragment is created
     *
     * @return [Unit]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context!!.getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        spEditor = sharedPreferences.edit()
    }

    /**
     * Method called when the view is created
     *
     * @return [Unit]
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        val viewModel= ViewModelProviders.of(this,
            LoginViewModel.Factory(activity!!.application, this))
            .get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }
}