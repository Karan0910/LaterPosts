package later.com.linkinbio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import later.com.linkinbio.R
import later.com.linkinbio.databinding.ItemPhotoBinding
import later.com.linkinbio.model.LinkinbioPost

class PhotosAdapter() :
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
        val photo = photoList.get(position)
        holder.itemGifBinding.executePendingBindings()
    }

    inner class PhotosViewHolder(val itemGifBinding: ItemPhotoBinding) :
            RecyclerView.ViewHolder(itemGifBinding.root)

    override fun getItemCount(): Int = photoList.size
}