package later.com.linkinbio.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import later.com.linkinbio.R
import later.com.linkinbio.databinding.ItemPhotoBinding
import later.com.linkinbio.model.LinkinbioPost
import later.com.linkinbio.ui.onItemClickListener

class PhotosAdapter(private val onItemClickListener: onItemClickListener) :
        RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    private val postList = ArrayList<LinkinbioPost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val itemPhotoBinding: ItemPhotoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_photo,
                parent,
                false
        )
        return PhotosViewHolder(itemPhotoBinding)
    }

    override fun onBindViewHolder(holder: PhotosAdapter.PhotosViewHolder, position: Int) {
        val post = postList[position]
        holder.itemPhotoBinding.photo = post
        Glide.with(holder.itemPhotoBinding.imageViewPhoto.context)
                .load(postList[position].image_url)
                .centerCrop()
                //.placeholder(R.drawable.ic_placeholder)
                .into(holder.itemPhotoBinding.imageViewPhoto)

        if (post.isViewed)
            holder.itemPhotoBinding.textDomainUrl.setBackgroundResource(R.color.whiteSmoke)
        else
            holder.itemPhotoBinding.textDomainUrl.setBackgroundResource(R.color.colorGray)


        holder.itemPhotoBinding.textDomainUrl.text= post.link_url?.toUri()?.authority ?: ""

        holder.itemPhotoBinding.postCardView.setOnClickListener(View.OnClickListener{
            onItemClickListener.onItemClick(post)
        })

        holder.itemPhotoBinding.executePendingBindings()
    }

    inner class PhotosViewHolder(val itemPhotoBinding: ItemPhotoBinding) :
            RecyclerView.ViewHolder(itemPhotoBinding.root)


    fun setUpPhotos(listOfPosts: List<LinkinbioPost>) {
        postList.clear()
        postList.addAll(listOfPosts)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = postList.size
}