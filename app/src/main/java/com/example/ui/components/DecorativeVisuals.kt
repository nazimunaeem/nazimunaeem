package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Commit
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.BadgeStyle
import com.example.model.BannerStyle
import com.example.model.DividerStyle
import com.example.model.ProfileReadmeConfig
import com.example.model.ReadmeThemePalette
import com.example.model.TechBadgeItem
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.parseHexColor
import kotlinx.coroutines.delay

@Composable
fun ProfileHeroBannerCard(
    config: ProfileReadmeConfig,
    modifier: Modifier = Modifier
) {
    val palette = config.themePalette
    val bg = parseHexColor(palette.bgHex)
    val surface = parseHexColor(palette.surfaceHex)
    val primary = parseHexColor(palette.primaryHex)
    val secondary = parseHexColor(palette.secondaryHex)
    val tertiary = parseHexColor(palette.tertiaryHex)
    val textColor = parseHexColor(palette.textHex, Color.White)

    val cornerShape = when (config.bannerStyle) {
        BannerStyle.MINIMAL_CYBER -> RoundedCornerShape(28.dp)
        BannerStyle.TYPING_TERMINAL -> RoundedCornerShape(14.dp)
        BannerStyle.AURORA_WAVE -> RoundedCornerShape(22.dp)
        BannerStyle.CAPSULE_GRADIENT -> RoundedCornerShape(18.dp)
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = cornerShape,
        color = bg,
        border = BorderStroke(1.dp, primary.copy(alpha = 0.35f)),
        tonalElevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Background art layer based on BannerStyle
            when (config.bannerStyle) {
                BannerStyle.AURORA_WAVE -> {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_aurora_banner_1795413089755),
                        contentDescription = "Aurora decorative header",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                            .alpha(0.55f)
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        bg.copy(alpha = 0.35f),
                                        bg.copy(alpha = 0.92f)
                                    )
                                )
                            )
                    )
                }
                BannerStyle.TYPING_TERMINAL -> {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val step = 28.dp.toPx()
                        var x = 0f
                        while (x < size.width) {
                            drawLine(
                                color = primary.copy(alpha = 0.08f),
                                start = Offset(x, 0f),
                                end = Offset(x, size.height),
                                strokeWidth = 1f
                            )
                            x += step
                        }
                        var y = 0f
                        while (y < size.height) {
                            drawLine(
                                color = primary.copy(alpha = 0.08f),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 1f
                            )
                            y += step
                        }
                    }
                }
                BannerStyle.CAPSULE_GRADIENT -> {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width, size.height * 0.56f)
                            lineTo(size.width * 0.5f, size.height * 0.72f)
                            lineTo(0f, size.height * 0.48f)
                            close()
                        }
                        drawPath(
                            path = path,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    primary.copy(alpha = 0.42f),
                                    secondary.copy(alpha = 0.36f)
                                )
                            )
                        )
                    }
                }
                BannerStyle.MINIMAL_CYBER -> {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        primary.copy(alpha = 0.24f),
                                        surface.copy(alpha = 0.95f),
                                        bg
                                    )
                                )
                            )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (config.bannerStyle == BannerStyle.TYPING_TERMINAL) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFF5F56)))
                        Box(Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFFBD2E)))
                        Box(Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF27C93F)))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "~/github/${config.githubUsername.ifBlank { "nazimunaeem" }}/README.md",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Avatar with glowing gradient ring
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(primary, secondary, tertiary))
                        )
                        .padding(3.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_avatar_nazim_1795413089349),
                        contentDescription = "Developer profile avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Hi there 👋, I'm ${config.displayName.ifBlank { config.githubUsername }}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = config.roleHeadline.ifBlank { "Software Developer & Open Source Builder" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.82f),
                    textAlign = TextAlign.Center
                )

                if (config.widgets.showTypingBanner && config.typingPhrases.any { it.isNotBlank() }) {
                    Spacer(modifier = Modifier.height(12.dp))
                    AnimatedTypingSubBanner(
                        phrases = config.typingPhrases.filter { it.isNotBlank() },
                        accentColor = primary,
                        surfaceColor = surface.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedTypingSubBanner(
    phrases: List<String>,
    accentColor: Color,
    surfaceColor: Color,
    modifier: Modifier = Modifier
) {
    if (phrases.isEmpty()) return
    var phraseIdx by remember(phrases) { mutableIntStateOf(0) }
    var charCount by remember(phrases) { mutableIntStateOf(0) }

    val currentPhrase = phrases[phraseIdx % phrases.size]

    LaunchedEffect(phrases, phraseIdx) {
        charCount = 0
        for (i in 1..currentPhrase.length) {
            charCount = i
            delay(38L)
        }
        delay(1700L)
        phraseIdx = (phraseIdx + 1) % phrases.size
    }

    val infiniteTransition = rememberInfiniteTransition(label = "cursor_blink")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(520),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = surfaceColor,
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Terminal,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = currentPhrase.take(charCount),
                style = MaterialTheme.typography.labelMedium,
                color = accentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .width(2.dp)
                    .height(14.dp)
                    .alpha(cursorAlpha)
                    .background(accentColor)
            )
        }
    }
}

@Composable
fun DecorativeDividerView(
    dividerStyle: DividerStyle,
    palette: ReadmeThemePalette,
    modifier: Modifier = Modifier
) {
    val primary = parseHexColor(palette.primaryHex)
    val secondary = parseHexColor(palette.secondaryHex)
    val tertiary = parseHexColor(palette.tertiaryHex)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        when (dividerStyle) {
            DividerStyle.RAINBOW_WAVE -> {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                ) {
                    val path = Path().apply {
                        val midY = size.height / 2f
                        moveTo(0f, midY)
                        val waveLen = size.width / 6f
                        var x = 0f
                        var up = true
                        while (x < size.width) {
                            val nextX = (x + waveLen).coerceAtMost(size.width)
                            val ctrlX = (x + nextX) / 2f
                            val ctrlY = if (up) 2.dp.toPx() else size.height - 2.dp.toPx()
                            quadraticTo(ctrlX, ctrlY, nextX, midY)
                            x = nextX
                            up = !up
                        }
                    }
                    drawPath(
                        path = path,
                        brush = Brush.horizontalGradient(listOf(primary, secondary, tertiary)),
                        style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
            DividerStyle.CYAN_PULSE -> {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                ) {
                    val y = size.height / 2f
                    drawLine(
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, primary, secondary, primary, Color.Transparent)
                        ),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 2.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawCircle(
                        color = tertiary,
                        radius = 4.dp.toPx(),
                        center = Offset(size.width / 2f, y)
                    )
                }
            }
            DividerStyle.PIXEL_STARS -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .height(1.5.dp)
                            .background(
                                Brush.horizontalGradient(listOf(Color.Transparent, primary.copy(alpha = 0.7f)))
                            )
                    )
                    Text(
                        text = "  ✦  ✧  ✦  ",
                        style = MaterialTheme.typography.labelMedium,
                        color = secondary
                    )
                    Box(
                        Modifier
                            .weight(1f)
                            .height(1.5.dp)
                            .background(
                                Brush.horizontalGradient(listOf(primary.copy(alpha = 0.7f), Color.Transparent))
                            )
                    )
                }
            }
            DividerStyle.CODE_BRACKETS -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(primary.copy(alpha = 0.45f))
                    )
                    Text(
                        text = "  </>  ",
                        style = MaterialTheme.typography.labelMedium.copy(fontFamily = JetBrainsMonoFontFamily),
                        color = tertiary
                    )
                    Box(
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(secondary.copy(alpha = 0.45f))
                    )
                }
            }
            DividerStyle.MINIMAL_DOTS -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) { idx ->
                        val dotColor = if (idx == 2) primary else secondary.copy(alpha = 0.45f)
                        Box(
                            modifier = Modifier
                                .size(if (idx == 2) 7.dp else 5.dp)
                                .clip(CircleShape)
                                .background(dotColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShieldsBadgePill(
    label: String,
    value: String? = null,
    accentHex: String,
    badgeStyle: BadgeStyle,
    modifier: Modifier = Modifier
) {
    val accent = parseHexColor(accentHex)
    val shape = when (badgeStyle) {
        BadgeStyle.FOR_THE_BADGE -> RoundedCornerShape(4.dp)
        BadgeStyle.FLAT_SQUARE -> RoundedCornerShape(2.dp)
        BadgeStyle.PLASTIC -> RoundedCornerShape(8.dp)
        BadgeStyle.SOCIAL -> RoundedCornerShape(50)
    }

    Surface(
        modifier = modifier,
        shape = shape,
        color = accent,
        border = if (badgeStyle == BadgeStyle.SOCIAL) BorderStroke(1.dp, accent.copy(alpha = 0.6f)) else null
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (value != null) {
                Text(
                    text = if (badgeStyle == BadgeStyle.FOR_THE_BADGE) label.uppercase() else label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = if (badgeStyle == BadgeStyle.FOR_THE_BADGE) 10.sp else 11.sp
                    ),
                    color = Color.White,
                    modifier = Modifier
                        .background(Color(0xFF334155))
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                )
            }
            Text(
                text = valText(value ?: label, badgeStyle),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = if (badgeStyle == BadgeStyle.FOR_THE_BADGE) 10.sp else 11.sp,
                    letterSpacing = if (badgeStyle == BadgeStyle.FOR_THE_BADGE) 0.8.sp else 0.2.sp
                ),
                color = contrastTextFor(accent),
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
            )
        }
    }
}

private fun valText(text: String, style: BadgeStyle): String =
    if (style == BadgeStyle.FOR_THE_BADGE) text.uppercase() else text

private fun contrastTextFor(color: Color): Color {
    val luminance = (0.299f * color.red + 0.587f * color.green + 0.114f * color.blue)
    return if (luminance > 0.65f) Color(0xFF0F172A) else Color.White
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TechBadgeWrap(
    badges: List<TechBadgeItem>,
    badgeStyle: BadgeStyle
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        badges.forEach { badge ->
            ShieldsBadgePill(
                label = badge.name,
                accentHex = badge.badgeColorHex,
                badgeStyle = badgeStyle
            )
        }
    }
}

@Composable
fun GitHubStatsPreviewCard(
    username: String,
    palette: ReadmeThemePalette,
    modifier: Modifier = Modifier
) {
    val bg = parseHexColor(palette.bgHex)
    val primary = parseHexColor(palette.primaryHex)
    val secondary = parseHexColor(palette.secondaryHex)
    val tertiary = parseHexColor(palette.tertiaryHex)
    val textColor = parseHexColor(palette.textHex, Color.White)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = bg,
        border = BorderStroke(1.dp, primary.copy(alpha = 0.28f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "${username.ifBlank { "nazimunaeem" }}'s GitHub Stats",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = primary
                )
                StatLine(icon = Icons.Rounded.Star, label = "Total Stars Earned", value = "Active", tint = secondary, textColor = textColor)
                StatLine(icon = Icons.Rounded.Commit, label = "Recent Commits", value = "Live Sync", tint = tertiary, textColor = textColor)
                StatLine(icon = Icons.Rounded.AutoAwesome, label = "Theme Slug", value = palette.statsSlug, tint = primary, textColor = textColor)
            }

            Spacer(Modifier.width(12.dp))

            // Grade ring preview
            Box(
                modifier = Modifier.size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawArc(
                        color = primary.copy(alpha = 0.18f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        brush = Brush.sweepGradient(listOf(primary, secondary, tertiary, primary)),
                        startAngle = -90f,
                        sweepAngle = 290f,
                        useCenter = false,
                        style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(
                    text = "A+",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun StatLine(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    tint: Color,
    textColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(6.dp))
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodySmall,
            color = textColor.copy(alpha = 0.78f),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = textColor
        )
    }
}

@Composable
fun TopLanguagesPreviewCard(
    palette: ReadmeThemePalette,
    selectedTechCount: Int,
    modifier: Modifier = Modifier
) {
    val bg = parseHexColor(palette.bgHex)
    val primary = parseHexColor(palette.primaryHex)
    val secondary = parseHexColor(palette.secondaryHex)
    val tertiary = parseHexColor(palette.tertiaryHex)
    val textColor = parseHexColor(palette.textHex, Color.White)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = bg,
        border = BorderStroke(1.dp, secondary.copy(alpha = 0.28f))
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Most Used Languages",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = primary
                )
                Text(
                    text = "$selectedTechCount badges",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor.copy(alpha = 0.65f)
                )
            }

            // Segmented language bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
            ) {
                Box(Modifier.weight(0.45f).fillMaxSize().background(primary))
                Box(Modifier.weight(0.32f).fillMaxSize().background(secondary))
                Box(Modifier.weight(0.23f).fillMaxSize().background(tertiary))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LangLegendDot("Kotlin / Mobile", primary, textColor)
                LangLegendDot("TypeScript / Web", secondary, textColor)
                LangLegendDot("Python / Tools", tertiary, textColor)
            }
        }
    }
}

@Composable
private fun LangLegendDot(name: String, color: Color, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(5.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = textColor.copy(alpha = 0.85f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun StreakAndQuotePreviewCards(
    username: String,
    palette: ReadmeThemePalette,
    showStreak: Boolean,
    showTrophies: Boolean,
    showQuote: Boolean
) {
    val bg = parseHexColor(palette.bgHex)
    val primary = parseHexColor(palette.primaryHex)
    val secondary = parseHexColor(palette.secondaryHex)
    val tertiary = parseHexColor(palette.tertiaryHex)
    val textColor = parseHexColor(palette.textHex, Color.White)

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (showTrophies) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = bg,
                border = BorderStroke(1.dp, tertiary.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("Commits" to primary, "Stars" to secondary, "Streak" to tertiary, "OSS" to primary).forEach { (title, color) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Rounded.EmojiEvents,
                                contentDescription = title,
                                tint = color,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelSmall,
                                color = textColor.copy(alpha = 0.85f)
                            )
                        }
                    }
                }
            }
        }

        if (showStreak) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = bg,
                border = BorderStroke(1.dp, primary.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Streak Widget", style = MaterialTheme.typography.labelSmall, color = textColor.copy(alpha = 0.7f))
                        Text("@${username.ifBlank { "nazimunaeem" }}", style = MaterialTheme.typography.labelMedium, color = primary)
                    }
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .border(2.dp, secondary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = secondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Theme", style = MaterialTheme.typography.labelSmall, color = textColor.copy(alpha = 0.7f))
                        Text(palette.displayName, style = MaterialTheme.typography.labelMedium, color = tertiary)
                    }
                }
            }
        }

        if (showQuote) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = bg,
                border = BorderStroke(1.dp, secondary.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FormatQuote,
                        contentDescription = null,
                        tint = secondary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "\"First, solve the problem. Then, write the code.\" — John Johnson",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}
