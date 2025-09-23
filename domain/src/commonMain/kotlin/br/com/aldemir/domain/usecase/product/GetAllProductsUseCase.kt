package br.com.aldemir.domain.usecase.product

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.ProductDomainModel
import br.com.aldemir.domain.repository.RemoteRepository

class GetAllProductsUseCase(
    private val remoteRepository: RemoteRepository
) : UseCase<UseCase.None, List<ProductDomainModel>> {
    override suspend fun execute(params: UseCase.None): List<ProductDomainModel> {
        return remoteRepository.getProducts()
    }

    override fun dispatchErrorResult(throwable: Throwable) {
        super.dispatchErrorResult(throwable)
    }
}