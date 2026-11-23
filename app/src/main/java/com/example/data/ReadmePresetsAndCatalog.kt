package com.example.data

import com.example.model.AboutItem
import com.example.model.BadgeStyle
import com.example.model.BannerStyle
import com.example.model.DecorativePreset
import com.example.model.DecorativeWidgetsConfig
import com.example.model.DividerStyle
import com.example.model.ProfileReadmeConfig
import com.example.model.ReadmeThemePalette
import com.example.model.TechBadgeItem

object ReadmePresetsAndCatalog {

    val techCatalog: List<TechBadgeItem> = listOf(
        // Languages
        TechBadgeItem("kotlin", "Kotlin", "Languages", "7F52FF", "kotlin"),
        TechBadgeItem("python", "Python", "Languages", "3776AB", "python"),
        TechBadgeItem("typescript", "TypeScript", "Languages", "3178C6", "typescript"),
        TechBadgeItem("javascript", "JavaScript", "Languages", "F7DF1E", "javascript", "000000"),
        TechBadgeItem("java", "Java", "Languages", "ED8B00", "openjdk"),
        TechBadgeItem("cpp", "C++", "Languages", "00599C", "cplusplus"),
        TechBadgeItem("go", "Go", "Languages", "00ADD8", "go"),
        TechBadgeItem("rust", "Rust", "Languages", "000000", "rust"),

        // Mobile & Web
        TechBadgeItem("compose", "Jetpack Compose", "Mobile & Web", "4285F4", "jetpackcompose"),
        TechBadgeItem("android", "Android", "Mobile & Web", "3DDC84", "android", "000000"),
        TechBadgeItem("react", "React", "Mobile & Web", "20232A", "react", "61DAFB"),
        TechBadgeItem("nextjs", "Next.js", "Mobile & Web", "000000", "nextdotjs"),
        TechBadgeItem("nodejs", "Node.js", "Mobile & Web", "339933", "nodedotjs"),
        TechBadgeItem("tailwind", "Tailwind CSS", "Mobile & Web", "0F172A", "tailwindcss", "38BDF8"),
        TechBadgeItem("flutter", "Flutter", "Mobile & Web", "02569B", "flutter"),

        // Cloud & Tools
        TechBadgeItem("git", "Git", "Cloud & Tools", "F05032", "git"),
        TechBadgeItem("github", "GitHub Actions", "Cloud & Tools", "2088FF", "githubactions"),
        TechBadgeItem("docker", "Docker", "Cloud & Tools", "2496ED", "docker"),
        TechBadgeItem("firebase", "Firebase", "Cloud & Tools", "FFCA28", "firebase", "000000"),
        TechBadgeItem("postgres", "PostgreSQL", "Cloud & Tools", "4169E1", "postgresql"),
        TechBadgeItem("linux", "Linux", "Cloud & Tools", "FCC624", "linux", "000000"),
        TechBadgeItem("figma", "Figma", "Cloud & Tools", "F24E1E", "figma")
    )

    val decorativePresets: List<DecorativePreset> = listOf(
        DecorativePreset(
            id = "aurora_night",
            name = "Aurora Night",
            tagline = "Wave banner + Tokyo Night + For-the-Badge",
            bannerStyle = BannerStyle.AURORA_WAVE,
            themePalette = ReadmeThemePalette.TOKYO_NIGHT,
            dividerStyle = DividerStyle.CYAN_PULSE,
            badgeStyle = BadgeStyle.FOR_THE_BADGE
        ),
        DecorativePreset(
            id = "dracula_synth",
            name = "Dracula Synth",
            tagline = "Venom ribbon + Dracula + Starlight",
            bannerStyle = BannerStyle.CAPSULE_GRADIENT,
            themePalette = ReadmeThemePalette.DRACULA,
            dividerStyle = DividerStyle.PIXEL_STARS,
            badgeStyle = BadgeStyle.FOR_THE_BADGE
        ),
        DecorativePreset(
            id = "radical_cyber",
            name = "Radical Cyber",
            tagline = "Terminal header + Radical + Code Brackets",
            bannerStyle = BannerStyle.TYPING_TERMINAL,
            themePalette = ReadmeThemePalette.RADICAL,
            dividerStyle = DividerStyle.CODE_BRACKETS,
            badgeStyle = BadgeStyle.FLAT_SQUARE
        ),
        DecorativePreset(
            id = "catppuccin_soft",
            name = "Catppuccin Soft",
            tagline = "Soft cylinder + Catppuccin + Gradient Wave",
            bannerStyle = BannerStyle.MINIMAL_CYBER,
            themePalette = ReadmeThemePalette.CATPPUCCIN_MOCHA,
            dividerStyle = DividerStyle.RAINBOW_WAVE,
            badgeStyle = BadgeStyle.PLASTIC
        ),
        DecorativePreset(
            id = "emerald_hacker",
            name = "Emerald Matrix",
            tagline = "Terminal header + Emerald + Minimal Dots",
            bannerStyle = BannerStyle.TYPING_TERMINAL,
            themePalette = ReadmeThemePalette.EMERALD_MATRIX,
            dividerStyle = DividerStyle.MINIMAL_DOTS,
            badgeStyle = BadgeStyle.FLAT_SQUARE
        )
    )

    fun defaultProfileConfig(): ProfileReadmeConfig = ProfileReadmeConfig(
        githubUsername = "nazimunaeem",
        displayName = "Nazim U. Naeem",
        roleHeadline = "Software Builder • Mobile & Full-Stack Enthusiast",
        typingPhrases = listOf(
            "Crafting clean mobile & web experiences",
            "Learning modern Kotlin, Compose & Cloud",
            "Always excited to collaborate on open source"
        ),
        location = "Remote • Worldwide",
        email = "nazim.bb.2023@gmail.com",
        websiteUrl = "https://github.com/nazimunaeem",
        linkedinHandle = "nazimunaeem",
        xHandle = "nazimunaeem",
        bannerStyle = BannerStyle.AURORA_WAVE,
        themePalette = ReadmeThemePalette.TOKYO_NIGHT,
        dividerStyle = DividerStyle.CYAN_PULSE,
        badgeStyle = BadgeStyle.FOR_THE_BADGE,
        aboutItems = listOf(
            AboutItem(
                id = "working_on",
                emoji = "🔭",
                label = "I’m currently working on",
                value = "Android apps with Jetpack Compose & full-stack side projects",
                enabled = true
            ),
            AboutItem(
                id = "learning",
                emoji = "🌱",
                label = "I’m currently learning",
                value = "Clean Architecture, TypeScript, and Cloud Native tooling",
                enabled = true
            ),
            AboutItem(
                id = "collaborate",
                emoji = "👯",
                label = "I’m looking to collaborate on",
                value = "Open-source developer tools & UI-focused mobile apps",
                enabled = true
            ),
            AboutItem(
                id = "help_with",
                emoji = "🤔",
                label = "I’m looking for help with",
                value = "Scaling distributed systems & automated CI/CD workflows",
                enabled = false
            ),
            AboutItem(
                id = "ask_me",
                emoji = "💬",
                label = "Ask me about",
                value = "Kotlin, Android UI craft, Python scripting, and Git workflows",
                enabled = true
            ),
            AboutItem(
                id = "reach_me",
                emoji = "📫",
                label = "How to reach me",
                value = "nazim.bb.2023@gmail.com",
                enabled = true
            ),
            AboutItem(
                id = "pronouns",
                emoji = "😄",
                label = "Pronouns",
                value = "He / Him",
                enabled = true
            ),
            AboutItem(
                id = "fun_fact",
                emoji = "⚡",
                label = "Fun fact",
                value = "I love turning blank READMEs into interactive visual showcases!",
                enabled = true
            )
        ),
        selectedTechIds = listOf(
            "kotlin",
            "compose",
            "android",
            "python",
            "typescript",
            "react",
            "nodejs",
            "git",
            "docker",
            "firebase"
        ),
        widgets = DecorativeWidgetsConfig(
            showTypingBanner = true,
            showProfileViews = true,
            showGitHubStats = true,
            showTopLangs = true,
            showStreakStats = true,
            showTrophies = true,
            showQuoteCard = true,
            showFooterWave = true
        )
    )
}
