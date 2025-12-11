package com.example.jogjatourismapp

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlannedVisit(
    val visitId: Long = System.currentTimeMillis(),
    val destinationId: Int,
    val date: String,
    val time: String,
    val personCount: Int,
    val estimatedDuration: String,
    val notes: String
) : Parcelable

object PlanningViewModel {
    val plannedVisits: SnapshotStateList<PlannedVisit> = mutableStateListOf()

    // Flag untuk menandakan apakah harus buka Wishlist setelah save
    var shouldOpenWishlist: Boolean by mutableStateOf(false)

    fun addPlan(plan: PlannedVisit) {
        if (plannedVisits.none { it.visitId == plan.visitId }) {
            plannedVisits.add(plan)
        }
    }

    fun getDestinationForPlan(plan: PlannedVisit): Destination? {
        return getDestinationById(plan.destinationId)
    }
}