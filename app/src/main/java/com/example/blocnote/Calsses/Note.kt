package com.example.blocnote.Calsses

import android.net.Uri
import java.io.Serializable
import java.util.*


class Note (titre : String , date : Date , description : String , image : String  , type : Int , taches : List<String>? = null )  : Serializable  {
    var titre : String = titre ;
    var date : Date = date ;
    var description : String = description ;
    var image : String = image ;
    var type : Int = type ;
    var taches : List<String>? =  taches ;
}