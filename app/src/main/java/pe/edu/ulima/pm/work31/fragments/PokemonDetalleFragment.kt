package pe.edu.ulima.pm.work31.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.work31.R
import pe.edu.ulima.pm.work31.model.Pokemon

class PokemonDetalleFragment(val pokemon: Pokemon): Fragment() {
    interface OnAddFavorite{
        fun onFavorite(pokemon: Pokemon)
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
            listener?.onFavorite(pokemon)
        }

        view.findViewById<TextView>(R.id.nombredetalle).setText(pokemon.name)
        view.findViewById<TextView>(R.id.txtAtaque2).setText(String.format("Attack : %s",pokemon.attack.toString()))
        view.findViewById<TextView>(R.id.txtDefensa2).setText(String.format("Defense : %s",pokemon.defense.toString()))
        view.findViewById<TextView>(R.id.txtVida2).setText(String.format("HP : %s",pokemon.hp.toString()))
        view.findViewById<TextView>(R.id.txtEspecial2).setText(String.format("Special attack: %s",pokemon.special_attack.toString()))
        view.findViewById<TextView>(R.id.txtDefensaEspecial2).setText(String.format("Special defense : %s",pokemon.special_defense.toString()))

        Glide.with(this)
            .load(pokemon.img)
            .fitCenter()
            .into(view.findViewById(R.id.imagedetalle))

    }


}