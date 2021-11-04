package pe.edu.ulima.pm.work31

import android.content.ContentValues.TAG
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.work31.fragments.FavoritoFragment
import pe.edu.ulima.pm.work31.fragments.PokemonDetalleFragment
import pe.edu.ulima.pm.work31.fragments.PokemonsFragment
import pe.edu.ulima.pm.work31.model.*
import pe.edu.ulima.pm.work31.network.APIPokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),
    PokemonsFragment.OnPokemonSelectedListener,
    FavoritoFragment.OnSelectFavorite,
        PokemonDetalleFragment.OnAddFavorite
{
    var opcion: String? = null
    var a : PokemonManager?= null
    var currentFragment : String?= null
    //pruebas
    var favoritos = ArrayList<PokemonData>()

    var numero = 0;




    private var fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        opcion = intent.getBundleExtra("dataopcion")?.getString("opcion")

//        Toast.makeText(this,opcion,Toast.LENGTH_LONG).show()
        currentFragment = opcion
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //pruebaaa con 60 pokemones

        //termina la prueba con 60 pokemones
        if(fragments.size == 0){
            fragments.add(PokemonsFragment())
        }

        a = PokemonManager().getInstance()

//        favoritos.addAll(a!!.getPokemones())

        if(currentFragment=="listado") changePokemonFragment()
        if(currentFragment=="favoritos") changeFavoritos()

    }

    fun changePokemonFragment(){
        val fragment = PokemonsFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frlayoutMain,fragment)
        ft.commit()
        currentFragment="listado"

    }

    fun changeFavoritos(){
        val fragment = FavoritoFragment(this.favoritos)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frlayoutMain,fragment)
        ft.commit()
        currentFragment="favoritos"
    }



    override fun onSelect(pokemon: PokemonData) {

        changePokemonDetalle(pokemon)
    }

    fun changePokemonDetalle(pokemon: PokemonData){
        val fragment = PokemonDetalleFragment(pokemon)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frlayoutMain,fragment)
        ft.commit()
        currentFragment="detalle"
    }



//    override fun onDelete(pokemon: PokemonData) {
//        favoritos.remove(pokemon)
//        changeFavoritos()
//    }

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

    override fun onFavorite(pokemon: PokemonData) {
        if (pokemon in this.favoritos){
            Toast.makeText(this,"Pokemon is already in favorites",Toast.LENGTH_LONG).show()
        }else {
            favoritos.add(pokemon)
            changePokemonFragment()
        }
    }



    override fun onDelete(pokemon: PokemonData) {
        favoritos.remove(pokemon)
        changeFavoritos()
    }


}