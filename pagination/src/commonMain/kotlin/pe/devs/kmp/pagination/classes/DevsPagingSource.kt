package pe.devs.kmp.pagination.classes

import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import pe.devs.kmp.pagination.dto.responses.PaginatedResponseV1

abstract class DevsPagingSourceV1<T : Any> : PagingSource<String, T>() {

    protected abstract suspend fun fetchData(from: String?, limit: Int): PaginatedResponseV1<T>

    override fun getRefreshKey(state: PagingState<String, T>): String? = null

    override suspend fun load(params: PagingSourceLoadParams<String>): PagingSourceLoadResult<String, T> {
        val from = params.key
        val limit = params.loadSize
        return try {
            val response = fetchData(from, limit)
            PagingSourceLoadResultPage(
                data = response.items,
                prevKey = response.prev,
                nextKey =  response.next
            ) as PagingSourceLoadResult<String, T>
        } catch (e: Exception) {
            PagingSourceLoadResultError<String, T>(e) as PagingSourceLoadResult<String, T>
        }
    }
}