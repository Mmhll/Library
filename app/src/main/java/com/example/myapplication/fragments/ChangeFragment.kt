package com.example.myapplication.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.room.AppDatabase
import com.example.myapplication.room.BookEntity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChangeBinding
import com.example.myapplication.presenter.ChangePresenter
import com.example.myapplication.view.ChangeView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ChangeFragment : MvpAppCompatFragment(), ChangeView {

    private var _binding: FragmentChangeBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter { ChangePresenter("Peepo", requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().get("bookId").toString().toInt()
        val data = presenter.getData(id)
        presenter.showData(id)

        binding.changeBookButton.setOnClickListener {
            presenter.changeData(data)
            findNavController().navigate(R.id.action_changeFragment_to_booksFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun changeBook() {
        findNavController().navigate(R.id.action_changeFragment_to_booksFragment)
    }

    override fun showMessage(message: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(message))
            .show()
    }

    override fun showBook(book: BookEntity) {
        binding.aboutChange.setText(book.about)
        binding.changeTitle.setText(book.title)
        binding.authorChange.setText(book.author)
        binding.yearChange.setText(book.year)
        binding.changeImageText.setText(book.uri)
        Glide.with(requireActivity()).load(book.uri).into(binding.changeImage)
    }
}