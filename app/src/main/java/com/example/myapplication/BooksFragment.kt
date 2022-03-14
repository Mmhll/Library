package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.myapplication.databinding.FragmentBooksBinding

class BooksFragment : Fragment() {
    private lateinit var binding : FragmentBooksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooksBinding.inflate(inflater)
        binding.buttonAdd.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, AddBookFragment())
                .commit()
        }
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "Peepo"
        ).allowMainThreadQueries().build()
        val dao = db.bookDao()
        val books = dao.getBooks()
        val recyclerAdapter = BookRecycler(requireContext(), books)
        recyclerAdapter.setOnItemClickListener(object : BookRecycler.onItemClickListener{
            override fun onItemClick(position: Int) {
                var prefs = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE)
                prefs.edit().putInt("id", books[position].id).apply()
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, OneBookFragment())
                    .commit()
            }
        })
        recyclerAdapter.setOnLongItemClickListener(object : BookRecycler.onLongItemClickListener{
            override fun onLongItemClick(position: Int): Boolean {
                db.bookDao().deleteById(DeleteId(books[position].id))
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, BooksFragment())
                    .commit()
                return true
            }

        })

        binding.helpButton.setOnClickListener {
            var dialog = AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.help))
                .create()
                .show()
        }
        binding.recyclerBooks.adapter = recyclerAdapter
        return binding.root
    }
}