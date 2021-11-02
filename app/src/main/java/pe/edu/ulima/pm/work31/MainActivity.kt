package pe.edu.ulima.pm.work31

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.work31.fragments.FavoritoFragment
import pe.edu.ulima.pm.work31.fragments.PokemonDetalleFragment
import pe.edu.ulima.pm.work31.fragments.PokemonsFragment
import pe.edu.ulima.pm.work31.model.Pokemon
import pe.edu.ulima.pm.work31.model.PokemonManager

class MainActivity : AppCompatActivity(),
    PokemonsFragment.OnPokemonSelectedListener,
    FavoritoFragment.OnSelectFavorite,
        PokemonDetalleFragment.OnAddFavorite
{
    var opcion: String? = null
    var a : PokemonManager?= null
    var currentFragment : String?= null
    var favoritos = ArrayList<Pokemon>()

    private var fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        opcion = intent.getBundleExtra("dataopcion")?.getString("opcion")

//        Toast.makeText(this,opcion,Toast.LENGTH_LONG).show()
        currentFragment = opcion
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(fragments.size == 0){
            fragments.add(PokemonsFragment())
        }

        a = PokemonManager().getInstance()

//        favoritos.addAll(a!!.getPokemones())

        if(currentFragment=="listado") changePokemonFragment()
        if(currentFragment=="favoritos") changeFavoritos()

    }

    fun changePokemonFragment(){
        val fragment = fragments[0]
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

    override fun onSelect(pokemon: Pokemon) {
        changePokemonDetalle(pokemon)
    }

    fun changePokemonDetalle(pokemon: Pokemon){
        val fragment = PokemonDetalleFragment(pokemon)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frlayoutMain,fragment)
        ft.commit()
        currentFragment="detalle"
    }



    override fun onDelete(pokemon: Pokemon) {
        favoritos.remove(pokemon)
        changeFavoritos()
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

    override fun onFavorite(pokemon: Pokemon) {
        if (pokemon in this.favoritos){
            Toast.makeText(this,"Pokemon is already in favorites",Toast.LENGTH_LONG).show()
        }else {
            favoritos.add(pokemon)
            changePokemonFragment()
        }
    }


}