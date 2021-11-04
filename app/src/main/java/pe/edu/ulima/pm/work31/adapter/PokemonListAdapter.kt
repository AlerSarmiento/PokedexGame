package pe.edu.ulima.pm.work31.adapter

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.work31.R
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.work31.adapter.PokemonListAdapter.ViewHolder
import pe.edu.ulima.pm.work31.model.*

class PokemonListAdapter(
    private val pokemonList: List<PokemonData>,
    private val fragment : Fragment,
    private val sp: SharedPreferences,
    private val listener : (Int)->Unit
): RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    class ViewHolder(
        view: View, val listener : (Int) -> Unit,
        val pokemonList : List<PokemonData>):
        RecyclerView.ViewHolder(view), View.OnClickListener
    {
        val imgPokemon: ImageView
        val nombre : TextView
        val vida: TextView
        val ataque: TextView
        val defensa : TextView
        val especial_ata : TextView
        val especial_def : TextView
        init{
            imgPokemon = view.findViewById(R.id.imagePokemon)
            nombre = view.findViewById(R.id.txtNombrePokemon)
            vida = view.findViewById(R.id.txtVida)
            ataque = view.findViewById(R.id.txtAtaque)
            defensa = view.findViewById(R.id.txtDefensa)
            especial_ata = view.findViewById(R.id.txtEspecial)
            especial_def = view.findViewById(R.id.txtDefensaEspecial)

            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            listener(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon,parent,false)
        val viewHolder = PokemonListAdapter.ViewHolder(view,listener,pokemonList)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PokemonListAdapter.ViewHolder, position: Int) {
            println("LOCAL STORAGE VACIO")
            PokemonManagerAPI(sp).getPokemonRetrofit(
                ConseguirCodigo(pokemonList[position].url),
                {pokemon  : Apivarible ->
                    // guardando pokemon en pokemonmanager
                    var pokemon2 = Pokemon(
                        ConseguirCodigo(pokemonList[position].url),
                        pokemon.sprites.front_default,
                        pokemon.name,
                        pokemon.stats[0].base_stat,
                        pokemon.stats[1].base_stat,
                        pokemon.stats[2].base_stat,
                        pokemon.stats[3].base_stat,
                        pokemon.stats[4].base_stat,
                    )
                    PokemonManager().getInstance().addPokemon(pokemon2)

                    holder.nombre.text = pokemon.name.capitalize()
                    holder.ataque.text = String.format("Attack : %s",pokemon.stats[1].base_stat.toString())
                    holder.defensa.text = String.format("Defense : %s",pokemon.stats[2].base_stat.toString())
                    holder.vida.text = String.format("HP : %s",pokemon.stats[0].base_stat.toString())
                    holder.especial_ata.text = String.format("Especial attack: %s",pokemon.stats[3].base_stat.toString())
                    holder.especial_def.text = String.format("Especial defense : %s",pokemon.stats[4].base_stat.toString())
                    Glide.with(fragment)
                        .load(pokemon.sprites.front_default)
                        .fitCenter()
                        .into(holder.imgPokemon)

                },{ error ->
                    Toast.makeText(fragment.context, "Error: " + error, Toast.LENGTH_SHORT).show()
                }
            )
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun  ConseguirCodigo(a : String):Int{
        var lista = a.split("/")

        return Integer.parseInt(lista.get(lista.size-2))
    }


}