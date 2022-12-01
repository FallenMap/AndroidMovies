package com.davilav.wigilabstest.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.DialogFragment
import com.davilav.wigilabstest.databinding.FilmLayoutOpenBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.ui.movie.MovieViewModel
import com.davilav.wigilabstest.utils.RoundCornersBitmap
import com.davilav.wigilabstest.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
class FilmDialog : DialogFragment() {

    private var _binding: FilmLayoutOpenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModel()
    private var mListener: ((MovieModel) -> Unit)? = null
    private lateinit var data: MovieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable(MOVIE_KEY) as MovieModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilmLayoutOpenBinding.inflate(inflater, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog!!.setCanceledOnTouchOutside(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.tvPopularity.text = data.popularity.toString()
        binding.tvReleaseDate.text = data.release_date
        binding.tvAverageScore.text = data.vote_average.toString()
        binding.tvDescriptionOpen.text = data.overview
        binding.tvMovieTitleOpen.text = data.title
        binding.ivPosterOpen.setImageBitmap(
            RoundCornersBitmap(
                data.poster_img!!,
                50
            ).roundedCornerBitmap()
        )

        binding.btnDownload.setOnClickListener {
            viewModel.downloadMovie(Utils().toMovie(data), requireContext())
            Toast.makeText(requireContext(), "saved", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val MOVIE_KEY = "movie_key"

        @JvmStatic
        fun instance(
            data: MovieModel,
            listener: (MovieModel) -> Unit
        ): FilmDialog {
            val fragment = FilmDialog().apply {
                arguments = Bundle().apply {
                    isCancelable = false
                    putSerializable(MOVIE_KEY, data as Serializable)
                }
            }
            fragment.mListener = listener
            return fragment
        }
    }
}