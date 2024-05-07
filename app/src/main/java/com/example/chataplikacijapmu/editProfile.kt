package com.example.chataplikacijapmu
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var buttonSave: Button

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextNewPassword = findViewById(R.id.editTextNewPassword)
        buttonSave = findViewById(R.id.buttonSave)

        firebaseAuth = Firebase.auth
        database = Firebase.database.reference

        val currentUser = firebaseAuth.currentUser
        val userId = currentUser?.uid

        // Učitavanje podataka o korisniku iz Firebasea i postavljanje polja za uređivanje
        userId?.let {
            database.child("users").child(it).get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let { user ->
                        editTextName.setText(user.name)
                        editTextEmail.setText(user.email)
                    }
                }
            }.addOnFailureListener {
                // Greška prilikom dohvata podataka o korisniku
            }
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val newPassword = editTextNewPassword.text.toString().trim()

            val userMap = hashMapOf(
                "name" to name,
                "email" to email
                // Dodajte ostale podatke koje želite spremiti
            )

            userId?.let {
                database.child("users").child(it).updateChildren(userMap)
                    .addOnSuccessListener {
                        // Uspješno spremanje promjena
                    }
                    .addOnFailureListener { exception ->
                        // Greška prilikom spremanja promjena
                    }
            }

            // Promjena lozinke
            if (newPassword.isNotEmpty()) {
                currentUser?.updatePassword(newPassword)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Uspješna promjena lozinke
                        } else {
                            // Greška prilikom promjene lozinke
                        }
                    }
            }
        }
    }
}
