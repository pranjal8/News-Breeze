package com.pranjal.newsappassignment.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.pranjal.newsappassignment.MainActivity
import com.pranjal.newsappassignment.R
import com.pranjal.newsappassignment.databinding.FragmentArticleBinding
import com.pranjal.newsappassignment.databinding.FragmentSavedNewsBinding
import com.pranjal.newsappassignment.viewmodel.NewsViewModel


class ArticleFragment : Fragment(R.layout.fragment_article) {
    private var _binding: FragmentArticleBinding? =null
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    private val args : ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding= FragmentArticleBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= (activity as MainActivity).newsViewModel

        setUpWebView()
        binding.fab.setOnClickListener {
            saveArticle(view)
        }
    }

    private fun saveArticle(view: View) {
        val article= args.article
        viewModel.saveArticle(article)
        Snackbar.make(view, "Job saved successfully",Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpWebView() {
        val article= args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}