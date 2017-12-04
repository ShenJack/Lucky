package com.example.shenjack.lucky

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.shenjack.lucky.constant.Constant
import com.example.shenjack.lucky.data.Response
import com.example.shenjack.lucky.data.UserBean
import com.example.shenjack.lucky.data.remote.apiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * sjjkk on 2017/12/3 in Beijing.

 */



/**
 * A login screen that offers login via email/password.
 */
class RegisterActivity: BaseActivity(){
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.

    private var mEmailView: AutoCompleteTextView? = null
    private var mPasswordView: EditText? = null
    private var mLoginFormView: View? = null

    private var mPasswordAgainView: EditText? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initViews() {
        super.initViews()
        mEmailView = find<AutoCompleteTextView>(R.id.email)

        mPasswordView = find<EditText>(R.id.password)

        mPasswordAgainView = find<EditText>(R.id.password_2)

        val mRegisterButton = find<Button>(R.id.register_button)
        mRegisterButton.onClick {
            attempRegister()
        }

        mLoginFormView = findViewById(R.id.login_form)
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete()
            }
        }
    }

    private fun attempRegister() {

        // Reset errors.
        mEmailView!!.error = null
        mPasswordView!!.error = null

        // Store values at the time of the login attempt.
        val username = mEmailView!!.text.toString()
        val password = mPasswordView!!.text.toString()
        val passwordAgain = mPasswordAgainView!!.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView!!.error = getString(R.string.can_not_be_empty)
            focusView = mPasswordView
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mEmailView!!.error = getString(R.string.error_field_required)
            focusView = mEmailView
            cancel = true
        }

        if (!passwordAgain.equals(password)){
            mPasswordAgainView!!.error = getString(R.string.not_same_password)
            focusView = mPasswordAgainView
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            apiService.apiServiceInstance!!.register(username,password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(object : Observer<Response<UserBean>> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onError(e: Throwable) {
                            showProgress(false)
                            toast(e.message.toString())

                        }

                        override fun onNext(t: Response<UserBean>){
                            showProgress(false)

                            if(t.status.equals(Constant.STATUS_SUCCESS)){
                                startActivity<MainActivity>()
                            }else{
                                toast(t.error)
                            }
                        }
                    })

        }
    }


    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}