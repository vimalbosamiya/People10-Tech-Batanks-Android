package com.batanks.nextplan.Settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.viewmodel.EditAccountViewModel
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.isEmailValid
import com.batanks.nextplan.home.isValidPhoneNumber
import com.batanks.nextplan.home.isValidUsername
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.registration.Registration
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.EditUser
import com.batanks.nextplan.swagger.model.UpdatedUser
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_edit_account.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.regex.Pattern

class Edit_Account : BaseAppCompatActivity(), View.OnClickListener {

    private val editAccountViewModel: EditAccountViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                EditAccountViewModel(it)
            }
        }).get(EditAccountViewModel::class.java)
    }

    //var user_obj : User? = null

   private var updated_user : UpdatedUser? = null

    var filePart : MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        tip_edit_account_name.markRequiredInRed()
        tip_edit_account_fname.markRequiredInRed()
        tip_edit_account_pseudonym.markRequiredInRed()
        tip_edit_account_email.markRequiredInRed()
        tip_edit_account_phone_no.markRequiredInRed()

        val userId : Int = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getInt("ID",0)
        val uName : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("USERNAME",null)
        val fName : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("FIRSTNAME",null)
        val lName : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("LASTNAME",null)
        val e_mail : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("EMAIL",null)
        val phNumber : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("PHONENUMBER",null)
        val phone_Number : String = phNumber!!.takeLast(10)
        val phoneCode : String = phNumber!!.take(phNumber.length - 10)
        println(phoneCode)
        val profileImage : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("PROFILEIMAGE",null)

        input_edit_account_name.setText(uName)
        input_edit_account_fname.setText(fName)
        input_edit_account_pseudonym.setText(lName)
        input_edit_account_email.setText(e_mail)
        input_edit_account_phone_no.setText(phone_Number)

        //getSharedPreferences("USER_DETAILS", MODE_PRIVATE).edit().clear().apply()

        //loadingDialog = this.getLoadingDialog(0, R.string.updating_user_please_wait, theme = R.style.AlertDialogCustom)

        editAccountViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {

                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    val updated_user = response.data as UpdatedUser

                    Toast.makeText(this,getString(R.string.account_updated),Toast.LENGTH_SHORT).show()

                    //getSharedPreferences("USER_DETAILS", MODE_PRIVATE).edit().clear().apply()

                    val id: Int? = updated_user?.id
                    val userName: String? = updated_user?.username
                    val firstName: String? = updated_user?.first_name
                    val lastName: String? = updated_user?.last_name
                    val email: String? = updated_user?.email
                    val phoneNumber: String? = updated_user?.phone_number.toString()
                    val image : String? = updated_user?.picture

                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putInt("ID", id!!).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("USERNAME", userName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("FIRSTNAME", firstName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("LASTNAME", lastName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("EMAIL", email).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PHONENUMBER", phoneNumber).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PROFILEIMAGE", image).apply()

                    finishActivity()
                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    Toast.makeText(this,"Username or Password is incorrect",Toast.LENGTH_LONG).show()
                }
            }
        })

       /* if (profileImage != null){

            Glide.with(this).load(profileImage).circleCrop().into(img_edit_account_icon)
        }*/

        edit_account_cancel.setOnClickListener {

           /* val intent = Intent(this, Account :: class.java)
            startActivity(intent)
            finish()*/

            finishActivity()
        }

        edit_account_ok.setOnClickListener {

            if (isValidUsername(tip_edit_account_name.editText?.text.toString()) && tip_edit_account_name.editText?.length()!! >= 2){

                if(tip_edit_account_fname.editText?.length()!! >= 2){

                    if( tip_edit_account_pseudonym.editText?.length()!! >= 2){

                        if (isEmailValid(tip_edit_account_email.editText?.text)){

                            if(isValidPhoneNumber(tip_edit_account_phone_no.editText?.text.toString()) && tip_edit_account_phone_no.editText?.length() ==10){

                                showLoader()

                                val user = EditUser(
                                        email = tip_edit_account_email.editText?.text.toString(),
                                        first_name = tip_edit_account_fname.editText?.text.toString(),
                                        last_name = tip_edit_account_pseudonym.editText?.text.toString(),
                                        phone_number = /*phoneCode*/"+91"+tip_edit_account_phone_no.editText?.text.toString())

                                editAccountViewModel.editUser(user/*,filePart!!*/)

                            } else {

                                tip_edit_account_phone_no.editText?.setError(getString(R.string.phonenumber_condition))
                                tip_edit_account_phone_no.editText?.requestFocus()
                            }

                        } else {

                            tip_edit_account_email.editText?.setError(getString(R.string.valid_email))
                            tip_edit_account_email.editText?.requestFocus()
                        }

                    }else{

                        tip_edit_account_pseudonym.editText?.setError(getString(R.string.lastname_condition))
                        tip_edit_account_pseudonym.editText?.requestFocus()
                    }

                }else {

                    tip_edit_account_fname.editText?.setError(getString(R.string.firstname_condition))
                    tip_edit_account_fname.editText?.requestFocus()
                }

            } else {

                tip_edit_account_name.editText?.setError(getString(R.string.username_condition))
                tip_edit_account_name.editText?.requestFocus()
            }
        }

        txt_edit_account_set_image.setOnClickListener(this)
    }

    private fun finishActivity(){

        val intent = Intent(this, Account :: class.java)
        startActivity(intent)
        finish()
    }

    fun saveToPrefs(user : UpdatedUser){

        val id: Int? = user?.id
        val userName: String? = user?.username
        val firstName: String? = user?.first_name
        val lastName: String? = user?.last_name
        val email: String? = user?.email
        val phoneNumber: String? = user?.phone_number.toString()
        val image : String? = user?.picture

        getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putInt("ID", id!!).apply()
        getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("USERNAME", userName).apply()
        getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("FIRSTNAME", firstName).apply()
        getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("LASTNAME", lastName).apply()
        getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("EMAIL", email).apply()
        getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PHONENUMBER", phoneNumber).apply()
        getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PROFILEIMAGE", image).apply()

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = getCurrentFocus()
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /*fun TextInputLayout.markRequiredInRed() {

        hint = buildSpannedString {
            append(hint)
            color(Color.RED) { append(" *") }
        }
    }*/
/*
    private fun isEmailValid(email: CharSequence?): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidUsername(textToCheck: String?) : Boolean = userNamePattern.matcher(textToCheck).matches()
    private fun isValidPhoneNumber(textToCheck: String?) : Boolean = PhoneNumberPattern.matcher(textToCheck).matches()*/

    companion object {

      /*  val userNamePattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")
        val PhoneNumberPattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")*/

        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.txt_edit_account_set_image -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED){
                        //permission denied
                        val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        //show popup to request runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else{
                    //system OS is < Marshmallow
                    pickImageFromGallery();
                }
            }
            }
        }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            img_edit_account_icon.setImageURI(data?.data)

            var file = File(data!!.data!!.path)
            var requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
            filePart = MultipartBody.Part.createFormData("upload_file", file.name, requestBody)

            //println(data?.data)

            val uri = data!!.data
            val picturePath = getPath(applicationContext, uri) // Write this line under the uri.
            //println(picturePath)

            val bm = BitmapFactory.decodeFile(picturePath)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
            val byteArray: ByteArray = baos.toByteArray()

            val encodedImage: String = Base64.encodeToString(byteArray, Base64.DEFAULT)

            println(encodedImage)
        }
    }

    private fun getPath(applicationContext: Context, uri: Uri?): String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = uri?.let { applicationContext.getContentResolver().query(it, proj, null, null, null) }
        if (cursor != null) {
            if (cursor!!.moveToFirst()) {
                val column_index = cursor!!.getColumnIndexOrThrow(proj[0])
                result = cursor!!.getString(column_index)
            }
            cursor!!.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

}
