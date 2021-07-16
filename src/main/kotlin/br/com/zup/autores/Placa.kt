package br.com.zup.autores

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*


@MustBeDocumented
@Target(FIELD, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [PlacaValidator::class])
annotation class Placa(
    val message: String =  "Placa com formato Inválido"
)

@Singleton
class PlacaValidator: ConstraintValidator<Placa, String> {
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<Placa>,
        context: ConstraintValidatorContext
    ): Boolean {

        // por padrão nossa notação não precisar validar null apenas se houver, se seque o padrão
        if(value == null) return true

        // confere se o valor corresponde ao Regex esperado
        return value.matches("[A-Z]{3}[0-9][0-9A-Z][0-9]{2}".toRegex())
    }

}
