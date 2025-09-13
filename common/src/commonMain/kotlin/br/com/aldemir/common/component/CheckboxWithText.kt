package br.com.aldemir.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.theme.taskItemTextColor

@Composable
fun CheckboxWithText(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = text, color = MaterialTheme.colorScheme.taskItemTextColor)
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
            enabled = true,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.taskItemTextColor,
                uncheckedColor = MaterialTheme.colorScheme.taskItemTextColor
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewComponent() {
    CheckboxWithText(
        text = "Este valor já foi pago?",
        isChecked = false,
        onCheckedChange = {}
    )
}