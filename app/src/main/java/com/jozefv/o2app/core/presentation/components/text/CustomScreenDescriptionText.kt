package com.jozefv.o2app.core.presentation.components.text

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.o2app.core.presentation.components.CustomSpaces
import com.jozefv.o2app.core.presentation.theme.O2AppTheme


@Composable
fun CustomScreenDescriptionText(
    modifier: Modifier = Modifier,
    defaultPaddingValues: PaddingValues = PaddingValues(bottom = CustomSpaces.L),
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
    text: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultPaddingValues)
            .then(modifier),
        style = textStyle,
        text = text
    )
}

@Preview
@Composable
private fun CustomScreenDescriptionTextPreview() {
    O2AppTheme {
        CustomScreenDescriptionText(
            modifier = Modifier.fillMaxWidth(),
            text = "Example screen description"
        )
    }
}