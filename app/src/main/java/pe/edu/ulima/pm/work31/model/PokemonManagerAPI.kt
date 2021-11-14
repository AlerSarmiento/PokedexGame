package pe.edu.ulima.pm.work31.model


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.google.gson.Gson
import pe.edu.ulima.pm.work31.network.APIPokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.reflect.TypeToken
import pe.edu.ulima.pm.work31.room.IRAppDatabase
import kotlin.coroutines.coroutineContext


class PokemonManagerAPI(context: Context) {
    private val dbFirebase = Firebase.firestore
    private val db = Room.databaseBuilder(
        context,
        IRAppDatabase::class.java, "db_imagen").allowMainThreadQueries().build()

    fun savePokemon(codigo:Double,
                    imagen: String,
                    name: String,
                    hp: Double,
                    attack: Double,
                    defense: Double,
                    special_attack: Double,
                    special_defense: Double
                    ){
        val data = hashMapOf<String, Any>(
            "codigo" to codigo,
            "imagen" to imagen,
            "name" to name,
            "hp" to hp,
            "attack" to attack,
            "defense" to defense,
            "special_attack" to special_attack,
            "special_defense" to special_defense
        )

        val pokeId = System.currentTimeMillis()

        dbFirebase.collection("pokemones").document(
            pokeId.toString()
        )
            .set(data)
    }

    //funcion que consigue lista de pokemones a lambda
    fun getPokemonesFirebase(callbackOK: (List<Pokemon>) -> Unit, callbackError: (String) -> Unit){
        dbFirebase.collection("pokemones")
            .orderBy("codigo")
            .get()
            .addOnSuccessListener {
                res ->
                var pokemones = arrayListOf<Pokemon>()
                for (document in res){
                    println()
                    val poke = Pokemon(
                        document.id.toLong(),
                        (document.data["codigo"]!! as Double).toInt(),
                        document.data["imagen"]!! as String,
                        document.data["name"]!! as String,
                        (document.data["hp"]!! as Double).toInt(),
                        (document.data["attack"]!! as Double).toInt(),
                        (document.data["defense"]!! as Double).toInt(),
                        (document.data["special_attack"]!! as Double).toInt(),
                        (document.data["special_defense"]!! as Double).toInt()
                    )
                    pokemones.add(poke)
                }
                saveIntoRoom(pokemones)
                callbackOK(pokemones)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }


    fun getPokemonName(camino:String,callbackOK: (String) -> Unit,callbackError: (String) -> Unit){
        dbFirebase.collection("pokemones")
            .document(camino)
            .get()
            .addOnSuccessListener { res->
                callbackOK(res.data!!["name"] as String)
            }
            .addOnFailureListener { callbackError(it.message!!) }
    }

    fun getImagenByRoom(codigo:Long):String{
        return db.ImagenDAO().findById(codigo).URL
    }

    private fun saveIntoRoom(pokemones: List<Pokemon> ){
        pokemones.forEach{
            db.ImagenDAO().insert(ImagenURL(it.codigo.toLong(),it.imagen))
        }
    }




    val API_URL = "https://pokeapi.co/api/v2/"
//callbackOK: (List<PokemonData>),
//    callbackError:(String)

//    // revisar
//    fun getPokemonesRetrofit(a:Int, callbackOK : (PokemonRespuesta) -> Unit, callbackError : (String) -> Unit){
//    val retrofit = Retrofit.Builder()
//        .baseUrl(API_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    val service = retrofit!!.create(APIPokemonService::class.java)
//        service.getPokemones(a,0).enqueue(object : Callback<PokemonRespuesta> {
//            override fun onResponse(
//                call: Call<PokemonRespuesta>,
//                response: Response<PokemonRespuesta>
//            ) {
//                callbackOK(response.body()!!)
//            }
//            override fun onFailure(call: Call<PokemonRespuesta>, t: Throwable) {
//                Log.e("PokemonManagerAPI", t.message!!)
//            }
//        })
//    }
//    //funcion get pokemon singular
//    fun getPokemonRetrofit(b:Int, callbackOK : (Apivarible) -> Unit, callbackError : (String) -> Unit){
//
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit!!.create(APIPokemonService::class.java)
//
//        service.getPokemon(b).enqueue(object : Callback<Apivarible> {
//            override fun onResponse(
//                call: Call<Apivarible>,
//                response: Response<Apivarible>
//            ) {
//                callbackOK(response.body()!!)
//                var editor = sp.edit()
//                // Guardamos instancia de pokemonmanager luego de que se hayan cargado todos los pokemons
//                var gson = Gson()
//                var json: String = gson.toJson(PokemonManager().getInstance())
//                editor.putString("LIST_POKEMONS", json)
//                editor.commit()
//            }
//
//            override fun onFailure(call: Call<Apivarible>, t: Throwable) {
//                Log.e("PokemonManagerAPI", t.message!!)
//            }
//
//        })
//
//
//
//    }




}