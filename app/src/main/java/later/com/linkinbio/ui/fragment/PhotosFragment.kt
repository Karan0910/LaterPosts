package later.com.linkinbio.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import later.com.linkinbio.databinding.FragmentPhotosBinding
import later.com.linkinbio.ui.viewmodel.PhotosViewModel

class PhotosFragment : Fragment() {


    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private lateinit var photosViewModel: PhotosViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photosViewModel =
                ViewModelProvider(requireActivity()).get(PhotosViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)

        init()

        //observeLiveData()

        return binding.root
    }

    private fun init(){
        photosViewModel.fetchLinks()
    }

    private fun observerLiveData(){
        photosViewModel.linksLiveData.observe(viewLifecycleOwner, Observer { linksList ->
            linksList.let {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}