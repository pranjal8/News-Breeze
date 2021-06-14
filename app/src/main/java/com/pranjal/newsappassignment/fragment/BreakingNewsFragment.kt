package com.pranjal.newsappassignment.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranjal.newsappassignment.MainActivity
import com.pranjal.newsappassignment.R
import com.pranjal.newsappassignment.adapters.NewsAdapter
import com.pranjal.newsappassignment.databinding.FragmentBreakingNewsBinding
import com.pranjal.newsappassignment.utils.Resource
import com.pranjal.newsappassignment.viewmodel.NewsViewModel

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private var _binding : FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    val TAG= "BreakingNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
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
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment, bundle)
        }
    }

    private fun hideProgressBar() {
       binding.paginationProgressBar.visibility = View.GONE

    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE

    }

    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager= LinearLayoutManager(activity)

        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
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
    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}