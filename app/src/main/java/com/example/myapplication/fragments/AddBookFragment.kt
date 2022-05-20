package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.room.AppDatabase
import com.example.myapplication.room.BookEntity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddBookBinding
import com.example.myapplication.presenter.AddBookPresenter
import com.example.myapplication.view.AddBookView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.lang.Exception

class AddBookFragment : MvpAppCompatFragment(), AddBookView {

    private var _binding : FragmentAddBookBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter { AddBookPresenter(databaseName = "Peepo", context = requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBookBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.AddImageButton.setOnClickListener {
            if (binding.AddImageText.text.toString().isNotEmpty()){
                Glide.with(requireActivity()).load(binding.AddImageText.text.toString()).into(binding.AddImage)
            }
        }

        binding.AddBookButton.setOnClickListener {
            presenter.putBook(
                about = binding.aboutAdd.text.toString(),
                author = binding.authorAdd.text.toString(),
                uri = binding.AddImageText.text.toString(),
                year = binding.yearAdd.text.toString(),
                title = binding.AddTitle.text.toString()
            )

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun putBook() {
        Toast.makeText(requireContext(), getString(R.string.successAdded), Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.error))
            .setPositiveButton("OK", null)
            .create()
            .show()
    }
}