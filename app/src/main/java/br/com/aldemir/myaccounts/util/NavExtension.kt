package br.com.aldemir.myaccounts.util

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import br.com.aldemir.myaccounts.R

private val slideLeftOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .setPopUpTo(R.id.mainFragment, true)
    .build()

fun NavController.getNavOptions(popUpTo: Int): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .setPopUpTo(popUpTo, true)
        .build()
}

fun NavController.navigateWithAnimations(
    destinationId: Int,
    animation: NavOptions = slideLeftOptions,
    bundle: Bundle? = null
) {
    this.navigate(destinationId, bundle, animation)
}