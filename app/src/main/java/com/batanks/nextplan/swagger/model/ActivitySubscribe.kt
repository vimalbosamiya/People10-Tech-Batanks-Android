import com.batanks.nextplan.swagger.model.Place

data class ActivitySubscribe(
        val id: Int,
        val place: Place,
        val price: String,
        val title: String,
        val detail: String,
        val date: String,
        val max_participants: Int,
        val price_currency: String,
        val perPerson: Boolean,
        val duration: Long,
        val participants: MutableList<Int>)