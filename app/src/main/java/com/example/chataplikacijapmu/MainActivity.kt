package com.example.chataplikacijapmu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chataplikacijapmu.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private val database:DatabaseReference=FirebaseDatabase.getInstance("https://chatapppmu-4876d-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
    var tekst=ArrayList<Poruka>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Unesi.setOnClickListener{
            val tempText=binding.Text.text.toString()
            val tempPosiljatelj=binding.Posiljatelj.text.toString()
            var tempId=0
            if(!tekst.isEmpty())    tempId=tekst[tekst.size-1].id+1

            tekst.add(Poruka(tempId,tempText,tempPosiljatelj))

            database.setValue(tekst)
        }

        database.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                tekst.clear()
                try{
                    val a:List<Poruka> = snapshot.children.map{dataSnapshot -> dataSnapshot.getValue(Poruka::class.java)!! }

                    tekst.addAll(a)
                }catch (e:Exception){}

                binding.recyclerViewMessages.apply {
                    layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
                    adapter=TextAdapter(tekst, this@MainActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        }
    }
