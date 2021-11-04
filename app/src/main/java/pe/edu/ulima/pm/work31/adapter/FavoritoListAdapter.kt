package pe.edu.ulima.pm.work31.adapter

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pe.edu.ulima.pm.work31.R
import pe.edu.ulima.pm.work31.model.Pokemon
import pe.edu.ulima.pm.work31.model.PokemonData
import pe.edu.ulima.pm.work31.model.PokemonManager

class FavoritoListAdapter(
    private val favoritosIds : List<Int>,
    private val sp: SharedPreferences,
    private val listener : (Int)->Unit
): RecyclerView.Adapter<FavoritoListAdapter.ViewHolder>() {

    class ViewHolder(view: View, val listener : (Int) -> Unit, val favoritosIds: List<Int>):
        RecyclerView.ViewHolder(view), View.OnClickListener
    {

        val txtNombre: TextView
        val delete : ImageView
        init{
            txtNombre = view.findViewById(R.id.txtNombre)
            delete = view.findViewById(R.id.delete)
            delete.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            println("LISTENER: "+adapterPosition)
            listener(adapterPosition)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorito,parent,false)
        val viewHolder = FavoritoListAdapter.ViewHolder(view,listener,favoritosIds)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // llamando a instancia en storage
        var pm: PokemonManager = Gson().fromJson(sp.getString("LIST_POKEMONS",""), object : TypeToken<PokemonManager?>(){}.type)
        holder.txtNombre.text = pm.getPokemon(favoritosIds[position]).name.capitalize()
    }

    override fun getItemCount(): Int {
        return favoritosIds.size
    }
}