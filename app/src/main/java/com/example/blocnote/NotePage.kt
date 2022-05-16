package com.example.blocnote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.blocnote.Calsses.Note
import kotlinx.android.synthetic.main.activity_ajouter_note.*
import kotlinx.android.synthetic.main.activity_ajouter_note.AddAppTitleTop
import kotlinx.android.synthetic.main.activity_ajouter_note.tvAddNoteDescription
import kotlinx.android.synthetic.main.activity_ajouter_note.tvAddNoteImage
import kotlinx.android.synthetic.main.activity_ajouter_note.tvAddNoteTitleSpace
import kotlinx.android.synthetic.main.activity_note_page.*

class NotePage : AppCompatActivity() {

    var note : Note? = null ;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_page)


        var cancelButton = findViewById<ImageView>(R.id.imageCancel) ;

        cancelButton.setOnClickListener {

            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()

        }


        var btnDelete = findViewById<ImageView>(R.id.btndelete) ;
        btnDelete.setOnClickListener {
            val intent = Intent()
            intent.putExtra("note" , note) ;
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        val intent = intent ;

        note = intent.getSerializableExtra("note") as? Note

        tvAddNoteTitleSpace.visibility = View.GONE ;
        tvOneNoteImage.visibility = View.GONE ;
        tvAddNoteDescription.visibility = View.GONE ;
        // tvOneNoteImage

        if(note!!.type == 1) {
            tvOneNoteTitleValue.text = note!!.titre.toString();
            tvOneNoteDescriptionValue.text = note!!.description.toString();
            tvAddNoteTitleSpace.visibility = View.VISIBLE ;
            tvAddNoteDescription.visibility = View.VISIBLE ;
        }else if(note!!.type == 2) {


            val context: Context = tvOneNoteImage.getContext()



            val decodedByte = Base64.decode( note!!.image!! , Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)

            tvOneNoteImage.setImageBitmap(bitmap)

            tvOneNoteImage.visibility = View.VISIBLE ;
        }else if(note!!.type == 3) {

            tvOneNoteDescriptionValue.text = note!!.description.toString();
            tvAddNoteDescription.visibility = View.VISIBLE ;


            val decodedByte = Base64.decode( note!!.image!! , Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)

            tvOneNoteImage.setImageBitmap(bitmap)

            tvOneNoteImage.visibility = View.VISIBLE ;

        }else if(note!!.type == 4) {
            tvOneNoteTitleValue.text = note!!.titre
            tvAddNoteTitleSpace.visibility = View.VISIBLE ;
            tvOneNoteDescriptionValue.text = note!!.description
            tvAddNoteDescription.visibility = View.VISIBLE ;
        }
    }
}