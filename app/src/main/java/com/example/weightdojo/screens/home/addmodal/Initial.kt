package com.example.weightdojo.screens.home.addmodal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.weightdojo.components.CustomDivider
import com.example.weightdojo.components.text.Heading
import com.example.weightdojo.components.text.TextDefault
import com.example.weightdojo.ui.Sizing

@Composable
fun Initial(setCurrentSubModal: (subModal: SubModals) -> Unit) {

    Column(
        modifier = Modifier
            .padding(horizontal = Sizing.paddings.medium)
            .clip(RoundedCornerShape(Sizing.cornerRounding))
            .background(MaterialTheme.colors.secondary)
    ) {
        Heading(
            text = "Add",
            modifier = Modifier.padding(horizontal = Sizing.paddings.medium)
        )
        CustomDivider(tinted = false)
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { setCurrentSubModal(SubModals.SetWeight) }) {
            TextDefault(
                text = "Weight",
                modifier = Modifier
                    .padding(Sizing.paddings.medium),
            )
        }
        CustomDivider()
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextDefault(
                text = "Calories",
                modifier = Modifier
                    .padding(Sizing.paddings.medium),
            )
        }
    }
}