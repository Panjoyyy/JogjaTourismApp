package com.example.jogjatourismapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
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
                title = { Text("Rencanakan Kunjungan") },
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

                // --- 2. Input Jam Mulai & Selesai (Side by Side) ---
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
                    val newPlan = PlannedVisit(
                        destinationId = destinationId,
                        date = selectedDate,
                        startTime = startTime, // Simpan jam mulai
                        endTime = endTime,     // Simpan jam selesai
                        personCount = personCount,
                        notes = ""
                    )
                    PlanningViewModel.addPlan(newPlan)
                    onSaveClicked()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = isFormValid
            ) {
                Text("Simpan Rencana")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}