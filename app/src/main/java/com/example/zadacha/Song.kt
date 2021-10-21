package com.example.zadacha

data class Song(val author: String = "", val songName: String = "") {
    override fun toString(): String {
        return author + " - " + songName
    }
}


