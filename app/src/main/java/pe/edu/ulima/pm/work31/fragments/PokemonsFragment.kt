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
    /*private var sp: SharedPreferences*/
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
        // CAMBIAR CANTIDAD DE POKÉMONS
        PokemonManager().getPokemonsFirebase({
            val recycListadoPokemon= view.findViewById<RecyclerView>(R.id.recycListaPokemons)
            recycListadoPokemon.adapter = PokemonListAdapter(it, this) {
                pokemonId: Int ->
                listener?.onSelect(pokemonId)
            }
        }, {

        })


        /*PokemonManagerAPI(sp).getPokemonesRetrofit(50,{ pokedex : PokemonRespuesta ->
            val recycListadoPokemon= view.findViewById<RecyclerView>(R.id.recycListaPokemons)
            recycListadoPokemon.adapter = PokemonListAdapter(
                pokedex.results,
                this,
                sp
            ) { pokemonId: Int ->
                listener?.onSelect(pokemonId)
            } },{
        })*/

    }
}