package com.example

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.CustomizeScreen
import com.example.ui.screens.DecorativesScreen
import com.example.ui.screens.MarkdownScreen
import com.example.ui.screens.PreviewScreen
import com.example.ui.theme.ReadmeCraftTheme
import com.example.ui.viewmodel.ReadmeViewModel
import com.example.ui.viewmodel.StudioTab
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val useRail = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
            ReadmeCraftTheme {
                ReadmeCraftApp(useNavigationRail = useRail)
            }
        }
    }
}

private data class NavTabItem(
    val tab: StudioTab,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadmeCraftApp(
    useNavigationRail: Boolean,
    viewModel: ReadmeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var currentTab by rememberSaveable { mutableStateOf(StudioTab.PREVIEW) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val copiedMessage = stringResource(R.string.copied_snackbar)
    val resetMessage = stringResource(R.string.reset_snackbar)

    if (currentTab != StudioTab.PREVIEW) {
        BackHandler { currentTab = StudioTab.PREVIEW }
    }

    val navItems = remember {
        listOf(
            NavTabItem(StudioTab.PREVIEW, R.string.tab_preview, Icons.Rounded.Visibility, Icons.Outlined.Visibility),
            NavTabItem(StudioTab.CUSTOMIZE, R.string.tab_customize, Icons.Rounded.Edit, Icons.Outlined.Edit),
            NavTabItem(StudioTab.DECORATIVES, R.string.tab_decoratives, Icons.Rounded.AutoAwesome, Icons.Outlined.AutoAwesome),
            NavTabItem(StudioTab.MARKDOWN, R.string.tab_markdown, Icons.Rounded.Code, Icons.Outlined.Code)
        )
    }

    fun copyMarkdown() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("README.md", uiState.generatedMarkdown))
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(copiedMessage)
        }
    }

    fun shareMarkdown() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "${uiState.config.githubUsername} GitHub Profile README.md")
            putExtra(Intent.EXTRA_TEXT, uiState.generatedMarkdown)
        }
        context.startActivity(Intent.createChooser(intent, "Share README.md"))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (currentTab) {
                            StudioTab.PREVIEW -> "Profile Preview"
                            StudioTab.CUSTOMIZE -> "Customize Bio & Stack"
                            StudioTab.DECORATIVES -> "Decorative Studio"
                            StudioTab.MARKDOWN -> "Export README.md"
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = { copyMarkdown() },
                        modifier = Modifier.testTag("topbar_copy_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = stringResource(R.string.action_copy_markdown)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            if (!useNavigationRail) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    navItems.forEach { item ->
                        val selected = currentTab == item.tab
                        val label = stringResource(item.labelRes)
                        NavigationBarItem(
                            selected = selected,
                            onClick = { currentTab = item.tab },
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = label
                                )
                            },
                            label = { Text(label) },
                            modifier = Modifier.testTag("nav_${item.tab.route}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (useNavigationRail) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    navItems.forEach { item ->
                        val selected = currentTab == item.tab
                        val label = stringResource(item.labelRes)
                        NavigationRailItem(
                            selected = selected,
                            onClick = { currentTab = item.tab },
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = label
                                )
                            },
                            label = { Text(label) },
                            modifier = Modifier.testTag("rail_${item.tab.route}")
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                when (currentTab) {
                    StudioTab.PREVIEW -> PreviewScreen(
                        uiState = uiState,
                        onApplyPreset = viewModel::applyPreset,
                        onCopyMarkdown = ::copyMarkdown,
                        onShareMarkdown = ::shareMarkdown
                    )
                    StudioTab.CUSTOMIZE -> CustomizeScreen(
                        uiState = uiState,
                        onUpdateIdentity = viewModel::updateIdentity,
                        onUpdateTypingPhrase = viewModel::updateTypingPhrase,
                        onUpdateAboutItem = viewModel::updateAboutItem,
                        onToggleTechBadge = viewModel::toggleTechBadge,
                        onTechSearchChange = viewModel::updateTechSearchQuery
                    )
                    StudioTab.DECORATIVES -> DecorativesScreen(
                        uiState = uiState,
                        onSelectBannerStyle = viewModel::setBannerStyle,
                        onSelectThemePalette = viewModel::setThemePalette,
                        onSelectDividerStyle = viewModel::setDividerStyle,
                        onSelectBadgeStyle = viewModel::setBadgeStyle,
                        onUpdateWidgets = viewModel::updateWidgets
                    )
                    StudioTab.MARKDOWN -> MarkdownScreen(
                        uiState = uiState,
                        onCopyMarkdown = ::copyMarkdown,
                        onShareMarkdown = ::shareMarkdown,
                        onResetDefaults = {
                            viewModel.resetToDefault()
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(resetMessage)
                            }
                        }
                    )
                }
            }
        }
    }
}
