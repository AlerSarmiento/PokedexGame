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
import pe.edu.ulima.pm.work31.model.*

class MainActivity : AppCompatActivity(),
    PokemonsFragment.OnPokemonSelectedListener,
    FavoritoFragment.OnSelectFavorite,
        PokemonDetalleFragment.OnAddFavorite
{
    var opcion: String? = null
    var a : PokemonManager?= null
    var currentFragment : String?= null
    //pruebas
    var favoritos = ArrayList<Pokemon>()
    var pokemones = ArrayList<Pokemon>()
    var numero = 0;




    private var fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        opcion = intent.getBundleExtra("dataopcion")?.getString("opcion")

//        Toast.makeText(this,opcion,Toast.LENGTH_LONG).show()
        currentFragment = opcion
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //pruebaaa con 60 pokemones
        CargarPokemones()
        //termina la prueba con 60 pokemones
        if(fragments.size == 0){
            fragments.add(PokemonsFragment(pokemones))
        }

        a = PokemonManager().getInstance()

//        favoritos.addAll(a!!.getPokemones())

        if(currentFragment=="listado") changePokemonFragment()
        if(currentFragment=="favoritos") changeFavoritos()

    }

    fun changePokemonFragment(){
        val fragment = PokemonsFragment(pokemones)
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



    //ESTO ES UNA PRUEBA

    fun CargarPokemones(){
        var link: String? = "https://pokeapi.co/api/v2/pokemon"
        //prueba con los 60 primeros pokemones
        var a=0;
        var aumentador = 0
        while(a<3) {

            PokemonManagerAPI().getPokemonesRetrofit(aumentador,
                { respuesta: PokemonRespuesta ->

                    aumentador=aumentador+20
                    for (i in respuesta.results) {

                        var codigo = ConseguirCodigo(i.url)
                        PokemonManagerAPI().getPokemonRetrofit(codigo,
                            { datito: Apivarible ->
//                                Toast.makeText(this, "RAAA: " + datito.name, Toast.LENGTH_LONG)
//                                    .show()
                                agregarPokemonLista(datito)
                                changePokemonFragment()
                            },
                            { error ->
                                Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show()
                            }

                        )


                    }

                },
                { error ->
                    Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show()
                }


            )

        a++
        }

    }





    fun agregarPokemonLista(pokemonnuevo: Apivarible){
        var nuevo = Pokemon(pokemonnuevo.id,
        pokemonnuevo.sprites.front_default,
            pokemonnuevo.name,
            pokemonnuevo.stats.get(0).base_stat,

            pokemonnuevo.stats.get(1).base_stat,
            pokemonnuevo.stats.get(2).base_stat,
            pokemonnuevo.stats.get(3).base_stat,
            pokemonnuevo.stats.get(4).base_stat
            )


        pokemones.add(nuevo)
    }

    fun  ConseguirCodigo(a : String):Int{
        var lista = a.split("/")

        return Integer.parseInt(lista.get(lista.size-2))
    }





    //AQUI TERMINA LA PRUEBA


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