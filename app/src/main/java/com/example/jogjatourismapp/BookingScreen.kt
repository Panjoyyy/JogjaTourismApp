package com.example.jogjatourismapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    destinationId: Int,
    visitIdToEdit: Long = -1L, // Parameter untuk Mode Edit (Default -1 artinya Mode Baru)
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    val destination = remember(destinationId) { getDestinationById(destinationId) }
    val context = LocalContext.current

    // State input
    var selectedDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") } // Jam Mulai
    var endTime by remember { mutableStateOf("") }   // Jam Selesai
    var personCount by remember { mutableStateOf(1) }

    // --- LOGIC 1: Load Data Lama Jika Mode Edit ---
    LaunchedEffect(visitIdToEdit) {
        if (visitIdToEdit != -1L) {
            val existingPlan = PlanningViewModel.getPlanById(visitIdToEdit)
            existingPlan?.let {
                selectedDate = it.date
                startTime = it.startTime
                endTime = it.endTime
                personCount = it.personCount
            }
        }
    }

    // Validasi form: Pastikan tanggal, jam mulai, dan jam selesai terisi
    val isFormValid by remember {
        derivedStateOf {
            selectedDate.isNotBlank() && startTime.isNotBlank() && endTime.isNotBlank()
        }
    }

    // --- Setup Calendar & Dialogs ---
    val calendar = Calendar.getInstance()

    // Dialog Tanggal
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year, month, day -> selectedDate = "$day/${month + 1}/$year" },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Dialog Jam Mulai
    val startTimePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute -> startTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )

    // Dialog Jam Selesai
    val endTimePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute -> endTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY) + 2, // Default 2 jam setelahnya
        calendar.get(Calendar.MINUTE), true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                // Ubah Judul Berdasarkan Mode
                title = { Text(if (visitIdToEdit != -1L) "Edit Rencana" else "Rencanakan Kunjungan") },
                navigationIcon = { IconButton(onClick = onBackClicked) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                } }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            destination?.let { dest ->
                Text("Lokasi:", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = dest.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(24.dp))

                // --- 1. Input Tanggal ---
                OutlinedButton(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (selectedDate.isNotBlank()) "Tanggal: $selectedDate" else "Pilih Tanggal Kunjungan")
                }

                Spacer(Modifier.height(16.dp))

                // --- 2. Input Jam Mulai & Selesai ---
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Tombol Jam Mulai
                    OutlinedButton(
                        onClick = { startTimePickerDialog.show() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Schedule, contentDescription = null)
                            Text("Mulai")
                            Text(
                                text = if (startTime.isNotBlank()) startTime else "--:--",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Tombol Jam Selesai
                    OutlinedButton(
                        onClick = { endTimePickerDialog.show() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.TimerOff, contentDescription = null)
                            Text("Selesai")
                            Text(
                                text = if (endTime.isNotBlank()) endTime else "--:--",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // --- 3. Input Jumlah Orang ---
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Jumlah Orang", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    IconButton(onClick = { if (personCount > 1) personCount-- }) {
                        Icon(Icons.Default.RemoveCircleOutline, "Kurangi")
                    }
                    Text("$personCount", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { personCount++ }) {
                        Icon(Icons.Default.AddCircleOutline, "Tambah")
                    }
                }
                Spacer(Modifier.height(24.dp))

                // --- 4. Tampilan Estimasi Biaya ---
                if (dest.price > 0) {
                    val totalCost = dest.price * personCount
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Total Biaya Tiket", style = MaterialTheme.typography.labelLarge)
                            Text(
                                text = "Rp ${formatPrice(totalCost)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = "(${formatPrice(dest.price)} x $personCount orang)",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                } else {
                    Text(
                        "Destinasi ini Gratis",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // --- Tombol Simpan ---
            Button(
                onClick = {
                    // Validasi 1: Jam Selesai > Jam Mulai
                    if (startTime >= endTime) {
                        Toast.makeText(context, "Jam selesai harus setelah jam mulai!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Validasi 2: Cek Konflik (Kirim visitIdToEdit agar tidak bentrok dengan diri sendiri)
                    val isConflict = PlanningViewModel.hasTimeConflict(
                        newDate = selectedDate,
                        newStartTime = startTime,
                        newEndTime = endTime,
                        excludeVisitId = visitIdToEdit
                    )

                    if (isConflict) {
                        Toast.makeText(context, "Jadwal bentrok dengan rencana lain!", Toast.LENGTH_LONG).show()
                    } else {
                        // --- LOGIC SIMPAN ---
                        if (visitIdToEdit != -1L) {
                            // MODE UPDATE: Gunakan ID lama
                            val updatedPlan = PlannedVisit(
                                visitId = visitIdToEdit,
                                destinationId = destinationId,
                                date = selectedDate,
                                startTime = startTime,
                                endTime = endTime,
                                personCount = personCount,
                                notes = ""
                            )
                            PlanningViewModel.updatePlan(updatedPlan)
                            Toast.makeText(context, "Rencana berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                        } else {
                            // MODE BARU: Buat baru
                            val newPlan = PlannedVisit(
                                destinationId = destinationId,
                                date = selectedDate,
                                startTime = startTime,
                                endTime = endTime,
                                personCount = personCount,
                                notes = ""
                            )
                            PlanningViewModel.addPlan(newPlan)
                            Toast.makeText(context, "Rencana berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        }
                        onSaveClicked()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = isFormValid
            ) {
                // Teks Tombol Berubah Sesuai Mode
                Text(if (visitIdToEdit != -1L) "Simpan Perubahan" else "Simpan Rencana")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}