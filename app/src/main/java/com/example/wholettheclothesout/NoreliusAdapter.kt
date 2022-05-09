import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wholettheclothesout.R
import com.example.wholettheclothesout.UserModal
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "NoreliusAdapter"

class NoreliusAdapter(
    private val mList: List<UserModal>,
    private val listener: OnItemClickListener) : RecyclerView.Adapter<NoreliusAdapter.ViewHolder>() {
    private lateinit var database: DatabaseReference

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.building_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.machineName.text = itemsViewModel.getMachineName
        holder.availability.text = itemsViewModel.getAvailability
        holder.countTimer.setText(itemsViewModel.getCountTime)
        holder.availability.isEnabled = holder.availability.text=="Start"

//        holder.gracePeriod.text = itemsViewModel.getGracePeriod
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val machineName: TextView = itemView.findViewById(R.id.machine_name)
        val availability: Button = itemView.findViewById(R.id.availability)
        val countTimer: EditText = itemView.findViewById(R.id.countTime)

        //        val countTime: EditText = itemView.findViewById(R.id.countTime)
//        val gracePeriod: TextView = itemView.findViewById(R.id.gracePeriod)
        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position!= RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

        init{
            itemView.setOnClickListener(this)

            availability.setOnClickListener {
                Log.d(TAG, "${availability.text}")


                fun setInUse(machine: String, status: String, time:String = "0"){
                    database = Firebase.database.reference
                    database.child("Dorms").child("Norelius").child(machine).child("Availability").setValue(status)
                    database.child("Dorms").child("Norelius").child(machine).child("Timer").setValue(time)

                }

                val available = availability.text

                if (available == "Start"){
                    availability.text = "In-Use"
                    availability.isEnabled = false
                    setInUse(machineName.text as String,"In-Use")
                    val timer = object: CountDownTimer(20000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            Log.d(TAG, "seconds remaining: " + millisUntilFinished / 1000)
                            countTimer.setText("${millisUntilFinished / 1000}")
                            setInUse(machineName.text as String, "In-Use", "${millisUntilFinished / 1000}")
                            Log.d(TAG, "${countTimer.text}")
                        }

                        override fun onFinish() {
                            Log.d(TAG, "Time's finished!")
                            availability.text = "Start"
                            availability.isEnabled = true
                            setInUse(machineName.text as String, "Start", "20")
                        }
                    }
                    timer.start()
                }else{
                    availability.text = "Start"
                    setInUse(machineName.text as String, "Start")
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {

        }
    }

}