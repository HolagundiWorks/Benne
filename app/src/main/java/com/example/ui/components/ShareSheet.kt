package com.example.ui.components

import com.example.ui.theme.Space
import com.example.ui.theme.TypeScale
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.data.Award
import com.example.data.LevelProgress
import com.example.ui.theme.BenneInk
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/** The templates a learner can share from Profile. */
enum class ShareTemplate(val label: String) {
    PROGRESS("My progress"),
    APP("About Benne"),
    AWARD("My award")
}

/** Text sent with every share. Matches the kit's share line. */
const val SHARE_TEXT = "I've started my journey of learning Kannada with Benne. Learn Kannada offline on Android."

/**
 * Share sheet: previews a share template and sends it as a PNG, WhatsApp first, with the system
 * share chooser as the fallback. The image is captured from the card on screen, so the wording is
 * live text, not a picture made in advance.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareSheet(
    xp: Int,
    streakDays: Int,
    lessonsDone: Int,
    lessonsTotal: Int,
    earnedAwards: List<Award>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var template by remember { mutableStateOf(ShareTemplate.PROGRESS) }
    var status by remember { mutableStateOf<String?>(null) }
    var story by remember { mutableStateOf(false) }
    val layer = rememberGraphicsLayer()
    val featuredAward = earnedAwards.lastOrNull()

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Space.X20, vertical = Space.X8).testTag("share_sheet"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Space.X12)
        ) {
            Text("Share", fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T20, color = PopPosterInk)
            Row(horizontalArrangement = Arrangement.spacedBy(Space.X8)) {
                ShareTemplate.entries.forEach { option ->
                    val enabled = option != ShareTemplate.AWARD || featuredAward != null
                    FilterChip(
                        selected = template == option,
                        onClick = { if (enabled) template = option },
                        enabled = enabled,
                        label = { Text(option.label) },
                        modifier = Modifier.testTag("share_template_${option.name.lowercase()}")
                    )
                }
            }

            if (template == ShareTemplate.PROGRESS) {
                FilterChip(
                    selected = story,
                    onClick = { story = !story },
                    label = { Text("9:16 story") },
                    modifier = Modifier.testTag("share_story_toggle")
                )
            }

            // The selected card is drawn here and recorded into the layer that is shared
            Box(
                modifier = Modifier.drawWithLayer(layer),
                contentAlignment = Alignment.Center
            ) {
                when (template) {
                    ShareTemplate.PROGRESS -> ProgressShareCard(
                        lessonsDone = lessonsDone,
                        lessonsTotal = lessonsTotal,
                        streakDays = streakDays,
                        awards = earnedAwards,
                        portrait = story
                    )
                    ShareTemplate.APP -> AppShareCard()
                    ShareTemplate.AWARD -> featuredAward?.let { AwardShareCard(it) }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(Space.X10)) {
                PopActionButton(
                    text = "Share on WhatsApp",
                    onClick = { scope.launch { status = shareCard(context, layer, preferWhatsApp = true) } },
                    modifier = Modifier.testTag("share_whatsapp")
                )
                PopActionButton(
                    text = "More apps",
                    onClick = { scope.launch { status = shareCard(context, layer, preferWhatsApp = false) } },
                    containerColor = PopPosterPaper,
                    modifier = Modifier.testTag("share_more")
                )
            }
            status?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = BenneInk) }
            Spacer(modifier = Modifier.height(Space.X12))
        }
    }
}

/** Draws the content normally and records it into [layer] so it can be captured. */
private fun Modifier.drawWithLayer(layer: GraphicsLayer): Modifier = this.drawWithContent {
    layer.record { this@drawWithContent.drawContent() }
    drawLayer(layer)
}

/**
 * Captures [layer] to a PNG in the app cache and sends it through FileProvider, so no storage
 * permission is needed. Returns a short status line for the sheet.
 */
private suspend fun shareCard(context: Context, layer: GraphicsLayer, preferWhatsApp: Boolean): String {
    val bitmap: ImageBitmap = layer.toImageBitmap()
    val file = withContext(Dispatchers.IO) { saveBitmap(context, bitmap) }
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val base = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_TEXT, SHARE_TEXT)
        clipData = ClipData.newRawUri("", uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    if (preferWhatsApp) {
        try {
            context.startActivity(Intent(base).setPackage("com.whatsapp"))
            return "Opened WhatsApp."
        } catch (e: ActivityNotFoundException) {
            // WhatsApp is not installed: fall back to the chooser below
        }
    }
    context.startActivity(Intent.createChooser(base, "Share Benne"))
    return if (preferWhatsApp) "WhatsApp is not installed. Choose another app." else "Choose an app to share."
}

private fun saveBitmap(context: Context, bitmap: ImageBitmap): File {
    val dir = File(context.cacheDir, "shared").apply { mkdirs() }
    val file = File(dir, "benne_share.png")
    FileOutputStream(file).use { out ->
        bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    return file
}
