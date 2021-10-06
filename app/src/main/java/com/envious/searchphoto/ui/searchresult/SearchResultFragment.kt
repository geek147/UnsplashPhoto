package com.envious.searchphoto.ui.searchresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.envious.searchphoto.R
import com.envious.searchphoto.base.BaseFragment
import com.envious.searchphoto.databinding.SearchResultFragmentBinding
import com.envious.searchphoto.ui.adapter.ItemAdapter
import com.envious.searchphoto.util.Effect
import com.envious.searchphoto.util.EndlessRecyclerViewScrollListener
import com.envious.searchphoto.util.Intent
import com.envious.searchphoto.util.State
import com.envious.searchphoto.util.ViewState

class SearchResultFragment : BaseFragment<Intent,
    State,
    Effect,
    SearchResultViewModel>() {

    companion object {
        val TAG = this::class.simpleName
        const val EXTRA_QUERY = "EXTRA_QUERY"

        @JvmStatic
        fun create(
            query: String,
        ): SearchResultFragment {
            val fragment = SearchResultFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_QUERY, query)
            }

            return fragment
        }
    }

    private var _binding: SearchResultFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ItemAdapter

    private lateinit var query: String

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SearchResultFragmentBinding.inflate(layoutInflater)
        setupRecyclerView()
        query = arguments?.getString(EXTRA_QUERY).orEmpty()
        binding.textSearchResult.text = "Search Result : $query"
        viewModel = ViewModelProvider(requireActivity())[SearchResultViewModel::class.java]
        dispatch(
            Intent.SearchPhoto(query)
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            recyclerview.layoutManager = gridLayoutManager
            recyclerview.itemAnimator = null
            adapter = ItemAdapter(requireContext())
            adapter.setHasStableIds(true)
            recyclerview.adapter = adapter
            scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?,
                ) {
                    currentPage = page + 1
                    dispatch(
                        Intent.LoadNext(currentPage)
                    )
                }
            }
            recyclerview.addOnScrollListener(scrollListener)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.main_fragment

    override fun invalidate(state: State) {
        with(binding) {
            pgProgressList.visibility = if (state.showLoading) View.VISIBLE else View.GONE
            errorView.visibility = if (state.showError) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        when (state.viewState) {
            ViewState.EmptyList -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        showError(
                            title = resources.getString(R.string.empty_state_title),
                            message = resources.getString(R.string.empty_state_message)
                        )
                    }
                    adapter.setList(emptyList())
                    recyclerview.visibility = View.GONE
                }
            }
            ViewState.ErrorFirstInit -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        showError()
                    }
                    adapter.setList(emptyList())
                    recyclerview.visibility = View.GONE
                }
            }
            ViewState.ErrorLoadMore -> {
                with(binding) {
                    recyclerview.visibility = View.VISIBLE
                }
            }
            ViewState.Idle -> {}
            ViewState.SuccessFirstInit -> {
                with(binding) {
                    recyclerview.visibility = View.VISIBLE
                    adapter.setList(state.listPhoto)
                }
            }
            ViewState.SuccessLoadMore -> {
                with(binding) {
                    recyclerview.visibility = View.VISIBLE
                    adapter.addData(state.listPhoto)
                }
            }
        }
    }

    override fun provideViewModel() = SearchResultViewModel::class.java

    override fun renderEffect(effect: Effect) {
        when (effect) {
            is Effect.ShowToast -> {
                Toast.makeText(
                    requireContext(),
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
