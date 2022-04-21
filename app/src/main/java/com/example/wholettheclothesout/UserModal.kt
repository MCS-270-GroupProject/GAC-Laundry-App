package layout

class UserModal() {
    private var machineName = String()
    private var availability = String()

    init {
        this.machineName = machineName
        this.availability = availability
    }

    val getMachineName: String
        get() = machineName

    val getAvailability: String
        get() = availability

    fun setMachineName(machineName:String) {
        this.machineName = machineName
    }

    fun setAvailability(availability:String) {
        this.availability = availability
    }


}