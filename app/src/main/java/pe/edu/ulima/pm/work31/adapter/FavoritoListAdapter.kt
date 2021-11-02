package pe.edu.ulima.pm.work31.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.work31.R
import pe.edu.ulima.pm.work31.model.Pokemon

class FavoritoListAdapter(
    private val favoritosList : List<Pokemon>,
    private val listener : (Pokemon)->Unit
): RecyclerView.Adapter<FavoritoListAdapter.ViewHolder>() {

    class ViewHolder(view: View, val listener : (Pokemon) -> Unit, val favoritosList: List<Pokemon>):
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
            listener(favoritosList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorito,parent,false)
        val viewHolder = FavoritoListAdapter.ViewHolder(view,listener,favoritosList)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNombre.text = favoritosList[position].name
    }

    override fun getItemCount(): Int {
        return favoritosList.size
    }
}