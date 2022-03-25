package com.tolodev.createshortenlinks.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment

fun startDestination(navDirection: NavDirections, fragment: Fragment) {
    NavHostFragment.findNavController(fragment).safeNavigate(navDirection)
}

private fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}