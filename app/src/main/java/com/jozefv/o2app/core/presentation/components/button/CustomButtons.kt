package com.jozefv.o2app.core.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.o2app.R
import com.jozefv.o2app.core.presentation.components.CustomCircularProgressIndicator
import com.jozefv.o2app.core.presentation.components.SpacerHorS
import com.jozefv.o2app.core.presentation.model.CustomIconPainter
import com.jozefv.o2app.core.presentation.theme.O2AppTheme
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

@Composable
fun CustomPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    leadingIcon: CustomIconPainter? = null,
    isLoading: Boolean = false,
    enabled: Boolean = !isLoading,
    rapidClickLimit: Duration? = 1.seconds,
) {
    var clickedAt by remember { mutableStateOf(Instant.DISTANT_PAST) }
    Button(
        modifier = modifier,
        colors = colors,
        shape = shape,
        enabled = enabled,
        onClick = {
            rapidClickLimit?.let { rapidClickLimit ->
                if (clickedAt.plus(rapidClickLimit) < Clock.System.now()) {
                    onClick()
                }
            } ?: onClick()
            clickedAt = Clock.System.now()
        },
    ) {
        if (isLoading) {
            CustomCircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = if (enabled) colors.contentColor else colors.disabledContentColor
            )
            SpacerHorS()
        } else {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = it.iconPainter,
                    contentDescription = it.iconDescription
                )
                SpacerHorS()
            }
        }
        Text(text, style = textStyle)
    }
}

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    leadingIcon: CustomIconPainter? = null,
    isLoading: Boolean = false,
    enabled: Boolean = !isLoading,
    rapidClickLimit: Duration? = 1.seconds,
    border: BorderStroke =
        BorderStroke(
            width = 1.dp,
            color = if (enabled) colors.contentColor else colors.disabledContentColor
        ),
) {
    var clickedAt by remember { mutableStateOf(Instant.DISTANT_PAST) }
    OutlinedButton(
        modifier = modifier,
        colors = colors,
        shape = shape,
        border = border,
        enabled = enabled,
        onClick = {
            rapidClickLimit?.let { rapidClickLimit ->
                if (clickedAt.plus(rapidClickLimit) < Clock.System.now()) {
                    onClick()
                }
            } ?: onClick()
            clickedAt = Clock.System.now()
        },
    ) {
        if (isLoading) {
            CustomCircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = if (enabled) colors.contentColor else colors.disabledContentColor
            )
            SpacerHorS()
        } else {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = it.iconPainter,
                    contentDescription = it.iconDescription
                )
                SpacerHorS()
            }
        }
        Text(text, style = textStyle)
    }
}

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    isLoading: Boolean = false,
    enabled: Boolean = !isLoading,
    rapidClickLimit: Duration? = 1.seconds,
    text: String,
    onClick: () -> Unit
) {
    var clickedAt by remember { mutableStateOf(Instant.DISTANT_PAST) }
    TextButton(
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        onClick = {
            rapidClickLimit?.let { rapidClickLimit ->
                if (clickedAt.plus(rapidClickLimit) < Clock.System.now()) {
                    onClick()
                }
            } ?: onClick()
            clickedAt = Clock.System.now()
        }
    ) {
        if (isLoading) {
            CustomCircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = if (enabled) colors.contentColor else colors.disabledContentColor
            )
            SpacerHorS()
        }
        Text(text = text, style = textStyle)
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomPrimaryButton(text = "Forward", onClick = {})
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonIconLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomPrimaryButton(
            text = "Forward",
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonLoadingLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomPrimaryButton(
            text = "Forward",
            isLoading = true,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonDisabledLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomPrimaryButton(
            text = "Forward",
            enabled = false,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomTextButtonLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomTextButton (
            text = "Forward",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomTextButtonLoadingLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomTextButton (
            text = "Forward",
            isLoading = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomTextButtonDisabledLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomTextButton (
            text = "Forward",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomOutlinedButton(text = "Forward", onClick = {})
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonIconLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomOutlinedButton(
            text = "Forward",
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonLoadingLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomOutlinedButton(
            text = "Forward",
            isLoading = true,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonDisabledLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        CustomOutlinedButton(
            text = "Forward",
            enabled = false,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomPrimaryButton(text = "Forward", onClick = {})
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonIconDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomPrimaryButton(
            text = "Forward",
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonLoadingDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomPrimaryButton(
            text = "Forward",
            isLoading = true,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomPrimaryButtonDisabledDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomPrimaryButton(
            text = "Forward",
            enabled = false,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomOutlinedButton(text = "Forward", onClick = {})
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonIconDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomOutlinedButton(
            text = "Forward",
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonLoadingDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomOutlinedButton(
            text = "Forward",
            isLoading = true,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomOutlinedButtonDisabledDarkPreview() {
    O2AppTheme(darkTheme = true) {
        CustomOutlinedButton(
            text = "Forward",
            enabled = false,
            leadingIcon = CustomIconPainter(
                iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                iconDescription = "Forward"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomTextButtonDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomTextButton (
            text = "Forward",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomTextButtonLoadingDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomTextButton (
            text = "Forward",
            isLoading = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CustomTextButtonDisabledDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        CustomTextButton (
            text = "Forward",
            enabled = false,
            onClick = {}
        )
    }
}