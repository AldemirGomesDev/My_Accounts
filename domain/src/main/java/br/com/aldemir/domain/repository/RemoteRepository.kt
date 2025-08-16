package br.com.aldemir.domain.repository

import br.com.aldemir.domain.model.PostDomainModel
import br.com.aldemir.domain.model.ProductDomainModel

interface RemoteRepository {
    suspend fun getPosts(): List<PostDomainModel>
    suspend fun getProducts(): List<ProductDomainModel>
}