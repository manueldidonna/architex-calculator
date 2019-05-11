package com.manueldidonna.architexcalc.settings.licenses

sealed class LicensesEvent {
    object GoBack : LicensesEvent()
    data class OpenLicenseUrl(val url: String): LicensesEvent()
}