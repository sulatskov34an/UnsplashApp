package ru.sulatskov.unsplashapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.sulatskov.unsplashapp.R
import ru.sulatskov.unsplashapp.common.TimeUtils
import ru.sulatskov.unsplashapp.common.getProgressBar
import ru.sulatskov.unsplashapp.common.gone
import ru.sulatskov.unsplashapp.databinding.ItemPhotoCardBinding
import ru.sulatskov.unsplashapp.model.network.dto.Photo

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotoCardViewHolder>() {

    private val list = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoCardViewHolder {
        val binding = ItemPhotoCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoCardViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun setData(items: List<Photo>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    class PhotoCardViewHolder(private val binding: ItemPhotoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.avatar.apply {
                val path = photo.user?.profileImage?.medium
                Picasso.get()
                    .load(path)
                    .error(R.drawable.ic_error)
                    .placeholder(getProgressBar(itemView.context))
                    .into(binding.avatar)
            }
            binding.userName.text = photo.user?.username
            photo.location?.title?.let {
                binding.location.text = it
            } ?: binding.location.gone()
            binding.image.apply {
                val path = photo.urls?.regular
                Picasso.get()
                    .load(path)
                    .error(R.drawable.ic_error)
                    .placeholder(getProgressBar(itemView.context))
                    .into(binding.image)
            }
            binding.likedByUser.isActivated = photo.likedByUser

            if (photo.likes > 0) {
                binding.like.text = itemView.context.resources.getQuantityString(
                    R.plurals.likes,
                    photo.likes,
                    photo.likes
                )
            } else {
                binding.like.gone()
            }

            photo.description?.let {
                binding.description.text = it
            } ?: binding.description.gone()

            photo.createdAt?.let {
                binding.createdAt.text = TimeUtils.getDate(itemView.context, it)
            }

        }
    }
}
