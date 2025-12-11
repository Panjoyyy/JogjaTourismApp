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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
    // Lambda ini sekarang hanya perlu memberi tahu bahwa simpan berhasil
    onSaveClicked: () -> Unit
) {
    val destination = remember(destinationId) { getDestinationById(destinationId) }
    val context = LocalContext.current

    // State untuk menyimpan semua input dari pengguna
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var personCount by remember { mutableStateOf(1) }
    var estimatedDuration by remember { mutableStateOf("") }

    // State untuk validasi form
    val isFormValid by remember {
        derivedStateOf {
            selectedDate.isNotBlank() && selectedTime.isNotBlank() && estimatedDuration.isNotBlank()
        }
    }

    // --- Pengaturan untuk Dialog Pemilih Tanggal & Waktu (Sama seperti sebelumnya) ---
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year, month, day -> selectedDate = "$day/${month + 1}/$year" },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute -> selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
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
                .verticalScroll(rememberScrollState()), // Membuat kolom bisa di-scroll
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

                // --- 1. Input Tanggal & Waktu ---
                OutlinedButton(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (selectedDate.isNotBlank()) "Tanggal: $selectedDate" else "Pilih Tanggal")
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = { timePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Schedule, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (selectedTime.isNotBlank()) "Jam: $selectedTime" else "Pilih Jam")
                }
                Spacer(Modifier.height(24.dp))

                // --- 2. Input Jumlah Orang ---
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

                // --- 3. Input Estimasi Durasi ---
                OutlinedTextField(
                    value = estimatedDuration,
                    onValueChange = { estimatedDuration = it },
                    label = { Text("Estimasi Durasi (Contoh: 2-3 jam)") },
                    leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))

                // --- 4. Tampilan Estimasi Biaya ---
                if (dest.price > 0) {
                    val totalCost = dest.price * personCount
                    Text("Estimasi Biaya Tiket", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Rp ${formatPrice(totalCost)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "(${formatPrice(dest.price)} x $personCount orang)",
                        style = MaterialTheme.typography.bodySmall
                    )
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
                        time = selectedTime,
                        personCount = personCount,
                        estimatedDuration = estimatedDuration,
                        notes = "" // Catatan bisa ditambahkan di masa depan
                    )
                    // Panggil fungsi addPlan dari ViewModel
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
