package com.james.haelia.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.james.haelia.data.ValuationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ValuationViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _valuations = MutableStateFlow<List<VehicleValuation>>(emptyList())
    val valuations: StateFlow<List<VehicleValuation>> = _valuations.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadValuations()
    }

    fun saveValuation(vehicleValuation: VehicleValuation, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                if (vehicleValuation.userId.isBlank()) {
                    android.util.Log.e("ValuationViewModel", "Cannot save valuation: userId is blank")
                    onComplete(false)
                    return@launch
                }

                // Validate required fields
                if (vehicleValuation.make.isBlank() || vehicleValuation.year.isBlank() || vehicleValuation.bodyType.isBlank()) {
                    android.util.Log.e("ValuationViewModel", "Required fields are missing")
                    onComplete(false)
                    return@launch
                }

                try {
                    db.collection("vehicle_valuations")
                        .document(vehicleValuation.id)
                        .set(vehicleValuation)
                        .await()

                    android.util.Log.d("ValuationViewModel", "Successfully saved valuation for ${vehicleValuation.make}")
                    loadValuations() // Refresh the list
                    onComplete(true)
                } catch (e: Exception) {
                    android.util.Log.e("ValuationViewModel", "Error saving valuation: ${e.message}")
                    e.printStackTrace()
                    onComplete(false)
                }
            } catch (e: Exception) {
                android.util.Log.e("ValuationViewModel", "Exception in saveValuation: ${e.message}")
                e.printStackTrace()
                onComplete(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadValuations() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val snapshot = db.collection("vehicle_valuations")
                    .orderBy("valuationDate")
                    .get()
                    .await()
                
                val list = snapshot.toObjects(VehicleValuation::class.java)
                _valuations.value = list
            } catch (e: Exception) {
                android.util.Log.e("ValuationViewModel", "Error loading valuations: ${e.message}")
                e.printStackTrace()
                _valuations.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteValuation(make: String, year: String, bodyType: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                // Validate input parameters
                if (make.isBlank() || year.isBlank() || bodyType.isBlank()) {
                    android.util.Log.e("ValuationViewModel", "Invalid parameters for deletion")
                    callback(false)
                    return@launch
                }

                val query = db.collection("vehicle_valuations")
                    .whereEqualTo("make", make)
                    .whereEqualTo("year", year)
                    .whereEqualTo("bodyType", bodyType)

                val querySnapshot = query.get().await()
                
                if (!querySnapshot.isEmpty) {
                    val docId = querySnapshot.documents[0].id
                    db.collection("vehicle_valuations")
                        .document(docId)
                        .delete()
                        .await()
                    
                    loadValuations() // Refresh after delete
                    callback(true)
                } else {
                    android.util.Log.d("ValuationViewModel", "No matching document found")
                    callback(false)
                }
            } catch (e: Exception) {
                android.util.Log.e("ValuationViewModel", "Exception in deleteValuation: ${e.message}")
                e.printStackTrace()
                callback(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
