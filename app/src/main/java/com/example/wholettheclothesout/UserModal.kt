package com.example.wholettheclothesout

class UserModal() {
    private var machineName = String()
    private var availability = String()
    private var countTime =  String()
    private var gracePeriod =  String()

    init {
        this.machineName = machineName
        this.availability = availability
        this.countTime = countTime
        this.gracePeriod = gracePeriod
    }
    override fun toString(): String {
        return "$machineName"    }

    val getMachineName: String
        get() = machineName

    val getAvailability: String
        get() = availability

    val getCountTime: String
        get() = countTime

    val getGracePeriod: String
        get() = gracePeriod

    fun setMachineName(machineName:String) {
        this.machineName = machineName
    }

    fun setAvailability(availability:String) {
        this.availability = availability
    }

    fun setCountTime(countTime:String) {
        this.countTime = countTime
    }

    fun setGracePeriod(gracePeriod:String) {
        this.gracePeriod = gracePeriod
    }

}