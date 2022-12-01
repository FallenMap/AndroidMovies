package com.davilav.wigilabstest.ui.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.databinding.FilmLayoutBinding
import com.davilav.wigilabstest.ui.movie.MovieViewModel
import com.davilav.wigilabstest.utils.ArrayImpl


class MovieAdapter(
    items: List<MovieModel>,
    private val viewModel: MovieViewModel,
    private val listenerDetail: (MovieModel) -> Unit,
    private val listenerDownload: (MovieModel) -> Unit,
    private val hasConnection: Boolean
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var mContext: Context
    private var filterResults: ArrayImpl = ArrayImpl().toArray(items.toMutableList()) as ArrayImpl

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val viewBinding = FilmLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mContext, viewBinding, viewModel)
    }

    fun updateData(data: List<MovieModel>) {
        filterResults.clear()
        filterResults.toArray(data.toMutableList())
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int = filterResults.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindData(filterResults.get(position) as MovieModel, listenerDetail,listenerDownload,hasConnection)

    class ViewHolder(private val context: Context, private val binding: FilmLayoutBinding, val viewModel: MovieViewModel) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: MovieModel, listenerDetail: (MovieModel) -> Unit, listenerDownload: (MovieModel) -> Unit,hasConnection: Boolean) {
            binding.tvMovieTitle.text = data.title
            binding.tvOverview.text = data.overview
            binding.tvOverview.text = binding.tvOverview.text.slice(1..binding.tvOverview.text.length-3)
            binding.tvOverview.append("...")
            binding.ivPoster.setImageBitmap(data.poster_img)
            binding.cardItem.setOnClickListener { listenerDetail.invoke(data) }
            binding.imbtnDownload.setOnClickListener { listenerDownload.invoke(data) }


        }
    }
}

