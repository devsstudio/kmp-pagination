package pe.devs.kmp.pagination.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponseV1<T>(
    val items: List<T>,
    val prev: String? = null,
    val next: String? = null
)
