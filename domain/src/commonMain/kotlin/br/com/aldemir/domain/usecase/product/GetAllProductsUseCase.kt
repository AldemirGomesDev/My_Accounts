package br.com.aldemir.domain.usecase.product

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.PostDomainModel
import br.com.aldemir.domain.model.ProductDomainModel
import br.com.aldemir.domain.repository.RemoteRepository

class GetAllProductsUseCase(
    private val remoteRepository: RemoteRepository
) : BaseUseCase<Unit, List<ProductDomainModel>> {
    override suspend fun execute(params: Unit): List<ProductDomainModel> {
        return remoteRepository.getProducts()
    }
}