package com.practicum.playlistmaker.presentation.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.creator.Creator.provideSearchHistoryRepository
import com.practicum.playlistmaker.data.network.ItunesApi
import com.practicum.playlistmaker.domain.api.OnItemClickListener
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.api.TrackInteractor
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.ui.player.AudioPlayer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private var searchValue = TEXT_DEF
    private val baseUrl = "https://itunes.apple.com/"

    private val tracks = mutableListOf<Track>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var placeholderMessage: TextView
    private lateinit var placeholderImage: ImageView
    private lateinit var updateButton: Button
    private lateinit var placeholderLayout: LinearLayout
    private lateinit var historyLayout: LinearLayout
    private lateinit var searchAdapter: TrackAdapter
    private lateinit var historyAdapter: TrackAdapter
    private lateinit var searchHistory: SearchHistoryRepository
    private lateinit var progressBar: ProgressBar
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunesService = retrofit.create(ItunesApi::class.java)

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val INTENT_TRACK_KEY = "intent_track"
        const val TEXT_DEF = ""
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val searchRunnable = Runnable { search() }
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        window.statusBarColor = resources.getColor(R.color.status_bar, theme)
        window.navigationBarColor = resources.getColor(R.color.navigation_bar, theme)
        placeholderImage = findViewById(R.id.placeholderImage)
        placeholderMessage = findViewById(R.id.placeholderMessage)
        updateButton = findViewById(R.id.updateResponse)
        placeholderLayout = findViewById(R.id.placeholder)
        recyclerView = findViewById(R.id.recycle_view)
        historyRecyclerView = findViewById(R.id.recycle_history_view)
        historyLayout = findViewById(R.id.history_layout)
        progressBar = findViewById(R.id.progress_bar)

        // Реализация тулбара - Обработка навигации назад
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Реализация поиска
        val cancelBtn = findViewById<ImageView>(R.id.cancel_button)
        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.requestFocus() // Запрос фокуса на строку поиска
        searchBar.hint = getString(R.string.search) // Установка подсказки для строки поиска
        searchBar.setText(searchValue) // Установка текущего значения поиска в строку поиска
        cancelBtn.setOnClickListener {
            searchBar.text.clear()
            tracks.clear()
            searchAdapter.notifyDataSetChanged()
            showMessage("", "", ResultResponse.HISTORY)
        }
        searchBar.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && searchBar.text.isEmpty()) {
                showMessage("", "", ResultResponse.HISTORY)
            } else {
                showMessage("", "", ResultResponse.SUCCESS)
            }
        }
        val clearHistoryBtn = findViewById<Button>(R.id.clear_history)
        clearHistoryBtn.setOnClickListener {
            searchHistory.clearHistory()
            historyLayout.visibility = GONE
            historyAdapter.notifyDataSetChanged()
            historyAdapter.items.clear()
        }

        // TextWatcher отслеживает изменения в EditText
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(s, cancelBtn)
                searchValue = s.toString()
                if (searchBar.hasFocus() && s?.isEmpty() == true) {
                    showMessage("", "", ResultResponse.HISTORY)
                } else {
                    searchDebounce()
                    showMessage("", "", ResultResponse.SUCCESS)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        searchBar.addTextChangedListener(simpleTextWatcher)
        searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            } else {
                false
            }
        }

        val onHistoryItemClickListener = OnItemClickListener { item ->
            startAudioPlayerActivity(item)

        }
        historyAdapter = TrackAdapter(onHistoryItemClickListener)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter

        searchHistory = provideSearchHistoryRepository()
        historyAdapter.items = searchHistory.updateTracks().toMutableList() as ArrayList<Track>
        historyRecyclerView.adapter = historyAdapter


        val onItemClickListener = OnItemClickListener { item ->

            // Запуск AudioPlayer с передачей данных трека
            startAudioPlayerActivity(item)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        searchAdapter = TrackAdapter(onItemClickListener)
        searchAdapter.items = tracks as ArrayList<Track>
        recyclerView.adapter = searchAdapter

        updateButton.setOnClickListener {
            search()
        }
        showMessage("", "", ResultResponse.HISTORY)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(searchRunnable)
    }

    private fun updateSearchHistoryAdapter() {
        historyAdapter.items.clear()
        historyAdapter.items.addAll(searchHistory.updateTracks())
        historyAdapter.notifyDataSetChanged()
    }

    // Запуск аудиоплеера
    private fun Context.startAudioPlayerActivity(trackItem: Track) {
        if (clickDebounce()) {

            val intent = Intent(this, AudioPlayer::class.java)
            intent.putExtra(INTENT_TRACK_KEY, trackItem)
            startActivity(intent)
            searchHistory.addTrack(trackItem)
            updateSearchHistoryAdapter()
        }
    }



    // Сохраняем текущее значение поиска в Bundle outState
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchValue)
    }

    // Восстанавливаем значение поиска из Bundle savedInstanceState
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchValue = savedInstanceState.getString(SEARCH_TEXT, TEXT_DEF)
        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.setText(searchValue)
    }

    // Метод для обновления видимости кнопки очистки и установки соответствующих иконок
    private fun clearButtonVisibility(s: CharSequence?, v: ImageView) {
        if (s.isNullOrEmpty()) {
            v.visibility = GONE
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(v.windowToken, 0)
        } else {
            v.visibility = VISIBLE
        }
    }

    private fun search() {
        progressBar.visibility = VISIBLE
        Creator.provideTrackInteractor()
            .search(searchValue, object : TrackInteractor.TrackConsumer {
                override fun consume(foundTracks: List<Track>) {
                    runOnUiThread {
                        progressBar.visibility = GONE

                        if (foundTracks.isNotEmpty()) {
                            tracks.clear()
                            tracks.addAll(foundTracks)
                            searchAdapter.notifyDataSetChanged()
                            showMessage("", "", ResultResponse.SUCCESS)
                        } else {
                            showMessage(getString(R.string.nothing), "", ResultResponse.EMPTY)
                        }

                    }
                }


                override fun onFailure(t: Throwable) {
                    showMessage(
                        getString(R.string.connect_error),
                        t.message.toString(),
                        ResultResponse.ERROR
                    )
                }

            })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showMessage(text: String, additionalMessage: String, errorType: ResultResponse) {
        progressBar.visibility = GONE
        when (errorType) {
            ResultResponse.SUCCESS -> {
                recyclerView.visibility = VISIBLE
                placeholderLayout.visibility = GONE
                historyLayout.visibility = GONE
            }

            ResultResponse.EMPTY -> {
                recyclerView.visibility = GONE
                placeholderLayout.visibility = VISIBLE
                placeholderMessage.visibility = VISIBLE
                placeholderImage.visibility = VISIBLE
                updateButton.visibility = GONE
                tracks.clear()
                historyAdapter.notifyDataSetChanged()
                placeholderMessage.text = text
                placeholderImage.setImageResource(R.drawable.nothing)
                if (additionalMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG).show()
                }
            }

            ResultResponse.HISTORY -> {
                recyclerView.visibility = GONE
                placeholderLayout.visibility = GONE
                if (historyAdapter.items.isNotEmpty())
                    historyLayout.visibility = VISIBLE
                else
                    historyLayout.visibility = GONE
            }

            ResultResponse.ERROR -> {
                recyclerView.visibility = GONE
                placeholderLayout.visibility = VISIBLE
                placeholderMessage.visibility = VISIBLE
                placeholderImage.visibility = VISIBLE
                updateButton.visibility = VISIBLE
                tracks.clear()
                historyAdapter.notifyDataSetChanged()
                placeholderMessage.text = text
                placeholderImage.setImageResource(R.drawable.connect_error)
                if (additionalMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}
