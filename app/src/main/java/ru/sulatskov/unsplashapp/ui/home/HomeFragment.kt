package ru.sulatskov.unsplashapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.R
import ru.sulatskov.unsplashapp.base.view.BaseFragment
import ru.sulatskov.unsplashapp.base.viewmodel.Status
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.databinding.FragmentHomeBinding
import ru.sulatskov.unsplashapp.databinding.FragmentProfileBinding
import ru.sulatskov.unsplashapp.model.network.dto.Photo
import java.lang.Exception

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var photosAdapter = PhotosAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getPhotos()
        binding?.list?.adapter = photosAdapter
        binding?.list?.layoutManager = LinearLayoutManager(context)
        homeViewModel.photo.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> onProgress()
                Status.SUCCESS -> onSuccess(it.data)
                Status.ERROR -> onError()
            }
        })

    }


    override fun onProgress() {

    }

    override fun hideProgress() {

    }

    override fun <T> onSuccess(data: T?) {
        try {
            photosAdapter.setData(data as List<Photo>)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    override fun showPlaceholder() {
    }

    override fun hidePlaceholder() {
    }

    override fun setupToolbar() {
    }

    override fun destroyBinding() {
        _binding = null
    }
}