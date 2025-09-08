package br.com.aldemir.domain.usecase.post

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.PostDomainModel
import br.com.aldemir.domain.repository.RemoteRepository

class GetAllPostsUseCase(
    private val remoteRepository: RemoteRepository
) : BaseUseCase<Unit, List<PostDomainModel>> {
    override suspend fun execute(params: Unit): List<PostDomainModel> {
        return remoteRepository.getPosts()
    }
}