package com.example.mycity.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.model.StoreboxType
import com.example.mycity.ui.theme.MyCityTheme

@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val viewModel: CityViewModel = viewModel()
    val cityUiState = viewModel.uiState.collectAsState().value
    val navigationType: CityNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CityNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            CityNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            CityNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            CityNavigationType.BOTTOM_NAVIGATION
        }
    }
    CityHomeScreen(
        viewModel = viewModel,
        navigationType = navigationType,
        cityUiState = cityUiState,
        onTabPressed = { storeBoxType: StoreboxType ->
            viewModel.updateCurrentType(storeBoxType)
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CityAppPreview() {
    MyCityTheme {
        Surface {
            CityApp(WindowWidthSizeClass.Expanded)
        }
    }
}