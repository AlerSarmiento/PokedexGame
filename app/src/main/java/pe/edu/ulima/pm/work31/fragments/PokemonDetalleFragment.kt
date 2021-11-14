package pe.edu.ulima.pm.work31.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pe.edu.ulima.pm.work31.R
import pe.edu.ulima.pm.work31.model.*

class PokemonDetalleFragment(
    val pokemonId: Int,
    val sp: SharedPreferences,
): Fragment() {
    interface OnAddFavorite{
        fun onFavorite(pokemonId: Int)
    }
    private var listener : PokemonDetalleFragment.OnAddFavorite?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnAddFavorite
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detallepokemon,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.agregarfavorito).setOnClickListener {
            listener?.onFavorite(pokemonId)
        }
        var gson = Gson()
        var pm: PokemonManager = gson.fromJson(sp.getString("LIST_POKEMONS",""),
            object : TypeToken<PokemonManager?>(){}.type)
        var pokemon = pm.getPokemon(pokemonId)
        view.findViewById<TextView>(R.id.txtAtaque2).setText(String.format("Attack : %s",pokemon.attack))
        view.findViewById<TextView>(R.id.txtDefensa2).setText(String.format("Defense : %s",pokemon.defense))
        view.findViewById<TextView>(R.id.txtVida2).setText(String.format("HP : %s",pokemon.hp))
        view.findViewById<TextView>(R.id.txtEspecial2).setText(String.format("Special attack: %s",pokemon.special_attack))
        view.findViewById<TextView>(R.id.txtDefensaEspecial2).setText(String.format("Special defense : %s",pokemon.special_defense))
        Glide.with(this)
            .load(pokemon.imagen)
            .fitCenter()
            .into(view.findViewById(R.id.imagedetalle))
    }
}