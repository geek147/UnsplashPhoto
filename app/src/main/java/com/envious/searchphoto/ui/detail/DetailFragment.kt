package com.envious.searchphoto.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.envious.data.local.imagecache.ImageLoader
import com.envious.searchphoto.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    companion object {
        val TAG = this::class.simpleName
        const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"

        @JvmStatic
        fun create(
            query: String,
        ): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_IMAGE_URL, query)
            }

            return fragment
        }
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        imageUrl = arguments?.getString(EXTRA_IMAGE_URL).orEmpty()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImageLoader.with(requireContext()).load(binding.imageFull, imageUrl)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle the up button here
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) ||
            super.onOptionsItemSelected(item)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}
