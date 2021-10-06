package com.envious.searchphoto.ui.advancedsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.envious.searchphoto.R

class AdvancedSearchFragment : Fragment() {

    companion object {
        fun newInstance() = AdvancedSearchFragment()
    }

    private lateinit var viewModel: AdvancedSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.advanced_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdvancedSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
