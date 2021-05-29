package com.christopher_elias.myapplication.navigation_ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.christopher_elias.myapplication.R

/*
 * Created by Christopher Elias on 16/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

/**
 * Extension for add or replace the fragment to the container.
 * Add means to put the fragment on top of each other,
 * replace will replace the whole fragment view.
 *
 * @param transactionType a [TransactionType] object.
 * @param newFragment the fragment to ADD or REPLACE.
 * @param addToBackStack if true then it will be added to the fragment back stack.
 * @param containerId the container id.
 * @param transactionAnimations NEW. For override animations..
 */
internal fun FragmentTransaction.fragmentStackManager(
    transactionType: TransactionType,
    newFragment: Fragment,
    addToBackStack: Boolean,
    containerId: Int? = null,
    fragmentTag: String? = null,
    transactionAnimations: TransactionAnimations
) {
    when (transactionAnimations) {
        TransactionAnimations.NONE -> {
            /* None transition animation is applied */
        }
        TransactionAnimations.BOTTOM_TO_TOP -> {
            setCustomAnimations(
                R.anim.slide_in_from_bottom, R.anim.slide_out_from_top,
                R.anim.slide_in_from_bottom, R.anim.slide_out_from_top
            )
        }
        TransactionAnimations.RIGHT_TO_LEFT -> {
            setCustomAnimations(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right
            )
        }
    }

    when (transactionType) {
        TransactionType.ADD_FRAGMENT -> {
            add(
                containerId ?: android.R.id.content,
                newFragment,
                fragmentTag
            ).also {
                if (addToBackStack) {
                    it.addToBackStack(fragmentTag)
                }
            }.commit()
        }
        TransactionType.REPLACE_FRAGMENT -> {
            replace(
                containerId ?: android.R.id.content,
                newFragment,
                fragmentTag
            ).also {
                if (addToBackStack) {
                    it.addToBackStack(fragmentTag)
                }
            }.commit()
        }
    }
}

enum class TransactionAnimations {
    RIGHT_TO_LEFT,
    BOTTOM_TO_TOP,
    NONE
}

enum class TransactionType {
    /**
     * Add fragment on top of the current fragment that call this method.
     */
    ADD_FRAGMENT,

    /**
     * Replace the current fragment for a new fragment that call this method.
     */
    REPLACE_FRAGMENT
}