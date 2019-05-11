package com.manueldidonna.arch

import com.bluelinelabs.conductor.Router

inline fun conductorHandleBack(router: Router, otherHandleBack: () -> Unit){
    if(router.backstackSize <= 1){
        if(router.backstack.last()?.controller()?.handleBack() == false)
            otherHandleBack()
    } else{
        router.handleBack()
    }
}