package pe.edu.ulima.pm.work31.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pe.edu.ulima.pm.work31.R
import pe.edu.ulima.pm.work31.adapter.PokemonListAdapter

class PokemonManager() {
    private val dbFirebase = Firebase.firestore
    fun addPokemonFirebase(
        id: Int, //convertir a string
        nombre: String,
        img: String,
        hp: Int,
        attack: Int, // convertir a long
        defense: Int,
        special_attack: Int,
        special_defense: Int,
        callbackOK : (Long) -> Unit,
        callbackError: (String) -> Unit){

        val data = hashMapOf<String, Any>(
            "nombre" to nombre,
            "img" to img,
            "hp" to hp,
            "attack" to attack.toLong(),
            "defense" to defense.toLong(),
            "special_attack" to special_attack.toLong(),
            "special_defense" to special_defense.toLong()
        )
        dbFirebase.collection("pokemons").document(
            id.toString()
        ).set(data).addOnSuccessListener {
                callbackOK(id.toLong())
        }.addOnFailureListener {
            callbackError(it.message!!)
        }
    }
    fun getPokemonById(id: Int, callbackOK: (Pokemon) -> Unit){
        dbFirebase.collection("pokemons").get().addOnSuccessListener { res ->
            lateinit var p : Pokemon
            for (document in res) {
                if(document.id.toInt() == id){
                    p = Pokemon(
                        document.id.toInt(),
                        document.data["img"]!! as String,
                        document.data["nombre"]!! as String,
                        document.data["hp"]!! as Long,
                        document.data["attack"]!! as Long,
                        document.data["defense"]!! as Long,
                        document.data["special_attack"]!! as Long,
                        document.data["special_defense"]!! as Long,
                    )
                }
            }
            callbackOK(p)
        }
    }
    fun getPokemonsFirebase(callbackOK : (List<Pokemon>) -> Unit, callbackError: (String) -> Unit){
        dbFirebase.collection("pokemons").get()
            .addOnSuccessListener { res ->
                // si no hay pokemons guardados
                if (res.size() == 1){
                    // añadimos pokemons
                    for (i in 1..50){
                        PokemonManagerAPI().getPokemonRetrofit(
                            i,
                            {pokemon  : Apivarible ->
                                addPokemonFirebase(
                                    i,
                                    pokemon.name.capitalize(),
                                    pokemon.sprites.front_default,
                                    pokemon.stats[0].base_stat,
                                    pokemon.stats[1].base_stat,
                                    pokemon.stats[2].base_stat,
                                    pokemon.stats[3].base_stat,
                                    pokemon.stats[4].base_stat,
                                    {
                                        Log.e("MENSAJE", "Añadido pokemon "+i)
                                    },
                                    {
                                        Log.e("MENSAJE", it)
                                    }
                                    )
                                /*holder.nombre.text = pokemon.name.capitalize()
                                holder.ataque.text = String.format("Attack : %s",pokemon.stats[1].base_stat.toString())
                                holder.defensa.text = String.format("Defense : %s",pokemon.stats[2].base_stat.toString())
                                holder.vida.text = String.format("HP : %s",pokemon.stats[0].base_stat.toString())
                                holder.especial_ata.text = String.format("Special attack: %s",pokemon.stats[3].base_stat.toString())
                                holder.especial_def.text = String.format("Special defense : %s",pokemon.stats[4].base_stat.toString())
                                Glide.with(fragment)
                                    .load(pokemon.sprites.front_default)
                                    .fitCenter()
                                    .into(holder.imgPokemon)*/

                            },{ error ->
                                Log.e("MENSAJE", "Error en añadir Pokemons")
                            }
                        )
                    }
                }
                // devolviendo pokemons
                Log.e("MENSAJE", "Devolviendo Pokemons")
                Log.e("MENSAJE", "Tamaño res: "+res.size())
                val pkms = arrayListOf<Pokemon>()
                for (document in res) {
                    if(document.id.toInt() != 0){
                        val p = Pokemon(
                            document.id.toInt(),
                            document.data["img"]!! as String,
                            document.data["nombre"]!! as String,
                            document.data["hp"]!! as Long,
                            document.data["attack"]!! as Long,
                            document.data["defense"]!! as Long,
                            document.data["special_attack"]!! as Long,
                            document.data["special_defense"]!! as Long,
                        )
                        pkms.add(p)
                    }
                }
                callbackOK(pkms)
            }
    }
}