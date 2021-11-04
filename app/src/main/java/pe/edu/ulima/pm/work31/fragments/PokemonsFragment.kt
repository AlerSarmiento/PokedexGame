package pe.edu.ulima.pm.work31.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.work31.R
import pe.edu.ulima.pm.work31.adapter.PokemonListAdapter
import pe.edu.ulima.pm.work31.model.Pokemon
import pe.edu.ulima.pm.work31.model.PokemonManager

class PokemonsFragment(val pokedex: List<Pokemon>): Fragment() {
    interface OnPokemonSelectedListener {
        fun onSelect(pokemon: Pokemon)
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

        val recycListadoPokemon= view.findViewById<RecyclerView>(R.id.recycListaPokemons)
//        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            recycListadoPokemon.layoutManager = GridLayoutManager(context, 2)
//            Toast.makeText(context,"RAAA",Toast.LENGTH_LONG).show()
//        }
//        else {
//            recycListadoPokemon.layoutManager = GridLayoutManager(context, 1)
//            Toast.makeText(context,"GG",Toast.LENGTH_LONG).show()
//        }

        recycListadoPokemon.adapter = PokemonListAdapter(pokedex,
            this
        ) {
                pokemon: Pokemon ->
            Log.i("PokemonFragment",pokemon.name)
            listener?.onSelect(pokemon)
        }
    }
}