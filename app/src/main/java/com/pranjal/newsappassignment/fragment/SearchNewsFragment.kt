package com.pranjal.newsappassignment.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pranjal.newsappassignment.MainActivity
import com.pranjal.newsappassignment.R
import com.pranjal.newsappassignment.adapters.NewsAdapter
import com.pranjal.newsappassignment.databinding.FragmentSearchNewsBinding
import com.pranjal.newsappassignment.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.pranjal.newsappassignment.utils.Resource
import com.pranjal.newsappassignment.viewmodel.NewsViewModel
import kotlinx.coroutines.*

class SearchNewsFragment : Fragment(R.layout.fragment_search_news){

    private var _binding :FragmentSearchNewsBinding? =null
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    val TAG = "SearchNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).newsViewModel
        setUpRecyclerView()

        newsAdapter.setOnClickListener {
            val bundle =Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
        }

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity,"An error occurred : ${message}", Toast.LENGTH_SHORT).show()

                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }
    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.GONE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        newsAdapter= NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}