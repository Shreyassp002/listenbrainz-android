package org.listenbrainz.android.ui.screens.brainzplayer.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.listenbrainz.android.R
import org.listenbrainz.android.model.Album
import org.listenbrainz.android.ui.components.ListenCardSmall
import org.listenbrainz.android.ui.theme.ListenBrainzTheme

@Composable
fun AlbumsOverViewScreen(
    albums : List<Album>,
    onPlayIconClick: (Album) -> Unit
) {
    val albumsStarting: MutableMap<Char, MutableList<Album>> = mutableMapOf()
    for (i in 0..25) {
        albumsStarting['A' + i] = mutableListOf()
    }

    for (i in 1..albums.size) {
        albumsStarting[albums[i - 1].title[0]]?.add(albums[i-1])
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        for (i in 0..25) {
            val startingLetter: Char = ('A' + i)
            if (albumsStarting[startingLetter]!!.size > 0) {
                Column(modifier = Modifier
                    .background(
                        brush = ListenBrainzTheme.colorScheme.gradientBrush
                    )
                    .padding(top = 15.dp, bottom = 15.dp)) {
                    Text(
                        startingLetter.toString(),
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 5.dp),
                        style = TextStyle(
                            color = ListenBrainzTheme.colorScheme.lbSignature,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        )
                    )
                    for (j in 1..albumsStarting[startingLetter]!!.size) {
                        val coverArt = albumsStarting[startingLetter]!![j - 1].albumArt
                        ListenCardSmall(
                            trackName = albumsStarting[startingLetter]!![j - 1].title,
                            artist = albumsStarting[startingLetter]!![j - 1].artist,
                            coverArtUrl = coverArt,
                            errorAlbumArt = R.drawable.ic_erroralbumart,
                            modifier = Modifier.padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 3.dp,
                                bottom = 3.dp
                            ),
                            goToArtistPage = { onPlayIconClick(albumsStarting[startingLetter]!![j-1]) },
                            onClick = { onPlayIconClick(albumsStarting[startingLetter]!![j-1]) }
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}