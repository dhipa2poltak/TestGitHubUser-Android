package com.dpfht.testgithubuser.ui.base

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

open class BaseFragment: Fragment() {

  lateinit var prgDialog: AlertDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    prgDialog = get { parametersOf(requireActivity()) }
  }
}
