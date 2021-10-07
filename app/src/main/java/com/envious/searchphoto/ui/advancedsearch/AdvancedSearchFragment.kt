package com.envious.searchphoto.ui.advancedsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.envious.data.util.Filter
import com.envious.data.util.Orientation
import com.envious.data.util.Sort
import com.envious.searchphoto.R
import com.envious.searchphoto.base.BaseFragment
import com.envious.searchphoto.databinding.AdvancedSearchFragmentBinding
import com.envious.searchphoto.util.Effect
import com.envious.searchphoto.util.Intent
import com.envious.searchphoto.util.State
import com.envious.searchphoto.util.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdvancedSearchFragment : BaseFragment<Intent,
    State,
    Effect>() {

    companion object {
        fun newInstance() = AdvancedSearchFragment()
    }

    private var _binding: AdvancedSearchFragmentBinding? = null
    private val binding get() = _binding!!
    private var sort: Sort = Sort.relevant
    private var color: Filter = Filter.any
    private var orientation: Orientation = Orientation.any

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = AdvancedSearchFragmentBinding.inflate(layoutInflater)
        viewModel.onIntentReceived(Intent.GetDefaultSetting)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            invalidate(it)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect { effect ->
                    effect?.getContentIfNotHandled()?.let {
                        renderEffect(it)
                    }
                }
            }
        }

        setButtonSettings()
    }

    override val layoutResourceId: Int
        get() = R.layout.advanced_search_fragment

    override fun invalidate(state: State) {
        // handle  setting data
        with(binding) {
            when (state.sort) {
                Sort.relevant -> {
                    radioRelevance.isChecked = true
                }
                Sort.latest -> {
                    radioNewest.isChecked = true
                }
                Sort.unknown -> {}
            }

            when (state.filter) {
                Filter.black_and_white -> {
                    radioBlackWhite.isChecked = true
                }
                Filter.black -> {}
                Filter.white -> {}
                Filter.yellow -> {}
                Filter.orange -> {}
                Filter.red -> {}
                Filter.purple -> {}
                Filter.magenta -> {}
                Filter.green -> {}
                Filter.teal -> {}
                Filter.blue -> {}
                Filter.unknown -> {}
                Filter.any -> {
                    radioAnyColor.isChecked = true
                }
            }

            when (state.orientation) {
                Orientation.portrait -> {
                    radioPortrait.isChecked = true
                }
                Orientation.landscape -> {
                    radioLandscape.isChecked = true
                }
                Orientation.squarish -> {
                    radioSquare.isChecked = true
                }
                Orientation.unknown -> {}
                Orientation.any -> {
                    radioAnyOrient.isChecked = true
                }
            }
            if (state.viewState == ViewState.BackToSearchResult) {
                findNavController().popBackStack()
            }
        }
    }

    private fun setButtonSettings() {
        with(binding) {
            radioGroupSort.setOnCheckedChangeListener { radioGroup, i ->
                val checkedRadioButton = radioGroup?.findViewById(radioGroup.checkedRadioButtonId) as? RadioButton
                Sort.values().forEach {
                    if (it.type == checkedRadioButton?.text.toString()) {
                        sort = it
                    }
                }
            }

            radioGroupColor.setOnCheckedChangeListener { radioGroup, i ->
                val checkedRadioButton = radioGroup?.findViewById(radioGroup.checkedRadioButtonId) as? RadioButton
                Filter.values().forEach {
                    if (it.color == checkedRadioButton?.text.toString()) {
                        color = it
                    }
                }
            }

            radioGroupOrientation.setOnCheckedChangeListener { radioGroup, i ->
                val checkedRadioButton = radioGroup?.findViewById(radioGroup.checkedRadioButtonId) as? RadioButton
                Orientation.values().forEach {
                    if (it.type == checkedRadioButton?.text.toString()) {
                        orientation = it
                    }
                }
            }

            buttonUpdateSearch.setOnClickListener {
                viewModel.onIntentReceived(
                    Intent.SetSettings(
                        sort = sort.name,
                        filter = color.name,
                        orientation = orientation.name
                    )
                )
            }

            // reset data
            buttonClear.setOnClickListener {
                radioBlackWhite.isChecked
                radioRelevance.isChecked
                radioPortrait.isChecked
            }

            buttonCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun renderEffect(effect: Effect) {
        when (effect) {
            is Effect.ShowToast -> {
                Toast.makeText(
                    requireContext(),
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Effect.ReturnToSearchResult -> {
                Toast.makeText(
                    requireContext(),
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }
}
