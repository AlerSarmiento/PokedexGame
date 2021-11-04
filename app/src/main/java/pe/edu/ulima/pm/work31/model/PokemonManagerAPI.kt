package pe.edu.ulima.pm.work31.model

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import pe.edu.ulima.pm.work31.network.APIPokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokemonManagerAPI {
    val API_URL = "https://pokeapi.co/api/v2/"
//callbackOK: (List<PokemonData>),
//    callbackError:(String)
    fun getPokemonesRetrofit(a:Int, callbackOK : (PokemonRespuesta) -> Unit, callbackError : (String) -> Unit){


    val retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit!!.create(APIPokemonService::class.java)

        service.getPokemones(0,a).enqueue(object : Callback<PokemonRespuesta> {
            override fun onResponse(
                call: Call<PokemonRespuesta>,
                response: Response<PokemonRespuesta>
            ) {
                callbackOK(response.body()!!)

            }

            override fun onFailure(call: Call<PokemonRespuesta>, t: Throwable) {
                Log.e("PokemonManagerAPI", t.message!!)

            }

        })



    }



    //funcion get pokemon singular

    fun getPokemonRetrofit(b:Int, callbackOK : (Apivarible) -> Unit, callbackError : (String) -> Unit){


        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit!!.create(APIPokemonService::class.java)

        service.getPokemon(b).enqueue(object : Callback<Apivarible> {
            override fun onResponse(
                call: Call<Apivarible>,
                response: Response<Apivarible>
            ) {
                callbackOK(response.body()!!)
            }

            override fun onFailure(call: Call<Apivarible>, t: Throwable) {
                Log.e("PokemonManagerAPI", t.message!!)
            }

        })



    }




}