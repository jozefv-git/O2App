package com.jozefv.o2app.feat_dashboard.presentation.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.o2app.R
import com.jozefv.o2app.core.presentation.components.CustomSpaces
import com.jozefv.o2app.core.presentation.theme.O2AppTheme


@Composable
fun CustomCodeCard(
    modifier: Modifier = Modifier,
    code: String
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.large.copy(
                        bottomStart = CornerSize(CustomSpaces.M),
                        bottomEnd = CornerSize(CustomSpaces.M),
                    )
                )
                .padding(CustomSpaces.L),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                style =
                    MaterialTheme.typography.displayMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    ),
                text = code
            )
        }
    }
}

@Preview
@Composable
private fun CustomCodeCardLightThemePreviewCustom() {
    O2AppTheme(darkTheme = false) {
        CustomCodeCard(
            modifier = Modifier.fillMaxWidth(),
            code = "Generate code"
        )
    }
}


@Preview
@Composable
private fun CustomCodeCardDarkThemePreviewCustom() {
    O2AppTheme(darkTheme = true) {
        CustomCodeCard(
            modifier = Modifier.fillMaxWidth(),
            code = "Generate code"
        )
    }
}