package br.unifor.anota

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText

import br.unifor.anota.adapter.NotasAdapter



class MainActivity : AppCompatActivity(),RecycleViewOnClickListerner{
    lateinit var mRecyclerView: RecyclerView



    lateinit var adapter:NotasAdapter
    lateinit var b:BancoDados
    lateinit var buscaEditText:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        b = BancoDados(this)
        val llm = LinearLayoutManager(this)
        val mFab = findViewById(R.id.fabAdicionar)
        buscaEditText = findViewById(R.id.editTextBuscar) as EditText
        var imgBuscar = findViewById(R.id.imgBuscar)
        mRecyclerView = findViewById(R.id.recyclerViewLista) as RecyclerView
        mRecyclerView.layoutManager = llm
        adapter = NotasAdapter(b.consultaNotas(),this)
        adapter.setRecycleViewOnClickListener(this)
        mRecyclerView.adapter = adapter

        imgBuscar.setOnClickListener(View.OnClickListener {

            adapter.clear()

            println(buscaEditText.getText().toString())

            adapter.reload(b.buscaNotas(buscaEditText.getText().toString()))
            if (buscaEditText.getText().toString().length==0){
                adapter.reload(b.consultaNotas())
            }
            mRecyclerView.adapter = adapter
        })

        mFab.setOnClickListener(View.OnClickListener {

            val intent = Intent(this,NotaActivity::class.java)
            startActivity(intent)

        })
    }
    override fun onClickListener(view: View, position: Int) {

        val intent = Intent(this,NotaActivity::class.java)
        intent.putExtra("codigo",b.consultaNotas().get(position).codigo)

        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        adapter.clear()

    }

    override fun onResume() {
        super.onResume()
        adapter.reload(b.consultaNotas())
        mRecyclerView.adapter = adapter
    }



}
