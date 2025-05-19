//package com.james.haelia.model
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//import com.google.firebase.firestore.FirebaseFirestore
//
//class ValuationViewModel : ViewModel() {
//    private val db = FirebaseFirestore.getInstance()
//    private val collectionRef = db.collection("valuations")
//
//    // Public list you can observe from Compose
//    val valuations = mutableStateListOf<VehicleValuation>()
//
//    fun saveValuation(valuation: VehicleValuation, callback: (Boolean) -> Unit) {
//        collectionRef.document(valuation.id)
//            .set(valuation)
//            .addOnSuccessListener { callback(true) }
//            .addOnFailureListener { callback(false) }
//    }
//
//
//    // Load all valuations for the user
//    fun loadValuations(userId: String = "user123") {
//        collectionRef
//            .whereEqualTo("userId", userId)
//            .get()
//            .addOnSuccessListener { result ->
//                valuations.clear()
//                for (doc in result.documents) {
//                    val valuation = doc.toObject(VehicleValuation::class.java)
//                    valuation?.let { valuations.add(it) }
//                }
//            }
//            .addOnFailureListener {
//                // Handle failure (e.g., show error toast in UI)
//            }
//    }
//    fun deleteValuation(make: String, year: String, bodyType: String, callback: (Boolean) -> Unit) {
//        collectionRef
//            .whereEqualTo("make", make)
//            .whereEqualTo("year", year)
//            .whereEqualTo("bodyType", bodyType)
//            .whereEqualTo("userId", "user123") // Optional: restrict to specific user
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                if (!querySnapshot.isEmpty) {
//                    val docId = querySnapshot.documents[0].id
//                    collectionRef.document(docId)
//                        .delete()
//                        .addOnSuccessListener { callback(true) }
//                        .addOnFailureListener { callback(false) }
//                } else {
//                    callback(false) // No matching document found
//                }
//            }
//            .addOnFailureListener {
//                callback(false) // Query failed
//            }
//    }
//}
//
package com.james.haelia.model

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.james.haelia.data.ValuationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ValuationViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _valuations = MutableStateFlow<List<VehicleValuation>>(emptyList())
    val valuations: StateFlow<List<VehicleValuation>> = _valuations.asStateFlow()

    fun saveValuation(vehicleValuation: VehicleValuation, onComplete: (Boolean) -> Unit) {
        db.collection("vehicle_valuations")
            .document(vehicleValuation.id)
            .set(vehicleValuation)
            .addOnSuccessListener {
                loadValuations() // Refresh the list
                onComplete(true)
            }
            .addOnFailureListener { onComplete(false) }
    }

    fun loadValuations() {
        db.collection("vehicle_valuations")
            .orderBy("valuationDate")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.toObjects(VehicleValuation::class.java)
                _valuations.value = list
            }
            .addOnFailureListener {
                _valuations.value = emptyList()
            }
    }

    fun deleteValuation(make: String, year: String, bodyType: String, callback: (Boolean) -> Unit) {
        db.collection("vehicle_valuations")
            .whereEqualTo("make", make)
            .whereEqualTo("year", year)
            .whereEqualTo("bodyType", bodyType)
            .whereEqualTo("userId", "user123")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val docId = querySnapshot.documents[0].id
                    db.collection("vehicle_valuations").document(docId)
                        .delete()
                        .addOnSuccessListener {
                            loadValuations() // Refresh after delete
                            callback(true)
                        }
                        .addOnFailureListener { callback(false) }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { callback(false) }
    }
}
