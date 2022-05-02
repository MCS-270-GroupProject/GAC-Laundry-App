import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wholettheclothesout.R
import com.example.wholettheclothesout.UserModal

class SohreAdapter(
    private val mList: List<UserModal>,
    private val listener: OnItemClickListener) : RecyclerView.Adapter<SohreAdapter.ViewHolder>() {

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
//        holder.countTime.setText(itemsViewModel.getCountTime)
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
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {

        }
    }

}