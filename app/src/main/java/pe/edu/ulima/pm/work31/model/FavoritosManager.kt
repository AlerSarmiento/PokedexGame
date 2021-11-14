package pe.edu.ulima.pm.work31.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoritosManager {

    companion object {
        private var instance: FavoritosManager? = null
    }

    fun getInstance(): FavoritosManager {
        if (instance == null) {
            instance = FavoritosManager()
        }
        return instance!!
    }

    private val dbFirebase = Firebase.firestore
    fun addFavorito(id: Int,
                    callbackOK : (Long) -> Unit,
                    callbackError: (String) -> Unit){

        val data = hashMapOf<String, Any>(
            "pokemonId" to id.toString()
        )
        dbFirebase.collection("favoritos").document(
            id.toString()
        ).set(data).addOnSuccessListener {
            callbackOK(id.toLong())
        }.addOnFailureListener {
            callbackError(it.message!!)
        }
    }
    fun eliminarFavorito(pokemonId: Int, callbackOK: (String) -> Unit){
        dbFirebase.collection("favoritos").get()
            .addOnSuccessListener { res ->
                for(document in res){
                    if(document.id.toInt() == pokemonId) dbFirebase.collection("favoritos").document(document.id).delete()
                }
                callbackOK("listo")
            }

    }
    fun getFavoritos(callbackOK: (ArrayList<Int>) -> Unit){
        dbFirebase.collection("favoritos").get()
            .addOnSuccessListener { res ->
                val listaFavs = ArrayList<Int>()
                for(document in res){
                    if(document.id.toInt() != 0) listaFavs.add(document.data["pokemonId"]!!.toString().toInt())
                }
                callbackOK(listaFavs)
            }
    }
}