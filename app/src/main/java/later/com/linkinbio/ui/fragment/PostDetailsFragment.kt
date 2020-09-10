package later.com.linkinbio.ui.fragment

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import later.com.linkinbio.databinding.FragmentPostDetailsBinding


class PostDetailsFragment : Fragment() {


    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        initView()
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView(){

        _binding?.webView?.webViewClient = WebViewClient()
        _binding?.webView?.loadUrl(arguments?.getString("post_url"))
        val webSettings = _binding?.webView?.settings
        if (webSettings != null) {
            webSettings.javaScriptEnabled = true
        }

        (activity as AppCompatActivity).setSupportActionBar(_binding!!.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.home) {
            activity?.supportFragmentManager?.popBackStack()
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}