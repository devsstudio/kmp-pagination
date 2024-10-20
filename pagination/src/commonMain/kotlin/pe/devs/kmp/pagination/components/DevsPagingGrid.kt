package pe.devs.kmp.pagination.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import pe.devs.kmp.pagination.pagination.generated.resources.Res
import pe.devs.kmp.pagination.pagination.generated.resources.exception_pagination_error
import pe.devs.kmp.pagination.pagination.generated.resources.labels_empty

@OptIn(ExperimentalResourceApi::class)
@Composable
fun <T : Any> DevsPagingGrid(
    columns: Int,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(5.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(5.dp),
    modifier: Modifier = Modifier.fillMaxSize(),
    data: LazyPagingItems<T>,
    progressColor: Color = MaterialTheme.colors.primary,
    content: @Composable (T, Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
    ) {
        items(data.itemCount) { index ->
            val item = data[index]
            item?.let { content(it, index) }
        }
        data.loadState.apply {
            when {
                refresh is LoadStateNotLoading && data.itemCount < 1 -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(1f).padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(Res.string.labels_empty),
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                refresh is LoadStateLoading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = progressColor,
                            )
                        }
                    }
                }

                append is LoadStateLoading -> {
                    item {
                        CircularProgressIndicator(
                            color = progressColor,
                            modifier = Modifier.fillMaxWidth(1f)
                                .padding(20.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                refresh is LoadStateError -> {
                    item {
                        DevsPagingErrorView(
                            message = stringResource(Res.string.exception_pagination_error),
                            onClickRetry = { data.retry() },
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                    }
                }

                append is LoadStateError -> {
                    item {
                        DevsPagingErrorItem(
                            message = stringResource(Res.string.exception_pagination_error),
                            onClickRetry = { data.retry() },
                        )
                    }
                }
            }
        }
    }
}