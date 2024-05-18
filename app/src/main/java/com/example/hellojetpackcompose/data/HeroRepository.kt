package com.example.hellojetpackcompose.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.hellojetpackcompose.model.Hero
import com.example.hellojetpackcompose.model.HeroesData
import com.example.hellojetpackcompose.model.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


/**
 * Created by Alo-BambangHariantoSianturi on 25/10/23.
 */
class HeroRepository {

    companion object {
        @Volatile
        private var instance: HeroRepository? = null

        fun getInstance(): HeroRepository =
            instance ?: synchronized(this) {
                HeroRepository().apply {
                    instance = this
                }
            }
    }

    private val heroes = mutableStateListOf<User>()
    var mIdUser: Long = 0
    private val mRef = FirebaseDatabase.getInstance().reference.child("users")

    init {
        firebaseEventListener()
    }

    private fun firebaseEventListener() {
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    mIdUser = snapshot.childrenCount
                    Log.d("ID USER", "ID USER IS: $mIdUser")
                } else {
                    mIdUser = 0
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        mRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.getValue(User::class.java)!!
                heroes.add(data)
                Log.d("SUCCESS", "VALUE IS $heroes")
                Log.d("INDEX TEST", "INDEX: ${snapshot.childrenCount}")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.getValue(User::class.java)!!
                heroes.add(data)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getHeroes(): List<User> {
        return heroes
    }

    fun searchHeroes(query: String): List<Hero> {
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getHeroesById(heroId: String): User {
        return heroes.first {
            it.name == heroId
        }
    }

    fun addItemVehicle(user: User): User {
        mRef.child((mIdUser + 1).toString()).setValue(user)
        return user
    }
}