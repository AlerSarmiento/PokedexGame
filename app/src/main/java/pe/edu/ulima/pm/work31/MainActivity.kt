package pe.edu.ulima.pm.work31

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pe.edu.ulima.pm.work31.fragments.FavoritoFragment
import pe.edu.ulima.pm.work31.fragments.PokemonDetalleFragment
import pe.edu.ulima.pm.work31.fragments.PokemonsFragment
import pe.edu.ulima.pm.work31.fragments.StoredPokemonsFragment
import pe.edu.ulima.pm.work31.model.*

class MainActivity : AppCompatActivity(),
    PokemonsFragment.OnPokemonSelectedListener,
    StoredPokemonsFragment.OnPokemonSelectedListener2,
    FavoritoFragment.OnSelectFavorite,
    PokemonDetalleFragment.OnAddFavorite
{
    var opcion: String? = null
    var currentFragment : String?= null
    lateinit var favoritosIds: ArrayList<Int>
    /*lateinit var sp: SharedPreferences*/

    override fun onCreate(savedInstanceState: Bundle?) {
        /*sp = getSharedPreferences("POKEMON_INFO", Context.MODE_PRIVATE)
        if(sp.getString("LIST_FAVORITOS","")=="") favoritosIds = ArrayList<Int>()
        else favoritosIds = Gson().fromJson(sp.getString("LIST_FAVORITOS",""), object : TypeToken<ArrayList<Int>?>(){}.type)*/
        opcion = intent.getBundleExtra("dataopcion")?.getString("opcion")
        currentFragment = opcion
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(currentFragment=="listado") changePokemonFragment()
        if(currentFragment=="favoritos") changeFavoritos()
    }

    fun changePokemonFragment(){
        setTitle("Pokémons")
        var fragment:Fragment = PokemonsFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frlayoutMain,fragment)
        ft.commit()
        currentFragment="listado"
    }

    fun changeFavoritos(){
        setTitle("Favoritos")
        FavoritosManager().getInstance().getFavoritos {
            val fragment = FavoritoFragment(it)
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.frlayoutMain,fragment)
            ft.commit()
            currentFragment="favoritos"
        }
        /*
        val fragment = FavoritoFragment(favoritosIds,sp)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frlayoutMain,fragment)
        ft.commit()
        currentFragment="favoritos"*/
    }


    override fun onSelect(pokemonId: Int) {
        changePokemonDetalle(pokemonId)
    }

    override fun onSelect2(pokemonId: Int) {
        changePokemonDetalle(pokemonId)
    }

    fun changePokemonDetalle(pokemonId: Int){
        //var pm: PokemonManager = Gson().fromJson(sp.getString("LIST_POKEMONS",""), object : TypeToken<PokemonManager?>(){}.type)
        PokemonManager().getPokemonById(pokemonId){
            setTitle(it.name)
            var fragment = PokemonDetalleFragment(it)
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.frlayoutMain,fragment)
            ft.commit()
            currentFragment="detalle"
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(currentFragment == "listado"){
            val recycListadoPokemon= findViewById<RecyclerView>(R.id.recycListaPokemons)
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recycListadoPokemon.layoutManager = GridLayoutManager(this, 2)
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                recycListadoPokemon.layoutManager = GridLayoutManager(this, 1)
            }
        }
    }

    override fun onFavorite(pokemonId: Int) {
        FavoritosManager().getInstance().getFavoritos {
            if (pokemonId in it){
                Toast.makeText(this,"Pokemon is already in favorites",Toast.LENGTH_LONG).show()
            } else {
                FavoritosManager().getInstance().addFavorito(pokemonId, {
                    changePokemonFragment()
                }, {

                })
            }
        }
    }

    override fun onDelete(pokemonId: Int) {
        FavoritosManager().getInstance().eliminarFavorito(pokemonId){
            changeFavoritos()
        }
    }

    fun updateStoredFav(){
        /*var editor = sp.edit()
        var jsonF: String = Gson().toJson(favoritosIds)
        editor.putString("LIST_FAVORITOS", jsonF)
        editor.commit()
        favoritosIds = Gson().fromJson(sp.getString("LIST_FAVORITOS",""), object : TypeToken<ArrayList<Int>?>(){}.type)*/
    }
    override fun onBackPressed() {
        if(currentFragment == "detalle") {
            changePokemonFragment()
        }
        else super.onBackPressed()
    }
}