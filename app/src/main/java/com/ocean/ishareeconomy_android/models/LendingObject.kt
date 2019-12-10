package com.ocean.ishareeconomy_android.models

class LendingObject(
    id: String,
    name: String,
    description: String,
    type: String,
    owner: ObjectOwner,
    user: ObjectUser,
    waitingList: List<LendingObject>
)