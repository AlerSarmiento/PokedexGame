package pe.edu.ulima.pm.work31.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.work31.R
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.work31.adapter.PokemonListAdapter.ViewHolder
import pe.edu.ulima.pm.work31.model.Pokemon

class PokemonListAdapter(
    private val pokemonList: List<Pokemon>,
    private val fragment : Fragment,
    private val listener : (Pokemon)->Unit
): RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {


    class ViewHolder(view: View, val listener : (Pokemon) -> Unit, val pokemonList : List<Pokemon>):
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
            listener(pokemonList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon,parent,false)
        val viewHolder = PokemonListAdapter.ViewHolder(view,listener,pokemonList)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PokemonListAdapter.ViewHolder, position: Int) {
        holder.nombre.text = pokemonList[position].name
        holder.ataque.text = String.format("Attack : %s",pokemonList[position].attack.toString())
        holder.defensa.text = String.format("Defense : %s",pokemonList[position].defense.toString())
        holder.vida.text = String.format("HP : %s",pokemonList[position].hp.toString())
        holder.especial_ata.text = String.format("Especial attack: %s",pokemonList[position].special_attack.toString())
        holder.especial_def.text = String.format("Especial defense : %s",pokemonList[position].special_defense.toString())
        Glide.with(fragment)
            .load(pokemonList[position].img)
            .fitCenter()
            .into(holder.imgPokemon)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }


}