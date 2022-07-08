package com.dlight.mynotes.feature_note.domain.util

//class to define the sort type
sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
