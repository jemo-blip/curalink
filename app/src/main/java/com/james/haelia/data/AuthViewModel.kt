package com.james.haelia.data


import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.james.haelia.model.User
import com.james.haelia.ui.theme.navigation.ROUT_HOME
import com.james.haelia.ui.theme.navigation.ROUT_LOGIN
import com.james.haelia.ui.theme.navigation.ROUT_SIGNUP
import com.james.haelia.ui.theme.navigation.ROUT_SERVICE


class AuthViewModel(var navController: NavController, var context: Context) {
    val mAuth: FirebaseAuth

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    fun signup(name: String, email: String, password: String, confirmpassword: String) {

        if (email.isBlank() || password.isBlank() || confirmpassword.isBlank()) {
            Toast.makeText(context, "Please email and password cannot be blank", Toast.LENGTH_LONG)
                .show()
        } else if (password != confirmpassword) {
            Toast.makeText(context, "Password do not match", Toast.LENGTH_LONG).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userdata = User(name, email, password, mAuth.currentUser!!.uid)
                    val regRef = FirebaseDatabase.getInstance().getReference()
                        .child("Users/" + mAuth.currentUser!!.uid)
                    regRef.setValue(userdata).addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate(ROUT_SERVICE)
                        } else {
                            Toast.makeText(context, "${it.exception!!.message}", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate(ROUT_SIGNUP)
                        }
                    }
                } else {
                    navController.navigate(ROUT_SIGNUP)
                }

            }
        }

    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUT_SERVICE) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            } else {
                val errorMessage = when {
                    task.exception?.message?.contains("no user record") == true -> 
                        "No account found with this email. Please sign up first."
                    task.exception?.message?.contains("password is invalid") == true -> 
                        "Incorrect password. Please try again."
                    else -> "Login failed: ${task.exception?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun logout() {
        mAuth.signOut()
        navController.navigate(ROUT_LOGIN) {
            popUpTo(ROUT_SERVICE) { inclusive = true }
        }
    }

    fun isLoggedIn(): Boolean = mAuth.currentUser != null


}
