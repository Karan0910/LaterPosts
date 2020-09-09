package later.com.linkinbio.ui.fragment

import later.com.linkinbio.R

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import later.com.linkinbio.databinding.FragmentPhotosBinding
import later.com.linkinbio.model.LinkinbioPost
import later.com.linkinbio.ui.adapter.PhotosAdapter
import later.com.linkinbio.ui.onItemClickListener
import later.com.linkinbio.ui.viewmodel.PhotosViewModel


class PhotosFragment : Fragment(), onItemClickListener {


    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private lateinit var photosViewModel: PhotosViewModel
    private lateinit var photosAdapter: PhotosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photosViewModel =
                ViewModelProvider(requireActivity()).get(PhotosViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)

        init()
        observeLiveData()

        return binding.root
    }

    private fun init(){
        photosViewModel.fetchLinks()
        photosAdapter = PhotosAdapter(this)

        binding.photosRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = photosAdapter
        }

    }

    private fun observeLiveData(){
        photosViewModel.linksLiveData.observe(viewLifecycleOwner, Observer { linksList ->
            linksList.let {
                _binding?.photosRecyclerView?.visibility ?:  View.VISIBLE
                photosAdapter.setUpPhotos(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(post: LinkinbioPost) {

        val b = Bundle()
        b.putString("post_url", post.link_url)
        val fragment: Fragment = PostDetailsFragment()
        fragment.arguments = b
        try {

            parentFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .add(R.id.fragment_view_holder, fragment)
                    .addToBackStack(null)
                    .commit()
        }  catch (e :Exception) {
            e.printStackTrace()
        }


    }

}