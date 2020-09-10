package later.com.linkinbio.ui.fragment

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
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

        _binding?.webView?.webViewClient = webClient()
        _binding?.webView?.loadUrl(arguments?.getString("post_url"))
        val webSettings = _binding?.webView?.settings

        if (webSettings != null) {
            webSettings.javaScriptEnabled = true
        }
        _binding?.webView?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK && _binding?.webView?.canGoBack()!!) {
                    _binding?.webView?.goBack()
                    return true
                }
                return false
            }
        })

        (activity as AppCompatActivity).setSupportActionBar(_binding!!.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    inner class webClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            _binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.home) {
            activity?.supportFragmentManager?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }




}