package pe.edu.ulima.pm.work31.model

class PokemonManager {

    private val mPokemones = arrayListOf<ImagenURL>()

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

    fun getPokemones() : List<ImagenURL> {
        return mPokemones
    }

    fun addPokemon(pokemon: ImagenURL){
        var flag = true
        for (i in mPokemones){
            if(i.codigo == pokemon.codigo) flag = false
        }
        if (flag) mPokemones.add(pokemon)
    }

    fun getPokemon(id: Int): ImagenURL{
        for (i in mPokemones){
            if(i.codigo == (id+1)) return i
        }
        return mPokemones[0]
    }


}