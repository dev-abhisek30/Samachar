package com.dev.abhisek30.samachar.ui.stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.abhisek30.samachar.R
import com.dev.abhisek30.samachar.databinding.FragmentSourceBinding
import com.dev.abhisek30.samachar.databinding.FragmentStoryBinding


class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            StoryFragment().apply {
                arguments = Bundle().apply {
                    putString("", param1)
                    putString("", param2)
                }
            }
    }
}