package earth.mp3.models

import android.media.AudioAttributes
import android.media.MediaPlayer

object MediaPlayerManager {
    private const val ROOT_PATH: String = "/sdcard"//Environment.getExternalStorageDirectory().path
    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private val musicQueueToPlay: ArrayDeque<Music> = ArrayDeque()
    private var musicPlaying: Music? = null
    private var musicPlayingIndex: Int = -1

    /**
     * Start the first music of the music queue if it's not already playing
     *
     * @throws NoSuchElementException if the queue is empty
     */
    fun startMusic() {
        if (mediaPlayer!!.isPlaying) {
            return
        }
        musicPlaying = getNextMusic()
        val path = "$ROOT_PATH/${musicPlaying!!.relativePath}${musicPlaying!!.name}"
        mediaPlayer!!.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(path)
            prepare()
            start()
        }
    }

    /**
     * Get the music from queue, increment the music play index and return the music from the queue
     *
     * @return the first music of the array deque
     * @throws NoSuchElementException if the queue is empty
     */
    private fun getNextMusic(): Music {
        musicPlayingIndex++
        return musicQueueToPlay[musicPlayingIndex]
    }

    /**
     * Play music if it is paused otherwise start music
     */
    fun playPause() {
        if (isPlaying()) {
            mediaPlayer!!.pause()
        } else {
            mediaPlayer!!.start()
        }
    }

    /**
     * Stop the music
     */
    fun stop(reset: Boolean = true) {
        if (reset) {
            musicPlayingIndex = -1
        }
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        mediaPlayer = MediaPlayer()
    }

    /**
     * Play the next music in queue
     */
    fun next() {
        if (musicQueueToPlay.isNotEmpty()) {
            if (mediaPlayer!!.isPlaying) {
                stop(reset = false)
            }
            startMusic()
        }
    }

    fun isPlaying(): Boolean {
        return mediaPlayer!!.isPlaying
    }

    /**
     * Add all music from musicMap to the queue in the same order
     */
    fun loadMusic(musicMap: Map<Long, Music>) {
        for (music in musicMap.values) {
            if (!musicQueueToPlay.contains(music)) {
                musicQueueToPlay.add(music)
            }
        }
    }

    fun hasNext(): Boolean {
        return musicQueueToPlay.isNotEmpty()
    }

    /**
     * Play the previous music in deque
     */
    fun previous() {
        if (musicQueueToPlay.isNotEmpty() && musicPlayingIndex > -1) {

        }
    }

    fun hasPrevious(): Boolean {
        return musicQueueToPlay.isNotEmpty() && musicPlaying != musicQueueToPlay[0]
    }
}