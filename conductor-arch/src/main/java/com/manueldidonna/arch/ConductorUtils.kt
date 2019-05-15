package com.manueldidonna.arch

import com.bluelinelabs.conductor.Router

/**
 * This function fixes a strange behavior of conductor's router.
 *
 * If the router's backstack contains only one controller, it gets popped
 * and its view stays in layout causing a possible glitch/overlapping
 */
inline fun conductorHandleBack(router: Router, otherHandleBack: () -> Unit){
    if(router.backstackSize <= 1){
        if(router.backstack.last()?.controller()?.handleBack() == false)
            otherHandleBack()
    } else{
        router.handleBack()
    }
}