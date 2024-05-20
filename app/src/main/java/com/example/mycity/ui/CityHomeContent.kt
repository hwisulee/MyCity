package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.local.LocalDataProvider
import com.example.mycity.model.StoreInfo
import com.example.mycity.ui.theme.MyCityTheme

@Composable
fun CityListAndDetailContent(
    cityUiState: CityUiState,
    onItemCardPressed: (StoreInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val lists = cityUiState.currentStore
    Row(
        modifier = modifier
    ) {
        CategoryDetailList(
            stores = lists,
            onClicked = onItemCardPressed,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
        ListItemDetail(
            selectedStoreInfo = cityUiState.currentStore[cityUiState.currentIndex],
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CategoryListItem(
    store: StoreInfo,
    itemSize: Int,
    onClicked: (StoreInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val category = when (store.categoryId) {
        1 -> "Cafe"
        2 -> "Restaurant"
        3 -> "Shopping Mall"
        else -> "Null"
    }

    val description = when (store.categoryId) {
        1 -> stringResource(R.string.description01)
        2 -> stringResource(R.string.description02)
        3 -> stringResource(R.string.description03)
        else -> "Null"
    }

    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.padding_small)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onClicked(store) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            CategoryListImageItem(
                store = store,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$itemSize items",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun CategoryListImageItem(
    store: StoreInfo,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(store.image),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .background(Color.Gray, CircleShape)
        )
    }
}

@Composable
fun CategoryList(
    stores: List<StoreInfo>,
    onClicked: (StoreInfo) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(
            vertical = dimensionResource(R.dimen.padding_medium),
            horizontal = dimensionResource(R.dimen.padding_small)
        )
    ) {
        items(stores, key = { store -> store.categoryId }) { store ->
            CategoryListItem(
                store = store,
                itemSize = stores.size,
                onClicked = onClicked
            )
        }
    }
}

@Composable
fun CategoryDetailList(
    stores: List<StoreInfo>,
    onClicked: (StoreInfo) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(
            vertical = dimensionResource(R.dimen.padding_medium),
            horizontal = dimensionResource(R.dimen.padding_small)
        )
    ) {
        items(stores, key = { store -> store.id }) { store ->
            CategoryDetailListItem(
                store = store,
                onClicked = onClicked
            )
        }
    }
}

@Composable
fun CategoryDetailListItem(
    store: StoreInfo,
    onClicked: (StoreInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onClicked(store) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            CategoryListImageItem(
                store = store,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(store.name),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                Text(
                    text = stringResource(store.address),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(store.contact),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ListItemDetail(
    selectedStoreInfo: StoreInfo,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box(
                modifier = Modifier.background(Color.Gray)
            ) {
                Box(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(selectedStoreInfo.image),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedStoreInfo.name),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.padding(
                            vertical = dimensionResource(R.dimen.padding_small),
                            horizontal = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }
            }
            Text(
                text = stringResource(R.string.detail),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    MyCityTheme {
        Surface {
            CategoryListItem(
                store = LocalDataProvider.defaultCafe,
                LocalDataProvider.getCafeData().size,
                onClicked = {}
            )
        }
    }
}

@Preview
@Composable
fun ListPreview() {
    MyCityTheme {
        Surface {
            CategoryList(
                stores = LocalDataProvider.getCategoryData(),
                onClicked = {}
            )
        }
    }
}

@Preview
@Composable
fun DetailListPreview() {
    MyCityTheme {
        Surface {
            CategoryDetailList(
                stores = LocalDataProvider.getCafeData(),
                onClicked = {}
            )
        }
    }
}

@Preview
@Composable
fun ListItemDetailPreview() {
    MyCityTheme {
        Surface {
            ListItemDetail(
                selectedStoreInfo = LocalDataProvider.defaultCafe,
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}

@Preview
@Composable
fun ListAndDetailPreview() {
    MyCityTheme {
        Surface {
            CityListAndDetailContent(
                cityUiState = CityUiState(),
                onItemCardPressed = {}
            )
        }
    }
}