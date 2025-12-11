package com.example.jogjatourismapp

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.parcelize.Parcelize
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale
@Parcelize
data class PlannedVisit(
    val visitId: Long = System.currentTimeMillis(),
    val destinationId: Int,
    val date: String,
    val startTime: String, // Diubah dari 'time'
    val endTime: String,   // Diubah dari 'estimatedDuration'
    val personCount: Int,
    val notes: String
) : Parcelable

object PlanningViewModel {
    val plannedVisits: SnapshotStateList<PlannedVisit> = mutableStateListOf()
    var shouldOpenWishlist: Boolean by mutableStateOf(false)

    fun addPlan(plan: PlannedVisit) {
        if (plannedVisits.none { it.visitId == plan.visitId }) {
            plannedVisits.add(plan)
        }
    }
    fun removePlan(plan: PlannedVisit) {
        plannedVisits.remove(plan)
    }

    fun getPlanById(visitId: Long): PlannedVisit? {
        return plannedVisits.find { it.visitId == visitId }
    }

    fun updatePlan(updatedPlan: PlannedVisit) {
        val index = plannedVisits.indexOfFirst { it.visitId == updatedPlan.visitId }
        if (index != -1) {
            plannedVisits[index] = updatedPlan
        }
    }

    fun getDestinationForPlan(plan: PlannedVisit): Destination? {
        return getDestinationById(plan.destinationId)
    }

    // --- FUNGSI BARU: Cek Jadwal Bentrok ---
    // Update fungsi cek bentrok agar bisa mengecualikan ID sendiri saat Edit
    @SuppressLint("SimpleDateFormat")
    fun hasTimeConflict(newDate: String, newStartTime: String, newEndTime: String, excludeVisitId: Long = -1): Boolean {
        val dateFormat = SimpleDateFormat("d/M/yyyy HH:mm", Locale.getDefault())
        try {
            val newStartTs = dateFormat.parse("$newDate $newStartTime")?.time ?: return false
            val newEndTs = dateFormat.parse("$newDate $newEndTime")?.time ?: return false

            for (plan in plannedVisits) {
                // Lewati pengecekan jika ini adalah rencana yang sedang diedit
                if (plan.visitId == excludeVisitId) continue

                val existingStartTs = dateFormat.parse("${plan.date} ${plan.startTime}")?.time ?: continue
                val existingEndTs = dateFormat.parse("${plan.date} ${plan.endTime}")?.time ?: continue

                if (newStartTs < existingEndTs && newEndTs > existingStartTs) {
                    return true
                }
            }
        } catch (e: Exception) { e.printStackTrace() }
        return false
    }
}