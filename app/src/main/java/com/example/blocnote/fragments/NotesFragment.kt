package com.example.blocnote.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blocnote.Adapter.NoteAdapter
import com.example.blocnote.Calsses.Comunication
import com.example.blocnote.Calsses.Note
import com.example.blocnote.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_notes_fragment.*

class NotesFragment(var list: List<Note> ) : Fragment(R.layout.recycler_notes_fragment){


    private lateinit var comm: Comunication
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NoteAdapter.ViewHolder>? = null
    private var recyclerView : RecyclerView? = null ;



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //tv_racine_text.text = racine.racine
        comm = requireActivity() as Comunication

        layoutManager = LinearLayoutManager(requireContext())



        val recyclerView = view?.findViewById<RecyclerView>(R.id.notes_recycler)

        val columns = getResources().getInteger(R.integer.column);
        recyclerView?.layoutManager = GridLayoutManager(requireContext() ,columns)




        adapter = NoteAdapter(list , comm)



        recyclerView?.adapter = this.adapter

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.w("rotation" , newConfig.orientation.toString() ) ;
        super.onConfigurationChanged(newConfig)
        val orientation: Int = newConfig.orientation

        comm = requireActivity() as Comunication

        layoutManager = LinearLayoutManager(requireContext())



        val columns = getResources().getInteger(R.integer.column);
        recyclerView?.layoutManager = GridLayoutManager(requireContext() ,columns)

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView?.layoutManager = GridLayoutManager(requireContext() ,2)
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView?.layoutManager = GridLayoutManager(requireContext() ,3)
        }





        adapter = NoteAdapter(list , comm)



        recyclerView?.adapter = this.adapter

    }









}