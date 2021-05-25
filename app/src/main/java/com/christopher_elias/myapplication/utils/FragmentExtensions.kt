package com.christopher_elias.myapplication.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/*
 * Created by Christopher Elias on 16/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */



fun Fragment.pop() = parentFragmentManager.popBackStack()

fun Fragment.popUntil(
    fragmentTag: String
) = parentFragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)

fun Fragment.replaceFragmentExt(
    newFragment: Fragment,
    addToBackStack: Boolean,
    fromParent: Boolean = true,
    containerId: Int? = null,
    fragmentTag: String? = null,
    transactionAnimations: TransactionAnimations = TransactionAnimations.NONE
) = this.navUtils(
    TransactionType.REPLACE_FRAGMENT,
    newFragment,
    addToBackStack,
    fromParent,
    containerId,
    fragmentTag,
    transactionAnimations
)

fun Fragment.addFragmentExt(
    newFragment: Fragment,
    addToBackStack: Boolean,
    fromParent: Boolean = true,
    containerId: Int? = null,
    fragmentTag: String? = null,
    transactionAnimations: TransactionAnimations = TransactionAnimations.NONE
) = this.navUtils(
    TransactionType.ADD_FRAGMENT,
    newFragment,
    addToBackStack,
    fromParent,
    containerId,
    fragmentTag,
    transactionAnimations
)

internal fun Fragment.navUtils(
    transactionType: TransactionType,
    newFragment: Fragment,
    addToBackStack: Boolean,
    fromParent: Boolean = true,
    containerId: Int? = null,
    fragmentTag: String? = null,
    transactionAnimations: TransactionAnimations
) {
    val manager = if (fromParent) activity?.supportFragmentManager else childFragmentManager

    if (manager?.findFragmentByTag(newFragment::class.java.simpleName) != null) {
        Log.d("NavUtils", "Fragment already added transaction")
        return
    }
    Log.d("NavUtils", "Begin transaction")
    manager?.beginTransaction()?.fragmentStackManager(
        transactionType = transactionType,
        newFragment = newFragment,
        addToBackStack = addToBackStack,
        containerId = containerId,
        fragmentTag = newFragment::class.java.simpleName,
        transactionAnimations = transactionAnimations
    )
}