package com.natasamisic.mymemos.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.natasamisic.mymemos.R


val fonts = FontFamily(
    Font(R.font.josefinsans_light, FontWeight.Light),
    Font(R.font.josefinsans_semibold, FontWeight.SemiBold),
    Font(R.font.josefinsans_regular, FontWeight.Normal),
    Font(R.font.josefinsans_bold, FontWeight.Bold)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 90.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        color = Color.White,
        fontSize = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        color = Color.Black,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        color = Color.Black,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
    ),
)