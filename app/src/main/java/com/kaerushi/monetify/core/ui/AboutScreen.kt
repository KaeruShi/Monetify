package com.kaerushi.monetify.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.R

@Composable
fun AboutScreen() {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.monetify_icon_pastel), contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .background(
                    color = colorResource(R.color.ic_launcher_background),
                    shape = CircleShape
                )
                .padding(12.dp),
            tint = null
        )
        Text(
            text = stringResource(R.string.app_name), fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(text = BuildConfig.VERSION_NAME, fontSize = 14.sp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    uriHandler.openUri("https://github.com/KaeruShi")
                }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = "https://github.com/KaeruShi.png",
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                                shape = CircleShape
                            )
                            .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("KaeruShi")
                        Text(stringResource(R.string.developer), fontSize = 14.sp)
                    }
                }
            }

            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    uriHandler.openUri("https://github.com/KaeruShi/monetify")
                }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.github),
                        contentDescription = "GitHub",
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("GitHub")
                        Text(stringResource(R.string.source_code), fontSize = 14.sp)
                    }
                }
            }
        }
        Text(stringResource(R.string.credits_subtitle), modifier = Modifier.padding(vertical = 18.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        ) {
            Text(modifier = Modifier.padding(16.dp), text = stringResource(R.string.contribution_desc))
        }
    }
}