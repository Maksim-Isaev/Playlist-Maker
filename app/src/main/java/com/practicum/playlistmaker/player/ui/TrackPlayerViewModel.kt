package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.player.domain.api.TrackPlayerInteractor
import com.practicum.playlistmaker.player.domain.model.PlayingState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrackPlayerViewModel(
    private val trackPlayerInteractor: TrackPlayerInteractor,
) : ViewModel() {

    private val playingState = MutableLiveData<PlayingState>(PlayingState.Default)
    private val positionState = MutableLiveData(0)
    fun observePlayingState(): LiveData<PlayingState> = playingState
    fun observePositionState(): LiveData<Int> = positionState

    companion object {
        private const val TIMER_UPDATE_DELAY = 250L
    }

    private var timerJob: Job? = null

    init {
        onPrepare()
    }

    private fun onPrepare() {
        try {
            trackPlayerInteractor.prepare()
            playingState.postValue(PlayingState.Prepared)
            positionState.postValue(0)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            playingState.postValue(PlayingState.Default)
        }
    }

    private fun onPlay() {
        trackPlayerInteractor.start()
        playingState.postValue(PlayingState.Playing)
        startTimer()
    }

    private fun onPause() {
        trackPlayerInteractor.pause()
        playingState.postValue(PlayingState.Paused)
        pauseTimer()
    }

    fun stateControl() {
        playingState.postValue(trackPlayerInteractor.state)
    }

    fun playingControl() {
        if (playingState.value is PlayingState.Playing) onPause()
        else onPlay()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (playingState.value is PlayingState.Playing) {
                positionState.postValue(trackPlayerInteractor.getCurrentPosition())
                delay(TIMER_UPDATE_DELAY)
            }
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
    }

    fun release() {
        pauseTimer()
        trackPlayerInteractor.release()
    }

    override fun onCleared() {
        super.onCleared()
        release()
    }
}
