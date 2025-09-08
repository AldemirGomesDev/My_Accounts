package br.com.aldemir.data.mapper

import br.com.aldemir.data.database.model.RecipeDTO
import br.com.aldemir.data.database.model.RecipeMonthlyDTO
import br.com.aldemir.data.database.model.RecipePerMonthDTO
import br.com.aldemir.data.database.model.RecipeUpdateDTO
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.model.RecipeUpdateDomain

fun RecipeDomain.toDto() = RecipeDTO(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)

fun RecipeUpdateDomain.toDto() = RecipeUpdateDTO(
    id = id,
    name = name,
    description = description
)

@JvmName("RecipeDTOToDomain")
fun List<RecipeDTO>.toDomain(): List<RecipeDomain> {
    return this.map {
        RecipeDomain(
            id = it.id,
            name = it.name,
            description = it.description,
            created_at = it.created_at,
            due_date = it.due_date,
            status = it.status
        )
    }
}

fun RecipeMonthlyDomain.toDTO() = RecipeMonthlyDTO(
    id = id,
    id_recipe = id_recipe,
    year = year,
    month = month,
    value = value,
    status = status
)

fun RecipePerMonthDTO.toDomain() = RecipePerMonthDomain(
    id = id,
    id_recipe = id_recipe,
    name = name,
    description = description,
    due_date = due_date,
    year = year,
    month = month,
    value = value,
    status = status
)

@JvmName("RecipeMonthlyDTOToDomain")
fun List<RecipeMonthlyDTO>.toDomain(): List<RecipeMonthlyDomain> {
    return this.map {
        RecipeMonthlyDomain(
            id = it.id,
            id_recipe = it.id_recipe,
            year = it.year,
            month = it.month,
            value = it.value,
            status = it.status
        )
    }
}

@JvmName("RecipePerMonthDTOToDomain")
fun List<RecipePerMonthDTO>.toDomain(): List<RecipePerMonthDomain> {
    return this.map {
        RecipePerMonthDomain(
            id = it.id,
            id_recipe = it.id_recipe,
            name = it.name,
            description = it.description,
            due_date = it.due_date,
            year = it.year,
            month = it.month,
            value = it.value,
            status = it.status
        )
    }
}