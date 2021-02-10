package ru.sulatskov.unsplashapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.R
import ru.sulatskov.unsplashapp.base.view.BaseFragment
import ru.sulatskov.unsplashapp.base.viewmodel.Status
import ru.sulatskov.unsplashapp.databinding.FragmentProfileBinding
import ru.sulatskov.unsplashapp.model.network.dto.UserProfile

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.hasToken()
        profileViewModel.hasToken.observe(viewLifecycleOwner, Observer {
            if (it) {
                profileViewModel.getUser()
                profileViewModel.user.observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        Status.LOADING -> onProgress()
                        Status.SUCCESS -> onSuccess(it.data)
                        Status.ERROR -> onError()
                    }
                })
            } else {
                findNavController().navigate(R.id.action_to_oauth)
            }
        })
    }

    override fun <T> onSuccess(data: T?) {
        Toast.makeText(context, (data as UserProfile).lastName, Toast.LENGTH_SHORT).show()
    }

    override fun destroyBinding() {
        _binding = null
    }
}