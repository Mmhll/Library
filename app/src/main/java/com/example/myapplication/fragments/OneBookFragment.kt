package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.room.AppDatabase
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOneBookBinding
import com.example.myapplication.presenter.OneBookPresenter
import com.example.myapplication.room.BookEntity
import com.example.myapplication.view.OneBookView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class OneBookFragment : MvpAppCompatFragment(), OneBookView {


    private var _binding: FragmentOneBookBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter {
        OneBookPresenter(
            databaseName = "Peepo",
            context = requireContext(),
            id = requireArguments().getInt("bookId")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneBookBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.changeButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_oneBookFragment_to_changeFragment,
                savedInstanceState
            )
        }
    }

    override fun getBook(book: BookEntity) {
        Glide.with(requireActivity()).load(book.uri).into(binding.oneImage)
        binding.titleOne.text = book.title
        binding.authorOne.text = book.author
        binding.yearOne.text = book.year
        binding.aboutOne.text = book.about
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}