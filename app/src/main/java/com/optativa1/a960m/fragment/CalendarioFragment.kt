package com.optativa1.a960m.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.optativa1.a960m.R
class CalendarioFragment : Fragment() {
    companion object {
        fun newInstance(): CalendarioFragment = CalendarioFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_calendario, container, false)
}