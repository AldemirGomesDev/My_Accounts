package br.com.aldemir.common.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class MaskCurrencyVisualTransformation(private val currencySymbol: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val originalText = text.text

        val formattedText: String = if (originalText.isNotEmpty()) {
            originalText.fromCurrency().toCurrency(currencySymbol)
        } else {
            emptyString()
        }

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return formattedText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return originalText.length
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}