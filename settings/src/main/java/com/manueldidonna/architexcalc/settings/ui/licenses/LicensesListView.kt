package com.manueldidonna.architexcalc.settings.ui.licenses

import android.view.View
import android.widget.LinearLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.architexcalc.core.extensions.find
import com.manueldidonna.architexcalc.core.extensions.iterateAndBuildModels
import com.manueldidonna.architexcalc.core.ui.FixedDividerItemDecoration
import com.manueldidonna.architexcalc.settings.R

data class OpenLicenseUrl(val url: String)

internal class LicensesListView(
    view: View,
    licenses: List<LicenseItem>
) : ArxInteractiveView<OpenLicenseUrl> by ArxSubjectView() {
    init {
        view.find<EpoxyRecyclerView>(R.id.licenses_recycler).apply {
            addItemDecoration(FixedDividerItemDecoration(context, LinearLayout.VERTICAL))
            iterateAndBuildModels(licenses) { index, item ->
                licenseEpoxyView {
                    id("license$index")
                    name(item.name)
                    type(item.type)
                    listener { _ ->
                        emitEvent(OpenLicenseUrl(item.url))
                    }
                }
            }
        }
    }
}