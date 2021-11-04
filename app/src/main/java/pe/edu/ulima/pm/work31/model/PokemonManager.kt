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
        mPokemones.add(pokemon)
    }

    fun getPokemon(id: Int): Pokemon{
        return mPokemones[id]
    }


}