package pe.edu.ulima.pm.work31.model

class PokemonManager {

    private val mPokemones = arrayListOf<Pokemon>()

    init {
        mPokemones.add(
            Pokemon(
                1,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "Vulvasur",
                50,
                45,
                25,
                15,
                85
            )
        )
        mPokemones.add(
            Pokemon(
                2,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                "Ivasur",
                75,
                45,
                30,
                55,
                100
            )
        )
        mPokemones.add(
            Pokemon(
                3,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                "Noce",
                100,
                45,
                250,
                195,
                185
            )
        )
        mPokemones.add(
            Pokemon(
                4,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                "Squirtle",
                60,
                55,
                35,
                25,
                55
            )
        )
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


}