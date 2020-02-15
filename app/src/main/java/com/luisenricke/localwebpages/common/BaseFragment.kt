package com.luisenricke.localwebpages.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.luisenricke.localwebpages.R
import com.luisenricke.localwebpages.data.WebDatabase

@Suppress("unused")
abstract class BaseFragment : Fragment() {

    lateinit var activity: AppCompatActivity
    lateinit var database: WebDatabase
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as AppCompatActivity
        database = WebDatabase.getInstance(activity)
        navController = Navigation.findNavController(activity, R.id.fragment_host)
        setHasOptionsMenu(true)
    }
}