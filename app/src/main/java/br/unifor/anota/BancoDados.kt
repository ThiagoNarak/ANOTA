package br.unifor.anota

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor



import java.util.ArrayList

class BancoDados(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private val DATABASE_NAME = "Banco.db"
        private val TABLE_NOTAS = "NOTAS"
        //private static final String TABLE_RANKING = "ranking";

        private val DATABASE_VERSION = 1

        // Criando as TAGS para imprimir o Log de cada operação
        private val TAG_D = "DELETAR REGISTRO"
        private val TAG_I = "INSERIR REGISTRO"
        private val TAG_S = "SELECIONAR REGISTROS"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val sql_notas = ("CREATE TABLE " + TABLE_NOTAS
                + "(codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tituloConstrutor TEXT, "
                + "descricao TEXT, "
                + "cor INT, "
                + "foto BYTE); ")
        sqLiteDatabase.execSQL(sql_notas)

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {

    }
    fun buscaNotas(titulo:String): ArrayList<Nota>{
        val notaArrayList = ArrayList<Nota>()
        val sql = "SELECT * FROM " + TABLE_NOTAS +" WHERE tituloConstrutor LIKE '%$titulo%'"
        val cursor = readableDatabase.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val n = Nota()
            n.codigo = cursor.getInt(0)
            n.tituloConstrutor = cursor.getString(1)
            n.descricao = cursor.getString(2)
            n.cor = cursor.getInt(3)
            n.foto = cursor.getBlob(4)

            notaArrayList.add(n)

        }
        return notaArrayList

    }
    fun consultaNotas(): ArrayList<Nota> {
        val notaArrayList = ArrayList<Nota>()
        val sql = "SELECT * FROM " + TABLE_NOTAS
        val cursor = readableDatabase.rawQuery(sql, null)

        while (cursor.moveToNext()) {

            val n = Nota()
            n.codigo = cursor.getInt(0)
            n.tituloConstrutor = cursor.getString(1)
            n.descricao = cursor.getString(2)
            n.cor = cursor.getInt(3)
            n.foto = cursor.getBlob(4)

            notaArrayList.add(n)

        }
        return notaArrayList
    }

    fun deleteNota(){
        val sql = "DELETE FROM " + TABLE_NOTAS+ " WHERE tituloConstrutor like '%android.support.v7%'"
        val database = this.writableDatabase
        database.execSQL(sql)

    }
    fun editNota2(nota:Nota){

        val sql = "UPDATE "+ TABLE_NOTAS+ " SET" +
                " tituloConstrutor = '${nota.tituloConstrutor}'," +
                " descricao = '${nota.descricao}'," +
                " cor = '${nota.cor}'," +
                " foto = '${nota.foto}'" +
                " WHERE codigo = '${nota.codigo}'"
        val database = this.writableDatabase
        database.execSQL(sql)
        println("nota = ${nota.codigo}")

    }

    fun editNota(nota:Nota) {
        val contentValues = ContentValues()

        val database = this.writableDatabase
        contentValues.put("tituloConstrutor", nota.tituloConstrutor)
        contentValues.put("descricao", nota.descricao)
        contentValues.put("cor", nota.cor)
        contentValues.put("foto", nota.foto)

        database.update(TABLE_NOTAS, contentValues, "codigo = ?", arrayOf(nota.codigo.toString()))
    }
    fun addNota(nota: Nota) {

        val contentValues = ContentValues()

        contentValues.put("tituloConstrutor", nota.tituloConstrutor)
        contentValues.put("descricao", nota.descricao)
        contentValues.put("cor", nota.cor)
        contentValues.put("foto", nota.foto)





        writableDatabase.insert(TABLE_NOTAS, null, contentValues)


    }

    fun delete() {
        writableDatabase.delete(TABLE_NOTAS, null, null)

    }

    fun deleteNotas(codigo: Int) {

        val db = this.readableDatabase

        db.execSQL("DELETE FROM $TABLE_NOTAS WHERE codigo = '$codigo'")


    }


}

