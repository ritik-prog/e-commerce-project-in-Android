package com.example.ecommerce

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var CreateAccountButton: Button
    private lateinit var InputName: EditText
    private lateinit var InputPhoneNumber: EditText
    private lateinit var InputPassword: EditText
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        CreateAccountButton = findViewById(R.id.register_btn)
        InputName = findViewById(R.id.register_user_name_input)
        InputPhoneNumber = findViewById(R.id.register_phone_number_input)
        InputPassword = findViewById(R.id.register_password_input)
        loadingBar = ProgressDialog(this)

        CreateAccountButton.setOnClickListener{
            CreateAccount()
        }

    }

    private fun CreateAccount() {
        var name = InputName.text.toString()
        var phone = InputPhoneNumber.text.toString()
        var password = InputPassword.text.toString()

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Write your name... ", Toast.LENGTH_SHORT).show()
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Write your phone number... ", Toast.LENGTH_SHORT).show()
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Write your password... ", Toast.LENGTH_SHORT).show()
        }
        else{
            loadingBar.setTitle("Create Account")
            loadingBar.setMessage("Please wait, while we validate your information.")
            loadingBar.setCanceledOnTouchOutside(false)
            loadingBar.show()

            ValidatePhoneNumber(name, phone, password)

        }


    }

    private fun ValidatePhoneNumber(name: String, phone: String, password: String) {

        val RootRef:DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()

        RootRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val post = dataSnapshot.getValue(String::class.java)
                //Update the UI with received data
                if (!(dataSnapshot.child("Users").child(phone).exists())){

                }
                else{
                    Toast.makeText(this@RegisterActivity, "This " + phone + "already exists.", Toast.LENGTH_SHORT).show()
                    loadingBar.dismiss()
                    Toast.makeText(this@RegisterActivity, "Please try another number", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })//Listener

    }//end of method
}
