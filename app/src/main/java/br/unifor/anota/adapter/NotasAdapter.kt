package br.unifor.anota.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.widget.Toast
import br.unifor.anota.Nota
import br.unifor.anota.R
import br.unifor.anota.RecycleViewOnClickListerner
import kotlinx.android.synthetic.main.nota.view.*


/**
 * Created by thiagorodrigo on 26/10/17.
 */

class NotasAdapter( private var notas:ArrayList<Nota>,  private var context:Context) :  Adapter<NotasAdapter.ViewHolder>(){

    var mLayoutInflater:LayoutInflater
    lateinit var mRecycleViewOnClickListener:RecycleViewOnClickListerner

    init {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

   inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{

       fun bindView(nota: Nota) {
            val titulo = itemView.textView4
            val cor = itemView.imageView3

            titulo.text = nota.tituloConstrutor
            cor.setColorFilter(nota.cor)
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            mRecycleViewOnClickListener.onClickListener(v!!,position)

        }


   }
    fun setRecycleViewOnClickListener(mRecycleViewOnClickListener:RecycleViewOnClickListerner){
        this.mRecycleViewOnClickListener = mRecycleViewOnClickListener
    }
    fun reload(notas:ArrayList<Nota>) {
        this.notas = notas
    }

    fun  clear() {
        var size = this.notas.size;
        if (size > 0) {
            for (i in 0..size-1) {
                this.notas.removeAt(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val note = notas[position]
        holder?.let {
            it.bindView(note)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.nota, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return notas.size
    }


}
