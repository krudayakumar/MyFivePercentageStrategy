package model

import java.text.ParseException
import java.util.*
import java.util.stream.Collectors

class Daily {
    var data : ArrayList<DailyDetails> = arrayListOf()
    @Throws(ParseException::class)
    fun between(start: Date, end: Date):List<DailyDetails> {
        return data.stream()
            .filter { dates -> dates.orgDate.after(start) && dates.orgDate.before(end) }
            .collect(Collectors.toList()).toList()

    }
}