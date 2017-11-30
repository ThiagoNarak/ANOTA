package br.unifor.anota

import android.graphics.Color

/**
 * Created by thiagorodrigo on 05/10/17.
 */

class Nota ( ) {
     var codigo:Int = -1
     var tituloConstrutor:String? =""
     var descricao: String? = ""
     var cor: Int = Color.WHITE
     var foto: ByteArray? = null


    override fun toString(): String {
    return tituloConstrutor.toString()
    }
}
