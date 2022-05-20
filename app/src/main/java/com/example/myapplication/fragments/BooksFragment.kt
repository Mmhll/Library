package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBooksBinding
import com.example.myapplication.presenter.BooksPresenter
import com.example.myapplication.room.BookEntity
import com.example.myapplication.util.BookRecycler
import com.example.myapplication.view.BooksView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class BooksFragment : MvpAppCompatFragment(), BooksView {

    private var _binding : FragmentBooksBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter {
        BooksPresenter(requireContext(), "Peepo")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_booksFragment_to_addBookFragment)
        }

        binding.helpButton.setOnClickListener {
           presenter.getManual()
        }
    }

    override fun showManual(message : Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(message))
            .create()
            .show()
    }

    override fun fillRecycler(data: MutableList<BookEntity>) {
        val recyclerAdapter = BookRecycler(requireContext(), data)
        recyclerAdapter.setOnItemClickListener(object : BookRecycler.onItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = bundleOf("bookId" to data[position].id)
                findNavController().navigate(R.id.action_booksFragment_to_oneBookFragment, bundle)
            }
        })
        recyclerAdapter.setOnLongItemClickListener(object : BookRecycler.onLongItemClickListener {
            override fun onLongItemClick(position: Int): Boolean {
                presenter.deleteBook(data[position].id)
                data.removeAt(position)
                binding.recyclerBooks.adapter?.notifyItemRemoved(position)
                return true
            }

        })
        binding.recyclerBooks.adapter = recyclerAdapter
    }

    override fun booksIsEmpty() {
        binding.recyclerBooks.adapter = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}