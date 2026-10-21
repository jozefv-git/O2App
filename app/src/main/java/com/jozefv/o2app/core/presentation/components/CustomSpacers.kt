package com.jozefv.o2app.core.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object CustomSpaces {
    val XS = 4.dp
    val S = 8.dp
    val M = 16.dp
    val L = 24.dp
    val XL = 32.dp
}

@Composable
fun SpacerHorXS() {
    Spacer(modifier = Modifier.width(CustomSpaces.XS))
}

@Composable
fun SpacerHorS() {
    Spacer(modifier = Modifier.width(CustomSpaces.S))
}

@Composable
fun SpacerHorM() {
    Spacer(modifier = Modifier.width(CustomSpaces.M))
}

@Composable
fun SpacerHorL() {
    Spacer(modifier = Modifier.width(CustomSpaces.L))
}

@Composable
fun SpacerHorXL() {
    Spacer(modifier = Modifier.width(CustomSpaces.XL))
}

@Composable
fun SpacerVerXS() {
    Spacer(modifier = Modifier.height(CustomSpaces.XS))
}

@Composable
fun SpacerVerS() {
    Spacer(modifier = Modifier.height(CustomSpaces.S))
}

@Composable
fun SpacerVerM() {
    Spacer(modifier = Modifier.height(CustomSpaces.M))
}

@Composable
fun SpacerVerL() {
    Spacer(modifier = Modifier.height(CustomSpaces.L))
}

@Composable
fun SpacerVerXL() {
    Spacer(modifier = Modifier.height(CustomSpaces.XL))
}