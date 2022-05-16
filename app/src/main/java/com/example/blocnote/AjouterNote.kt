package com.example.blocnote

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.example.blocnote.Calsses.Note
import kotlinx.android.synthetic.main.activity_ajouter_note.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.splash_screen.*
import java.io.*
import java.util.*
import java.io.ByteArrayOutputStream;


class AjouterNote : AppCompatActivity() {
    var pickedPhoto : Uri? = null ;
    var pickedBitmap : Bitmap? = null ;
    var ListeTache : List<String> = emptyList()
    var descript : String = ""
    var type = 1 ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_note)

        var cancelButton = findViewById<ImageView>(R.id.imageCancel) ;

        cancelButton.setOnClickListener {

            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()

        }
        btnAddTache.setOnClickListener{
            if(tvAddNoteTacheInput.text.toString()!="") {
                descript = descript + "   - " + tvAddNoteTacheInput.text.toString()+"\n"
                liste.text = liste.text.toString() + "   - " + tvAddNoteTacheInput.text.toString()+"\n"

                tvAddNoteTacheInput.setText("")
                btnAddNote.visibility = View.VISIBLE
                liste.height += 48
            }
            else {
                Toast.makeText(this, "Tache vide", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddNote.setOnClickListener {

            val intent = Intent()
            intent.putExtra("action" , "add") ;

            var today : Calendar = Calendar.getInstance() ;
            val date = Date(today.get(Calendar.YEAR)  , today.get(Calendar.MONTH)  , today.get(Calendar.DAY_OF_MONTH) ) ;

            if(type == 1) {
                var newNote = Note(tvAddNoteTitleInput.text.toString() , date , tvAddNoteDescriptionInput.text.toString() , "" ,  type) ;
                intent.putExtra("note" , newNote) ;
            }else if(type == 2) {
                val byteArrayOutputStream = ByteArrayOutputStream()
                pickedBitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                val encoded: String = android.util.Base64.encodeToString(byteArray, Base64.DEFAULT)
                var newNote = Note("" , date , "" , encoded  , type) ;
                intent.putExtra("note" , newNote) ;
            }else if(type == 3) {
                // val uri = this.bitmapToFile(pickedBitmap) ;
                val byteArrayOutputStream = ByteArrayOutputStream()
                pickedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

                val encoded: String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)

                var newNote = Note("" , date , tvAddNoteDescriptionInput.text.toString() , encoded , type) ;
                intent.putExtra("note" , newNote) ;
            }else if(type == 4) {

                var newNote = Note("Liste des taches :" , date , descript , "" ,  type,ListeTache )
                intent.putExtra("note", newNote)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()

        }

        val intent = intent ;

        Log.w("result Action" , intent.toString()) ;

        type = intent.getIntExtra("type" , 1) ;
        tvAddNoteTitleSpace.visibility = View.GONE ;
        tvAddNoteImage.visibility = View.GONE ;
        tvAddNoteDescription.visibility = View.GONE ;
        tvAddNoteTacheSpace.visibility = View.GONE
        btnAddTache.visibility = View.GONE
        liste.visibility = View.GONE

        if(type == 1) {
            AddAppTitleTop.text = "Nouvelle Note" ;
            tvAddNoteTitleSpace.visibility = View.VISIBLE ;
            tvAddNoteDescription.visibility = View.VISIBLE ;
        }else if(type == 2) {
            AddAppTitleTop.text = "Nouvelle Image" ;
            tvAddNoteImage.visibility = View.VISIBLE ;
        }else if(type == 3) {
            AddAppTitleTop.text = "Nouvelle carte" ;
            tvAddNoteImage.visibility = View.VISIBLE ;
            tvAddNoteDescription.visibility = View.VISIBLE ;
        }else if(type == 4) {
            AddAppTitleTop.text = "Nouvelle Liste de taches" ;
            tvAddNoteTacheSpace.visibility = View.VISIBLE
            btnAddTache.visibility = View.VISIBLE
            liste.visibility = View.VISIBLE
            btnAddNote.visibility = View.GONE
        }

    }

    fun pickPhoto(view : View) {
        if (ContextCompat.checkSelfPermission(this , android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this , arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE) , 1 ) ;
        } else {
            val galeriIntent = Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
            startActivityForResult(galeriIntent , 2 ) ;
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1 ) {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galerieIntent = Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
                startActivityForResult(galerieIntent , 2 ) ;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null ) {
            pickedPhoto = data.data
            if(pickedPhoto != null) {
                if(Build.VERSION.SDK_INT >=28) {
                    val source  = ImageDecoder.createSource(this.contentResolver , pickedPhoto!!) ;
                    pickedBitmap = ImageDecoder.decodeBitmap(source) ;
                    tvAddNoteImage.setImageBitmap(pickedBitmap)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap:Bitmap?): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream:OutputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e:IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }
}