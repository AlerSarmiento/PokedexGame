package pe.edu.ulima.pm.work31.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.work31.R

class StoredPokemonsFragment(
    private var sp: SharedPreferences
): Fragment() {
    interface OnPokemonSelectedListener2 {
        fun onSelect2(pokemonId: Int)
    }

    private var listener : OnPokemonSelectedListener2?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPokemonSelectedListener2
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemons,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*super.onViewCreated(view, savedInstanceState)
            var gson = Gson()
            var pm:PokemonManager = gson.fromJson(sp.getString("LIST_POKEMONS",""),
                object : TypeToken<PokemonManager?>(){}.type)
            val recycListadoPokemon= view.findViewById<RecyclerView>(R.id.recycListaPokemons)
            recycListadoPokemon.adapter = StoredPokemonListAdapter(
                pm.getPokemones().sortedBy { it.id },
                this
            ) { pokemonId: Int ->
                listener?.onSelect2(pokemonId)
            }*/

    }
}