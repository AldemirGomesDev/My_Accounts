package br.com.aldemir.myaccounts.util

import androidx.compose.runtime.snapshots.SnapshotStateList

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