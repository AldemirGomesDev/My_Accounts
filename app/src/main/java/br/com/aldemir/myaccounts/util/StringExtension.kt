package br.com.aldemir.myaccounts.util

import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshots.SnapshotStateList

fun emptyString(): String = ""

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}

fun <T> SnapshotStateList<T>.updateList(index: Int, newItem: T){
    remove(newItem)
    add(index, newItem)
}