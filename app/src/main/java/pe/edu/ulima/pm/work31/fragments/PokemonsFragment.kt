package pe.edu.ulima.pm.work31.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.work31.R
import pe.edu.ulima.pm.work31.adapter.PokemonListAdapter
import pe.edu.ulima.pm.work31.model.*

class PokemonsFragment(
    private var sp: SharedPreferences
): Fragment() {
    interface OnPokemonSelectedListener {
        fun onSelect(pokemonId: Int)
    }

    private var listener : OnPokemonSelectedListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPokemonSelectedListener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemons,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            recycListadoPokemon.layoutManager = GridLayoutManager(context, 2)
//            Toast.makeText(context,"RAAA",Toast.LENGTH_LONG).show()
//        }
//        else {
//            recycListadoPokemon.layoutManager = GridLayoutManager(context, 1)
//            Toast.makeText(context,"GG",Toast.LENGTH_LONG).show()
//        }

        PokemonManagerAPI(sp).getPokemonesRetrofit(3,{ pokedex : PokemonRespuesta ->
            val recycListadoPokemon= view.findViewById<RecyclerView>(R.id.recycListaPokemons)
            recycListadoPokemon.adapter = PokemonListAdapter(
                pokedex.results,
                this,
                sp
            ) { pokemonId: Int ->
                listener?.onSelect(pokemonId)
            }
            println(PokemonManager().getInstance().getPokemones().size)
            //println(PokemonManager().getInstance().getPokemon(1).name)
        },{

        })

    }
}