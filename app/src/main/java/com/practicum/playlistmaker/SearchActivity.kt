package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SearchActivity : AppCompatActivity() {
    private var clearButtonVisibility = false
    private var searchValue = TEXT_DEF

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val TEXT_DEF = ""
        const val DRAWABLE_RIGHT = 2
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.requestFocus()
        searchBar.hint = "Поиск" // Set the hint for the search bar
        searchBar.setText(searchValue)

        // TextWatcher to handle clear button visibility and searchValue updates
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(s, searchBar)
                searchValue = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }

        searchBar.addTextChangedListener(simpleTextWatcher)

        // TouchListener to handle the clear button functionality
        searchBar.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                searchBar.postDelayed({
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
                }, 100)
            }
            if (clearButtonVisibility && event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (searchBar.right - searchBar.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    searchBar.setText("")
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    // Handle saving and restoring of searchValue
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchValue = savedInstanceState.getString(SEARCH_TEXT, TEXT_DEF)
    }

    // Method to update clear button visibility based on search text
    private fun clearButtonVisibility(s: CharSequence?, v: EditText) {
        clearButtonVisibility = if (s.isNullOrEmpty()) {
            v.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)
            false
        } else {
            v.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_search,
                0,
                R.drawable.ic_cancel,
                0
            )
            true
        }
    }
}