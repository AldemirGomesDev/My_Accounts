package br.com.aldemir.myaccounts.util

import androidx.compose.runtime.snapshots.SnapshotStateList

fun emptyString(): String = ""

fun pointString(): String = "."

fun zeroMoneyString(): String = "R$ 0,00"

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}

fun <T> SnapshotStateList<T>.updateList(index: Int, newItem: T){
    remove(newItem)
    add(index, newItem)
}