package com.manueldidonna.architexcalc.settings.ui.licenses

import android.view.View
import android.widget.LinearLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.manueldidonna.arch.view.ArxView
import com.manueldidonna.architexcalc.common.ui.extensions.find
import com.manueldidonna.architexcalc.common.ui.recycler.FixedDividerItemDecoration
import com.manueldidonna.architexcalc.settings.R

internal class LicensesListView(
    view: View,
    licenses: List<LicenseItem>
) : ArxView {
    init {
        view.find<EpoxyRecyclerView>(R.id.licenses_recycler).apply {
            addItemDecoration(FixedDividerItemDecoration(context, LinearLayout.VERTICAL))
            withModels {
                licenses.forEachIndexed { index, item ->
                    licenseEpoxyView {
                        id("license$index")
                        name(item.name)
                        type(item.type)
                    }
                }
            }

        }
    }
}