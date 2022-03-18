package com.example.myapplication

import android.app.AlertDialog
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentAddBookBinding
import java.lang.Exception

class AddBookFragment : Fragment() {

    private lateinit var binding : FragmentAddBookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBookBinding.inflate(inflater)
        
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "Peepo"
        ).allowMainThreadQueries().build()



        binding.AddImageButton.setOnClickListener {
            if (binding.AddImageText.text.toString().isNotEmpty()){
                Glide.with(requireActivity()).load(binding.AddImageText.text.toString()).into(binding.AddImage)
            }
        }

        var lastIndex = 0
        try {
            lastIndex = db.bookDao().getBooks()[db.bookDao().getBooks().size - 1].id + 1
        }
        catch (e : Exception){

        }
        binding.AddBookButton.setOnClickListener {
            if (binding.AddImageText.text.toString().isNotEmpty() and
                binding.AddTitle.text.toString().isNotEmpty() and
                binding.aboutAdd.text.toString().isNotEmpty() and
                binding.authorAdd.text.toString().isNotEmpty() and
                binding.yearAdd.text.toString().isNotEmpty()) {
                db.bookDao().putBook(
                    BookEntity(
                        lastIndex,
                        binding.AddTitle.text.toString(),
                        binding.AddImageText.text.toString(),
                        binding.aboutAdd.text.toString(),
                        binding.authorAdd.text.toString(),
                        binding.yearAdd.text.toString()
                    )
                )
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, BooksFragment())
                    .commit()
            }
            else {
                AlertDialog.Builder(requireContext())
                    .setMessage("Одно или несколько полей не заполнены и(или) не выбрана картинка")
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            }
        }

        binding.AddBackButton.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, BooksFragment())
                .commit()
        }
        return binding.root
    }
}