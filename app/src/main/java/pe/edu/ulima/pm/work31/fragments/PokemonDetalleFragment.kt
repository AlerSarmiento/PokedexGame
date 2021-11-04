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
import pe.edu.ulima.pm.work31.model.Apivarible
import pe.edu.ulima.pm.work31.model.Pokemon
import pe.edu.ulima.pm.work31.model.PokemonData
import pe.edu.ulima.pm.work31.model.PokemonManagerAPI

class PokemonDetalleFragment(val pokemon: PokemonData): Fragment() {
    interface OnAddFavorite{
        fun onFavorite(pokemon: PokemonData)
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

        PokemonManagerAPI().getPokemonRetrofit(ConseguirCodigo(pokemon.url),
            {   dato: Apivarible ->


                view.findViewById<TextView>(R.id.nombredetalle).setText(dato.name)
                view.findViewById<TextView>(R.id.txtAtaque2).setText(String.format("Attack : %s",dato.stats[1].base_stat.toString()))
                view.findViewById<TextView>(R.id.txtDefensa2).setText(String.format("Defense : %s",dato.stats[2].base_stat.toString()))
                view.findViewById<TextView>(R.id.txtVida2).setText(String.format("HP : %s",dato.stats[0].base_stat.toString()))
                view.findViewById<TextView>(R.id.txtEspecial2).setText(String.format("Special attack: %s",dato.stats[3].base_stat.toString()))
                view.findViewById<TextView>(R.id.txtDefensaEspecial2).setText(String.format("Special defense : %s",dato.stats[4].base_stat.toString()))

                Glide.with(this)
                    .load(dato.sprites.front_default)
                    .fitCenter()
                    .into(view.findViewById(R.id.imagedetalle))

        },{

            })




    }

    fun  ConseguirCodigo(a : String):Int{
        var lista = a.split("/")

        return Integer.parseInt(lista.get(lista.size-2))
    }


}