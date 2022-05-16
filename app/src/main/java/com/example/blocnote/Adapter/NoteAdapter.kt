package com.example.blocnote.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.blocnote.AjouterNote
import com.example.blocnote.Calsses.Comunication
import com.example.blocnote.Calsses.Note
import com.example.blocnote.NotePage
import com.example.blocnote.R
import kotlinx.android.synthetic.main.activity_ajouter_note.*
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(var msg: List<Note>, comunication: Comunication):RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    private lateinit var comm: Comunication

    var notesFilterList = ArrayList<Note>()


    private  var dataSet= arrayListOf<Note>()

    init {
        comm = comunication ;
        dataSet.addAll(msg)
        notesFilterList = dataSet
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var titre: TextView = itemView.findViewById(R.id.tvTitleNote)
        var date: TextView = itemView.findViewById(R.id.tvDateNote)
        var description: TextView = itemView.findViewById(R.id.tvNoteDesc)
        var image: ImageView = itemView.findViewById(R.id.noteimageViewer)
        var btnDelete : ImageView = itemView.findViewById(R.id.btndelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewObj = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(viewObj)
    }


    override fun getItemCount(): Int {
        return notesFilterList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.titre.visibility = View.GONE ;
        holder.description.visibility = View.GONE ;
        holder.image.visibility = View.GONE ;

        //date
        holder.date.text = notesFilterList[position].date.getDate().toString()+"/"+(notesFilterList[position].date.getMonth()+1).toString()+"/"+notesFilterList[position].date.getYear().toString()

        // titre
        if(notesFilterList[position].type == 1) {
            holder.titre.text = notesFilterList[position].titre.toString() ;
            holder.titre.visibility = View.VISIBLE ;
        }
        //description
        if (notesFilterList[position].type == 1 || notesFilterList[position].type == 3) {
            holder.description.text = notesFilterList[position].description.toString() ;
            holder.description.visibility = View.VISIBLE ;
        }
        // image
        if (notesFilterList[position].type == 2 || notesFilterList[position].type == 3) {
            val context: Context = holder.image.getContext()

            val decodedByte = android.util.Base64.decode( notesFilterList[position].image!! , Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)


            holder.image.setImageBitmap(bitmap)
            holder.image.visibility = View.VISIBLE ;
        }
        if(notesFilterList[position].type == 4) {
            holder.titre.text = notesFilterList[position].titre
            holder.titre.visibility = View.VISIBLE
            holder.description.text = notesFilterList[position].description
            holder.description.visibility = View.VISIBLE
        }

        holder.btnDelete.setOnClickListener {
            val deleted_task = notesFilterList[position] ;
            notesFilterList.remove(notesFilterList[position]) ;
            notifyDataSetChanged()
            comm.passDataCom(deleted_task)
        }


        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                comm.startActivituy(notesFilterList[position])

                /*
                val activity = v!!.context as AppCompatActivity

                val versetPageFragment = VersetPageFragment(versetsFilterList[position])

                activity.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  versetPageFragment)
                    addToBackStack(null)
                    commit()
                }

                 */
            }
        }
        )
    }




}