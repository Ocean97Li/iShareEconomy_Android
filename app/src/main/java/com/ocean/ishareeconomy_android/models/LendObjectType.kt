package com.ocean.ishareeconomy_android.models

enum class LendObjectType {
    Tool("tool"), Service("service"), Transportation("transportation");

    private lateinit  var string: String

    constructor(value: String) {
        string = value
    }

    override fun toString(): String {
        return string
    }

    companion object {
        fun fromString(value: String): LendObjectType {
            var type = when(value) {
                Tool.toString() -> Tool
                Service.toString() -> Service
                Transportation.toString() -> Transportation
                else -> throw IllegalArgumentException("Not a valid type!")
            }
            return type
        }
    }
}