package br.unifor.anota

import android.app.Activity
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_nota.*
import android.graphics.Bitmap.CompressFormat
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory
import android.widget.*


class NotaActivity : AppCompatActivity(),View.OnClickListener  {
    var titulo: EditText? = null

    var descricao: EditText? = null
    var codIntent:Int= -1
    lateinit var corVerde:ImageView
    lateinit var corAmarelo:ImageView
    lateinit var corAzul:ImageView
    lateinit var corVermelho:ImageView
    lateinit var buttonDeletar:Button
    lateinit var nota:Nota
    lateinit var anexo:ImageButton
    val REQUEST_IMAGE_CAPTURE:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nota)

        anexo = findViewById(R.id.imageButtonAnexo) as ImageButton
        titulo = findViewById(R.id.editTituloNota) as EditText
        descricao = findViewById(R.id.EditTextCampo) as EditText
        corVerde = findViewById(R.id.imageViewVerde) as ImageView
        corVermelho = findViewById(R.id.imageViewVermehlo) as ImageView
        corAmarelo = findViewById(R.id.imageViewAmarelo) as ImageView
        corAzul = findViewById(R.id.imageViewAzul) as ImageView
        buttonDeletar = findViewById(R.id.buttonDeletar) as Button
        codIntent = intent.getIntExtra("codigo",-1)



        if (codIntent!=-1){

            val b = BancoDados(this)
            for (i in 0..b.consultaNotas().size-1){

                if (b.consultaNotas().get(i).codigo==codIntent){
                    println("if "+ b.consultaNotas().get(i).codigo)
                    nota= b.consultaNotas().get(i)
                    this.titulo!!.setText(nota.tituloConstrutor)
                    this.descricao!!.setText(nota.descricao)
                    modifyColor(nota.cor)
                    if (nota.foto!=null)
                        this.imageButtonAnexo.setImageBitmap(BitmapFactory.decodeByteArray(nota.foto, 0, nota.foto!!.size))


                }
            }
        }else{
            nota= Nota()
        }

        buttonDeletar.setOnClickListener(this)
        corAmarelo.setOnClickListener(this)
        corAzul.setOnClickListener(this)
        corVerde.setOnClickListener(this)
        corVermelho.setOnClickListener(this)
        anexo.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.imageButtonAnexo -> callIntentWithoutParametrsToTakePhoto()
            R.id.buttonDeletar -> deletarNota()
            R.id.imageViewAmarelo -> modifyColor(Color.YELLOW)
            R.id.imageViewAzul -> modifyColor(Color.BLUE)
            R.id.imageViewVerde -> modifyColor(Color.GREEN)
            R.id.imageViewVermehlo -> modifyColor(Color.RED)
        }
    }

    private fun deletarNota() {

        val banco = BancoDados(this)
        if(nota.codigo!=-1){
            banco.deleteNotas(nota.codigo)
            Toast.makeText(this,"Nota deletada",Toast.LENGTH_SHORT).show()
            finish()
        }
        finish()


    }

    private fun modifyColor(color:Int){
        when(color)    {
            Color.RED -> {
                corVerde.alpha = 0.1F
                corAmarelo.alpha = 0.1F
                corAzul.alpha = 0.1F
                corVermelho.alpha = 1F
                nota.cor=Color.RED
            }
            Color.YELLOW -> {
                corVerde.alpha = 0.1F
                corAmarelo.alpha = 1F
                corAzul.alpha = 0.1F
                corVermelho.alpha = 0.1F
                nota.cor=Color.YELLOW
            }

            Color.BLUE -> {
                corVerde.alpha = 0.1F
                corAmarelo.alpha = 0.1F
                corAzul.alpha = 1F
                corVermelho.alpha = 0.1F
                nota.cor=Color.BLUE
            }
            Color.GREEN -> {
                corVerde.alpha = 1F
                corAmarelo.alpha = 0.1F
                corAzul.alpha = 0.1F
                corVermelho.alpha = 0.1F
                nota.cor=Color.GREEN
            }

        }
    }


    private fun callIntentWithoutParametrsToTakePhoto() {
        val it = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (it.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(it, REQUEST_IMAGE_CAPTURE);
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras
            var imageBitmap: Bitmap = extras.get("data") as Bitmap
            var foto=getBitmapAsByteArray(imageBitmap)
            nota.foto= foto
            imageButtonAnexo.setImageBitmap(imageBitmap)

        }

    }

    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val banco = BancoDados(this)
        nota.tituloConstrutor = titulo!!.text.toString()
        nota.descricao = descricao!!.text.toString()

        if (codIntent==-1) {
            banco.addNota(nota)
        }else{
            banco.editNota(nota)
        }

        Toast.makeText(this,"nota salva!",Toast.LENGTH_SHORT).show()
    }
}
