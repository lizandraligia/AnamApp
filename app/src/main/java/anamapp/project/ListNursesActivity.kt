package anamapp.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_listnurses_nurses.*
import kotlinx.android.synthetic.main.activity_menu.*

class ListNursesActivity : Activity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    val myDataSet = arrayOf("Maria Menezes", "Cristiana Soares", "Rômulo Dias", "Marcos Antonio", "Angela lucena"
    , "Andreia Nascimento", "Joana Lins", "Cristiane Dias", "João Marcos", "Eliane Oliveira", "Carla Mendes", "André Peixoto",
        "Luana Marques", "Andreia Martins", "Mariana Gomes", "Alex Teixeira", "Maria Celia", "Nataly Spindola", "Raul Borba")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listnurses_nurses)


        menu_image_view_back2.setOnClickListener {
            finish()
        }

        btnRegisterNewNurses.setOnClickListener {
            val intent = Intent(applicationContext, registerNewEmployeeActivity::class.java)
            startActivity(intent)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(this.myDataSet)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNurse).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }




    }


}