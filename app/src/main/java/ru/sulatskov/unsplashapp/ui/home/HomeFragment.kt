package ru.sulatskov.unsplashapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.base.view.BaseFragment
import ru.sulatskov.unsplashapp.base.viewmodel.Status
import ru.sulatskov.unsplashapp.databinding.FragmentHomeBinding
import ru.sulatskov.unsplashapp.model.network.dto.Photo

@AndroidEntryPoint
class HomeFragment : BaseFragment(), PhotoListClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var photosAdapter = PhotosAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getPhotos()
        binding?.list?.adapter = photosAdapter
        binding?.list?.layoutManager = LinearLayoutManager(context)
        binding?.swipeContainer?.setOnRefreshListener {
            homeViewModel.getPhotos()
        }
        homeViewModel.photo.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> onProgress()
                Status.SUCCESS -> onSuccess(it.data)
                Status.ERROR -> onError()
            }
        })
    }

    override fun onProgress() {
        binding?.swipeContainer?.isRefreshing = false
    }

    override fun hideProgress() {
        binding?.swipeContainer?.isRefreshing = false
    }

    override fun <T> onSuccess(data: T?) {
        try {
            photosAdapter.setData(data as List<Photo>)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    override fun destroyBinding() {
        _binding = null
    }

    override fun onLikeClick(isLike: Boolean, id: String?) {
        if (isLike) {
            homeViewModel.likePhoto(id)
        } else {
            homeViewModel.unlikePhoto(id)
        }
    }
}