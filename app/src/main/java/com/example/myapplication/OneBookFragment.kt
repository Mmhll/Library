package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentOneBookBinding

class OneBookFragment : Fragment() {
    private lateinit var binding : FragmentOneBookBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBookBinding.inflate(inflater)
        val id = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE).getInt("id", 0)
        Log.d("TAG", id.toString())
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "Peepo"
        ).allowMainThreadQueries().build()
        var book = db.bookDao().getCurrentBook(id)
        Glide.with(requireActivity()).load(book.uri).into(binding.oneImage)
        binding.titleOne.text = book.title
        binding.authorOne.text =book.author
        binding.yearOne.text = book.year
        binding.aboutOne.text = book.about

        binding.oneBackButton.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, BooksFragment())
                .commit()
        }
        binding.changeButton.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ChangeFragment())
                .commit()
        }
        return binding.root
    }
}