package br.com.aldemir.myaccounts.util

import androidx.compose.runtime.snapshots.SnapshotStateList

private const val LENGTH_STRING = 2
private const val PAD_CHAR = '0'

fun emptyString(): String = ""

fun pointString(): String = "."

fun zeroString(): String = "0"

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}

fun <T> SnapshotStateList<T>.updateList(index: Int, newItem: T){
    remove(newItem)
    add(index, newItem)
}

private fun convertIntToString(value: Int): String {
    return value.toString().padStart(LENGTH_STRING, PAD_CHAR)
}