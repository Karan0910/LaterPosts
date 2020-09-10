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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import later.com.linkinbio.api.ApiService
import later.com.linkinbio.databinding.FragmentPostsBinding
import later.com.linkinbio.model.LinkinbioPost
import later.com.linkinbio.ui.adapter.PhotosAdapter
import later.com.linkinbio.ui.onItemClickListener
import later.com.linkinbio.ui.viewmodel.PostsViewModel


class PostsFragment : Fragment(), onItemClickListener {


    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var postsViewModel: PostsViewModel
    private lateinit var photosAdapter: PhotosAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postsViewModel =
                ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return PostsViewModel(Schedulers.io()
                                ,AndroidSchedulers.mainThread()
                                , ApiService.create()) as T
                    }

                }).get(PostsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)

        init()
        observeLiveData()

        return binding.root
    }

    private fun init(){
        postsViewModel.fetchLinks()
        photosAdapter = PhotosAdapter(this)

        binding.photosRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = photosAdapter
        }
    }

    private fun observeLiveData(){
        postsViewModel.linksLiveData.observe(viewLifecycleOwner, Observer { linksList ->
            linksList.let {
                _binding?.photosRecyclerView?.visibility ?:  View.VISIBLE
                _binding?.noPostText?.visibility ?:  View.INVISIBLE
                photosAdapter.setUpPhotos(it)
            }
        })

        postsViewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer {
            if(it) {
                _binding?.photosRecyclerView?.visibility =  View.GONE
                _binding?.noPostText?.visibility =  View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(post: LinkinbioPost) {

        val b = Bundle()
        b.putString("post_url", post.linkUrl)
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
        postsViewModel.onItemClick(post)

    }


}