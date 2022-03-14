package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentChangeBinding

class ChangeFragment : Fragment() {

    private lateinit var binding : FragmentChangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangeBinding.inflate(inflater)
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "Peepo"
        ).allowMainThreadQueries().build()
        val prefs = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        val id = prefs.getInt("id", 0)
        val book = db.bookDao().getCurrentBook(id)

        binding.aboutChange.setText(book.about)
        binding.changeTitle.setText(book.title)
        binding.authorChange.setText(book.author)
        binding.yearChange.setText(book.year)
        binding.changeImageText.setText(book.uri)
        Glide.with(requireActivity()).load(book.uri).into(binding.changeImage)

        binding.changeBookButton.setOnClickListener {
            if (binding.changeImageText.text.toString().isNotEmpty() and
                binding.changeTitle.text.toString().isNotEmpty() and
                binding.aboutChange.text.toString().isNotEmpty() and
                binding.authorChange.text.toString().isNotEmpty() and
                binding.yearChange.text.toString().isNotEmpty()) {
                db.bookDao().putBook(
                    BookEntity(
                        id,
                        binding.changeTitle.text.toString(),
                        binding.changeImageText.text.toString(),
                        binding.aboutChange.text.toString(),
                        binding.authorChange.text.toString(),
                        binding.yearChange.text.toString()
                    )
                )
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, BooksFragment())
                    .commit()
            }
            else {
                var dialog = AlertDialog.Builder(requireContext())
                    .setMessage("Одно или несколько полей не заполнены и(или) не выбрана картинка")
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            }
        }
        return binding.root

    }
}