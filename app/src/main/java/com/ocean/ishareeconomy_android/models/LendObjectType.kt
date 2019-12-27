package com.ocean.ishareeconomy_android.models

/**
 * Part of *models*.
 *
 * LendObject is an object, serive or transportation mode that one user lends out and that other users can use
 * @property string the raw [String] value of the type
 */
enum class LendObjectType(value: String) {
    Tool("tool"), Service("service"), Transportation("transportation");

    private var string: String = value

    override fun toString(): String {
        return string
    }

    companion object {
        fun fromString(value: String): LendObjectType {
            return when(value) {
                Tool.toString() -> Tool
                Service.toString() -> Service
                Transportation.toString() -> Transportation
                else -> throw IllegalArgumentException("Not a valid type!")
            }
        }
    }
}