package com.example.mycity.ui

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycity.R
import com.example.mycity.data.local.LocalDataProvider
import com.example.mycity.model.StoreInfo
import com.example.mycity.model.StoreboxType

enum class CityAppScreen(@StringRes val title: Int) {
    Category(title = R.string.storeCategoryScreen),
    List(title = R.string.storeListScreen),
    Info(title = R.string.storeInfoScreen)
}

@Composable
fun CityHomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: CityViewModel,
    navigationType: CityNavigationType,
    cityUiState: CityUiState,
    onTabPressed: (StoreboxType) -> Unit,
    modifier: Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CityAppScreen.valueOf(backStackEntry?.destination?.route ?: CityAppScreen.List.name)
    val navigationItemContentList = listOf(
        NavigationItemContent(
            storeboxType = StoreboxType.Cafe,
            icon = Icons.Default.Menu,
            text = "Cafe",
            category = 1
        ),
        NavigationItemContent(
            storeboxType = StoreboxType.Restaurant,
            icon = Icons.Default.Menu,
            text = "Restaurant",
            category = 2,
        ),
        NavigationItemContent(
            storeboxType = StoreboxType.Mall,
            icon = Icons.Default.Menu,
            text = "Mall",
            category = 3
        )
    )
    Scaffold(
        topBar = {
            CityAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {
        if (navigationType == CityNavigationType.PERMANENT_NAVIGATION_DRAWER || navigationType == CityNavigationType.NAVIGATION_RAIL) {
            val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet(
                        Modifier.width(dimensionResource(R.dimen.drawer_width)),
                        drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ) {
                        NavigationDrawerContent(
                            viewModel = viewModel,
                            selectedDestination = cityUiState.currentType,
                            onTabPressed = onTabPressed,
                            navigationItemContentList = navigationItemContentList,
                            modifier = Modifier
                                .wrapContentWidth()
                                .fillMaxHeight()
                                .padding(dimensionResource(R.dimen.drawer_padding_content))
                        )
                    }
                },
                modifier = Modifier.testTag(navigationDrawerContentDescription)
            ) {
                CityAppContent(
                    contentPadding = it,
                    navigationType = navigationType,
                    cityUiState = cityUiState,
                    onTabPressed = onTabPressed,
                    onStorePressed = {
                        viewModel.updateCurrentIndex(it.id)
                    },
                    navigationItemContentList = navigationItemContentList,
                    modifier = modifier
                )
            }
        } else {
            NavHost(
                navController = navController,
                startDestination = CityAppScreen.Category.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                composable(route = CityAppScreen.Category.name) {
                    CategoryList(
                        stores = LocalDataProvider.getCategoryData(),
                        onClicked = {
                            viewModel.updateCurrentCategory(it.categoryId)
                            navController.navigate(CityAppScreen.List.name)
                        }
                    )
                }

                composable(route = CityAppScreen.List.name) {
                    CategoryDetailList(
                        stores = cityUiState.currentStore,
                        onClicked = {
                            viewModel.updateCurrentIndex(it.id)
                            navController.navigate(CityAppScreen.Info.name)
                        }
                    )
                }

                composable(route = CityAppScreen.Info.name) {
                    ListItemDetail(
                        selectedStoreInfo = cityUiState.currentStore[cityUiState.currentIndex]
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityAppBar(
    currentScreen: CityAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    )
}

@Composable
private fun CityAppContent(
    navigationType: CityNavigationType,
    cityUiState: CityUiState,
    onTabPressed: (StoreboxType) -> Unit,
    onStorePressed: (StoreInfo) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    Row(modifier = modifier.fillMaxSize()) {
        val navigationRailContentDescription = stringResource(R.string.navigation_rail)
        AnimatedVisibility(visible = navigationType == CityNavigationType.NAVIGATION_RAIL) {
            CityNavigationRail(
                currentTab = cityUiState.currentType,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier
                    .testTag(navigationRailContentDescription)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(top = contentPadding.calculateTopPadding())
        ) {
            CityListAndDetailContent(
                cityUiState = cityUiState,
                onItemCardPressed = onStorePressed,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CityNavigationRail(
    currentTab: StoreboxType,
    onTabPressed: ((StoreboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.storeboxType,
                onClick = { onTabPressed(navItem.storeboxType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}

@Composable
private fun NavigationDrawerContent(
    viewModel: CityViewModel,
    selectedDestination: StoreboxType,
    onTabPressed: ((StoreboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column {
        NavigationDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.profile_image_padding))
        )
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.storeboxType,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = {
                    viewModel.updateCurrentCategory(navItem.category)
                    onTabPressed(navItem.storeboxType)
                }
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "test")
    }
}

private data class NavigationItemContent(
    val storeboxType: StoreboxType,
    val icon: ImageVector,
    val text: String,
    val category: Int
)