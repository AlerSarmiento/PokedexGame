package pe.edu.ulima.pm.work31.network

import pe.edu.ulima.pm.work31.model.Apivarible
import pe.edu.ulima.pm.work31.model.PokemonData
import pe.edu.ulima.pm.work31.model.PokemonRespuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://pokeapi.co/api/v2
interface APIPokemonService {

    @GET("pokemon")
    fun getPokemones(@Query("limit") limit: Int, @Query("offset") offset:Int) : Call<PokemonRespuesta>


    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id:Int) : Call<Apivarible>
}