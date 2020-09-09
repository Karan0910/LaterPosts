package later.com.linkinbio.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import later.com.linkinbio.R
import later.com.linkinbio.databinding.ItemPhotoBinding
import later.com.linkinbio.model.LinkinbioPost

class PhotosAdapter :
        RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    private val photoList = ArrayList<LinkinbioPost>()

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
        val photo = photoList[position]
        holder.itemPhotoBinding.photo = photo
        Glide.with(holder.itemPhotoBinding.imageViewPhoto.context)
                .load(photoList[position].image_url)
                .centerCrop()
                //.placeholder(R.drawable.ic_placeholder)
                .into(holder.itemPhotoBinding.imageViewPhoto)

        holder.itemPhotoBinding.textDomainUrl.text= photo.link_url?.toUri()?.authority ?: ""
        holder.itemPhotoBinding.executePendingBindings()
    }

    inner class PhotosViewHolder(val itemPhotoBinding: ItemPhotoBinding) :
            RecyclerView.ViewHolder(itemPhotoBinding.root)


    fun setUpPhotos(listOfPhotos: List<LinkinbioPost>) {
        photoList.clear()
        photoList.addAll(listOfPhotos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = photoList.size
}