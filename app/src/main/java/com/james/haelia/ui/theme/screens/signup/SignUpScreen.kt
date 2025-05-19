package com.james.haelia.ui.theme.screens.signup

//import android.R.attr.name
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.icon.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.james.haelia.ui.theme.navigation.ROUT_LOGIN
//import com.james.haelia.ui.theme.navigation.ROUT_SERVICE
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import com.james.haelia.data.AuthViewModel

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isFormValid = username.isNotBlank() && email.isNotBlank() &&
            password.length >= 6 && password == confirmPassword

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1C1C1C), Color(0xFF323232)))),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Your Account",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    //colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0xFF2196F3))
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    //colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0xFF2196F3))
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                   // colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0xFF2196F3))
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                   // colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0xFF2196F3))
                )

                Spacer(modifier = Modifier.height(24.dp))
                val context = LocalContext.current
                val authViewModel = AuthViewModel(navController, context)
                Button(
                    onClick = {
                        authViewModel.signup(username, email, password,confirmPassword)

                        if (isFormValid) {
                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUT_LOGIN)
                        } else {
                            Toast.makeText(
                                context,
                                "Check inputs. Passwords must match and be at least 6 characters.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    enabled = isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sign Up", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { navController.navigate(ROUT_LOGIN) }) {
                    Text("Already have an account? Log in", color = Color(0xFF2196F3))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}
