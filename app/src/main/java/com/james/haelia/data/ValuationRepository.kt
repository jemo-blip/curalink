package com.james.haelia.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.james.haelia.model.VehicleValuation

class ValuationRepository( ) {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("valuations")
    private val auth = FirebaseAuth.getInstance()

    // Save vehicle valuation to Firebase
    fun saveValuation(vehicleValuation: VehicleValuation, onComplete: (Boolean) -> Unit) {
        val valuationId = databaseReference.push().key ?: return

        val valuationToSave = vehicleValuation.copy(
            id = valuationId,
            userId = auth.currentUser?.uid ?: "",
            valuationDate = System.currentTimeMillis().toString()
        )

        databaseReference.child(valuationId).setValue(valuationToSave)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    // Retrieve valuation history for the user from Firebase
    fun getUserValuationHistory(userId: String, onComplete: (List<VehicleValuation>) -> Unit) {
        databaseReference.orderByChild("userId").equalTo(userId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val valuations = mutableListOf<VehicleValuation>()
                    for (snapshot in task.result.children) {
                        val valuation = snapshot.getValue(VehicleValuation::class.java)
                        valuation?.let { valuations.add(it) }
                    }
                    onComplete(valuations)
                } else {
                    onComplete(emptyList())
                }
            }
    }

    // Delete vehicle valuation from Firebase by ID
    fun deleteValuation(valuationId: String, onComplete: (Boolean) -> Unit) {
        databaseReference.child(valuationId).removeValue()
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }
}
