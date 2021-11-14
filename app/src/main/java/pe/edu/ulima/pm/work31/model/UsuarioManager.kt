package pe.edu.ulima.pm.work31.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Error

class UsuarioManager {
    companion object{
        var instance : UsuarioManager = UsuarioManager()
            private set
    }

    private val dbFirebase = Firebase.firestore

    fun saveUser(
        nombre:String,
        callbackOK: (Long)->Unit,
        callbackError: (String)->Unit
    ){
        val favoritos = arrayListOf<Pokemon>()

        val data = hashMapOf<String, Any>(
            "nombre" to nombre,
            "favoritos" to favoritos
        )

        val userId = System.currentTimeMillis()

        dbFirebase.collection("usuarios").document(
            userId.toString()
        )
            .set(data)
            .addOnSuccessListener {
                callbackOK(userId)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }

    }

    fun CheckUser(nombre: String, callbackOK: (Long) -> Unit, callbackError: (String) -> Unit){

        dbFirebase.collection("usuarios")
            .get()
            .addOnSuccessListener {res->
                var check : Long = 0
                for(document in res){
                    if((document.data["name"]!! as String).equals(nombre)) check = document.id.toLong()
                }
                callbackOK(check)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }

    fun getFavoritos(codigo: Long, callbackOK: (List<DocumentReference>) -> Unit,callbackError: (String) -> Unit){
        dbFirebase.collection("usuarios")
            .document(codigo.toString())
            .get()
            .addOnSuccessListener {
                res->
                callbackOK(res.data!!["favoritos"] as List<DocumentReference>)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }

    fun updateFavoritos(codigoU: String, listafav: List<DocumentReference>){
        val data = hashMapOf<String,Any>(
            "favoritos" to listafav
        )
        dbFirebase.collection("usuarios")
            .document(codigoU)
            .update(data)
    }



}