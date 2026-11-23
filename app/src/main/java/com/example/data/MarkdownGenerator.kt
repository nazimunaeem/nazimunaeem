package com.example.data

import com.example.model.DividerStyle
import com.example.model.ProfileReadmeConfig
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object MarkdownGenerator {

    fun generate(config: ProfileReadmeConfig): String {
        val username = config.githubUsername.trim().ifEmpty { "nazimunaeem" }
        val displayName = config.displayName.trim().ifEmpty { username }
        val palette = config.themePalette
        val styleParam = config.badgeStyle.shieldsParam

        val sb = StringBuilder()

        // 1. Decorative Header Banner (Capsule Render)
        val encodedTitle = encodeUrl("Hi there, I'm $displayName")
        val encodedDesc = encodeUrl(config.roleHeadline.trim().ifEmpty { "Developer & Builder" })
        sb.appendLine("<div align=\"center\">")
        sb.appendLine(
            "  <img src=\"https://capsule-render.vercel.app/api?type=${config.bannerStyle.capsuleType}" +
                "&color=0:${palette.bgHex},50:${palette.primaryHex},100:${palette.secondaryHex}" +
                "&height=210&section=header&text=$encodedTitle&fontSize=38&fontColor=${palette.textHex}" +
                "&animation=fadeIn&fontAlignY=38&desc=$encodedDesc&descAlignY=60&descSize=16\" width=\"100%\" alt=\"Header Banner\" />"
        )
        sb.appendLine("</div>")
        sb.appendLine()

        // 2. Typing SVG Sub-Banner
        if (config.widgets.showTypingBanner && config.typingPhrases.any { it.isNotBlank() }) {
            val linesParam = config.typingPhrases
                .filter { it.isNotBlank() }
                .joinToString(";") { encodeUrl(it.trim()) }
            sb.appendLine("<p align=\"center\">")
            sb.appendLine(
                "  <img src=\"https://readme-typing-svg.demolab.com?font=JetBrains+Mono&weight=600&size=20" +
                    "&duration=3200&pause=1000&color=${palette.primaryHex}&center=true&vCenter=true" +
                    "&width=620&lines=$linesParam\" alt=\"Typing SVG\" />"
            )
            sb.appendLine("</p>")
            sb.appendLine()
        }

        // 3. Profile Views & Social Badges Row
        val hasSocials = config.widgets.showProfileViews ||
            config.email.isNotBlank() ||
            config.linkedinHandle.isNotBlank() ||
            config.xHandle.isNotBlank() ||
            config.websiteUrl.isNotBlank()

        if (hasSocials) {
            sb.appendLine("<p align=\"center\">")
            if (config.widgets.showProfileViews) {
                sb.appendLine(
                    "  <img src=\"https://komarev.com/ghpvc/?username=${encodeUrl(username)}" +
                        "&label=PROFILE+VIEWS&color=${palette.primaryHex}&style=$styleParam\" alt=\"Profile Views\" />"
                )
            }
            if (config.location.isNotBlank()) {
                sb.appendLine(
                    "  <img src=\"https://img.shields.io/badge/${encodeBadgeSegment(config.location)}-${palette.surfaceHex}" +
                        "?style=$styleParam&logo=googlemaps&logoColor=${palette.tertiaryHex}\" alt=\"Location\" />"
                )
            }
            if (config.email.isNotBlank()) {
                sb.appendLine(
                    "  <a href=\"mailto:${config.email.trim()}\">" +
                        "<img src=\"https://img.shields.io/badge/Email-${palette.primaryHex}?style=$styleParam&logo=gmail&logoColor=FFFFFF\" alt=\"Email\" /></a>"
                )
            }
            if (config.linkedinHandle.isNotBlank()) {
                sb.appendLine(
                    "  <a href=\"https://linkedin.com/in/${config.linkedinHandle.trim()}\">" +
                        "<img src=\"https://img.shields.io/badge/LinkedIn-0A66C2?style=$styleParam&logo=linkedin&logoColor=FFFFFF\" alt=\"LinkedIn\" /></a>"
                )
            }
            if (config.xHandle.isNotBlank()) {
                sb.appendLine(
                    "  <a href=\"https://x.com/${config.xHandle.trim()}\">" +
                        "<img src=\"https://img.shields.io/badge/X-000000?style=$styleParam&logo=x&logoColor=FFFFFF\" alt=\"X\" /></a>"
                )
            }
            sb.appendLine("</p>")
            sb.appendLine()
        }

        appendDivider(sb, config.dividerStyle, palette.primaryHex, palette.secondaryHex)

        // 4. About Me Section (from nazimunaeem/nazimunaeem profile prompts)
        val enabledAbout = config.aboutItems.filter { it.enabled && it.value.isNotBlank() }
        if (enabledAbout.isNotEmpty()) {
            sb.appendLine("### ✨ About Me")
            sb.appendLine()
            enabledAbout.forEach { item ->
                sb.appendLine("- ${item.emoji} **${item.label}:** ${item.value.trim()}")
            }
            sb.appendLine()
            appendDivider(sb, config.dividerStyle, palette.primaryHex, palette.secondaryHex)
        }

        // 5. Tech Stack & Tools Badges
        val selectedTech = ReadmePresetsAndCatalog.techCatalog.filter { it.id in config.selectedTechIds }
        if (selectedTech.isNotEmpty()) {
            sb.appendLine("### 🛠️ Tech Stack & Tools")
            sb.appendLine()
            val grouped = selectedTech.groupBy { it.category }
            grouped.forEach { (category, items) ->
                sb.appendLine("**$category**")
                sb.appendLine("<p>")
                items.forEach { badge ->
                    val label = encodeBadgeSegment(badge.name)
                    sb.appendLine(
                        "  <img src=\"https://img.shields.io/badge/$label-${badge.badgeColorHex}" +
                            "?style=$styleParam&logo=${badge.logoSlug}&logoColor=${badge.logoColorHex}\" alt=\"${badge.name}\" />"
                    )
                }
                sb.appendLine("</p>")
                sb.appendLine()
            }
            appendDivider(sb, config.dividerStyle, palette.primaryHex, palette.secondaryHex)
        }

        // 6. GitHub Trophies
        if (config.widgets.showTrophies) {
            sb.appendLine("### 🏆 GitHub Trophies")
            sb.appendLine()
            sb.appendLine("<p align=\"center\">")
            sb.appendLine(
                "  <img src=\"https://github-profile-trophy.vercel.app/?username=${encodeUrl(username)}" +
                    "&theme=${palette.statsSlug}&no-frame=true&no-bg=true&margin-w=6&row=1&column=6\" alt=\"GitHub Trophies\" />"
            )
            sb.appendLine("</p>")
            sb.appendLine()
        }

        // 7. GitHub Analytics & Stats Cards
        val hasStats = config.widgets.showGitHubStats ||
            config.widgets.showTopLangs ||
            config.widgets.showStreakStats

        if (hasStats) {
            sb.appendLine("### 📊 GitHub Analytics")
            sb.appendLine()
            sb.appendLine("<div align=\"center\">")
            if (config.widgets.showGitHubStats) {
                sb.appendLine(
                    "  <img src=\"https://github-readme-stats.vercel.app/api?username=${encodeUrl(username)}" +
                        "&show_icons=true&theme=${palette.statsSlug}&hide_border=true&bg_color=${palette.bgHex}\" height=\"165\" alt=\"GitHub Stats\" />"
                )
            }
            if (config.widgets.showTopLangs) {
                sb.appendLine(
                    "  <img src=\"https://github-readme-stats.vercel.app/api/top-langs/?username=${encodeUrl(username)}" +
                        "&layout=compact&theme=${palette.statsSlug}&hide_border=true&bg_color=${palette.bgHex}\" height=\"165\" alt=\"Top Languages\" />"
                )
            }
            if (config.widgets.showStreakStats) {
                sb.appendLine("  <br/>")
                sb.appendLine(
                    "  <img src=\"https://streak-stats.demolab.com/?user=${encodeUrl(username)}" +
                        "&theme=${palette.statsSlug}&hide_border=true&background=${palette.bgHex}\" alt=\"GitHub Streak\" />"
                )
            }
            sb.appendLine("</div>")
            sb.appendLine()
        }

        // 8. Decorative Dev Quote Card
        if (config.widgets.showQuoteCard) {
            sb.appendLine("<p align=\"center\">")
            sb.appendLine(
                "  <img src=\"https://quotes-github-readme.vercel.app/api?type=horizontal&theme=${palette.statsSlug}\" alt=\"Developer Quote\" />"
            )
            sb.appendLine("</p>")
            sb.appendLine()
        }

        // 9. Footer Wave
        if (config.widgets.showFooterWave) {
            sb.appendLine("<div align=\"center\">")
            sb.appendLine(
                "  <img src=\"https://capsule-render.vercel.app/api?type=waving" +
                    "&color=0:${palette.secondaryHex},100:${palette.primaryHex}" +
                    "&height=100&section=footer\" width=\"100%\" alt=\"Footer Wave\" />"
            )
            sb.appendLine("</div>")
        }

        return sb.toString().trimEnd() + "\n"
    }

    private fun appendDivider(
        sb: StringBuilder,
        dividerStyle: DividerStyle,
        primaryHex: String,
        secondaryHex: String
    ) {
        when (dividerStyle) {
            DividerStyle.RAINBOW_WAVE -> {
                sb.appendLine(
                    "<img src=\"https://capsule-render.vercel.app/api?type=rect&color=0:$primaryHex,100:$secondaryHex&height=3\" width=\"100%\" alt=\"divider\" />"
                )
            }
            DividerStyle.CYAN_PULSE -> {
                sb.appendLine(
                    "<p align=\"center\"><img src=\"https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif\" width=\"100%\" alt=\"divider\" /></p>"
                )
            }
            DividerStyle.PIXEL_STARS -> {
                sb.appendLine("<p align=\"center\">✦ ━━━━━━━━━━━━━━━ ✧ ━━━━━━━━━━━━━━━ ✦</p>")
            }
            DividerStyle.CODE_BRACKETS -> {
                sb.appendLine("<p align=\"center\"><code>&lt;/&gt; ─────────────────────────── &lt;/&gt;</code></p>")
            }
            DividerStyle.MINIMAL_DOTS -> {
                sb.appendLine("---")
            }
        }
        sb.appendLine()
    }

    private fun encodeUrl(value: String): String =
        URLEncoder.encode(value, StandardCharsets.UTF_8.name()).replace("+", "%20")

    private fun encodeBadgeSegment(value: String): String =
        value.trim()
            .replace("-", "--")
            .replace("_", "__")
            .replace(" ", "%20")
}
