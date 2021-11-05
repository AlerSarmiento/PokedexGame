package pe.edu.ulima.pm.work31.model

class PokemonManager {

    private val mPokemones = arrayListOf<Pokemon>()

    init {

    }

    companion object {
        private var instance: PokemonManager? = null
    }

    fun getInstance(): PokemonManager {
        if (instance == null) {
            instance = PokemonManager()
        }
        return instance!!
    }

    fun getPokemones() : List<Pokemon> {
        return mPokemones
    }

    fun addPokemon(pokemon: Pokemon){
        var flag = true
        for (i in mPokemones){
            if(i.id == pokemon.id) flag = false
        }
        if (flag) mPokemones.add(pokemon)
    }

    fun getPokemon(id: Int): Pokemon{
        for (i in mPokemones){
            if(i.id == (id+1)) return i
        }
        return mPokemones[0]
    }


}