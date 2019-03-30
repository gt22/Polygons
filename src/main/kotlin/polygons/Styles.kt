package polygons

import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass
import tornadofx.multi

class Styles : Stylesheet() {
    companion object {
        val primaryCanvas by cssclass()
    }

    init {
        primaryCanvas {
            backgroundColor = multi(c("#2C2F33"))
        }
    }


}