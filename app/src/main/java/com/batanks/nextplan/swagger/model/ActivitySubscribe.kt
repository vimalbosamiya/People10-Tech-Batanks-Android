import com.batanks.nextplan.swagger.model.Place
import com.batanks.nextplan.swagger.model.UpdatedUser

data class ActivitySubscribe(
        /*val id: Int,
        val place: Place,
        val price: String,
        val title: String,
        val detail: String,
        val date: String,
        val max_participants: Int,
        val price_currency: String,
        val perPerson: Boolean,
        val duration: Long,
        val participants: MutableList<Int>*/

        val id : Int,
        val place : Place,
        val price : Double,
        val participants : ArrayList<UpdatedUser>,
        val title : String,
        val detail : String,
        val date : String,
        val max_participants : Int,
        val price_currency : String,
        val per_person : Boolean,
        val duration : Int
)