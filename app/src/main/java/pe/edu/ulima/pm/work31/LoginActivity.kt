package pe.edu.ulima.pm.work31

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.work31.fragments.PokemonsFragment

class LoginActivity: AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        var btnContinuar : Button = findViewById(R.id.buttonContinuar)
        var btnFavoritos : Button = findViewById(R.id.buttonFavoritos)

        btnContinuar.setOnClickListener {

            val intent : Intent = Intent()
            intent.setClass(this,MainActivity::class.java)
            val bundle : Bundle = Bundle() //pa pasar informacion //otra opcion, pasar bundle con el tipo de button escogido
            bundle.putString("opcion","listado")
            intent.putExtra("dataopcion",bundle)
            startActivity(intent)
        }

        btnFavoritos.setOnClickListener {
            val intent : Intent = Intent()
            intent.setClass(this,MainActivity::class.java)
            val bundle : Bundle = Bundle() //pa pasar informacion //otra opcion, pasar bundle con el tipo de button escogido
            bundle.putString("opcion","favoritos")
            intent.putExtra("dataopcion",bundle)
            startActivity(intent)

        }



    }





}