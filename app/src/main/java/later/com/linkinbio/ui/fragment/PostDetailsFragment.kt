package later.com.linkinbio.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import later.com.linkinbio.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : Fragment() {


    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        initWebView()

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(){

        _binding?.webView?.webViewClient = WebViewClient()
        _binding?.webView?.loadUrl(arguments?.getString("post_url"))
        val webSettings = _binding?.webView?.settings
        if (webSettings != null) {
            webSettings.javaScriptEnabled = true
        }

    }

}