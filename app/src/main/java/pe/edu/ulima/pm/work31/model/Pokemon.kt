package pe.edu.ulima.pm.work31.model

data class Pokemon(
    var id : Int,
    var img: String,
    var name: String,
    var hp : Long,
    var attack : Long,
    var defense: Long,
    var special_attack : Long,
    var special_defense : Long,
)
