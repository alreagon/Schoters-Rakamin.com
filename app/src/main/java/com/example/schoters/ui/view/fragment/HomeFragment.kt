package com.example.schoters.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.schoters.R
import com.example.schoters.data.remote.model.GetNewsResponse
import com.example.schoters.databinding.FragmentHomeBinding
import com.example.schoters.ui.view.adapter.NewsApiAdapter
import com.example.schoters.ui.viewmodel.NewssViewModel
import com.example.schoters.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        const val apiKey = "f1e5a792cea444b58f25ee7a9dc0d245"
    }

    private lateinit var rvNews: RecyclerView
    private val homeViewModel: NewssViewModel by viewModel()
    private lateinit var newsAdater: NewsApiAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeHome()
        setupRecycler()

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_bar.clearFocus()
                homeViewModel.getNewss(query!!, apiKey)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    homeViewModel.getNewss(newText, apiKey)
                    homeViewModel.getNews.observe(viewLifecycleOwner) { response ->

                    }
                }
                return true
            }
        })
    }

    private fun observeHome() {
        homeViewModel.getNews.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.PBSearch.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        binding.PBSearch.visibility = View.GONE
                        newsAdater.submitData(listOf(it))
                    }
                    if (it.data!!.status.isNullOrEmpty()) {
                        binding.displayDefault.visibility = View.VISIBLE
                    } else {
                        binding.displayDefault.visibility = View.GONE
                    }
                    binding.PBSearch.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.PBSearch.visibility = View.GONE
                    binding.displayDefault.visibility = View.GONE
                }
            }
        }
    }

    private fun setupRecycler() {

        newsAdater = NewsApiAdapter()
        rvNews = binding.rvSearchResult
        rvNews.setHasFixedSize(true)
        rvNews.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        newsAdater.setOnItemClickCallback(object : NewsApiAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GetNewsResponse) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        data.status.toString()
                    )
                )
            }
        })
        rvNews.adapter = newsAdater
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
