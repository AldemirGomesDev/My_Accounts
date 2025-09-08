package br.com.aldemir.data.repository.post

import br.com.aldemir.data.remote.ApiService
import br.com.aldemir.domain.model.PostDomainModel
import br.com.aldemir.domain.model.ProductDomainModel
import br.com.aldemir.domain.model.RatingDomainModel
import br.com.aldemir.domain.repository.RemoteRepository

class RemoteRepositoryImpl(
    private val apiService: ApiService
) : RemoteRepository {

    override suspend fun getPosts(): List<PostDomainModel> {
        val postDTO = apiService.getPosts()
        return postDTO.map { post ->
            PostDomainModel(
                id = post.id,
                userId = post.userId,
                title = post.title,
                body = post.body
            )
        }
    }

    override suspend fun getProducts(): List<ProductDomainModel> {
        val productDTO = apiService.getProducts()
        return productDTO.map { product ->
            ProductDomainModel(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                category = product.category,
                image = product.image,
                rating = product.rating.let { rating ->
                    RatingDomainModel(
                        rate = rating.rate,
                        count = rating.count
                    )
                }
            )
        }
    }
}