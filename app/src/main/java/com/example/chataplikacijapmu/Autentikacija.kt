package com.example.chataplikacijapmu
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AuthentificationActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonAction: Button
    private lateinit var buttonToggle: Button

    private lateinit var firebaseAuth: FirebaseAuth

    private var isRegisterMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.autentikacija)

        editTextEmail = findViewById(R.id.Email)
        editTextPassword = findViewById(R.id.Password)
        buttonAction = findViewById(R.id.Register)
        buttonToggle = findViewById(R.id.Login)

        firebaseAuth = FirebaseAuth.getInstance()

        buttonToggle.setOnClickListener {
            toggleMode()
        }
    }

    private fun toggleMode() {
        isRegisterMode = !isRegisterMode
        if (isRegisterMode) {
            buttonAction.text = "Registriraj"
            buttonToggle.text = "Već imate račun? Prijavite se."
        } else {
            buttonAction.text = "Prijavi se"
            buttonToggle.text = "Nemate račun? Registrirajte se."
        }
    }

    private fun registerUser() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Unesite email!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Unesite lozinku!", Toast.LENGTH_SHORT).show()
            return
        }

        // Registracija korisnika s Firebaseom
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Korisnik je uspješno registriran
                    Toast.makeText(applicationContext, "Registracija uspješna!", Toast.LENGTH_SHORT).show()
                } else {
                    // Došlo je do greške prilikom registracije korisnika
                    Toast.makeText(applicationContext, "Greška prilikom registracije: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginUser() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Unesite email!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Unesite lozinku!", Toast.LENGTH_SHORT).show()
            return
        }

        // Prijava korisnika s Firebaseom
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Korisnik je uspješno prijavljen
                    Toast.makeText(applicationContext, "Prijava uspješna!", Toast.LENGTH_SHORT).show()
                    // Ovdje možete otvoriti glavnu aktivnost ili neku drugu nakon prijave
                } else {
                    // Došlo je do greške prilikom prijave korisnika
                    Toast.makeText(applicationContext, "Greška prilikom prijave: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
